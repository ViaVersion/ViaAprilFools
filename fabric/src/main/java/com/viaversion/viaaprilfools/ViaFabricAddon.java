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
package com.viaversion.viaaprilfools;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaManager;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import net.fabricmc.loader.api.FabricLoader;
import com.viaversion.viaaprilfools.api.VAFServerVersionProvider;
import com.viaversion.viaaprilfools.fabric.util.LoggerWrapper;
import com.viaversion.viaaprilfools.platform.ViaAprilFoolsPlatform;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

public class ViaFabricAddon implements ViaAprilFoolsPlatform, Runnable {

    private final Logger logger = new LoggerWrapper(LogManager.getLogger("ViaAprilFools"));
    private File configDir;

    @Override
    public void run() {
        final Path configDirPath = FabricLoader.getInstance().getConfigDir().resolve("ViaAprilFools");
        this.configDir = configDirPath.toFile();
        this.init(new File(getDataFolder(), "config.yml"));

        final ViaManager manager = Via.getManager();
        manager.addPostEnableListener(() -> {
            final VersionProvider delegate = manager.getProviders().get(VersionProvider.class);
            manager.getProviders().use(VersionProvider.class, new VAFServerVersionProvider(delegate));
        });
    }

    @Override
    public File getDataFolder() {
        return this.configDir;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

}
