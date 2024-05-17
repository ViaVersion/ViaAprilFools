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
package net.raphimc.viaaprilfools.protocol.s3d_sharewaretov1_14.rewriter;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import net.raphimc.viaaprilfools.protocol.s3d_sharewaretov1_14.packet.ClientboundPackets3D_Shareware;
import net.raphimc.viaaprilfools.protocol.s3d_sharewaretov1_14.Protocol1_14to3D_Shareware;

import java.util.List;

public class EntityPacketRewriter3D_Shareware extends RewriterBase<Protocol1_14to3D_Shareware> {

    public EntityPacketRewriter3D_Shareware(Protocol1_14to3D_Shareware protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        this.protocol.registerClientbound(ClientboundPackets3D_Shareware.ADD_MOB, new PacketHandlers() {
            @Override
            public void register() {
                map(Types.VAR_INT); // 0 - Entity ID
                map(Types.UUID); // 1 - Entity UUID
                map(Types.VAR_INT); // 2 - Entity Type
                map(Types.DOUBLE); // 3 - X
                map(Types.DOUBLE); // 4 - Y
                map(Types.DOUBLE); // 5 - Z
                map(Types.BYTE); // 6 - Yaw
                map(Types.BYTE); // 7 - Pitch
                map(Types.BYTE); // 8 - Head Pitch
                map(Types.SHORT); // 9 - Velocity X
                map(Types.SHORT); // 10 - Velocity Y
                map(Types.SHORT); // 11 - Velocity Z
                map(Types1_14.ENTITY_DATA_LIST); // 12 - Entity data
                handler(packetWrapper -> handleEntityData(packetWrapper.user(), packetWrapper.get(Types1_14.ENTITY_DATA_LIST, 0)));
            }
        });
        this.protocol.registerClientbound(ClientboundPackets3D_Shareware.ADD_PLAYER, new PacketHandlers() {
            @Override
            public void register() {
                map(Types.VAR_INT); // 0 - Entity ID
                map(Types.UUID); // 1 - Player UUID
                map(Types.DOUBLE); // 2 - X
                map(Types.DOUBLE); // 3 - Y
                map(Types.DOUBLE); // 4 - Z
                map(Types.BYTE); // 5 - Yaw
                map(Types.BYTE); // 6 - Pitch
                map(Types1_14.ENTITY_DATA_LIST); // 7 - Entity data
                handler(packetWrapper -> handleEntityData(packetWrapper.user(), packetWrapper.get(Types1_14.ENTITY_DATA_LIST, 0)));
            }
        });
        this.protocol.registerClientbound(ClientboundPackets3D_Shareware.SET_ENTITY_DATA, new PacketHandlers() {
            @Override
            public void register() {
                map(Types.VAR_INT); // 0 - Entity ID
                map(Types1_14.ENTITY_DATA_LIST);
                handler(packetWrapper -> handleEntityData(packetWrapper.user(), packetWrapper.get(Types1_14.ENTITY_DATA_LIST, 0)));
            }
        });
    }

    public void handleEntityData(final UserConnection user, final List<EntityData> entityDataList) {
        for (EntityData entityData : entityDataList) {
            if (entityData.dataType() == Types1_14.ENTITY_DATA_TYPES.itemType) {
                entityData.setValue(this.protocol.getItemRewriter().handleItemToClient(user, entityData.value()));
            }
        }
    }

}
