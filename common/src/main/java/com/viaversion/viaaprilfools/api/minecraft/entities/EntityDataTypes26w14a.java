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
package com.viaversion.viaaprilfools.api.minecraft.entities;

import com.viaversion.viaversion.api.minecraft.PaintingVariant;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.minecraft.entitydata.types.AbstractEntityDataTypes;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.VersionedTypesHolder;

public final class EntityDataTypes26w14a extends AbstractEntityDataTypes {

    public final EntityDataType byteType = add(0, Types.BYTE);
    public final EntityDataType varIntType = add(1, Types.VAR_INT);
    public final EntityDataType longType = add(2, Types.VAR_LONG);
    public final EntityDataType floatType = add(3, Types.FLOAT);
    public final EntityDataType stringType = add(4, Types.STRING);
    public final EntityDataType componentType = add(5, Types.TRUSTED_TAG);
    public final EntityDataType optionalComponentType = add(6, Types.TRUSTED_OPTIONAL_TAG);
    public final EntityDataType itemType;
    public final EntityDataType booleanType = add(8, Types.BOOLEAN);
    public final EntityDataType rotationsType = add(9, Types.ROTATIONS);
    public final EntityDataType blockPositionType = add(10, Types.BLOCK_POSITION1_14);
    public final EntityDataType optionalBlockPositionType = add(11, Types.OPTIONAL_POSITION_1_14);
    public final EntityDataType directionType = add(12, Types.VAR_INT);
    public final EntityDataType optionalUUIDType = add(13, Types.OPTIONAL_UUID);
    public final EntityDataType blockStateType = add(14, Types.VAR_INT);
    public final EntityDataType optionalBlockStateType = add(15, Types.VAR_INT);
    public final EntityDataType particleType;
    public final EntityDataType particlesType;
    public final EntityDataType villagerDataType = add(18, Types.VILLAGER_DATA);
    public final EntityDataType optionalVarIntType = add(19, Types.OPTIONAL_VAR_INT);
    public final EntityDataType poseType = add(20, Types.VAR_INT);
    public final EntityDataType catVariantType = add(21, Types.VAR_INT);
    public final EntityDataType catSoundVariant = add(22, Types.VAR_INT);
    public final EntityDataType cowVariantType = add(23, Types.VAR_INT);
    public final EntityDataType cowSoundVariant = add(24, Types.VAR_INT);
    public final EntityDataType wolfVariantType = add(25, Types.VAR_INT);
    public final EntityDataType wolfSoundVariantType = add(26, Types.VAR_INT);
    public final EntityDataType frogVariantType = add(27, Types.VAR_INT);
    public final EntityDataType pigVariantType = add(28, Types.VAR_INT);
    public final EntityDataType pigSoundVariant = add(29, Types.VAR_INT);
    public final EntityDataType chickenVariantType = add(30, Types.VAR_INT);
    public final EntityDataType chickenSoundVariant = add(31, Types.VAR_INT);
    public final EntityDataType zombieNautilusVariantType = add(32, Types.VAR_INT);
    public final EntityDataType optionalGlobalPosition = add(33, Types.OPTIONAL_GLOBAL_POSITION);
    public final EntityDataType paintingVariantType = add(34, PaintingVariant.TYPE1_21_2);
    public final EntityDataType snifferState = add(35, Types.VAR_INT);
    public final EntityDataType armadilloState = add(36, Types.VAR_INT);
    public final EntityDataType copperGolemState = add(37, Types.VAR_INT);
    public final EntityDataType weatheringCopperState = add(38, Types.VAR_INT);
    public final EntityDataType vector3FType = add(39, Types.VECTOR3F);
    public final EntityDataType quaternionType = add(40, Types.QUATERNION);
    public final EntityDataType mannequinProfileType = add(41, Types.RESOLVABLE_PROFILE);
    public final EntityDataType humanoidArmType = add(42, Types.VAR_INT);
    public final EntityDataType targetType = add(43, Types.VAR_INT);
    public final EntityDataType movementDeltaType = add(44, Types.BYTE);
    public final EntityDataType slotDisplayType = add(45, Types.VAR_INT);

    public EntityDataTypes26w14a(final VersionedTypesHolder types) {
        super(46);
        this.itemType = add(7, types.item());
        this.particleType = add(16, types.particle());
        this.particlesType = add(17, types.particles());
    }
}
