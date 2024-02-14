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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.viaversion.viaversion.api.protocol.version.VersionType.SPECIAL;

public class AprilFoolsProtocolVersion {

    public static final List<ProtocolVersion> PROTOCOLS = new ArrayList<>();
    public static final List<ProtocolVersion> SNAPSHOTS_PROTOCOLS = new ArrayList<>();
    public static final List<ProtocolVersion> APRIL_FOOLS_PROTOCOLS = new ArrayList<>();

    public static final ProtocolVersion s3d_shareware = new ProtocolVersion(SPECIAL, 1, -1, "3D Shareware", null) {
        @Override
        protected Comparator<ProtocolVersion> customComparator() {
            return (o1, o2) -> {
                if (o1 == s3d_shareware) {
                    o1 = ProtocolVersion.v1_13_2;
                }
                if (o2 == s3d_shareware) {
                    o2 = ProtocolVersion.v1_13_2;
                }
                return o1.compareTo(o2);
            };
        }
    };
    public static final ProtocolVersion s20w14infinite = new ProtocolVersion(SPECIAL, 709, -1, "20w14infinite", null) {
        @Override
        protected Comparator<ProtocolVersion> customComparator() {
            return (o1, o2) -> {
                if (o1 == s20w14infinite) {
                    o1 = ProtocolVersion.v1_15_2;
                }
                if (o2 == s20w14infinite) {
                    o2 = ProtocolVersion.v1_15_2;
                }
                return o1.compareTo(o2);
            };
        }
    };
    public static final ProtocolVersion sCombatTest8c = new ProtocolVersion(SPECIAL, 803, -1, "Combat Test 8c", null) {
        @Override
        protected Comparator<ProtocolVersion> customComparator() {
            return (o1, o2) -> {
                if (o1 == sCombatTest8c) {
                    o1 = ProtocolVersion.v1_16_1;
                }
                if (o2 == sCombatTest8c) {
                    o2 = ProtocolVersion.v1_16_1;
                }
                return o1.compareTo(o2);
            };
        }
    };

    static {
        registerAprilFools(s3d_shareware);
        registerAprilFools(s20w14infinite);
        registerSnapshot(sCombatTest8c);
    }

    private static void registerSnapshot(final ProtocolVersion protocolVersion) {
        ProtocolVersion.register(protocolVersion);
        PROTOCOLS.add(protocolVersion);
        SNAPSHOTS_PROTOCOLS.add(protocolVersion);
    }

    private static void registerAprilFools(final ProtocolVersion protocolVersion) {
        ProtocolVersion.register(protocolVersion);
        PROTOCOLS.add(protocolVersion);
        APRIL_FOOLS_PROTOCOLS.add(protocolVersion);
    }

}
