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
package net.raphimc.viaaprilfools;

import net.raphimc.viaaprilfools.platform.ViaAprilFoolsConfig;
import net.raphimc.viaaprilfools.platform.ViaAprilFoolsPlatform;

public class ViaAprilFools {

    public static final String VERSION = "${version}";
    public static final String IMPL_VERSION = "${impl_version}";

    private static ViaAprilFoolsPlatform platform;
    private static ViaAprilFoolsConfig config;

    private ViaAprilFools() {
    }

    public static void init(final ViaAprilFoolsPlatform platform, final ViaAprilFoolsConfig config) {
        if (ViaAprilFools.platform != null) throw new IllegalStateException("ViaAprilFools is already initialized");

        ViaAprilFools.platform = platform;
        ViaAprilFools.config = config;
    }

    public static ViaAprilFoolsPlatform getPlatform() {
        return ViaAprilFools.platform;
    }

    public static ViaAprilFoolsConfig getConfig() {
        return ViaAprilFools.config;
    }

}
