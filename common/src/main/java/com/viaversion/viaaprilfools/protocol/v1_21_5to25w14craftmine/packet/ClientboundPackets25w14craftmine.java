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

public enum ClientboundPackets25w14craftmine implements ClientboundPacket25w14craftmine {

    BUNDLE_DELIMITER, // 0x00
    ADD_ENTITY, // 0x01
    ANIMATE, // 0x02
    AWARD_STATS, // 0x03
    BLOCK_CHANGED_ACK, // 0x04
    BLOCK_DESTRUCTION, // 0x05
    BLOCK_ENTITY_DATA, // 0x06
    BLOCK_EVENT, // 0x07
    BLOCK_UPDATE, // 0x08
    BOSS_EVENT, // 0x09
    CHANGE_DIFFICULTY, // 0x0A
    CHUNK_BATCH_FINISHED, // 0x0B
    CHUNK_BATCH_START, // 0x0C
    CHUNKS_BIOMES, // 0x0D
    CLEAR_TITLES, // 0x0E
    COMMAND_SUGGESTIONS, // 0x0F
    COMMANDS, // 0x10
    CONTAINER_CLOSE, // 0x11
    CONTAINER_SET_CONTENT, // 0x12
    CONTAINER_SET_DATA, // 0x13
    CONTAINER_SET_SLOT, // 0x14
    COOKIE_REQUEST, // 0x15
    COOLDOWN, // 0x16
    CUSTOM_CHAT_COMPLETIONS, // 0x17
    CUSTOM_PAYLOAD, // 0x18
    DAMAGE_EVENT, // 0x19
    DEBUG_SAMPLE, // 0x1A
    DELETE_CHAT, // 0x1B
    DISCONNECT, // 0x1C
    DISGUISED_CHAT, // 0x1D
    ENTITY_EVENT, // 0x1E
    ENTITY_POSITION_SYNC, // 0x1F
    EXPLODE, // 0x20
    FORGET_LEVEL_CHUNK, // 0x21
    GAME_EVENT, // 0x22
    HORSE_SCREEN_OPEN, // 0x23
    HURT_ANIMATION, // 0x24
    INITIALIZE_BORDER, // 0x25
    KEEP_ALIVE, // 0x26
    LEVEL_CHUNK_WITH_LIGHT, // 0x27
    LEVEL_EVENT, // 0x28
    LEVEL_PARTICLES, // 0x29
    LIGHT_UPDATE, // 0x2A
    LOGIN, // 0x2B
    MAP_ITEM_DATA, // 0x2C
    MERCHANT_OFFERS, // 0x2D
    MOVE_ENTITY_POS, // 0x2E
    MOVE_ENTITY_POS_ROT, // 0x2F
    MOVE_MINECART_ALONG_TRACK, // 0x30
    MOVE_ENTITY_ROT, // 0x31
    MOVE_VEHICLE, // 0x32
    OPEN_BOOK, // 0x33
    OPEN_WINDOW, // 0x34
    UPDATE_SCREEN, // 0x35
    OPEN_SIGN_EDITOR, // 0x36
    PING, // 0x37
    PONG_RESPONSE, // 0x38
    PLACE_GHOST_RECIPE, // 0x39
    PLAYER_ABILITIES, // 0x3A
    PLAYER_CHAT, // 0x3B
    PLAYER_COMBAT_END, // 0x3C
    PLAYER_COMBAT_ENTER, // 0x3D
    PLAYER_COMBAT_KILL, // 0x3E
    PLAYER_INFO_REMOVE, // 0x3F
    PLAYER_INFO_UPDATE, // 0x40
    PLAYER_LOOK_AT, // 0x41
    PLAYER_POSITION, // 0x42
    PLAYER_ROTATION, // 0x43
    RECIPE_BOOK_ADD, // 0x44
    RECIPE_BOOK_REMOVE, // 0x45
    RECIPE_BOOK_SETTINGS, // 0x46
    REMOVE_ENTITIES, // 0x47
    REMOVE_MOB_EFFECT, // 0x48
    RESET_SCORE, // 0x49
    RESOURCE_PACK_POP, // 0x4A
    RESOURCE_PACK_PUSH, // 0x4B
    RESPAWN, // 0x4C
    UPDATE_UNLOCKED_EFFECTS, // 0x4D
    ROTATE_HEAD, // 0x4E
    SECTION_BLOCKS_UPDATE, // 0x4F
    SELECT_ADVANCEMENTS_TAB, // 0x50
    SERVER_DATA, // 0x51
    SET_ACTION_BAR_TEXT, // 0x52
    SET_BORDER_CENTER, // 0x53
    SET_BORDER_LERP_SIZE, // 0x54
    SET_BORDER_SIZE, // 0x55
    SET_BORDER_WARNING_DELAY, // 0x56
    SET_BORDER_WARNING_DISTANCE, // 0x57
    SET_CAMERA, // 0x58
    SET_CHUNK_CACHE_CENTER, // 0x59
    SET_CHUNK_CACHE_RADIUS, // 0x5A
    SET_CURSOR_ITEM, // 0x5B
    SET_DEFAULT_SPAWN_POSITION, // 0x5C
    SET_DISPLAY_OBJECTIVE, // 0x5D
    SET_ENTITY_DATA, // 0x5E
    SET_ENTITY_LINK, // 0x5F
    SET_ENTITY_MOTION, // 0x60
    SET_EQUIPMENT, // 0x61
    SET_EXPERIENCE, // 0x62
    SET_HEALTH, // 0x63
    SET_HELD_SLOT, // 0x64
    SET_OBJECTIVE, // 0x65
    SET_PASSENGERS, // 0x66
    SET_PLAYER_INVENTORY, // 0x67
    SET_PLAYER_TEAM, // 0x68
    SET_SCORE, // 0x69
    SET_SIMULATION_DISTANCE, // 0x6A
    SET_SUBTITLE_TEXT, // 0x6B
    SET_TIME, // 0x6C
    SET_TITLE_TEXT, // 0x6D
    SET_TITLES_ANIMATION, // 0x6E
    SOUND_ENTITY, // 0x6F
    SOUND, // 0x70
    START_CONFIGURATION, // 0x71
    STOP_SOUND, // 0x72
    STORE_COOKIE, // 0x73
    OPEN_DOOR, // 0x74
    SYSTEM_CHAT, // 0x75
    TAB_LIST, // 0x76
    TAG_QUERY, // 0x77
    TAKE_ITEM_ENTITY, // 0x78
    TELEPORT_ENTITY, // 0x79
    TEST_INSTANCE_BLOCK_STATUS, // 0x7A
    TICKING_STATE, // 0x7B
    TICKING_STEP, // 0x7C
    TRANSFER, // 0x7D
    UPDATE_ADVANCEMENTS, // 0x7E
    UPDATE_ATTRIBUTES, // 0x7F
    UPDATE_MOB_EFFECT, // 0x80
    UPDATE_RECIPES, // 0x81
    UPDATE_TAGS, // 0x82
    PROJECTILE_POWER, // 0x83
    CHANGE_DIMENSION_TYPE, // 0x84
    CUSTOM_REPORT_DETAILS, // 0x85
    SERVER_LINKS, // 0x86
    UPDATE_PLAYER_UNLOCKS; // 0x87

    @Override
    public int getId() {
        return ordinal();
    }

    @Override
    public String getName() {
        return name();
    }
}
