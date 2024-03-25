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
package net.raphimc.viaaprilfools.protocols.protocol1_14to3D_Shareware;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import net.raphimc.viaaprilfools.api.data.AprilFoolsMappings;
import net.raphimc.viaaprilfools.protocols.protocol1_14to3D_Shareware.packets.BlockItemPackets3D_Shareware;
import net.raphimc.viaaprilfools.protocols.protocol1_14to3D_Shareware.packets.EntityPackets3D_Shareware;
import net.raphimc.viaaprilfools.protocols.protocol1_14to3D_Shareware.storage.ChunkCenterTracker3D_Shareware;

public class Protocol1_14to3D_Shareware extends BackwardsProtocol<ClientboundPackets3D_Shareware, ClientboundPackets1_14, ServerboundPackets3D_Shareware, ServerboundPackets1_14> {

    public static final BackwardsMappings MAPPINGS = new AprilFoolsMappings("3D_Shareware", "1.14", Protocol1_14To1_13_2.class);
    private static final int SERVERSIDE_VIEW_DISTANCE = 64;

    private final BlockItemPackets3D_Shareware blockItemPackets = new BlockItemPackets3D_Shareware(this);

    public Protocol1_14to3D_Shareware() {
        super(ClientboundPackets3D_Shareware.class, ClientboundPackets1_14.class, ServerboundPackets3D_Shareware.class, ServerboundPackets1_14.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();

        new EntityPackets3D_Shareware(this).registerPackets();
        final SoundRewriter<ClientboundPackets3D_Shareware> soundRewriter = new SoundRewriter<>(this);
        soundRewriter.registerSound(ClientboundPackets3D_Shareware.SOUND);
        soundRewriter.registerSound(ClientboundPackets3D_Shareware.ENTITY_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets3D_Shareware.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets3D_Shareware.STOP_SOUND);

        this.registerClientbound(ClientboundPackets3D_Shareware.CHUNK_DATA, wrapper -> {
            final ChunkCenterTracker3D_Shareware entityTracker = wrapper.user().get(ChunkCenterTracker3D_Shareware.class);

            final Chunk chunk = wrapper.passthrough(ChunkType1_14.TYPE);
            final int diffX = Math.abs(entityTracker.getChunkCenterX() - chunk.getX());
            final int diffZ = Math.abs(entityTracker.getChunkCenterZ() - chunk.getZ());

            if (entityTracker.isForceSendCenterChunk() || diffX >= SERVERSIDE_VIEW_DISTANCE || diffZ >= SERVERSIDE_VIEW_DISTANCE) {
                final PacketWrapper fakePosLook = wrapper.create(ClientboundPackets1_14.UPDATE_VIEW_POSITION); // Set center chunk
                fakePosLook.write(Type.VAR_INT, chunk.getX());
                fakePosLook.write(Type.VAR_INT, chunk.getZ());
                fakePosLook.send(Protocol1_14to3D_Shareware.class);
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
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    @Override
    public BlockItemPackets3D_Shareware getItemRewriter() {
        return this.blockItemPackets;
    }

}
