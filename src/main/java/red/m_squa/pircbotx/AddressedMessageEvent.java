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

    public String getAddressedMessage() {
        return this.addressedMessage;
    }
}

