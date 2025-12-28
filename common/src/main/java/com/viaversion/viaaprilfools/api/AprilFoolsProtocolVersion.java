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
package com.viaversion.viaaprilfools.api;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.protocol.SpecialProtocolVersion;
import java.util.ArrayList;
import java.util.List;

public class AprilFoolsProtocolVersion {

    public static final List<ProtocolVersion> PROTOCOLS = new ArrayList<>();
    public static final List<ProtocolVersion> SNAPSHOTS_PROTOCOLS = new ArrayList<>();
    public static final List<ProtocolVersion> APRIL_FOOLS_PROTOCOLS = new ArrayList<>();

    public static final ProtocolVersion s3d_shareware = registerAprilFools(1, "3D Shareware", ProtocolVersion.v1_13_2, ProtocolVersion.v1_13_2);
    public static final ProtocolVersion s20w14infinite = registerAprilFools(709, "20w14infinite", ProtocolVersion.v1_15_2, ProtocolVersion.v1_16);
    public static final ProtocolVersion sCombatTest8c = registerSnapshot(803, "Combat Test 8c", ProtocolVersion.v1_16_1);
    public static final ProtocolVersion s25w14craftMine = registerAprilFools(770, 244, "25w14craftmine", ProtocolVersion.v1_21_5);

    private static ProtocolVersion registerSnapshot(final int version, final String name, final ProtocolVersion origin) {
        final ProtocolVersion protocolVersion = new SpecialProtocolVersion(version, name, origin);
        ProtocolVersion.register(protocolVersion);
        PROTOCOLS.add(protocolVersion);
        SNAPSHOTS_PROTOCOLS.add(protocolVersion);
        return protocolVersion;
    }

    private static ProtocolVersion registerAprilFools(final int version, final String name, final ProtocolVersion origin, final ProtocolVersion baseProtocolVersion) {
        return registerAprilFools(version, -1, name, origin, baseProtocolVersion);
    }

    private static ProtocolVersion registerAprilFools(final int version, final int snapshotVersion, final String name, final ProtocolVersion origin) {
        return registerAprilFools(version, snapshotVersion, name, origin, origin);
    }

    private static ProtocolVersion registerAprilFools(final int version, final int snapshotVersion, final String name, final ProtocolVersion origin, final ProtocolVersion baseProtocolVersion) {
        final ProtocolVersion protocolVersion = new SpecialProtocolVersion(version, snapshotVersion, name, null, origin) {

            @Override
            public ProtocolVersion getBaseProtocolVersion() {
                // The infinite snapshot needs to be treated as >=1.16 and not as 1.15.2 to have the correct uuid reading behavior in the base protocol
                return baseProtocolVersion;
            }

        };
        ProtocolVersion.register(protocolVersion);
        PROTOCOLS.add(protocolVersion);
        APRIL_FOOLS_PROTOCOLS.add(protocolVersion);
        return protocolVersion;
    }

}
