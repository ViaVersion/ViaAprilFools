/*
 * This file is part of ViaAprilFools - https://github.com/RaphiMC/ViaAprilFools
 * Copyright (C) 2021-2024 RK_01/RaphiMC and contributors
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
package net.raphimc.viaaprilfools.protocols.protocol1_14to3D_Shareware.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import net.raphimc.viaaprilfools.protocols.protocol1_14to3D_Shareware.ClientboundPackets3D_Shareware;
import net.raphimc.viaaprilfools.protocols.protocol1_14to3D_Shareware.Protocol1_14to3D_Shareware;

public class BlockItemPackets3D_Shareware extends ItemRewriter<ClientboundPackets3D_Shareware, ServerboundPackets1_14, Protocol1_14to3D_Shareware> {

    public BlockItemPackets3D_Shareware(Protocol1_14to3D_Shareware protocol) {
        super(protocol, Type.ITEM1_13_2, Type.ITEM1_13_2_SHORT_ARRAY);
    }

    @Override
    protected void registerPackets() {
        this.registerSetCooldown(ClientboundPackets3D_Shareware.COOLDOWN);
        this.registerWindowItems(ClientboundPackets3D_Shareware.WINDOW_ITEMS);
        this.registerSetSlot(ClientboundPackets3D_Shareware.SET_SLOT);
        this.registerEntityEquipment(ClientboundPackets3D_Shareware.ENTITY_EQUIPMENT);
        this.registerAdvancements(ClientboundPackets3D_Shareware.ADVANCEMENTS);
        this.registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW);
        this.registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION);
        this.registerSpawnParticle(ClientboundPackets3D_Shareware.SPAWN_PARTICLE, Type.FLOAT);

        this.protocol.registerClientbound(ClientboundPackets3D_Shareware.TRADE_LIST, new PacketHandlers() {
            public void register() {
                this.handler((wrapper) -> {
                    wrapper.passthrough(Type.VAR_INT);
                    int size = wrapper.passthrough(Type.UNSIGNED_BYTE);

                    for (int i = 0; i < size; ++i) {
                        BlockItemPackets3D_Shareware.this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
                        BlockItemPackets3D_Shareware.this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
                        if (wrapper.passthrough(Type.BOOLEAN)) {
                            BlockItemPackets3D_Shareware.this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
                        }

                        wrapper.passthrough(Type.BOOLEAN);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.FLOAT);
                    }

                });
            }
        });
    }

}
