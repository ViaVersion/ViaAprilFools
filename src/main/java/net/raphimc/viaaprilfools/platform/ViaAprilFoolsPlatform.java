/*
 * This file is part of ViaAprilFools - https://github.com/RaphiMC/ViaAprilFools
 * Copyright (C) 2023 RK_01/RaphiMC and contributors
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
package net.raphimc.viaaprilfools.platform;

import com.google.common.collect.Range;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.protocols.base.BaseProtocol1_16;
import net.raphimc.viaaprilfools.ViaAprilFools;
import net.raphimc.viaaprilfools.ViaAprilFoolsConfig;
import net.raphimc.viaaprilfools.api.AprilFoolsProtocolVersion;
import net.raphimc.viaaprilfools.protocols.protocol1_14to3D_Shareware.Protocol1_14to3D_Shareware;
import net.raphimc.viaaprilfools.protocols.protocol1_16_2toCombatTest8c.Protocol1_16_2toCombatTest8c;
import net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.Protocol1_16to20w14infinite;
import net.raphimc.viaaprilfools.protocols.protocol3D_Sharewareto1_14.Protocol3D_Sharewareto1_14;

import java.io.File;
import java.util.logging.Logger;

public interface ViaAprilFoolsPlatform {

    default void init(final File dataFolder) {
        final ViaAprilFoolsConfig config = new ViaAprilFoolsConfig(new File(dataFolder, "viaaprilfools.yml"));
        config.reload();
        ViaAprilFools.init(this, config);
        Via.getManager().getConfigurationProvider().register(config);
        Via.getManager().getSubPlatforms().add(ViaAprilFools.IMPL_VERSION);


        final ProtocolManager protocolManager = Via.getManager().getProtocolManager();

        protocolManager.registerProtocol(new Protocol1_14to3D_Shareware(), ProtocolVersion.v1_14, AprilFoolsProtocolVersion.s3d_shareware);
        protocolManager.registerProtocol(new Protocol3D_Sharewareto1_14(), AprilFoolsProtocolVersion.s3d_shareware, ProtocolVersion.v1_14);
        protocolManager.registerProtocol(new Protocol1_16to20w14infinite(), ProtocolVersion.v1_16, AprilFoolsProtocolVersion.s20w14infinite);
        protocolManager.registerProtocol(new Protocol1_16_2toCombatTest8c(), ProtocolVersion.v1_16_2, AprilFoolsProtocolVersion.sCombatTest8c);

        protocolManager.registerBaseProtocol(new BaseProtocol1_16(), Range.singleton(AprilFoolsProtocolVersion.s20w14infinite.getVersion()));
        protocolManager.registerBaseProtocol(new BaseProtocol1_16(), Range.singleton(AprilFoolsProtocolVersion.sCombatTest8c.getVersion()));
    }

    Logger getLogger();

    File getDataFolder();

}
