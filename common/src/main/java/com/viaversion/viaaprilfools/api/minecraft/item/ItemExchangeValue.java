/*
 * This file is part of ViaAprilFools - https://github.com/ViaVersion/ViaAprilFools
 * Copyright (C) 2021-2026 the original authors
 *                         - RK_01/RaphiMC
 *                         - FlorianMichael/EnZaXD <git@florianmichael.de>
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
package com.viaversion.viaaprilfools.api.minecraft.item;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public record ItemExchangeValue(float value) {

    public static final Type<ItemExchangeValue> TYPE = new Type<>(ItemExchangeValue.class) {
        @Override
        public ItemExchangeValue read(ByteBuf byteBuf) {
            final float value = Types.FLOAT.readPrimitive(byteBuf);
            return new ItemExchangeValue(value);
        }

        @Override
        public void write(ByteBuf byteBuf, ItemExchangeValue itemExchangeValue) {
            Types.FLOAT.writePrimitive(byteBuf, itemExchangeValue.value);
        }
    };

}
