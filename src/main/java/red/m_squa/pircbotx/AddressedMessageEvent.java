/*
 * Copyright (C) 2019 Molly Miller.
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

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * A message which was sent to a user and a channel which is prefixed with the
 * bot's nick.
 *
 * @author Molly Miller
 */
public class AddressedMessageEvent extends MessageEvent {
    private String addressedMessage;

    public AddressedMessageEvent(MessageEvent me, String msg) {
        super(me.getBot(),
              me.getChannel(),
              me.getChannelSource(),
              me.getUserHostmask(),
              me.getUser(),
              me.getMessage(),
              me.getTags());

        this.addressedMessage = msg;
    }

    /**
     * Get the component of the message following the addressed prefix.
     * <p>
     * For example, if there is a bot on a channel with the nick
     * {@code foobar}, and another user sends a message to a common channel
     * of {@code foobar: here's a message}, then this method will return
     * the string "here's a message".
     *
     * @returns The remainder of the triggering message after the prefix.
     */
    public String getAddressedMessage() {
        return this.addressedMessage;
    }
}

