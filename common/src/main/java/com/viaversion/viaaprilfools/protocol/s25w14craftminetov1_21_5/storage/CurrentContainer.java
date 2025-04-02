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
package com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public final class CurrentContainer implements StorableObject {

    private Integer dimensionControlContainer;
    private Integer mapMakingContainer;

    public boolean isDimensionControlContainer(final int containerId) {
        return this.dimensionControlContainer != null && this.dimensionControlContainer == containerId;
    }

    public boolean isMapMakingContainer(final int containerId) {
        return this.mapMakingContainer != null && this.mapMakingContainer == containerId;
    }

    public void openDimensionControlContainer(final int containerId) {
        this.dimensionControlContainer = containerId;
    }

    public void openMapMakingContainer(final int containerId) {
        this.mapMakingContainer = containerId;
    }

    public void close() {
        this.dimensionControlContainer = null;
        this.mapMakingContainer = null;
    }

}
