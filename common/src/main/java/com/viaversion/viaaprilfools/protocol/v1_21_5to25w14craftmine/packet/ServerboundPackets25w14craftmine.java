/*
 * This file is part of ViaAprilFools - https://github.com/ViaVersion/ViaAprilFools
 * Copyright (C) 2021-2025 the original authors
 *                         - RK_01/RaphiMC
 *                         - FlorianMichael/EnZaXD <florian.michael07@gmail.com>
 * Copyright (C) 2023-2025 ViaVersion and contributors
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

public enum ServerboundPackets25w14craftmine implements ServerboundPacket25w14craftmine {

    ACCEPT_TELEPORTATION, // 0x00
    BLOCK_ENTITY_TAG_QUERY, // 0x01
    BUNDLE_ITEM_SELECTED, // 0x02
    CHANGE_DIFFICULTY, // 0x03
    CHAT_ACK, // 0x04
    CHAT_COMMAND, // 0x05
    CHAT_COMMAND_SIGNED, // 0x06
    CHAT, // 0x07
    CHAT_SESSION_UPDATE, // 0x08
    CHUNK_BATCH_RECEIVED, // 0x09
    CLIENT_COMMAND, // 0x0A
    CLIENT_TICK_END, // 0x0B
    PLAYER_DONATE_EXPERIENCE, // 0x0C
    CLIENT_INFORMATION, // 0x0D
    COMMAND_SUGGESTION, // 0x0E
    CONFIGURATION_ACKNOWLEDGED, // 0x0F
    CONTAINER_BUTTON_CLICK, // 0x10
    CONTAINER_CLICK, // 0x11
    CONTAINER_CLOSE, // 0x12
    CONTAINER_SLOT_STATE_CHANGED, // 0x13
    COOKIE_RESPONSE, // 0x14
    CUSTOM_PAYLOAD, // 0x15
    DEBUG_SAMPLE_SUBSCRIPTION, // 0x16
    EDIT_BOOK, // 0x17
    ENTITY_TAG_QUERY, // 0x18
    INTERACT, // 0x19
    JIGSAW_GENERATE, // 0x1A
    KEEP_ALIVE, // 0x1B
    LOCK_DIFFICULTY, // 0x1C
    MOVE_PLAYER_POS, // 0x1D
    MOVE_PLAYER_POS_ROT, // 0x1E
    MOVE_PLAYER_ROT, // 0x1F
    MOVE_PLAYER_STATUS_ONLY, // 0x20
    MOVE_VEHICLE, // 0x21
    PADDLE_BOAT, // 0x22
    PICK_ITEM_FROM_BLOCK, // 0x23
    PICK_ITEM_FROM_ENTITY, // 0x24
    PING_REQUEST, // 0x25
    PLACE_RECIPE, // 0x26
    PLAYER_ABILITIES, // 0x27
    PLAYER_ACTION, // 0x28
    PLAYER_COMMAND, // 0x29
    PLAYER_INPUT, // 0x2A
    PLAYER_LOADED, // 0x2B
    PLAYER_BUY_UNLOCK, // 0x2C
    PLAYER_REACTIVATE_UNLOCK, // 0x2D
    PONG, // 0x2E
    RECIPE_BOOK_CHANGE_SETTINGS, // 0x2F
    RECIPE_BOOK_SEEN_RECIPE, // 0x30
    RENAME_ITEM, // 0x31
    RESOURCE_PACK, // 0x32
    SEEN_ADVANCEMENTS, // 0x33
    SELECT_TRADE, // 0x34
    SET_BEACON, // 0x35
    SET_CARRIED_ITEM, // 0x36
    SET_COMMAND_BLOCK, // 0x37
    SET_COMMAND_MINECART, // 0x38
    SET_CREATIVE_MODE_SLOT, // 0x39
    SET_JIGSAW_BLOCK, // 0x3A
    SET_STRUCTURE_BLOCK, // 0x3B
    SET_TEST_BLOCK, // 0x3C
    SIGN_UPDATE, // 0x3D
    SWING, // 0x3E
    TELEPORT_TO_ENTITY, // 0x3F
    TEST_INSTANCE_BLOCK_ACTION, // 0x40
    USE_ITEM_ON, // 0x41
    USE_ITEM; // 0x42

    @Override
    public int getId() {
        return ordinal();
    }

    @Override
    public String getName() {
        return name();
    }
}
