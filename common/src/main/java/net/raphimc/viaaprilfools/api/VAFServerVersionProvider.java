/*
 * This file is part of ViaAprilFools - https://github.com/RaphiMC/ViaAprilFools
 * Copyright (C) 2021-2025 RK_01/RaphiMC and contributors
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

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.api.protocol.version.VersionType;

public class VAFServerVersionProvider implements VersionProvider {

    private final VersionProvider delegate;

    public VAFServerVersionProvider(final VersionProvider delegate) {
        this.delegate = delegate;
    }

    @Override
    public ProtocolVersion getClientProtocol(UserConnection connection) {
        final ProtocolVersion version = connection.getProtocolInfo().protocolVersion();
        if (!version.isKnown()) {
            return ProtocolVersion.getProtocol(VersionType.SPECIAL, version.getOriginalVersion());
        } else {
            return delegate.getClientProtocol(connection);
        }
    }

    @Override
    public ProtocolVersion getClosestServerProtocol(UserConnection connection) throws Exception {
        return delegate.getClosestServerProtocol(connection);
    }

}
