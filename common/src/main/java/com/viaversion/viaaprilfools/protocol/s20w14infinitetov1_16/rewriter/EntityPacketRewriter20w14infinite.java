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
package com.viaversion.viaaprilfools.protocol.s20w14infinitetov1_16.rewriter;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.data.AttributeMappings1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.data.DimensionRegistries1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ClientboundPackets1_16;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaaprilfools.protocol.s20w14infinitetov1_16.packet.ClientboundPackets20w14infinite;
import com.viaversion.viaaprilfools.protocol.s20w14infinitetov1_16.Protocol20w14infiniteTo1_16;

import java.util.UUID;

public class EntityPacketRewriter20w14infinite extends EntityRewriter<ClientboundPackets20w14infinite, Protocol20w14infiniteTo1_16> {

    private final PacketHandler DIMENSION_HANDLER = wrapper -> {
        int dimension = wrapper.read(Types.INT);
        String dimensionType;
        String dimensionName = switch (dimension) {
            case -1 -> dimensionType = "minecraft:the_nether";
            case 0 -> dimensionType = "minecraft:overworld";
            case 1 -> dimensionType = "minecraft:the_end";
            default -> {
                dimensionType = "minecraft:overworld";
                yield dimensionType + dimension;
            }
        };

        wrapper.write(Types.STRING, dimensionType); // dimension type
        wrapper.write(Types.STRING, dimensionName); // dimension
    };

    public EntityPacketRewriter20w14infinite(Protocol20w14infiniteTo1_16 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        registerTrackerWithData(ClientboundPackets20w14infinite.ADD_ENTITY, EntityTypes1_16.FALLING_BLOCK);
        registerTracker(ClientboundPackets20w14infinite.ADD_MOB);
        registerTracker(ClientboundPackets20w14infinite.ADD_PLAYER, EntityTypes1_16.PLAYER);
        registerSetEntityData(ClientboundPackets20w14infinite.SET_ENTITY_DATA, Types1_14.ENTITY_DATA_LIST);
        registerRemoveEntities(ClientboundPackets20w14infinite.REMOVE_ENTITIES);

        protocol.registerClientbound(ClientboundPackets20w14infinite.ADD_GLOBAL_ENTITY, ClientboundPackets1_16.ADD_ENTITY, wrapper -> {
            final int entityId = wrapper.passthrough(Types.VAR_INT); // entity id
            wrapper.user().getEntityTracker(Protocol20w14infiniteTo1_16.class).addEntity(entityId, EntityTypes1_16.LIGHTNING_BOLT);

            wrapper.write(Types.UUID, UUID.randomUUID()); // uuid
            wrapper.write(Types.VAR_INT, EntityTypes1_16.LIGHTNING_BOLT.getId()); // entity type

            wrapper.read(Types.BYTE); // remove type

            wrapper.passthrough(Types.DOUBLE); // x
            wrapper.passthrough(Types.DOUBLE); // y
            wrapper.passthrough(Types.DOUBLE); // z
            wrapper.write(Types.BYTE, (byte) 0); // yaw
            wrapper.write(Types.BYTE, (byte) 0); // pitch
            wrapper.write(Types.INT, 0); // data
            wrapper.write(Types.SHORT, (short) 0); // velocity
            wrapper.write(Types.SHORT, (short) 0); // velocity
            wrapper.write(Types.SHORT, (short) 0); // velocity
        });
        protocol.registerClientbound(ClientboundPackets20w14infinite.RESPAWN, new PacketHandlers() {
            @Override
            public void register() {
                handler(DIMENSION_HANDLER);
                map(Types.LONG); // Seed
                map(Types.UNSIGNED_BYTE); // Gamemode
                handler(wrapper -> {
                    tracker(wrapper.user()).clearEntities();

                    wrapper.write(Types.BYTE, (byte) -1); // Previous gamemode, set to none

                    final String levelType = wrapper.read(Types.STRING);
                    wrapper.write(Types.BOOLEAN, false); // debug
                    wrapper.write(Types.BOOLEAN, levelType.equals("flat"));
                    wrapper.write(Types.BOOLEAN, true); // keep all playerdata
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets20w14infinite.LOGIN, new PacketHandlers() {
            @Override
            public void register() {
                map(Types.INT); // Entity ID
                map(Types.UNSIGNED_BYTE); //  Gamemode
                handler(wrapper -> {
                    wrapper.write(Types.BYTE, (byte) -1); // Previous gamemode, set to none
                    wrapper.write(Types.STRING_ARRAY, DimensionRegistries1_16.getWorldNames()); // World list - only used for command completion
                    wrapper.write(Types.NAMED_COMPOUND_TAG, DimensionRegistries1_16.getDimensionsTag()); // Dimension registry
                });
                handler(DIMENSION_HANDLER); // Dimension
                map(Types.LONG); // Seed
                map(Types.UNSIGNED_BYTE); // Max players
                handler(playerTrackerHandler());
                handler(wrapper -> {
                    final String type = wrapper.read(Types.STRING);// level type
                    wrapper.passthrough(Types.VAR_INT); // View distance
                    wrapper.passthrough(Types.BOOLEAN); // Reduced debug info
                    wrapper.passthrough(Types.BOOLEAN); // Show death screen

                    wrapper.write(Types.BOOLEAN, false); // Debug
                    wrapper.write(Types.BOOLEAN, type.equals("flat"));
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets20w14infinite.UPDATE_ATTRIBUTES, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            int size = wrapper.passthrough(Types.INT);
            int actualSize = size;
            for (int i = 0; i < size; i++) {
                // Attributes have been renamed and are now namespaced identifiers
                String key = wrapper.read(Types.STRING);
                String attributeIdentifier = AttributeMappings1_16.attributeIdentifierMappings().get(key);
                if (attributeIdentifier == null) {
                    attributeIdentifier = "minecraft:" + key;
                    if (!Key.isValid(attributeIdentifier)) {
                        if (!Via.getConfig().isSuppressConversionWarnings()) {
                            protocol.getLogger().warning("Invalid attribute: " + key);
                        }
                        actualSize--;
                        wrapper.read(Types.DOUBLE);
                        int modifierSize = wrapper.read(Types.VAR_INT);
                        for (int j = 0; j < modifierSize; j++) {
                            wrapper.read(Types.UUID);
                            wrapper.read(Types.DOUBLE);
                            wrapper.read(Types.BYTE);
                        }
                        continue;
                    }
                }

                wrapper.write(Types.STRING, attributeIdentifier);

                wrapper.passthrough(Types.DOUBLE);
                int modifierSize = wrapper.passthrough(Types.VAR_INT);
                for (int j = 0; j < modifierSize; j++) {
                    wrapper.passthrough(Types.UUID);
                    wrapper.passthrough(Types.DOUBLE);
                    wrapper.passthrough(Types.BYTE);
                }
            }
            if (size != actualSize) {
                wrapper.set(Types.INT, 0, actualSize);
            }
        });
    }

    @Override
    protected void registerRewrites() {
        registerEntityDataTypeHandler(Types1_14.ENTITY_DATA_TYPES.itemType, Types1_14.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_14.ENTITY_DATA_TYPES.particleType);
        registerBlockStateHandler(EntityTypes1_16.ABSTRACT_MINECART, 10);

        filter().type(EntityTypes1_16.ABSTRACT_ARROW).removeIndex(8);
    }

    @Override
    public void onMappingDataLoaded() {
        mapTypes();
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_16.getTypeFromId(type);
    }

}
