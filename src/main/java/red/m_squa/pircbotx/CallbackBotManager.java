/*
 * Copyright (C) 2019-2020 Molly Miller.
 *
 * This file is part of pircbotx-extras.
 * 
 * pircbotx-extras is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * pircbotx-extras is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with pircbotx-extras.  If not, see <https://www.gnu.org/licenses/>.
 */

package red.m_squa.pircbotx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import org.pircbotx.MultiBotManager;
import org.pircbotx.PircBotX;

/**
 * Manager class for coordinating multiple bots concurrently which supports
 * running a callback upon a bot's termination.
 *
 * Adapted from the MultiBotManager sources.
 *
 * @author Molly Miller
 */
public class CallbackBotManager extends MultiBotManager {
    private static final Logger log = LoggerFactory.getLogger(CallbackBotManager.class);

    /**
     * A callback which is executed when a bot exits without raising an
     * exception.
     *
     * @author Molly Miller
     */
    @FunctionalInterface
    public interface SuccessCallback {
        /**
         * Success condition handler.
         *
         * @param bot The (no longer connected) bot object.
         * @param result The (void) value returned by the bot.
         */
        public void success(PircBotX bot, Void result);
    }

    /**
     * A callback which is executed when a bot exits with an exception.
     *
     * @author Molly Miller
     */
    @FunctionalInterface
    public interface FailureCallback {
        /**
         * Failure condition handler.
         *
         * @param bot The bot which exited with an exception.
         * @param t The exception which was thrown by the bot.
         */
        public void failure(PircBotX bot, Throwable t);
    }

    private final FailureCallback onfail;
    private final SuccessCallback onsuccess;

    /**
     * A bot manager derived from the {@link MultiBotManager} class, which
     * additionally supports executing user-supplied callbacks when a bot
     * object exits.
     *
     * @param s The callback to be executed upon successful termination.
     * @param f The callback to be executed upon exceptional termination.
     */
    public CallbackBotManager(SuccessCallback s, FailureCallback f) {
        super();
        this.onfail = f;
        this.onsuccess = s;
    }

    @Override
    protected ListenableFuture<Void> startBot(final PircBotX bot) {
        Preconditions.checkNotNull(bot, "Bot cannot be null");
        ListenableFuture<Void> future = botPool.submit(new BotRunner(bot));

        synchronized (runningBotsLock) {
            runningBots.put(bot, future);
            runningBotsNumbers.put(bot, bot.getBotId());
        }

        Futures.addCallback(future, new TerminationCallback(bot),
                            MoreExecutors.directExecutor());
        return future;
    }

    private class TerminationCallback extends MultiBotManager.BotFutureCallback {
        public TerminationCallback(final PircBotX bot) {
            super(bot);
        }

        @Override
        public void onSuccess(Void result) {
            log.debug("Bot #" + bot.getBotId() + " finished");
            super.remove();
            CallbackBotManager.this.onsuccess.success(bot, result);
        }

        @Override
        public void onFailure(Throwable t) {
            log.error("Bot exited with Exception", t);
            super.remove();
            CallbackBotManager.this.onfail.failure(bot, t);
        }
    }
}
