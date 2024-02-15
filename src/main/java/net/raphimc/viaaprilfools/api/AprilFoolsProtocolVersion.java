/*
 * This file is part of ViaAprilFools - https://github.com/RaphiMC/ViaAprilFools
 * Copyright (C) 2021-2024 RK_01/RaphiMC and contributors
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
package net.raphimc.viaaprilfools.api;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.protocol.RedirectProtocolVersion;

import java.util.ArrayList;
import java.util.List;

public class AprilFoolsProtocolVersion {

    public static final List<ProtocolVersion> PROTOCOLS = new ArrayList<>();
    public static final List<ProtocolVersion> SNAPSHOTS_PROTOCOLS = new ArrayList<>();
    public static final List<ProtocolVersion> APRIL_FOOLS_PROTOCOLS = new ArrayList<>();

    public static final ProtocolVersion s3d_shareware = registerAprilFools(1, "3D Shareware", ProtocolVersion.v1_13_2);
    public static final ProtocolVersion s20w14infinite = registerAprilFools(709, "20w14infinite", ProtocolVersion.v1_15_2);
    public static final ProtocolVersion sCombatTest8c = registerSnapshot(803, "Combat Test 8c", ProtocolVersion.v1_16_1);

    private static ProtocolVersion registerSnapshot(final int version, final String name, final ProtocolVersion origin) {
        final ProtocolVersion protocolVersion = new RedirectProtocolVersion(version, name, origin);
        ProtocolVersion.register(protocolVersion);
        PROTOCOLS.add(protocolVersion);
        SNAPSHOTS_PROTOCOLS.add(protocolVersion);
        return protocolVersion;
    }

    private static ProtocolVersion registerAprilFools(final int version, final String name, final ProtocolVersion origin) {
        final ProtocolVersion protocolVersion = new RedirectProtocolVersion(version, name, origin);
        ProtocolVersion.register(protocolVersion);
        PROTOCOLS.add(protocolVersion);
        APRIL_FOOLS_PROTOCOLS.add(protocolVersion);
        return protocolVersion;
    }

}
