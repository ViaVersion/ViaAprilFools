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

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_15;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.ClientboundPackets20w14infinite;
import net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.Protocol1_16to20w14infinite;
import net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.data.BiomeData20w14infinite;

import java.util.Map;

public class BlockItemPackets20w14infinite extends ItemRewriter<ClientboundPackets20w14infinite, ServerboundPackets1_16, Protocol1_16to20w14infinite> {

    public BlockItemPackets20w14infinite(Protocol1_16to20w14infinite protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        this.registerSetCooldown(ClientboundPackets20w14infinite.COOLDOWN);
        this.registerWindowItems(ClientboundPackets20w14infinite.WINDOW_ITEMS, Type.ITEM1_13_2_SHORT_ARRAY);
        this.registerSetSlot(ClientboundPackets20w14infinite.SET_SLOT, Type.ITEM1_13_2);
        this.registerTradeList(ClientboundPackets20w14infinite.TRADE_LIST);
        this.registerAdvancements(ClientboundPackets20w14infinite.ADVANCEMENTS, Type.ITEM1_13_2);
        this.registerSpawnParticle(ClientboundPackets20w14infinite.SPAWN_PARTICLE, Type.ITEM1_13_2, Type.DOUBLE);
        this.registerClickWindow(ServerboundPackets1_16.CLICK_WINDOW, Type.ITEM1_13_2);
        this.registerCreativeInvAction(ServerboundPackets1_16.CREATIVE_INVENTORY_ACTION, Type.ITEM1_13_2);
        final BlockRewriter<ClientboundPackets20w14infinite> blockRewriter = new BlockRewriter<>(this.protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets20w14infinite.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets20w14infinite.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets20w14infinite.MULTI_BLOCK_CHANGE);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets20w14infinite.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerEffect(ClientboundPackets20w14infinite.EFFECT, 1010, 2001);

        protocol.registerClientbound(ClientboundPackets20w14infinite.UPDATE_LIGHT, new PacketHandlers() {
            @Override
            public void register() {
                map(Type.VAR_INT); // x
                map(Type.VAR_INT); // y
                handler(wrapper -> wrapper.write(Type.BOOLEAN, true)); // Take neighbour's light into account as well
            }
        });
        protocol.registerClientbound(ClientboundPackets20w14infinite.CHUNK_DATA, new PacketHandlers() {
            @Override
            public void register() {
                handler(wrapper -> {
                    Chunk chunk = wrapper.read(new ChunkType1_15());
                    wrapper.write(new ChunkType1_16(), chunk);

                    chunk.setIgnoreOldLightData(chunk.isFullChunk());

                    for (int s = 0; s < chunk.getSections().length; s++) {
                        ChunkSection section = chunk.getSections()[s];
                        if (section == null) continue;
                        final DataPalette blockPalette = section.palette(PaletteType.BLOCKS);
                        for (int i = 0; i < blockPalette.size(); i++) {
                            int old = blockPalette.idByIndex(i);
                            blockPalette.setIdByIndex(i, protocol.getMappingData().getNewBlockStateId(old));
                        }
                    }

                    if (chunk.getBiomeData() != null) {
                        for (int i = 0; i < chunk.getBiomeData().length; i++) {
                            if (!BiomeData20w14infinite.isValid(chunk.getBiomeData()[i])) {
                                chunk.getBiomeData()[i] = 1; // plains
                            }
                        }
                    }

                    CompoundTag heightMaps = chunk.getHeightMap();
                    for (Map.Entry<String, Tag> heightMapTag : heightMaps) {
                        LongArrayTag heightMap = (LongArrayTag) heightMapTag.getValue();
                        int[] heightMapData = new int[256];
                        CompactArrayUtil.iterateCompactArray(9, heightMapData.length, heightMap.getValue(), (i, v) -> heightMapData[i] = v);
                        heightMap.setValue(CompactArrayUtil.createCompactArrayWithPadding(9, heightMapData.length, i -> heightMapData[i]));
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets20w14infinite.ENTITY_EQUIPMENT, new PacketHandlers() {
            @Override
            public void register() {
                map(Type.VAR_INT); // 0 - Entity ID

                handler(wrapper -> {
                    int slot = wrapper.read(Type.VAR_INT);
                    wrapper.write(Type.BYTE, (byte) slot);
                    handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
                });
            }
        });

        protocol.registerServerbound(ServerboundPackets1_16.EDIT_BOOK, new PacketHandlers() {
            @Override
            public void register() {
                handler(wrapper -> handleItemToServer(wrapper.passthrough(Type.ITEM1_13_2)));
            }
        });
    }

}
