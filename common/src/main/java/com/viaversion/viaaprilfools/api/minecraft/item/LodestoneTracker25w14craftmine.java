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

import com.viaversion.viaversion.api.minecraft.GlobalBlockPosition;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public record LodestoneTracker25w14craftmine(GlobalBlockPosition position, boolean tracked, boolean exits) {

    public static final Type<LodestoneTracker25w14craftmine> TYPE = new Type<>(LodestoneTracker25w14craftmine.class) {
        @Override
        public LodestoneTracker25w14craftmine read(final ByteBuf buffer) {
            final GlobalBlockPosition position = Types.OPTIONAL_GLOBAL_POSITION.read(buffer);
            final boolean tracked = buffer.readBoolean();
            final boolean exits = buffer.readBoolean();
            return new LodestoneTracker25w14craftmine(position, tracked, exits);
        }

        @Override
        public void write(final ByteBuf buffer, final LodestoneTracker25w14craftmine value) {
            Types.OPTIONAL_GLOBAL_POSITION.write(buffer, value.position);
            buffer.writeBoolean(value.tracked);
            buffer.writeBoolean(value.exits);
        }
    };

}
