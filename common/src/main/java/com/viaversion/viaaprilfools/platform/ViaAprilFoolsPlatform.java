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
package com.viaversion.viaaprilfools.platform;

import com.viaversion.viaaprilfools.api.data.VAFMappingDataLoader;
import com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.Protocol25w14craftmineTo1_21_5;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.Protocol1_21_5To_25w14craftmine;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaaprilfools.ViaAprilFools;
import com.viaversion.viaaprilfools.ViaAprilFoolsConfig;
import com.viaversion.viaaprilfools.api.AprilFoolsProtocolVersion;
import com.viaversion.viaaprilfools.protocol.s3d_sharewaretov1_14.Protocol3D_SharewareTo1_14;
import com.viaversion.viaaprilfools.protocol.scombattest8ctov1_16_2.ProtocolCombatTest8cTo1_16_2;
import com.viaversion.viaaprilfools.protocol.s20w14infinitetov1_16.Protocol20w14infiniteTo1_16;
import com.viaversion.viaaprilfools.protocol.v1_14tos3d_shareware.Protocol1_14To3D_Shareware;
import com.viaversion.viaaprilfools.protocol.v1_16_2toscombattest8c.Protocol1_16_2ToCombatTest8c;

import java.io.File;
import java.util.logging.Logger;

public interface ViaAprilFoolsPlatform {

    default void init(final File configFile) {
        final ViaAprilFoolsConfig config = new ViaAprilFoolsConfig(configFile, getLogger());
        config.reload();
        ViaAprilFools.init(this, config);
        Via.getManager().getConfigurationProvider().register(config);
        Via.getManager().getSubPlatforms().add(ViaAprilFools.IMPL_VERSION);
        VAFMappingDataLoader.loadGlobalIdentifiers();

        final ProtocolManager protocolManager = Via.getManager().getProtocolManager();
        protocolManager.registerProtocol(new Protocol1_14To3D_Shareware(), AprilFoolsProtocolVersion.s3d_shareware, ProtocolVersion.v1_14);
        protocolManager.registerProtocol(new Protocol3D_SharewareTo1_14(), ProtocolVersion.v1_14, AprilFoolsProtocolVersion.s3d_shareware);

        protocolManager.registerProtocol(new Protocol20w14infiniteTo1_16(), ProtocolVersion.v1_16, AprilFoolsProtocolVersion.s20w14infinite);

        protocolManager.registerProtocol(new Protocol1_16_2ToCombatTest8c(), AprilFoolsProtocolVersion.sCombatTest8c, ProtocolVersion.v1_16_2);
        protocolManager.registerProtocol(new ProtocolCombatTest8cTo1_16_2(), ProtocolVersion.v1_16_2, AprilFoolsProtocolVersion.sCombatTest8c);

        protocolManager.registerProtocol(new Protocol1_21_5To_25w14craftmine(), AprilFoolsProtocolVersion.s25w14craftMine, ProtocolVersion.v1_21_5);
        protocolManager.registerProtocol(new Protocol25w14craftmineTo1_21_5(), ProtocolVersion.v1_21_5, AprilFoolsProtocolVersion.s25w14craftMine);
    }

    Logger getLogger();

    File getDataFolder();

}
