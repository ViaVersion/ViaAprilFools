/*
 * This file is part of ViaAprilFools - https://github.com/ViaVersion/ViaAprilFools
 * Copyright (C) 2021-2026 the original authors
 *                         - RK_01/RaphiMC
 *                         - Florian Reuth <git@florianreuth.de>
 * Copyright (C) 2023-2026 ViaVersion and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet;

import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPacket1_21;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ClientboundPacket1_21_5;
import com.viaversion.viaversion.protocols.v1_21to1_21_2.packet.ClientboundPacket1_21_2;

// Copied from VV to extend our clientbound packet interface
public enum ClientboundConfigurationPackets1_21 implements ClientboundPacket1_21, ClientboundPacket1_21_2, ClientboundPacket1_21_5, ClientboundPacket25w14craftmine {

    COOKIE_REQUEST, // 0x00
    CUSTOM_PAYLOAD, // 0x01
    DISCONNECT, // 0x02
    FINISH_CONFIGURATION, // 0x03
    KEEP_ALIVE, // 0x04
    PING, // 0x05
    RESET_CHAT, // 0x06
    REGISTRY_DATA, // 0x07
    RESOURCE_PACK_POP, // 0x08
    RESOURCE_PACK_PUSH, // 0x09
    STORE_COOKIE, // 0x0A
    TRANSFER, // 0x0B
    UPDATE_ENABLED_FEATURES, // 0x0C
    UPDATE_TAGS, // 0x0D
    SELECT_KNOWN_PACKS, // 0x0E
    CUSTOM_REPORT_DETAILS, // 0x0F
    SERVER_LINKS; // 0x10

    @Override
    public int getId() {
        return ordinal();
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public State state() {
        return State.CONFIGURATION;
    }
}
