/*
 * This file is part of ViaAprilFools - https://github.com/RaphiMC/ViaAprilFools
 * Copyright (C) 2021-2025 RK_01/RaphiMC and contributors
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
package net.raphimc.viaaprilfools.api.data;

import com.viaversion.viabackwards.api.data.BackwardsMappingDataLoader;
import net.raphimc.viaaprilfools.ViaAprilFools;

import java.io.File;
import java.util.logging.Logger;

public class AprilFoolsMappingDataLoader extends BackwardsMappingDataLoader {

    public static final AprilFoolsMappingDataLoader INSTANCE = new AprilFoolsMappingDataLoader();

    public AprilFoolsMappingDataLoader() {
        super(AprilFoolsMappingDataLoader.class, "assets/viaaprilfools/data/");
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
