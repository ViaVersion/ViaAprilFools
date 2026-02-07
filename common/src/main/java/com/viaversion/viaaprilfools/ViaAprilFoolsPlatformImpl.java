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
package com.viaversion.viaaprilfools;

import com.viaversion.viaaprilfools.platform.ViaAprilFoolsPlatform;
import com.viaversion.viaversion.api.Via;
import java.io.File;
import java.util.logging.Logger;

public class ViaAprilFoolsPlatformImpl implements ViaAprilFoolsPlatform {

    private final Logger logger;

    public ViaAprilFoolsPlatformImpl() {
        logger = Via.getPlatform().createLogger("ViaAprilFools");
        init(new File(getDataFolder(), "viaaprilfools.yml"));
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public File getDataFolder() {
        return Via.getPlatform().getDataFolder();
    }
}
