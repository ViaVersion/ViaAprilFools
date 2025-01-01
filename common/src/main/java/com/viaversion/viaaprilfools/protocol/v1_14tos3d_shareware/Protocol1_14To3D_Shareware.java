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
package com.viaversion.viaaprilfools.protocol.v1_14tos3d_shareware;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaaprilfools.protocol.s3d_sharewaretov1_14.packet.ClientboundPackets3D_Shareware;
import com.viaversion.viaaprilfools.protocol.s3d_sharewaretov1_14.packet.ServerboundPackets3D_Shareware;

public class Protocol1_14To3D_Shareware extends BackwardsProtocol<ClientboundPackets1_14, ClientboundPackets3D_Shareware, ServerboundPackets1_14, ServerboundPackets3D_Shareware> {

    public Protocol1_14To3D_Shareware() {
        super(ClientboundPackets1_14.class, ClientboundPackets3D_Shareware.class, ServerboundPackets1_14.class, ServerboundPackets3D_Shareware.class);
    }

    @Override
    protected void registerPackets() {
        this.cancelClientbound(ClientboundPackets1_14.SET_CHUNK_CACHE_CENTER);
    }

}
