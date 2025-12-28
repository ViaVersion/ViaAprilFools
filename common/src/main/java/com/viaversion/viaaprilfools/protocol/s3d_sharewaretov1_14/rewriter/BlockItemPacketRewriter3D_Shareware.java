/*
 * This file is part of ViaAprilFools - https://github.com/ViaVersion/ViaAprilFools
 * Copyright (C) 2021-2026 the original authors
 *                         - RK_01/RaphiMC
 *                         - FlorianMichael/EnZaXD <git@florianmichael.de>
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
package com.viaversion.viaaprilfools.protocol.s3d_sharewaretov1_14.rewriter;

import com.viaversion.viaaprilfools.protocol.s3d_sharewaretov1_14.Protocol3D_SharewareTo1_14;
import com.viaversion.viaaprilfools.protocol.s3d_sharewaretov1_14.packet.ClientboundPackets3D_Shareware;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.rewriter.ItemRewriter;

public class BlockItemPacketRewriter3D_Shareware extends ItemRewriter<ClientboundPackets3D_Shareware, ServerboundPackets1_14, Protocol3D_SharewareTo1_14> {

    public BlockItemPacketRewriter3D_Shareware(Protocol3D_SharewareTo1_14 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_SHORT_ARRAY);
    }

    @Override
    protected void registerPackets() {
        this.registerCooldown(ClientboundPackets3D_Shareware.COOLDOWN);
        this.registerSetContent(ClientboundPackets3D_Shareware.CONTAINER_SET_CONTENT);
        this.registerSetSlot(ClientboundPackets3D_Shareware.CONTAINER_SET_SLOT);
        this.registerSetEquippedItem(ClientboundPackets3D_Shareware.SET_EQUIPPED_ITEM);
        this.registerAdvancements(ClientboundPackets3D_Shareware.UPDATE_ADVANCEMENTS);
        this.registerContainerClick(ServerboundPackets1_14.CONTAINER_CLICK);
        this.registerSetCreativeModeSlot(ServerboundPackets1_14.SET_CREATIVE_MODE_SLOT);

        this.protocol.registerClientbound(ClientboundPackets3D_Shareware.MERCHANT_OFFERS, new PacketHandlers() {
            public void register() {
                this.handler((wrapper) -> {
                    wrapper.passthrough(Types.VAR_INT);
                    int size = wrapper.passthrough(Types.UNSIGNED_BYTE);

                    for (int i = 0; i < size; ++i) {
                        BlockItemPacketRewriter3D_Shareware.this.handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                        BlockItemPacketRewriter3D_Shareware.this.handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                        if (wrapper.passthrough(Types.BOOLEAN)) {
                            BlockItemPacketRewriter3D_Shareware.this.handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                        }

                        wrapper.passthrough(Types.BOOLEAN);
                        wrapper.passthrough(Types.INT);
                        wrapper.passthrough(Types.INT);
                        wrapper.passthrough(Types.INT);
                        wrapper.passthrough(Types.INT);
                        wrapper.passthrough(Types.FLOAT);
                    }

                });
            }
        });
    }

}
