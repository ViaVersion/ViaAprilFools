/*
 * This file is part of ViaAprilFools - https://github.com/ViaVersion/ViaAprilFools
 * Copyright (C) 2021-2026 the original authors
 *                         - RK_01/RaphiMC
 *                         - Florian Reuth <git@florianreuth.de>
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
package com.viaversion.viaaprilfools.protocol.v26_1tos26w14a.rewriter;

import com.viaversion.viaaprilfools.api.minecraft.entities.EntityDataTypes26w14a;
import com.viaversion.viaaprilfools.api.minecraft.entities.EntityTypes26w14a;
import com.viaversion.viaaprilfools.protocol.v26_1tos26w14a.Protocol26_1To26w14a;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.protocols.v1_21_11to26_1.packet.ClientboundPacket26_1;
import com.viaversion.viaversion.protocols.v1_21_11to26_1.packet.ClientboundPackets26_1;
import com.viaversion.viaversion.rewriter.EntityRewriter;

public final class EntityPacketRewriter26w14a extends EntityRewriter<ClientboundPacket26_1, Protocol26_1To26w14a> {

    public EntityPacketRewriter26w14a(final Protocol26_1To26w14a protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        registerTrackerWithData1_21_9(ClientboundPackets26_1.ADD_ENTITY);
        registerSetEntityData(ClientboundPackets26_1.SET_ENTITY_DATA);
        registerRemoveEntities(ClientboundPackets26_1.REMOVE_ENTITIES);
        registerPlayerAbilities(ClientboundPackets26_1.PLAYER_ABILITIES);
        registerGameEvent(ClientboundPackets26_1.GAME_EVENT);
        registerLogin1_20_5(ClientboundPackets26_1.LOGIN);
        registerRespawn1_20_5(ClientboundPackets26_1.RESPAWN);
    }

    @Override
    protected void registerRewrites() {
        final EntityDataTypes26w14a entityDataTypes = protocol.mappedTypes().entityDataTypes();
        filter().mapDataType(id -> {
            if (id >= entityDataTypes.targetType.typeId()) {
                id++;
            }
            if (id >= entityDataTypes.movementDeltaType.typeId()) {
                id++;
            }
            if (id >= entityDataTypes.slotDisplayType.typeId()) {
                id++;
            }
            return entityDataTypes.byId(id);
        });
        registerEntityDataTypeHandler(
            entityDataTypes.itemType,
            entityDataTypes.blockStateType,
            entityDataTypes.optionalBlockStateType,
            entityDataTypes.particleType,
            entityDataTypes.particlesType,
            entityDataTypes.componentType,
            entityDataTypes.optionalComponentType
        );
    }

    @Override
    public EntityType typeFromId(final int type) {
        return EntityTypes26w14a.getTypeFromId(type);
    }

}
