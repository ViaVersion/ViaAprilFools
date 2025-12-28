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
package com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.data;

import com.viaversion.nbt.tag.CompoundTag;

public final class DimensionTypes25w14craftmine {

    private static final CompoundTag OVERWORLD_CAVES_EFFECT = createOverworldCavesEffect();
    private static final CompoundTag GENERATED_DIMENSION_TAG = createGeneratedDimension();

    private static CompoundTag createGeneratedDimension() {
        final CompoundTag tag = new CompoundTag();
        tag.putFloat("ambient_light", 0F);
        tag.putByte("bed_works", (byte) 1);
        tag.putDouble("coordinate_scale", 1D);
        tag.put("effects", OVERWORLD_CAVES_EFFECT.copy());
        tag.putByte("has_ceiling", (byte) 0);
        tag.putByte("has_raids", (byte) 1);
        tag.putByte("has_skylight", (byte) 1);
        tag.putInt("height", 384);
        tag.putString("infiniburn", "#minecraft:infiniburn_overworld");
        tag.putInt("logical_height", 384);
        tag.putInt("min_y", -64);
        tag.putInt("monster_spawn_block_light_limit", 0);
        final CompoundTag monsterSpawnLightLevel = new CompoundTag();
        tag.put("monster_spawn_light_level", monsterSpawnLightLevel);
        monsterSpawnLightLevel.putInt("max_inclusive", 7);
        monsterSpawnLightLevel.putInt("min_inclusive", 0);
        monsterSpawnLightLevel.putString("type", "minecraft:uniform");
        tag.putByte("natural", (byte) 1);
        tag.putByte("piglin_safe", (byte) 0);
        tag.putByte("respawn_anchor_works", (byte) 0);
        tag.putByte("ultrawarm", (byte) 0);
        return tag;
    }

    private static CompoundTag createOverworldCavesEffect() {
        final CompoundTag tag = new CompoundTag();
        tag.putFloat("cloud_level", 192F);
        tag.putByte("constant_ambient_light", (byte) 0);
        tag.putString("fog_scaler", "overworld");
        tag.putByte("force_bright_lightmap", (byte) 0);
        tag.putByte("has_ground", (byte) 1);
        tag.putByte("has_sunrise_and_sunset", (byte) 1);
        tag.putByte("is_always_foggy", (byte) 0);
        final CompoundTag skyType = new CompoundTag();
        tag.put("sky_type", skyType);
        skyType.putString("type", "overworld");
        return tag;
    }

    public static CompoundTag getGeneratedDimensionTag() {
        return GENERATED_DIMENSION_TAG.copy();
    }

    public static CompoundTag getOverworldCavesDimensionEffectsTag() {
        return OVERWORLD_CAVES_EFFECT.copy();
    }

}
