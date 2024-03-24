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
package net.raphimc.viaaprilfools.api;

import com.viaversion.viabackwards.api.data.BackwardsMappingDataLoader;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import net.raphimc.viaaprilfools.ViaAprilFools;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.logging.Logger;

public class AprilFoolsMappings extends BackwardsMappings {

    private static final BackwardsMappingDataLoader LOADER = new BackwardsMappingDataLoader(AprilFoolsMappings.class, "assets/viaaprilfools/data/");

    public AprilFoolsMappings(String unmappedVersion, String mappedVersion, @Nullable Class<? extends Protocol<?, ?, ?, ?>> vvProtocolClass) {
        super(unmappedVersion, mappedVersion, vvProtocolClass);
    }

    @Override
    protected @Nullable CompoundTag readMappingsFile(String name) {
        return LOADER.loadNBT(name);
    }

    @Override
    protected @Nullable CompoundTag readUnmappedIdentifiersFile(String name) {
        return LOADER.loadNBT(name, true);
    }

    @Override
    protected Logger getLogger() {
        return ViaAprilFools.getPlatform().getLogger();
    }
}
