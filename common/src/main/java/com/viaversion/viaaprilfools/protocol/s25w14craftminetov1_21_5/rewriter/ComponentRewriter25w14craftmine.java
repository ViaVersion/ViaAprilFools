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
package com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.Protocol25w14craftmineTo1_21_5;
import com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.data.MappingData25w14craftmine;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet.ClientboundPacket25w14craftmine;
import com.viaversion.viabackwards.api.rewriters.text.TranslatableRewriter;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.rewriter.text.NBTComponentRewriter;

import static com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.rewriter.BlockItemPacketRewriter25w14craftmine.NEW_DATA_TO_REMOVE;
import static com.viaversion.viaversion.util.TagUtil.getNamespacedCompoundTag;

public final class ComponentRewriter25w14craftmine extends NBTComponentRewriter<ClientboundPacket25w14craftmine> implements TranslatableRewriter {

    private final MappingData25w14craftmine mappingData;

    public ComponentRewriter25w14craftmine(final Protocol25w14craftmineTo1_21_5 protocol) {
        super(protocol);
        this.mappingData = protocol.getMappingData();
    }

    @Override
    protected void handleTranslate(final JsonObject root, final String translate) {
        final String newTranslate = mappedTranslationKey(translate);
        if (newTranslate != null) {
            root.addProperty("translate", newTranslate);
        }
    }

    @Override
    protected void handleTranslate(final UserConnection connection, final CompoundTag parentTag, final StringTag translateTag) {
        final String newTranslate = mappedTranslationKey(translateTag.getValue());
        if (newTranslate != null) {
            parentTag.put("translate", new StringTag(newTranslate));
        }
    }

    @Override
    protected void handleShowItem(final UserConnection connection, final CompoundTag itemTag, final CompoundTag componentsTag) {
        super.handleShowItem(connection, itemTag, componentsTag);
        if (componentsTag == null) {
            return;
        }

        final CompoundTag lodestoneTracker = getNamespacedCompoundTag(componentsTag, "lodestoneTracker");
        if (lodestoneTracker != null) {
            lodestoneTracker.remove("exits");
        }

        removeDataComponents(componentsTag, NEW_DATA_TO_REMOVE);
    }

    @Override
    public String mappedTranslationKey(String translationKey) {
        return this.mappingData.getTranslation(translationKey);
    }

}
