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
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryEntry;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.rewriter.RegistryDataRewriter;
import com.viaversion.viaversion.util.Key;

public final class RegistryDataRewriter25w14craftmine extends RegistryDataRewriter {

    public RegistryDataRewriter25w14craftmine(Protocol<?, ?, ?, ?> protocol) {
        super(protocol);

        // Now back to inlined strings - reuse the dimension key
        addHandler("dimension_type", (key, compoundTag) -> compoundTag.putString("effects", Key.namespaced(key)));
    }

    @Override
    public RegistryEntry[] handle(UserConnection connection, String key, RegistryEntry[] entries) {
        for (final RegistryEntry entry : entries) {
            if (entry.tag() == null) {
                continue;
            }

            if (entry.tag() instanceof CompoundTag compoundTag) {
                final String soundEvent = compoundTag.getString("sound_event");
                if (soundEvent != null && soundEvent.startsWith("nothingtoseehere")) {
                    // Cancelled in normal packets and easier than removing registry entries
                    compoundTag.putString("sound_event", "minecraft:intentionally_empty");
                }
            }
        }
        return super.handle(connection, key, entries);
    }

}
