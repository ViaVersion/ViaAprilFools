/*
 * This file is part of ViaAprilFools - https://github.com/ViaVersion/ViaAprilFools
 * Copyright (C) 2021-2026 the original authors
 *                         - RK_01/RaphiMC
 *                         - Florian Reuth <git@florianreuth.de>
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
package com.viaversion.viaaprilfools.protocol.v26_1tos26w14a;

import com.viaversion.viaaprilfools.api.data.VAFMappingData;
import com.viaversion.viaaprilfools.api.minecraft.entities.EntityDataTypes26w14a;
import com.viaversion.viaaprilfools.api.minecraft.entities.EntityTypes26w14a;
import com.viaversion.viaaprilfools.api.minecraft.item.VAFStructuredDataKeys;
import com.viaversion.viaaprilfools.api.types.VAFTypes;
import com.viaversion.viaaprilfools.protocol.v26_1tos26w14a.rewriter.BlockItemPacketRewriter26w14a;
import com.viaversion.viaaprilfools.protocol.v26_1tos26w14a.rewriter.EntityPacketRewriter26w14a;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.data.version.StructuredDataKeys1_21_11;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes26_1;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.packet.provider.SimplePacketTypesProvider;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_20_5;
import com.viaversion.viaversion.api.type.types.version.VersionedTypes;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.data.item.ItemHasherBase;
import com.viaversion.viaversion.protocols.v1_21_11to26_1.packet.ClientboundPacket26_1;
import com.viaversion.viaversion.protocols.v1_21_11to26_1.packet.ClientboundPackets26_1;
import com.viaversion.viaversion.protocols.v1_21_11to26_1.packet.ServerboundPacket26_1;
import com.viaversion.viaversion.protocols.v1_21_11to26_1.packet.ServerboundPackets26_1;
import com.viaversion.viaversion.protocols.v1_21_7to1_21_9.packet.ClientboundConfigurationPackets1_21_9;
import com.viaversion.viaversion.protocols.v1_21_7to1_21_9.packet.ServerboundConfigurationPackets1_21_9;
import com.viaversion.viaversion.rewriter.ParticleRewriter;
import com.viaversion.viaversion.rewriter.RegistryDataRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.rewriter.text.NBTComponentRewriter;

import static com.viaversion.viaversion.util.ProtocolUtil.packetTypeMap;

public final class Protocol26_1To26w14a extends AbstractProtocol<ClientboundPacket26_1, ClientboundPacket26_1, ServerboundPacket26_1, ServerboundPacket26_1> {

    public static final VAFMappingData MAPPINGS = new VAFMappingData("26.1", "26w14a");
    private final EntityPacketRewriter26w14a entityRewriter = new EntityPacketRewriter26w14a(this);
    private final BlockItemPacketRewriter26w14a itemRewriter = new BlockItemPacketRewriter26w14a(this);
    private final ParticleRewriter<ClientboundPacket26_1> particleRewriter = new ParticleRewriter<>(this);
    private final TagRewriter<ClientboundPacket26_1> tagRewriter = new TagRewriter<>(this);
    private final NBTComponentRewriter<ClientboundPacket26_1> componentRewriter = new NBTComponentRewriter<>(this);
    private final RegistryDataRewriter registryDataRewriter = new RegistryDataRewriter(this);

    public Protocol26_1To26w14a() {
        super(ClientboundPacket26_1.class, ClientboundPacket26_1.class, ServerboundPacket26_1.class, ServerboundPacket26_1.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();

        registerClientbound(ClientboundConfigurationPackets1_21_9.REGISTRY_DATA, registryDataRewriter::handle);

        registerClientbound(ClientboundPackets26_1.UPDATE_TAGS, tagRewriter::handleGeneric);
        registerClientbound(ClientboundConfigurationPackets1_21_9.UPDATE_TAGS, tagRewriter::handleGeneric);

        componentRewriter.registerOpenScreen1_14(ClientboundPackets26_1.OPEN_SCREEN);
        componentRewriter.registerComponentPacket(ClientboundPackets26_1.SET_ACTION_BAR_TEXT);
        componentRewriter.registerComponentPacket(ClientboundPackets26_1.SET_TITLE_TEXT);
        componentRewriter.registerComponentPacket(ClientboundPackets26_1.SET_SUBTITLE_TEXT);
        componentRewriter.registerBossEvent(ClientboundPackets26_1.BOSS_EVENT);
        componentRewriter.registerComponentPacket(ClientboundPackets26_1.DISCONNECT);
        componentRewriter.registerTabList(ClientboundPackets26_1.TAB_LIST);
        componentRewriter.registerSetPlayerTeam1_21_5(ClientboundPackets26_1.SET_PLAYER_TEAM);
        componentRewriter.registerPlayerCombatKill1_20(ClientboundPackets26_1.PLAYER_COMBAT_KILL);
        componentRewriter.registerPlayerInfoUpdate1_21_4(ClientboundPackets26_1.PLAYER_INFO_UPDATE);
        componentRewriter.registerComponentPacket(ClientboundPackets26_1.SYSTEM_CHAT);
        componentRewriter.registerDisguisedChat(ClientboundPackets26_1.DISGUISED_CHAT);
        componentRewriter.registerPlayerChat1_21_5(ClientboundPackets26_1.PLAYER_CHAT);
        componentRewriter.registerLoginDisconnect();

        particleRewriter.registerLevelParticles1_21_4(ClientboundPackets26_1.LEVEL_PARTICLES);
        particleRewriter.registerExplode1_21_9(ClientboundPackets26_1.EXPLODE);

        new StatisticsRewriter<>(this).register(ClientboundPackets26_1.AWARD_STATS);

        registerServerbound(ServerboundPackets26_1.PICK_ITEM_FROM_BLOCK, wrapper -> {
            final BlockPosition position = wrapper.read(Types.BLOCK_POSITION1_14);
            wrapper.read(Types.VAR_INT); // Direction
            wrapper.read(Types.FLOAT); // x
            wrapper.read(Types.FLOAT); // y
            wrapper.read(Types.FLOAT); // z
            wrapper.read(Types.BOOLEAN); // Is inside
            wrapper.read(Types.BOOLEAN); // Is world border hit
            wrapper.write(Types.BLOCK_POSITION1_14, position);
        });
        registerServerbound(ServerboundPackets26_1.PLAYER_ACTION, wrapper -> {
            final int action = wrapper.passthrough(Types.VAR_INT);
            if (action > 7) { // Command pos and command
                wrapper.cancel();
            }
        });
    }

    @Override
    public void init(final UserConnection connection) {
        addEntityTracker(connection, new EntityTrackerBase(connection, EntityTypes26w14a.PLAYER));
        addItemHasher(connection, new ItemHasherBase(this, connection));
    }

    @Override
    protected void onMappingDataLoaded() {
        EntityTypes26w14a.initialize(this);
        ParticleType.Fillers.fill1_21_9(this);
        mappedTypes().structuredData.filler(this).add(StructuredDataKey.CUSTOM_DATA, StructuredDataKey.MAX_STACK_SIZE, StructuredDataKey.MAX_DAMAGE,
            StructuredDataKey.UNBREAKABLE1_21_5, StructuredDataKey.RARITY, StructuredDataKey.TOOLTIP_DISPLAY, StructuredDataKey.DAMAGE_RESISTANT26_1,
            StructuredDataKey.CUSTOM_NAME, StructuredDataKey.LORE, StructuredDataKey.ENCHANTMENTS1_21_5,
            StructuredDataKey.CUSTOM_MODEL_DATA1_21_4, StructuredDataKey.BLOCKS_ATTACKS26_1, StructuredDataKey.PROVIDES_BANNER_PATTERNS26_1,
            StructuredDataKey.REPAIR_COST, StructuredDataKey.CREATIVE_SLOT_LOCK, StructuredDataKey.ENCHANTMENT_GLINT_OVERRIDE,
            StructuredDataKey.INTANGIBLE_PROJECTILE, StructuredDataKey.STORED_ENCHANTMENTS1_21_5, StructuredDataKey.DYED_COLOR1_21_5,
            StructuredDataKey.MAP_COLOR, StructuredDataKey.MAP_ID, StructuredDataKey.MAP_DECORATIONS, StructuredDataKey.MAP_POST_PROCESSING,
            StructuredDataKey.POTION_CONTENTS1_21_2, StructuredDataKey.SUSPICIOUS_STEW_EFFECTS, StructuredDataKey.WRITABLE_BOOK_CONTENT,
            StructuredDataKey.WRITTEN_BOOK_CONTENT, StructuredDataKey.TRIM1_21_5, StructuredDataKey.DEBUG_STICK_STATE, StructuredDataKey.ENTITY_DATA1_21_9,
            StructuredDataKey.BUCKET_ENTITY_DATA, StructuredDataKey.BLOCK_ENTITY_DATA1_21_9, StructuredDataKey.INSTRUMENT26_1,
            StructuredDataKey.RECIPES, StructuredDataKey.LODESTONE_TRACKER, StructuredDataKey.FIREWORK_EXPLOSION, StructuredDataKey.FIREWORKS,
            StructuredDataKey.PROFILE1_21_9, StructuredDataKey.NOTE_BLOCK_SOUND, StructuredDataKey.BANNER_PATTERNS, StructuredDataKey.BASE_COLOR,
            StructuredDataKey.POT_DECORATIONS, StructuredDataKey.BLOCK_STATE, StructuredDataKey.BEES1_21_9, StructuredDataKey.LOCK1_21_2,
            StructuredDataKey.CONTAINER_LOOT, StructuredDataKey.TOOL1_21_5, StructuredDataKey.ITEM_NAME, StructuredDataKey.OMINOUS_BOTTLE_AMPLIFIER,
            StructuredDataKey.FOOD1_21_2, StructuredDataKey.JUKEBOX_PLAYABLE26_1, StructuredDataKey.ATTRIBUTE_MODIFIERS1_21_6,
            StructuredDataKey.REPAIRABLE, StructuredDataKey.ENCHANTABLE, StructuredDataKey.CONSUMABLE1_21_2, StructuredDataKey.ATTACK_RANGE,
            StructuredDataKey.USE_COOLDOWN, StructuredDataKey.DAMAGE, StructuredDataKey.EQUIPPABLE1_21_6, StructuredDataKey.ITEM_MODEL,
            StructuredDataKey.GLIDER, StructuredDataKey.TOOLTIP_STYLE, StructuredDataKey.DEATH_PROTECTION, StructuredDataKey.WEAPON,
            StructuredDataKey.POTION_DURATION_SCALE, StructuredDataKey.VILLAGER_VARIANT, StructuredDataKey.WOLF_VARIANT, StructuredDataKey.WOLF_COLLAR,
            StructuredDataKey.FOX_VARIANT, StructuredDataKey.SALMON_SIZE, StructuredDataKey.PARROT_VARIANT, StructuredDataKey.TROPICAL_FISH_PATTERN,
            StructuredDataKey.TROPICAL_FISH_BASE_COLOR, StructuredDataKey.TROPICAL_FISH_PATTERN_COLOR, StructuredDataKey.MOOSHROOM_VARIANT,
            StructuredDataKey.RABBIT_VARIANT, StructuredDataKey.PIG_VARIANT, StructuredDataKey.FROG_VARIANT, StructuredDataKey.HORSE_VARIANT,
            StructuredDataKey.PAINTING_VARIANT, StructuredDataKey.LLAMA_VARIANT, StructuredDataKey.AXOLOTL_VARIANT, StructuredDataKey.CAT_VARIANT,
            StructuredDataKey.CAT_COLLAR, StructuredDataKey.SHEEP_COLOR, StructuredDataKey.SHULKER_COLOR, StructuredDataKey.PROVIDES_TRIM_MATERIAL26_1,
            StructuredDataKey.BREAK_SOUND, StructuredDataKey.COW_VARIANT, StructuredDataKey.CHICKEN_VARIANT26_1, StructuredDataKey.WOLF_SOUND_VARIANT,
            StructuredDataKey.USE_EFFECTS, StructuredDataKey.MINIMUM_ATTACK_CHARGE, StructuredDataKey.DAMAGE_TYPE26_1, StructuredDataKey.PIERCING_WEAPON,
            StructuredDataKey.KINETIC_WEAPON, StructuredDataKey.SWING_ANIMATION, StructuredDataKey.ZOMBIE_NAUTILUS_VARIANT26_1, StructuredDataKey.ADDITIONAL_TRADE_COST,
            StructuredDataKey.DYE, StructuredDataKey.PIG_SOUND_VARIANT, StructuredDataKey.COW_SOUND_VARIANT, StructuredDataKey.CHICKEN_SOUND_VARIANT, StructuredDataKey.CAT_SOUND_VARIANT,
            VAFStructuredDataKeys.FOLLOW);
        super.onMappingDataLoaded();
    }

    @Override
    public VAFMappingData getMappingData() {
        return MAPPINGS;
    }

    @Override
    public EntityPacketRewriter26w14a getEntityRewriter() {
        return entityRewriter;
    }

    @Override
    public BlockItemPacketRewriter26w14a getItemRewriter() {
        return itemRewriter;
    }

    @Override
    public RegistryDataRewriter getRegistryDataRewriter() {
        return registryDataRewriter;
    }

    @Override
    public ParticleRewriter<ClientboundPacket26_1> getParticleRewriter() {
        return particleRewriter;
    }

    @Override
    public TagRewriter<ClientboundPacket26_1> getTagRewriter() {
        return tagRewriter;
    }

    @Override
    public NBTComponentRewriter<ClientboundPacket26_1> getComponentRewriter() {
        return componentRewriter;
    }

    @Override
    public Types1_20_5<StructuredDataKeys1_21_11, EntityDataTypes26_1> types() {
        return VersionedTypes.V26_1;
    }

    @Override
    public Types1_20_5<StructuredDataKeys1_21_11, EntityDataTypes26w14a> mappedTypes() {
        return VAFTypes.V26W14A;
    }

    @Override
    protected PacketTypesProvider<ClientboundPacket26_1, ClientboundPacket26_1, ServerboundPacket26_1, ServerboundPacket26_1> createPacketTypesProvider() {
        return new SimplePacketTypesProvider<>(
            packetTypeMap(unmappedClientboundPacketType, ClientboundPackets26_1.class, ClientboundConfigurationPackets1_21_9.class),
            packetTypeMap(mappedClientboundPacketType, ClientboundPackets26_1.class, ClientboundConfigurationPackets1_21_9.class),
            packetTypeMap(mappedServerboundPacketType, ServerboundPackets26_1.class, ServerboundConfigurationPackets1_21_9.class),
            packetTypeMap(unmappedServerboundPacketType, ServerboundPackets26_1.class, ServerboundConfigurationPackets1_21_9.class)
        );
    }

}
