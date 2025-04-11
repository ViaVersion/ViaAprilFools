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

    public static final int BLAST_FURNACE = 10;
    public static final int FURNACE = 14;
    public static final int DIMENSION_CONTROL = 16;
    public static final int MAP_MAKING = 20;

    private int containerId;
    private Integer containerTypeId;

    public void openContainer(final int containerId, final int containerTypeId) {
        this.containerId = containerId;
        this.containerTypeId = containerTypeId;
    }

    public boolean isOpen(final int containerTypeId, final int containerId) {
        return this.containerTypeId == containerTypeId && this.containerId == containerId;
    }

    public void close() {
        this.containerTypeId = null;
    }

}
