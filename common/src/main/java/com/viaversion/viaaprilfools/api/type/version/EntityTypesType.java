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
package com.viaversion.viaaprilfools.api.type.version;

import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_21_5;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.HolderType;
import com.viaversion.viaversion.util.Key;
import io.netty.buffer.ByteBuf;

public final class EntityTypesType extends HolderType<EntityTypes1_21_5> {

    @Override
    public EntityTypes1_21_5 readDirect(ByteBuf byteBuf) {
        final String resourceLocation = Types.STRING.read(byteBuf);
        for (EntityTypes1_21_5 value : EntityTypes1_21_5.values()) {
            if (value.identifier().equals(Key.namespaced(resourceLocation))) {
                return value;
            }
        }
        return EntityTypes1_21_5.PIG;
    }

    @Override
    public void writeDirect(ByteBuf byteBuf, EntityTypes1_21_5 entityType) {
        if (entityType == null) {
            Types.STRING.write(byteBuf, "minecraft:pig");
        } else {
            Types.STRING.write(byteBuf, entityType.identifier());
        }
    }

}
