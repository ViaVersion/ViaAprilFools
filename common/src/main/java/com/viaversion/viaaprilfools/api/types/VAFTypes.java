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
package com.viaversion.viaaprilfools.api.types;

import com.viaversion.viaaprilfools.api.minecraft.item.StructuredDataKeys25w14craftmine;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_21_5;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.HolderType;
import com.viaversion.viaversion.api.type.types.version.Types1_20_5;
import io.netty.buffer.ByteBuf;

public final class VAFTypes {

    public static final HolderType<String> HOLDER_STRING = new HolderType<>() {
        @Override
        public String readDirect(ByteBuf byteBuf) {
            return Types.STRING.read(byteBuf);
        }

        @Override
        public void writeDirect(ByteBuf byteBuf, String s) {
            Types.STRING.write(byteBuf, s);
        }
    };

    public static final Types1_20_5<StructuredDataKeys25w14craftmine, EntityDataTypes1_21_5> V25W14CRAFTMINE = new Types1_20_5<>(StructuredDataKeys25w14craftmine::new, EntityDataTypes1_21_5::new);

}
