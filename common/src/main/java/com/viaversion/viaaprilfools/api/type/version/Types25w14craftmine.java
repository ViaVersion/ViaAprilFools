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

import com.viaversion.viaaprilfools.api.minecraft.entities.EntityTypes25w14craftmine;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.data.StructuredData;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.ArrayType;
import com.viaversion.viaversion.api.type.types.item.ItemCostType1_20_5;
import com.viaversion.viaversion.api.type.types.item.ItemType1_20_5;
import com.viaversion.viaversion.api.type.types.item.LengthPrefixedStructuredDataType;
import com.viaversion.viaversion.api.type.types.item.StructuredDataType;
import com.viaversion.viaversion.api.type.types.misc.HolderType;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;

// Most of these are only safe to use after protocol loading
public final class Types25w14craftmine {

    public static final StructuredDataType STRUCTURED_DATA = new StructuredDataType();
    public static final LengthPrefixedStructuredDataType LENGTH_PREFIXED_STRUCTURED_DATA = new LengthPrefixedStructuredDataType(STRUCTURED_DATA);
    public static final Type<StructuredData<?>[]> STRUCTURED_DATA_ARRAY = new ArrayType<>(STRUCTURED_DATA);
    public static final ItemType1_20_5 ITEM = new ItemType1_20_5(STRUCTURED_DATA);
    public static final ItemType1_20_5 LENGTH_PREFIXED_ITEM = new ItemType1_20_5(LENGTH_PREFIXED_STRUCTURED_DATA);
    public static final Type<Item[]> ITEM_ARRAY = new ArrayType<>(ITEM);
    public static final Type<Item> ITEM_COST = new ItemCostType1_20_5(STRUCTURED_DATA_ARRAY);
    public static final Type<Item> OPTIONAL_ITEM_COST = new ItemCostType1_20_5.OptionalItemCostType(ITEM_COST);

    public static final ParticleType PARTICLE = new ParticleType();
    public static final ArrayType<Particle> PARTICLES = new ArrayType<>(PARTICLE);

    public static final HolderType<EntityTypes25w14craftmine> ENTITY_TYPE = new EntityTypesType();

}
