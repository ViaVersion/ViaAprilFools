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
package com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaaprilfools.api.minecraft.entities.EntityTypes25w14craftmine;
import com.viaversion.viaaprilfools.api.type.version.Types25w14craftmine;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.Protocol1_21_5To_25w14craftmine;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet.ClientboundPackets25w14craftmine;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.GameMode;
import com.viaversion.viaversion.api.minecraft.RegistryEntry;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_21_5;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundConfigurationPackets1_21;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ClientboundPacket1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ClientboundPackets1_21_5;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.rewriter.RegistryDataRewriter;
import com.viaversion.viaversion.util.Key;

import static com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.data.DimensionTypes25w14craftmine.getGeneratedDimensionTag;
import static com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.data.DimensionTypes25w14craftmine.getOverworldCavesDimensionEffectsTag;

public final class EntityPacketRewriter25w14craftmine extends EntityRewriter<ClientboundPacket1_21_5, Protocol1_21_5To_25w14craftmine> {

    private static final int[] UNLOCKED_PLAYER_EFFECTS = new int[] {
            15, // minecraft:crafting
            20, // minecraft:inventory_slots_1
            21, // minecraft:inventory_slots_2
            22, // minecraft:inventory_slots_3
            24, // minecraft:inventory_crafting
            79  // minecraft:armaments
    };

    public EntityPacketRewriter25w14craftmine(final Protocol1_21_5To_25w14craftmine protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        // Tracks entities, applies entity data rewrites registered below, untracks entities
        registerTrackerWithData1_19(ClientboundPackets1_21_5.ADD_ENTITY, EntityTypes25w14craftmine.FALLING_BLOCK);
        registerSetEntityData(ClientboundPackets1_21_5.SET_ENTITY_DATA, Types1_21_5.ENTITY_DATA_LIST, Types25w14craftmine.ENTITY_DATA_LIST);
        registerRemoveEntities(ClientboundPackets1_21_5.REMOVE_ENTITIES);
        registerPlayerAbilities(ClientboundPackets1_21_5.PLAYER_ABILITIES);
        registerGameEvent(ClientboundPackets1_21_5.GAME_EVENT);

        final RegistryDataRewriter registryDataRewriter = new RegistryDataRewriter(protocol) {
            @Override
            public RegistryEntry[] handle(UserConnection connection, String key, RegistryEntry[] entries) {
                if (Key.stripMinecraftNamespace(key).equals("dimension_type")) {
                    for (final RegistryEntry entry : entries) {
                        if (entry.tag() == null) {
                            continue;
                        }

                        // Use the ones that are sent with the minecraft:generated dimension as it's the most similar to
                        // a normal world, also still used in minecraft:overworld_caves
                        final CompoundTag tag = (CompoundTag) entry.tag();
                        tag.put("effects", getOverworldCavesDimensionEffectsTag());
                    }
                }
                return super.handle(connection, key, entries);
            }
        };
        registryDataRewriter.addEntries(
                "dimension_type",
                new RegistryEntry("minecraft:generated", getGeneratedDimensionTag())
        );
        protocol.registerClientbound(ClientboundConfigurationPackets1_21.REGISTRY_DATA, registryDataRewriter::handle);

        protocol.registerClientbound(ClientboundPackets1_21_5.LOGIN, wrapper -> {
            final int entityId = wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.BOOLEAN); // Hardcore
            wrapper.passthrough(Types.STRING_ARRAY); // World List
            wrapper.passthrough(Types.VAR_INT); // Max players
            wrapper.passthrough(Types.VAR_INT); // View distance
            wrapper.passthrough(Types.VAR_INT); // Simulation distance
            wrapper.passthrough(Types.BOOLEAN); // Reduced debug info
            wrapper.passthrough(Types.BOOLEAN); // Show death screen
            wrapper.passthrough(Types.BOOLEAN); // Limited crafting

            updatePlayerSpawnInfo(wrapper);
            trackPlayer(wrapper.user(), entityId);

            wrapper.send(Protocol1_21_5To_25w14craftmine.class);
            wrapper.cancel();

            final PacketWrapper updatePlayerUnlocks = PacketWrapper.create(ClientboundPackets25w14craftmine.UPDATE_PLAYER_UNLOCKS, wrapper.user());
            updatePlayerUnlocks.write(Types.VAR_INT, entityId);
            updatePlayerUnlocks.write(Types.BOOLEAN, true); // Reset
            updatePlayerUnlocks.write(Types.VAR_INT, UNLOCKED_PLAYER_EFFECTS.length);
            for (final int effect : UNLOCKED_PLAYER_EFFECTS) {
                updatePlayerUnlocks.write(Types.VAR_INT, effect);
                updatePlayerUnlocks.write(Types.BOOLEAN, true); // Active
            }
            updatePlayerUnlocks.write(Types.VAR_INT, UNLOCKED_PLAYER_EFFECTS.length);
            for (final int effect : UNLOCKED_PLAYER_EFFECTS) {
                updatePlayerUnlocks.write(Types.VAR_INT, effect);
                updatePlayerUnlocks.write(Types.VAR_INT, 0); // Visible
            }
            updatePlayerUnlocks.write(Types.VAR_INT, UNLOCKED_PLAYER_EFFECTS.length);
            for (final int effect : UNLOCKED_PLAYER_EFFECTS) {
                updatePlayerUnlocks.write(Types.VAR_INT, effect);
                updatePlayerUnlocks.write(Types.BOOLEAN, true); // Obtained
            }
            updatePlayerUnlocks.send(Protocol1_21_5To_25w14craftmine.class);
        });
        protocol.registerClientbound(ClientboundPackets1_21_5.RESPAWN, this::updatePlayerSpawnInfo);
    }

    private void updatePlayerSpawnInfo(final PacketWrapper wrapper) {
        final int dimensionId = wrapper.read(Types.VAR_INT);
        wrapper.write(Types.VAR_INT, dimensionId + 1); // Now as holder
        final String world = wrapper.passthrough(Types.STRING);
        wrapper.passthrough(Types.LONG); // Seed
        final byte gamemode = wrapper.passthrough(Types.BYTE); // Gamemode
        wrapper.passthrough(Types.BYTE); // Previous gamemode
        wrapper.passthrough(Types.BOOLEAN); // Debug
        wrapper.passthrough(Types.BOOLEAN); // Flat
        wrapper.passthrough(Types.OPTIONAL_GLOBAL_POSITION); // Last death location
        wrapper.passthrough(Types.VAR_INT); // Portal cooldown
        wrapper.passthrough(Types.VAR_INT); // Sea level
        wrapper.write(Types.BOOLEAN, false); // Is map - Only used for changing the spawn reason on the client (rendering only)
        wrapper.write(Types.VAR_INT, 0); // Unlocked (world) effects - None
        wrapper.write(Types.VAR_INT, 0); // Active (world) effects - None

        trackWorldDataByKey1_20_5(wrapper.user(), dimensionId, world);
        tracker(wrapper.user()).setInstaBuild(gamemode == GameMode.CREATIVE.id());
    }

    @Override
    protected void registerRewrites() {
        filter().mapDataType(Types25w14craftmine.ENTITY_DATA_TYPES::byId);
        registerEntityDataTypeHandler(
            Types25w14craftmine.ENTITY_DATA_TYPES.itemType,
            Types25w14craftmine.ENTITY_DATA_TYPES.blockStateType,
            Types25w14craftmine.ENTITY_DATA_TYPES.optionalBlockStateType,
            Types25w14craftmine.ENTITY_DATA_TYPES.particleType,
            Types25w14craftmine.ENTITY_DATA_TYPES.particlesType,
            Types25w14craftmine.ENTITY_DATA_TYPES.componentType,
            Types25w14craftmine.ENTITY_DATA_TYPES.optionalComponentType
        );
    }

    @Override
    public void onMappingDataLoaded() {
        mapTypes();
    }

    @Override
    public EntityType typeFromId(final int type) {
        return EntityTypes25w14craftmine.getTypeFromId(type);
    }

}
