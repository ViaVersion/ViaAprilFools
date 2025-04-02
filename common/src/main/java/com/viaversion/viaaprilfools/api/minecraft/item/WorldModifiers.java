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
package com.viaversion.viaaprilfools.api.minecraft.item;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.Copyable;
import io.netty.buffer.ByteBuf;

public record WorldModifiers(int[] effects, boolean includeDescription) implements Copyable {

    public static final Type<WorldModifiers> TYPE = new Type<>(WorldModifiers.class) {
        @Override
        public WorldModifiers read(ByteBuf byteBuf) {
            final int effectsLength = Types.VAR_INT.readPrimitive(byteBuf);
            final int[] effects = new int[effectsLength];
            for (int i = 0; i < effectsLength; i++) {
                effects[i] = Types.VAR_INT.readPrimitive(byteBuf);
            }
            final boolean includeDescription = Types.BOOLEAN.read(byteBuf);
            return new WorldModifiers(effects, includeDescription);
        }

        @Override
        public void write(ByteBuf byteBuf, WorldModifiers worldModifiers) {
            final int[] effects = worldModifiers.effects();
            Types.VAR_INT.writePrimitive(byteBuf, effects.length);
            for (final int effect : effects) {
                Types.VAR_INT.writePrimitive(byteBuf, effect);
            }
            Types.BOOLEAN.write(byteBuf, worldModifiers.includeDescription());
        }
    };

    @Override
    public WorldModifiers copy() {
        return new WorldModifiers(copy(effects), includeDescription);
    }

}
