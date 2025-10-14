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

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.LongArrayTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaaprilfools.protocol.s20w14infinitetov1_16.Protocol20w14infiniteTo1_16;
import com.viaversion.viaaprilfools.protocol.s20w14infinitetov1_16.data.BiomeData20w14infinite;
import com.viaversion.viaaprilfools.protocol.s20w14infinitetov1_16.packet.ClientboundPackets20w14infinite;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_15;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ServerboundPackets1_16;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import java.util.Map;

public class BlockItemPacketRewriter20w14infinite extends ItemRewriter<ClientboundPackets20w14infinite, ServerboundPackets1_16, Protocol20w14infiniteTo1_16> {

    public BlockItemPacketRewriter20w14infinite(Protocol20w14infiniteTo1_16 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_SHORT_ARRAY);
    }

    @Override
    protected void registerPackets() {
        this.registerCooldown(ClientboundPackets20w14infinite.COOLDOWN);
        this.registerSetContent(ClientboundPackets20w14infinite.CONTAINER_SET_CONTENT);
        this.registerSetSlot(ClientboundPackets20w14infinite.CONTAINER_SET_SLOT);
        this.registerMerchantOffers(ClientboundPackets20w14infinite.MERCHANT_OFFERS);
        this.registerAdvancements(ClientboundPackets20w14infinite.UPDATE_ADVANCEMENTS);
        this.registerContainerClick(ServerboundPackets1_16.CONTAINER_CLICK);
        this.registerSetCreativeModeSlot(ServerboundPackets1_16.SET_CREATIVE_MODE_SLOT);
        final BlockRewriter<ClientboundPackets20w14infinite> blockRewriter = BlockRewriter.for1_14(this.protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets20w14infinite.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets20w14infinite.BLOCK_UPDATE);
        blockRewriter.registerChunkBlocksUpdate(ClientboundPackets20w14infinite.CHUNK_BLOCKS_UPDATE);
        blockRewriter.registerBlockBreakAck(ClientboundPackets20w14infinite.BLOCK_BREAK_ACK);
        blockRewriter.registerLevelEvent(ClientboundPackets20w14infinite.LEVEL_EVENT, 1010, 2001);

        protocol.registerClientbound(ClientboundPackets20w14infinite.LIGHT_UPDATE, new PacketHandlers() {
            @Override
            public void register() {
                map(Types.VAR_INT); // x
                map(Types.VAR_INT); // y
                handler(wrapper -> wrapper.write(Types.BOOLEAN, true)); // Take neighbour's light into account as well
            }
        });
        protocol.registerClientbound(ClientboundPackets20w14infinite.LEVEL_CHUNK, new PacketHandlers() {
            @Override
            public void register() {
                handler(wrapper -> {
                    Chunk chunk = wrapper.read(ChunkType1_15.TYPE);
                    wrapper.write(ChunkType1_16.TYPE, chunk);

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
        protocol.registerClientbound(ClientboundPackets20w14infinite.SET_EQUIPMENT, new PacketHandlers() {
            @Override
            public void register() {
                map(Types.VAR_INT); // 0 - Entity ID

                handler(wrapper -> {
                    int slot = wrapper.read(Types.VAR_INT);
                    wrapper.write(Types.BYTE, (byte) slot);
                    handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                });
            }
        });

        protocol.registerServerbound(ServerboundPackets1_16.EDIT_BOOK, new PacketHandlers() {
            @Override
            public void register() {
                handler(wrapper -> handleItemToServer(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2)));
            }
        });
    }

}
