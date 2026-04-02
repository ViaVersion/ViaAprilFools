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
package com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.BackwardsRegistryRewriter;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryEntry;
import com.viaversion.viaversion.util.ArrayUtil;
import com.viaversion.viaversion.util.Key;

public final class RegistryDataRewriter25w14craftmine extends BackwardsRegistryRewriter {

    public RegistryDataRewriter25w14craftmine(BackwardsProtocol<?, ?, ?, ?> protocol) {
        super(protocol);

        // Now back to inlined strings - reuse the dimension key
        addHandler("dimension_type", (key, compoundTag) -> compoundTag.putString("effects", Key.namespaced(key)));
    }

    @Override
    public RegistryEntry[] handle(UserConnection connection, String key, RegistryEntry[] entries) {
        for (int i = 0; i < entries.length; i++) {
            final RegistryEntry entry = entries[i];
            if (Key.equals(key, "dimension_type") && Key.equals(entry.key(), "generated")) {
                // Remove the new dimension type
                entries = ArrayUtil.remove(entries, i--);
                continue;
            }

            if (entry.tag() instanceof final CompoundTag tag) {
                final String soundEvent = tag.getString("sound_event");
                if (soundEvent != null && Key.namespace(soundEvent).equals("nothingtoseehere")) { // Map to something else...
                    tag.putString("sound_event", "intentionally_empty");
                }
            }
        }
        return super.handle(connection, key, entries);
    }

}
