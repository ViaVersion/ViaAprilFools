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

import com.viaversion.viaaprilfools.api.type.version.VAFTypes;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public record MobTrophyInfo(Holder<String> type, boolean shiny) {

    public static final Type<MobTrophyInfo> TYPE = new Type<>(MobTrophyInfo.class) {
        @Override
        public MobTrophyInfo read(ByteBuf byteBuf) {
            final Holder<String> type = VAFTypes.HOLDER_STRING.read(byteBuf);
            final boolean shiny = byteBuf.readBoolean();
            return new MobTrophyInfo(type, shiny);
        }

        @Override
        public void write(ByteBuf byteBuf, MobTrophyInfo mobTrophyInfo) {
            VAFTypes.HOLDER_STRING.write(byteBuf, mobTrophyInfo.type());
            byteBuf.writeBoolean(mobTrophyInfo.shiny());
        }
    };

}
