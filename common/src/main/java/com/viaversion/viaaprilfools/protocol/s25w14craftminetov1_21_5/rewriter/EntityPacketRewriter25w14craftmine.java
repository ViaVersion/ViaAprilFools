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
package com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaaprilfools.api.minecraft.entities.EntityTypes25w14craftmine;
import com.viaversion.viaaprilfools.api.type.version.Types25w14craftmine;
import com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.Protocol25w14craftmineTo1_21_5;
import com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.storage.CurrentContainer;
import com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.storage.UnlockedEffects;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet.ClientboundConfigurationPackets1_21;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet.ClientboundPacket25w14craftmine;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet.ClientboundPackets25w14craftmine;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.GameMode;
import com.viaversion.viaversion.api.minecraft.RegistryEntry;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_21_5;
import com.viaversion.viaversion.rewriter.RegistryDataRewriter;
import com.viaversion.viaversion.util.Key;

import java.util.ArrayList;
import java.util.List;

public final class EntityPacketRewriter25w14craftmine extends EntityRewriter<ClientboundPacket25w14craftmine, Protocol25w14craftmineTo1_21_5> {

    public static final EntityTypes25w14craftmine[] PET_ENTITIES = new EntityTypes25w14craftmine[] {
            EntityTypes25w14craftmine.PET_ARMADILLO, EntityTypes25w14craftmine.PET_AXOLOTL, EntityTypes25w14craftmine.PET_BEE,
            EntityTypes25w14craftmine.CAT, EntityTypes25w14craftmine.PET_FOX, EntityTypes25w14craftmine.PET_TURTLE, EntityTypes25w14craftmine.PET_POLAR_BEAR,
            EntityTypes25w14craftmine.PET_SLIME, EntityTypes25w14craftmine.PET_WOLF, EntityTypes25w14craftmine.PET_CREEPER, EntityTypes25w14craftmine.PET_CHICKEN,
            EntityTypes25w14craftmine.PET_FROG, EntityTypes25w14craftmine.PET_COW, EntityTypes25w14craftmine.PET_MOOSHROOM
    };

    public EntityPacketRewriter25w14craftmine(final Protocol25w14craftmineTo1_21_5 protocol) {
        super(protocol, Types1_21_5.ENTITY_DATA_TYPES.optionalComponentType, Types1_21_5.ENTITY_DATA_TYPES.booleanType);
    }

    @Override
    public void registerPackets() {
        registerTrackerWithData1_19(ClientboundPackets25w14craftmine.ADD_ENTITY, EntityTypes25w14craftmine.FALLING_BLOCK);
        registerSetEntityData(ClientboundPackets25w14craftmine.SET_ENTITY_DATA, Types25w14craftmine.ENTITY_DATA_LIST, Types1_21_5.ENTITY_DATA_LIST);
        registerRemoveEntities(ClientboundPackets25w14craftmine.REMOVE_ENTITIES);
        registerPlayerAbilities(ClientboundPackets25w14craftmine.PLAYER_ABILITIES);
        registerGameEvent(ClientboundPackets25w14craftmine.GAME_EVENT);

        final RegistryDataRewriter registryDataRewriter = new RegistryDataRewriter(protocol) {
            @Override
            public RegistryEntry[] handle(UserConnection connection, String key, RegistryEntry[] entries) {
                for (final RegistryEntry entry : entries) {
                    if (entry.tag() == null) {
                        continue;
                    }

                    if (entry.tag() instanceof CompoundTag compoundTag) {
                        if (Key.stripMinecraftNamespace(key).equals("dimension_type")) {
                            // Now back to inlined strings - reuse the dimension key
                            compoundTag.putString("effects", Key.namespaced(entry.key()));
                        } else {
                            final String soundEvent = compoundTag.getString("sound_event");
                            if (soundEvent != null && soundEvent.startsWith("nothingtoseehere")) {
                                // Cancelled in normal packets and easier than removing registry entries
                                compoundTag.putString("sound_event", "minecraft:intentionally_empty");
                            }
                        }
                    }
                }
                return super.handle(connection, key, entries);
            }
        };
        protocol.registerClientbound(ClientboundConfigurationPackets1_21.REGISTRY_DATA, registryDataRewriter::handle);

        protocol.registerClientbound(ClientboundPackets25w14craftmine.LOGIN, wrapper -> {
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
        });
        protocol.registerClientbound(ClientboundPackets25w14craftmine.RESPAWN, wrapper -> {
            updatePlayerSpawnInfo(wrapper);
            wrapper.user().get(CurrentContainer.class).close();
        });
    }

    private void updatePlayerSpawnInfo(final PacketWrapper wrapper) {
        int dimensionHolderId = wrapper.read(Types.VAR_INT);
        if (dimensionHolderId != 0) {
            dimensionHolderId--; // Back to proper registry if possible, otherwise /shrug
        }
        wrapper.write(Types.VAR_INT, dimensionHolderId);
        final String world = wrapper.passthrough(Types.STRING);
        wrapper.passthrough(Types.LONG); // Seed
        final byte gamemode = wrapper.passthrough(Types.BYTE); // Gamemode
        wrapper.passthrough(Types.BYTE); // Previous gamemode
        wrapper.passthrough(Types.BOOLEAN); // Debug
        wrapper.passthrough(Types.BOOLEAN); // Flat
        wrapper.passthrough(Types.OPTIONAL_GLOBAL_POSITION); // Last death location
        wrapper.passthrough(Types.VAR_INT); // Portal cooldown
        wrapper.passthrough(Types.VAR_INT); // Sea level
        wrapper.read(Types.BOOLEAN); // Is map
        final int unlockedEffectsSize = wrapper.read(Types.VAR_INT);
        final List<Integer> unlockedEffects = new ArrayList<>();
        for (int i = 0; i < unlockedEffectsSize; i++) {
            unlockedEffects.add(wrapper.read(Types.VAR_INT)); // Id
        }
        wrapper.user().put(new UnlockedEffects(unlockedEffects));
        final int activeEffectsSize = wrapper.read(Types.VAR_INT);
        for (int i = 0; i < activeEffectsSize; i++) {
            wrapper.read(Types.VAR_INT); // Id
        }

        trackWorldDataByKey1_20_5(wrapper.user(), dimensionHolderId, world);
        tracker(wrapper.user()).setInstaBuild(gamemode == GameMode.CREATIVE.id());
    }

    @Override
    protected void registerRewrites() {
        registerEntityDataTypeHandler1_20_3(
            Types1_21_5.ENTITY_DATA_TYPES.itemType,
            Types1_21_5.ENTITY_DATA_TYPES.blockStateType,
            Types1_21_5.ENTITY_DATA_TYPES.optionalBlockStateType,
            Types1_21_5.ENTITY_DATA_TYPES.particleType,
            Types1_21_5.ENTITY_DATA_TYPES.particlesType,
            Types1_21_5.ENTITY_DATA_TYPES.componentType,
            Types1_21_5.ENTITY_DATA_TYPES.optionalComponentType
        );

        // Pets are tamable animals and the normal entities are just animals
        for (final EntityTypes25w14craftmine entity : PET_ENTITIES) {
            filter().type(entity).removeIndex(17); // Flags
            filter().type(entity).removeIndex(18); // Owner
        }
    }

    @Override
    public void onMappingDataLoaded() {
        mapTypes();

        mapEntityTypeWithData(EntityTypes25w14craftmine.PET_ARMADILLO, EntityTypes25w14craftmine.ARMADILLO).tagName();
        mapEntityTypeWithData(EntityTypes25w14craftmine.PET_AXOLOTL, EntityTypes25w14craftmine.AXOLOTL).tagName();
        mapEntityTypeWithData(EntityTypes25w14craftmine.PET_BEE, EntityTypes25w14craftmine.BEE).tagName();
        mapEntityTypeWithData(EntityTypes25w14craftmine.CAT, EntityTypes25w14craftmine.PET_CAT).tagName();
        mapEntityTypeWithData(EntityTypes25w14craftmine.PET_FOX, EntityTypes25w14craftmine.FOX).tagName();
        mapEntityTypeWithData(EntityTypes25w14craftmine.PET_TURTLE, EntityTypes25w14craftmine.TURTLE).tagName();
        mapEntityTypeWithData(EntityTypes25w14craftmine.PET_POLAR_BEAR, EntityTypes25w14craftmine.POLAR_BEAR).tagName();
        mapEntityTypeWithData(EntityTypes25w14craftmine.PET_SLIME, EntityTypes25w14craftmine.SLIME).tagName();
        mapEntityTypeWithData(EntityTypes25w14craftmine.PET_WOLF, EntityTypes25w14craftmine.WOLF).tagName();
        mapEntityTypeWithData(EntityTypes25w14craftmine.PET_CREEPER, EntityTypes25w14craftmine.CREEPER).tagName();
        mapEntityTypeWithData(EntityTypes25w14craftmine.PET_CHICKEN, EntityTypes25w14craftmine.CHICKEN).tagName();
        mapEntityTypeWithData(EntityTypes25w14craftmine.PET_FROG, EntityTypes25w14craftmine.FROG).tagName();
        mapEntityTypeWithData(EntityTypes25w14craftmine.PET_COW, EntityTypes25w14craftmine.COW).tagName();
        mapEntityTypeWithData(EntityTypes25w14craftmine.PET_MOOSHROOM, EntityTypes25w14craftmine.MOOSHROOM).tagName();

        mapEntityTypeWithData(EntityTypes25w14craftmine.ANGRY_GHAST, EntityTypes25w14craftmine.GHAST).tagName();
    }

    @Override
    public EntityType typeFromId(final int type) {
        return EntityTypes25w14craftmine.getTypeFromId(type);
    }

}
