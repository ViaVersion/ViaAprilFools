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
package com.viaversion.viaaprilfools.api.minecraft.item;

import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.data.version.StructuredDataKeys1_21_5;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.VersionedTypesHolder;
import com.viaversion.viaversion.util.Unit;

public class StructuredDataKeys25w14craftmine extends StructuredDataKeys1_21_5 {

    public static final StructuredDataKey<ItemExchangeValue> ITEM_EXCHANGE_VALUE = new StructuredDataKey<>("exchange_value", ItemExchangeValue.TYPE);
    public static final StructuredDataKey<Unit> WORLD_EFFECT_UNLOCK = new StructuredDataKey<>("world_effect_unlock", Types.EMPTY);
    public static final StructuredDataKey<Unit> WORLD_EFFECT_HINT = new StructuredDataKey<>("world_effect_uhint", Types.EMPTY); // Mfw typos
    public static final StructuredDataKey<Unit> MINE_ACTIVE = new StructuredDataKey<>("mine_active", Types.EMPTY);
    public static final StructuredDataKey<Integer> SPECIAL_MINE = new StructuredDataKey<>("special_mine", Types.VAR_INT);
    public static final StructuredDataKey<Boolean> MINE_COMPLETED = new StructuredDataKey<>("mine_completed", Types.BOOLEAN);
    public static final StructuredDataKey<WorldModifiers> WORLD_MODIFIERS = new StructuredDataKey<>("world_modifiers", WorldModifiers.TYPE);
    public static final StructuredDataKey<String> DIMENSION_ID = new StructuredDataKey<>("dimension_id", Types.STRING);
    public static final StructuredDataKey<LodestoneTracker25w14craftmine> LODESTONE_TRACKER = new StructuredDataKey<>("lodestone_tracker", LodestoneTracker25w14craftmine.TYPE);
    public static final StructuredDataKey<RoomerinoComponentino> ROOM = new StructuredDataKey<>("instant_room", RoomerinoComponentino.TYPE);
    public static final StructuredDataKey<Integer> SKY = new StructuredDataKey<>("sky", Types.INT);
    public static final StructuredDataKey<String> TROPHY_TYPE = new StructuredDataKey<>("trophy/type", Types.STRING);
    public static final StructuredDataKey<MobTrophyInfo> MOB_TROPHY_TYPE = new StructuredDataKey<>("mob_trophy/type", MobTrophyInfo.TYPE);

    public StructuredDataKeys25w14craftmine(VersionedTypesHolder types) {
        super(types);
    }

}
