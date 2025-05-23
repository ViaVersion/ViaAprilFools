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

import com.viaversion.viabackwards.api.data.BackwardsMappingDataLoader;
import com.viaversion.viaaprilfools.ViaAprilFools;

import java.io.File;
import java.util.logging.Logger;

public class VAFBackwardsMappingDataLoader extends BackwardsMappingDataLoader {

    public static final VAFBackwardsMappingDataLoader INSTANCE = new VAFBackwardsMappingDataLoader();

    public VAFBackwardsMappingDataLoader() {
        super(VAFBackwardsMappingDataLoader.class, "assets/viaaprilfools/data/");
    }

    @Override
    public String identifierFromGlobalId(final String registry, final int globalId) {
        final String[] array = VAFMappingDataLoader.GLOBAL_IDENTIFIER_INDEXES.get(registry);
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
