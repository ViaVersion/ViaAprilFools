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
package net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.metadata;

import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.ClientboundPackets20w14infinite;
import net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.Protocol1_16to20w14infinite;

public class MetadataRewriter1_16to20w14infinite extends EntityRewriter<ClientboundPackets20w14infinite, Protocol1_16to20w14infinite> {

    public MetadataRewriter1_16to20w14infinite(Protocol1_16to20w14infinite protocol) {
        super(protocol);
    }

    @Override
    protected void registerRewrites() {
        registerMetaTypeHandler(Types1_14.META_TYPES.itemType, Types1_14.META_TYPES.blockStateType, Types1_14.META_TYPES.particleType);

        filter().type(EntityTypes1_16.MINECART_ABSTRACT).index(10).handler((event, meta) -> {
            // Convert to new block id
            int data = (int) meta.getValue();
            meta.setValue(protocol.getMappingData().getNewBlockStateId(data));
        });

        filter().type(EntityTypes1_16.ABSTRACT_ARROW).removeIndex(8);
    }

    @Override
    public void onMappingDataLoaded() {
        mapTypes();
        mapEntityType(Entity20w14infiniteTypes.ZOMBIE_PIGMAN, EntityTypes1_16.ZOMBIFIED_PIGLIN);
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_16.getTypeFromId(type);
    }

}
