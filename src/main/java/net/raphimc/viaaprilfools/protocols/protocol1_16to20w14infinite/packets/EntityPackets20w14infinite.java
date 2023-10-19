/*
 * This file is part of ViaAprilFools - https://github.com/RaphiMC/ViaAprilFools
 * Copyright (C) 2023 RK_01/RaphiMC and contributors
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
package net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets;
import net.raphimc.viaaprilfools.ViaAprilFools;
import net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.ClientboundPackets20w14infinite;
import net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.Protocol1_16to20w14infinite;
import net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.metadata.MetadataRewriter1_16to20w14infinite;

import java.util.UUID;

public class EntityPackets20w14infinite {

    private static final PacketHandler DIMENSION_HANDLER = wrapper -> {
        int dimension = wrapper.read(Type.INT);
        String dimensionType;
        String dimensionName;
        switch (dimension) {
            case -1:
                dimensionName = dimensionType = "minecraft:the_nether";
                break;
            case 0:
                dimensionName = dimensionType = "minecraft:overworld";
                break;
            case 1:
                dimensionName = dimensionType = "minecraft:the_end";
                break;
            default:
                dimensionType = "minecraft:overworld";
                dimensionName = dimensionType + dimension;
        }

        wrapper.write(Type.STRING, dimensionType); // dimension type
        wrapper.write(Type.STRING, dimensionName); // dimension
    };
    private static final String[] WORLD_NAMES = {"minecraft:overworld", "minecraft:the_nether", "minecraft:the_end"};

    public static void register(Protocol1_16to20w14infinite protocol) {
        MetadataRewriter1_16to20w14infinite metadataRewriter = protocol.get(MetadataRewriter1_16to20w14infinite.class);
        metadataRewriter.registerTrackerWithData(ClientboundPackets20w14infinite.SPAWN_ENTITY, EntityTypes1_16.FALLING_BLOCK);
        metadataRewriter.registerTracker(ClientboundPackets20w14infinite.SPAWN_MOB);
        metadataRewriter.registerTracker(ClientboundPackets20w14infinite.SPAWN_PLAYER, EntityTypes1_16.PLAYER);
        metadataRewriter.registerMetadataRewriter(ClientboundPackets20w14infinite.ENTITY_METADATA, Types1_14.METADATA_LIST);
        metadataRewriter.registerRemoveEntities(ClientboundPackets20w14infinite.DESTROY_ENTITIES);

        // Spawn lightning -> Spawn entity
        protocol.registerClientbound(ClientboundPackets20w14infinite.SPAWN_GLOBAL_ENTITY, ClientboundPackets1_16.SPAWN_ENTITY, wrapper -> {
            final int entityId = wrapper.passthrough(Type.VAR_INT); // entity id
            wrapper.user().getEntityTracker(Protocol1_16to20w14infinite.class).addEntity(entityId, EntityTypes1_16.LIGHTNING_BOLT);

            wrapper.write(Type.UUID, UUID.randomUUID()); // uuid
            wrapper.write(Type.VAR_INT, EntityTypes1_16.LIGHTNING_BOLT.getId()); // entity type

            wrapper.read(Type.BYTE); // remove type

            wrapper.passthrough(Type.DOUBLE); // x
            wrapper.passthrough(Type.DOUBLE); // y
            wrapper.passthrough(Type.DOUBLE); // z
            wrapper.write(Type.BYTE, (byte) 0); // yaw
            wrapper.write(Type.BYTE, (byte) 0); // pitch
            wrapper.write(Type.INT, 0); // data
            wrapper.write(Type.SHORT, (short) 0); // velocity
            wrapper.write(Type.SHORT, (short) 0); // velocity
            wrapper.write(Type.SHORT, (short) 0); // velocity
        });
        protocol.registerClientbound(ClientboundPackets20w14infinite.RESPAWN, new PacketHandlers() {
            @Override
            public void register() {
                handler(DIMENSION_HANDLER);
                map(Type.LONG); // Seed
                map(Type.UNSIGNED_BYTE); // Gamemode
                handler(wrapper -> {
                    wrapper.write(Type.BYTE, (byte) -1); // Previous gamemode, set to none

                    final String levelType = wrapper.read(Type.STRING);
                    wrapper.write(Type.BOOLEAN, false); // debug
                    wrapper.write(Type.BOOLEAN, levelType.equals("flat"));
                    wrapper.write(Type.BOOLEAN, true); // keep all playerdata
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets20w14infinite.JOIN_GAME, new PacketHandlers() {
            @Override
            public void register() {
                map(Type.INT); // Entity ID
                map(Type.UNSIGNED_BYTE); //  Gamemode
                handler(wrapper -> {
                    wrapper.write(Type.BYTE, (byte) -1); // Previous gamemode, set to none
                    wrapper.write(Type.STRING_ARRAY, WORLD_NAMES); // World list - only used for command completion
                    wrapper.write(Type.NAMED_COMPOUND_TAG, EntityPackets.DIMENSIONS_TAG); // Dimension registry
                });
                handler(DIMENSION_HANDLER); // Dimension
                map(Type.LONG); // Seed
                map(Type.UNSIGNED_BYTE); // Max players
                handler(wrapper -> {
                    wrapper.user().getEntityTracker(Protocol1_16to20w14infinite.class).addEntity(wrapper.get(Type.INT, 0), EntityTypes1_16.PLAYER);

                    final String type = wrapper.read(Type.STRING);// level type
                    wrapper.passthrough(Type.VAR_INT); // View distance
                    wrapper.passthrough(Type.BOOLEAN); // Reduced debug info
                    wrapper.passthrough(Type.BOOLEAN); // Show death screen

                    wrapper.write(Type.BOOLEAN, false); // Debug
                    wrapper.write(Type.BOOLEAN, type.equals("flat"));
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets20w14infinite.ENTITY_PROPERTIES, wrapper -> {
            wrapper.passthrough(Type.VAR_INT);
            int size = wrapper.passthrough(Type.INT);
            int actualSize = size;
            for (int i = 0; i < size; i++) {
                // Attributes have been renamed and are now namespaced identifiers
                String key = wrapper.read(Type.STRING);
                String attributeIdentifier = Via.getManager().getProtocolManager().getProtocol(Protocol1_16To1_15_2.class).getMappingData().getAttributeMappings().get(key);
                if (attributeIdentifier == null) {
                    attributeIdentifier = "minecraft:" + key;
                    if (!com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.MappingData.isValid1_13Channel(attributeIdentifier)) {
                        if (!Via.getConfig().isSuppressConversionWarnings()) {
                            ViaAprilFools.getPlatform().getLogger().warning("Invalid attribute: " + key);
                        }
                        actualSize--;
                        wrapper.read(Type.DOUBLE);
                        int modifierSize = wrapper.read(Type.VAR_INT);
                        for (int j = 0; j < modifierSize; j++) {
                            wrapper.read(Type.UUID);
                            wrapper.read(Type.DOUBLE);
                            wrapper.read(Type.BYTE);
                        }
                        continue;
                    }
                }

                wrapper.write(Type.STRING, attributeIdentifier);

                wrapper.passthrough(Type.DOUBLE);
                int modifierSize = wrapper.passthrough(Type.VAR_INT);
                for (int j = 0; j < modifierSize; j++) {
                    wrapper.passthrough(Type.UUID);
                    wrapper.passthrough(Type.DOUBLE);
                    wrapper.passthrough(Type.BYTE);
                }
            }
            if (size != actualSize) {
                wrapper.set(Type.INT, 0, actualSize);
            }
        });
    }
}
