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
package com.viaversion.viaaprilfools.protocol.s3d_sharewaretov1_14;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.rewriter.ParticleRewriter;
import com.viaversion.viaaprilfools.api.data.AprilFoolsMappingData;
import com.viaversion.viaaprilfools.protocol.s3d_sharewaretov1_14.packet.ClientboundPackets3D_Shareware;
import com.viaversion.viaaprilfools.protocol.s3d_sharewaretov1_14.packet.ServerboundPackets3D_Shareware;
import com.viaversion.viaaprilfools.protocol.s3d_sharewaretov1_14.rewriter.BlockItemPacketRewriter3D_Shareware;
import com.viaversion.viaaprilfools.protocol.s3d_sharewaretov1_14.rewriter.EntityPacketRewriter3D_Shareware;
import com.viaversion.viaaprilfools.protocol.s3d_sharewaretov1_14.storage.ChunkCenterTracker3D_Shareware;

public class Protocol3D_SharewareTo1_14 extends BackwardsProtocol<ClientboundPackets3D_Shareware, ClientboundPackets1_14, ServerboundPackets3D_Shareware, ServerboundPackets1_14> {

    public static final BackwardsMappingData MAPPINGS = new AprilFoolsMappingData("3D_Shareware", "1.14", Protocol1_13_2To1_14.class);
    private static final int SERVERSIDE_VIEW_DISTANCE = 64;

    private final BlockItemPacketRewriter3D_Shareware itemRewriter = new BlockItemPacketRewriter3D_Shareware(this);
    private final ParticleRewriter<ClientboundPackets3D_Shareware> particleRewriter = new ParticleRewriter<>(this);

    public Protocol3D_SharewareTo1_14() {
        super(ClientboundPackets3D_Shareware.class, ClientboundPackets1_14.class, ServerboundPackets3D_Shareware.class, ServerboundPackets1_14.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();

        particleRewriter.registerLevelParticles1_13(ClientboundPackets3D_Shareware.LEVEL_PARTICLES, Types.FLOAT);

        new EntityPacketRewriter3D_Shareware(this).registerPackets();
        final SoundRewriter<ClientboundPackets3D_Shareware> soundRewriter = new SoundRewriter<>(this);
        soundRewriter.registerSound(ClientboundPackets3D_Shareware.SOUND);
        soundRewriter.registerSound(ClientboundPackets3D_Shareware.SOUND_ENTITY);
        soundRewriter.registerNamedSound(ClientboundPackets3D_Shareware.CUSTOM_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets3D_Shareware.STOP_SOUND);

        this.registerClientbound(ClientboundPackets3D_Shareware.LEVEL_CHUNK, wrapper -> {
            final ChunkCenterTracker3D_Shareware entityTracker = wrapper.user().get(ChunkCenterTracker3D_Shareware.class);

            final Chunk chunk = wrapper.passthrough(ChunkType1_14.TYPE);
            final int diffX = Math.abs(entityTracker.getChunkCenterX() - chunk.getX());
            final int diffZ = Math.abs(entityTracker.getChunkCenterZ() - chunk.getZ());

            if (entityTracker.isForceSendCenterChunk() || diffX >= SERVERSIDE_VIEW_DISTANCE || diffZ >= SERVERSIDE_VIEW_DISTANCE) {
                final PacketWrapper fakePosLook = wrapper.create(ClientboundPackets1_14.SET_CHUNK_CACHE_CENTER); // Set center chunk
                fakePosLook.write(Types.VAR_INT, chunk.getX());
                fakePosLook.write(Types.VAR_INT, chunk.getZ());
                fakePosLook.send(Protocol3D_SharewareTo1_14.class);
                entityTracker.setChunkCenterX(chunk.getX());
                entityTracker.setChunkCenterZ(chunk.getZ());
            }
        });
        this.registerClientbound(ClientboundPackets3D_Shareware.RESPAWN, wrapper -> {
            // The client may reset the center chunk if dimension is changed
            wrapper.user().get(ChunkCenterTracker3D_Shareware.class).setForceSendCenterChunk(true);
        });
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new ChunkCenterTracker3D_Shareware());
    }

    @Override
    public BackwardsMappingData getMappingData() {
        return MAPPINGS;
    }

    @Override
    public BlockItemPacketRewriter3D_Shareware getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public ParticleRewriter<ClientboundPackets3D_Shareware> getParticleRewriter() {
        return particleRewriter;
    }

}
