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
import com.viaversion.viaaprilfools.ViaAprilFools;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.Mappings;
import java.util.logging.Logger;

public class VAFMappingData extends MappingDataBase {

    public VAFMappingData(String unmappedVersion, String mappedVersion) {
        super(unmappedVersion, mappedVersion);
    }

    @Override
    protected CompoundTag readMappingsFile(String name) {
        return VAFMappingDataLoader.INSTANCE.loadNBT(name, true);
    }

    @Override
    protected CompoundTag readMappedIdentifiersFile(String name) {
        return VAFMappingDataLoader.INSTANCE.loadNBT(name, true);
    }

    @Override
    protected Mappings loadMappings(CompoundTag data, String key) {
        return VAFMappingDataLoader.INSTANCE.loadMappings(data, key);
    }

    @Override
    protected Logger getLogger() {
        return ViaAprilFools.getPlatform().getLogger();
    }

}
