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
package net.raphimc.viaaprilfools;

import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.velocity.util.LoggerWrapper;
import net.raphimc.viaaprilfools.platform.ViaAprilFoolsPlatform;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(id = "viaaprilfools",
        name = "ViaAprilFools",
        version = ViaAprilFools.VERSION,
        authors = {"RK_01", "FlorianMichael/EnZaXD"},
        description = "ViaVersion addon to add support for some notable Minecraft snapshots",
        dependencies = {
                @Dependency(id = "viaversion"),
                @Dependency(id = "viabackwards")
        },
        url = "https://viaversion.com/aprilfools"
)
public class VelocityPlugin implements ViaAprilFoolsPlatform {

    private Logger logger;

    @Inject
    private org.slf4j.Logger loggerSlf4j;

    @Inject
    @DataDirectory
    private Path configPath;

    @Subscribe(order = PostOrder.LATE)
    public void onProxyStart(ProxyInitializeEvent e) {
        this.logger = new LoggerWrapper(loggerSlf4j);
        Via.getManager().addEnableListener(() -> this.init(new File(getDataFolder(), "config.yml")));
    }

    @Override
    public File getDataFolder() {
        return configPath.toFile();
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }
}
