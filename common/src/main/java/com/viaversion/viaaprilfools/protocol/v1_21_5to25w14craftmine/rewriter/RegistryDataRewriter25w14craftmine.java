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
package com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.rewriter;

import com.viaversion.viaversion.api.minecraft.RegistryEntry;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.rewriter.RegistryDataRewriter;

import static com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.data.DimensionTypes25w14craftmine.getGeneratedDimensionTag;
import static com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.data.DimensionTypes25w14craftmine.getOverworldCavesDimensionEffectsTag;

public final class RegistryDataRewriter25w14craftmine extends RegistryDataRewriter {

    public RegistryDataRewriter25w14craftmine(Protocol<?, ?, ?, ?> protocol) {
        super(protocol);

        // Use the ones that are sent with the minecraft:generated dimension as it's the most similar to a normal world, also still used in minecraft:overworld_caves
        addHandler("dimension_type", (key, compoundTag) -> compoundTag.put("effects", getOverworldCavesDimensionEffectsTag()));
        addEntries("dimension_type", new RegistryEntry("minecraft:generated", getGeneratedDimensionTag()));
    }

}
