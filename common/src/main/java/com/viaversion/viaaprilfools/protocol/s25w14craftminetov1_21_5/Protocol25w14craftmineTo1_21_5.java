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
package com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5;

import com.viaversion.viaaprilfools.api.data.VAFBackwardsMappingData;
import com.viaversion.viaaprilfools.api.minecraft.entities.EntityTypes25w14craftmine;
import com.viaversion.viaaprilfools.api.type.version.Types25w14craftmine;
import com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.rewriter.BlockItemPacketRewriter25w14craftmine;
import com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.rewriter.ComponentRewriter25w14craftmine;
import com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.rewriter.EntityPacketRewriter25w14craftmine;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.Protocol1_21_5To_25w14craftmine;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet.*;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.text.NBTComponentRewriter;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.packet.provider.SimplePacketTypesProvider;
import com.viaversion.viaversion.api.type.types.version.Types1_21_5;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ClientboundPacket1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ClientboundPackets1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ServerboundPacket1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ServerboundPackets1_21_5;
import com.viaversion.viaversion.rewriter.AttributeRewriter;
import com.viaversion.viaversion.rewriter.ParticleRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

import static com.viaversion.viaversion.util.ProtocolUtil.packetTypeMap;

public final class Protocol25w14craftmineTo1_21_5 extends BackwardsProtocol<ClientboundPacket25w14craftmine, ClientboundPacket1_21_5, ServerboundPacket25w14craftmine, ServerboundPacket1_21_5> {

    public static final BackwardsMappingData MAPPINGS = new VAFBackwardsMappingData("25w14craftmine", "1.21.5", Protocol1_21_5To_25w14craftmine.class);
    private final EntityPacketRewriter25w14craftmine entityRewriter = new EntityPacketRewriter25w14craftmine(this);
    private final BlockItemPacketRewriter25w14craftmine itemRewriter = new BlockItemPacketRewriter25w14craftmine(this);
    private final ParticleRewriter<ClientboundPacket25w14craftmine> particleRewriter = new ParticleRewriter<>(this, Types25w14craftmine.PARTICLE, Types1_21_5.PARTICLE);
    private final NBTComponentRewriter<ClientboundPacket25w14craftmine> translatableRewriter = new ComponentRewriter25w14craftmine(this);
    private final TagRewriter<ClientboundPacket25w14craftmine> tagRewriter = new TagRewriter<>(this);

    public Protocol25w14craftmineTo1_21_5() {
        super(ClientboundPacket25w14craftmine.class, ClientboundPacket1_21_5.class, ServerboundPacket25w14craftmine.class, ServerboundPacket1_21_5.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();

        tagRewriter.registerGeneric(ClientboundPackets25w14craftmine.UPDATE_TAGS);
        tagRewriter.registerGeneric(ClientboundConfigurationPackets1_21.UPDATE_TAGS);

        final SoundRewriter<ClientboundPacket25w14craftmine> soundRewriter = new SoundRewriter<>(this);
        soundRewriter.registerSound1_19_3(ClientboundPackets25w14craftmine.SOUND);
        soundRewriter.registerSound1_19_3(ClientboundPackets25w14craftmine.SOUND_ENTITY);
        soundRewriter.registerStopSound(ClientboundPackets25w14craftmine.STOP_SOUND);

        new StatisticsRewriter<>(this).register(ClientboundPackets25w14craftmine.AWARD_STATS);
        new AttributeRewriter<>(this).register1_21(ClientboundPackets25w14craftmine.UPDATE_ATTRIBUTES);

        translatableRewriter.registerComponentPacket(ClientboundPackets25w14craftmine.SET_ACTION_BAR_TEXT);
        translatableRewriter.registerComponentPacket(ClientboundPackets25w14craftmine.SET_TITLE_TEXT);
        translatableRewriter.registerComponentPacket(ClientboundPackets25w14craftmine.SET_SUBTITLE_TEXT);
        translatableRewriter.registerBossEvent(ClientboundPackets25w14craftmine.BOSS_EVENT);
        translatableRewriter.registerComponentPacket(ClientboundPackets25w14craftmine.DISCONNECT);
        translatableRewriter.registerTabList(ClientboundPackets25w14craftmine.TAB_LIST);
        translatableRewriter.registerPlayerCombatKill1_20(ClientboundPackets25w14craftmine.PLAYER_COMBAT_KILL);
        translatableRewriter.registerPlayerInfoUpdate1_21_4(ClientboundPackets25w14craftmine.PLAYER_INFO_UPDATE);
        translatableRewriter.registerComponentPacket(ClientboundPackets25w14craftmine.SYSTEM_CHAT);
        translatableRewriter.registerDisguisedChat(ClientboundPackets25w14craftmine.DISGUISED_CHAT);
        translatableRewriter.registerPlayerChat1_21_5(ClientboundPackets25w14craftmine.PLAYER_CHAT);
        translatableRewriter.registerPing();

        particleRewriter.registerLevelParticles1_21_4(ClientboundPackets25w14craftmine.LEVEL_PARTICLES);
        particleRewriter.registerExplode1_21_2(ClientboundPackets25w14craftmine.EXPLODE);

        cancelClientbound(ClientboundPackets25w14craftmine.CHANGE_DIMENSION_TYPE); // Oh well
        cancelClientbound(ClientboundPackets25w14craftmine.OPEN_DOOR);
        cancelClientbound(ClientboundPackets25w14craftmine.UPDATE_PLAYER_UNLOCKS);
        cancelClientbound(ClientboundPackets25w14craftmine.UPDATE_UNLOCKED_EFFECTS);
    }

    @Override
    public void init(final UserConnection user) {
        addEntityTracker(user, new EntityTrackerBase(user, EntityTypes25w14craftmine.PLAYER));
    }

    @Override
    public BackwardsMappingData getMappingData() {
        return MAPPINGS;
    }

    @Override
    public EntityPacketRewriter25w14craftmine getEntityRewriter() {
        return entityRewriter;
    }

    @Override
    public BlockItemPacketRewriter25w14craftmine getItemRewriter() {
        return itemRewriter;
    }

    @Override
    public ParticleRewriter<ClientboundPacket25w14craftmine> getParticleRewriter() {
        return particleRewriter;
    }

    @Override
    public NBTComponentRewriter<ClientboundPacket25w14craftmine> getComponentRewriter() {
        return translatableRewriter;
    }

    @Override
    public TagRewriter<ClientboundPacket25w14craftmine> getTagRewriter() {
        return tagRewriter;
    }

    @Override
    protected PacketTypesProvider<ClientboundPacket25w14craftmine, ClientboundPacket1_21_5, ServerboundPacket25w14craftmine, ServerboundPacket1_21_5> createPacketTypesProvider() {
        return new SimplePacketTypesProvider<>(
            packetTypeMap(unmappedClientboundPacketType, ClientboundPackets25w14craftmine.class, ClientboundConfigurationPackets1_21.class),
            packetTypeMap(mappedClientboundPacketType, ClientboundPackets1_21_5.class, ClientboundConfigurationPackets1_21.class),
            packetTypeMap(mappedServerboundPacketType, ServerboundPackets25w14craftmine.class, ServerboundConfigurationPackets1_20_5.class),
            packetTypeMap(unmappedServerboundPacketType, ServerboundPackets1_21_5.class, ServerboundConfigurationPackets1_20_5.class)
        );
    }

}
