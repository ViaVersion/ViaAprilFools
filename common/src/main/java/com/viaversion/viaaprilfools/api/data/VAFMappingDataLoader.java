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
package com.viaversion.viaaprilfools.api.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaaprilfools.ViaAprilFools;
import com.viaversion.viaversion.api.data.MappingDataLoader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class VAFMappingDataLoader extends MappingDataLoader {

    static final Map<String, String[]> GLOBAL_IDENTIFIER_INDEXES = new HashMap<>();
    public static final VAFMappingDataLoader INSTANCE = new VAFMappingDataLoader();

    public VAFMappingDataLoader() {
        super(VAFMappingDataLoader.class, "assets/viaaprilfools/data/");
    }

    public static void loadGlobalIdentifiers() {
        // Override VV identifier table for our own protocols/mappings
        final CompoundTag globalIdentifiers = INSTANCE.loadNBT("identifier-table.nbt");
        for (final Map.Entry<String, Tag> entry : globalIdentifiers.entrySet()) {
            //noinspection unchecked
            final ListTag<StringTag> value = (ListTag<StringTag>) entry.getValue();
            final String[] array = new String[value.size()];
            for (int i = 0, size = value.size(); i < size; i++) {
                array[i] = value.get(i).getValue();
            }
            GLOBAL_IDENTIFIER_INDEXES.put(entry.getKey(), array);
        }
    }

    @Override
    public String identifierFromGlobalId(final String registry, final int globalId) {
        final String[] array = GLOBAL_IDENTIFIER_INDEXES.get(registry);
        if (array == null) {
            throw new IllegalArgumentException("Unknown global identifier key: " + registry);
        }
        if (globalId < 0 || globalId >= array.length) {
            throw new IllegalArgumentException("Unknown global identifier index: " + globalId);
        }
        return array[globalId];
    }

    @Override
    public File getDataFolder() {
        return ViaAprilFools.getPlatform().getDataFolder();
    }

    @Override
    public Logger getLogger() {
        return ViaAprilFools.getPlatform().getLogger();
    }

}
