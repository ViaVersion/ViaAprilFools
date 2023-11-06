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
package net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.ClientboundPackets20w14infinite;
import net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.Protocol1_16to20w14infinite;

import java.util.List;

public class MetadataRewriter1_16to20w14infinite extends EntityRewriter<ClientboundPackets20w14infinite, Protocol1_16to20w14infinite> {

    public MetadataRewriter1_16to20w14infinite(Protocol1_16to20w14infinite protocol) {
        super(protocol);
        mapEntityType(Entity20w14infiniteTypes.ZOMBIE_PIGMAN, EntityTypes1_16.ZOMBIFIED_PIGLIN);
        mapTypes(Entity20w14infiniteTypes.values(), EntityTypes1_16.class);
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_16.getTypeFromId(type);
    }

    @Override
    public void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) {
        if (metadata.metaType() == Types1_14.META_TYPES.itemType) {
            metadata.setValue(this.protocol.getItemRewriter().handleItemToClient(metadata.value()));
        } else if (metadata.metaType() == Types1_14.META_TYPES.blockStateType) {
            int data = (int) metadata.getValue();
            metadata.setValue(protocol.getMappingData().getNewBlockStateId(data));
        } else if (metadata.metaType() == Types1_14.META_TYPES.particleType) {
            rewriteParticle((Particle) metadata.getValue());
        }

        if (type == null) return;

        if (type.isOrHasParent(EntityTypes1_16.MINECART_ABSTRACT)
                && metadata.id() == 10) {
            // Convert to new block id
            int data = (int) metadata.getValue();
            metadata.setValue(protocol.getMappingData().getNewBlockStateId(data));
        }

        if (type.isOrHasParent(EntityTypes1_16.ABSTRACT_ARROW)) {
            if (metadata.id() == 8) {
                metadatas.remove(metadata);
            } else if (metadata.id() > 8) {
                metadata.setId(metadata.id() - 1);
            }
        }
    }

}
