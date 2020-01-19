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

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import red.m_squa.pircbotx.AddressedMessageEvent;

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * A ListenerAdapter which additionally supports sending an event when it is
 * addressed by another user on a channel.
 *
 * @author Molly Miller
 */
public abstract class AddressableListener extends ListenerAdapter {
    private final Pattern ADDRESS_REGEX = Pattern.compile("^[^,: /]+[,:]\\s+.*$");;

    @Override
    public void onEvent(Event e) throws Exception {
        super.onEvent(e);

        if (e instanceof MessageEvent) {
            checkAddressed((MessageEvent) e);
        }
    }

    private void checkAddressed(MessageEvent event) throws Exception {
        AddressedMessageEvent ame;
        String user, message;
        String[] split;
        Matcher m;


        m = ADDRESS_REGEX.matcher(event.getMessage());

        if (!m.matches()) {
            return;
        }

        split = event.getMessage().split("[:,]\\s+", 2);

        if (split.length != 2) {
            return;
        }

        user = split[0];
        message = split[1];

        if (!user.equals(event.getBot().getNick())) {
            return;
        }

        ame = new AddressedMessageEvent(event, message);

        onAddressed(ame);

        return;
    }

    public void onAddressed(AddressedMessageEvent event) throws Exception {
    }
}
