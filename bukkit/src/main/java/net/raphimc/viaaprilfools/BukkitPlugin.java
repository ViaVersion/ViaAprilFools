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

import com.viaversion.viaversion.api.Via;
import net.raphimc.viaaprilfools.platform.ViaAprilFoolsPlatform;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BukkitPlugin extends JavaPlugin implements ViaAprilFoolsPlatform {

    public BukkitPlugin() {
        Via.getManager().addEnableListener(() -> this.init(new File(getDataFolder(), "config.yml")));
    }

}
