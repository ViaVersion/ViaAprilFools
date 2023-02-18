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
package net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import net.raphimc.viaaprilfools.api.ItemBackwardsMappings;
import net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.metadata.MetadataRewriter1_16to20w14infinite;
import net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.packets.BlockItemPackets20w14infinite;
import net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.packets.EntityPackets20w14infinite;

import java.util.UUID;

public class Protocol1_16to20w14infinite extends BackwardsProtocol<ClientboundPackets20w14infinite, ClientboundPackets1_16, ServerboundPackets20w14infinite, ServerboundPackets1_16> {

    public static final BackwardsMappings MAPPINGS = new ItemBackwardsMappings("20w14infinite", "1.16");
    private static final UUID ZERO_UUID = new UUID(0, 0);

    private BlockItemPackets20w14infinite blockItemPackets;
    private TagRewriter<ClientboundPackets20w14infinite> tagRewriter;
    private MetadataRewriter1_16to20w14infinite metadataRewriter;

    public Protocol1_16to20w14infinite() {
        super(ClientboundPackets20w14infinite.class, ClientboundPackets1_16.class, ServerboundPackets20w14infinite.class, ServerboundPackets1_16.class);
    }

    @Override
    protected void registerPackets() {
        this.metadataRewriter = new MetadataRewriter1_16to20w14infinite(this);
        metadataRewriter.register();
        tagRewriter = new TagRewriter<>(this);

        this.executeAsyncAfterLoaded(Protocol1_16To1_15_2.class, () -> {
            MAPPINGS.load();
            this.onMappingDataLoaded();
        });

        tagRewriter.register(ClientboundPackets20w14infinite.TAGS, RegistryType.ENTITY);
        new StatisticsRewriter<>(this).register(ClientboundPackets20w14infinite.STATISTICS);
        this.blockItemPackets = new BlockItemPackets20w14infinite(this);
        this.blockItemPackets.register();
        EntityPackets20w14infinite.register(this);
        final SoundRewriter<ClientboundPackets20w14infinite> soundRewriter = new SoundRewriter<>(this);
        soundRewriter.registerSound(ClientboundPackets20w14infinite.SOUND);
        soundRewriter.registerSound(ClientboundPackets20w14infinite.ENTITY_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets20w14infinite.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets20w14infinite.STOP_SOUND);
        new RecipeRewriter<>(this).register(ClientboundPackets20w14infinite.DECLARE_RECIPES);

        this.registerClientbound(ClientboundPackets20w14infinite.CHAT_MESSAGE, new PacketHandlers() {
            @Override
            public void register() {
                map(Type.COMPONENT);
                map(Type.BYTE);
                handler(wrapper -> {
                    wrapper.write(Type.UUID, ZERO_UUID); // Sender uuid - always send as 'system'
                });
            }
        });

        this.cancelServerbound(ServerboundPackets1_16.GENERATE_JIGSAW);
        this.registerServerbound(ServerboundPackets1_16.INTERACT_ENTITY, new PacketHandlers() {
            @Override
            public void register() {
                handler(wrapper -> {
                    wrapper.passthrough(Type.VAR_INT); // Entity Id
                    int action = wrapper.passthrough(Type.VAR_INT);
                    if (action == 0 || action == 2) {
                        if (action == 2) {
                            // Location
                            wrapper.passthrough(Type.FLOAT);
                            wrapper.passthrough(Type.FLOAT);
                            wrapper.passthrough(Type.FLOAT);
                        }

                        wrapper.passthrough(Type.VAR_INT); // Hand
                    }

                    // New boolean: Whether the client is sneaking/pressing shift
                    wrapper.read(Type.BOOLEAN);
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_16.PLAYER_ABILITIES, new PacketHandlers() {
            @Override
            public void register() {
                handler(wrapper -> {
                    wrapper.passthrough(Type.BYTE);
                    // Flying and walking speed - not important anyways
                    wrapper.write(Type.FLOAT, 0.05F);
                    wrapper.write(Type.FLOAT, 0.1F);
                });
            }
        });
    }

    @Override
    protected void onMappingDataLoaded() {
        int[] wallPostOverrideTag = new int[47];
        int arrayIndex = 0;
        wallPostOverrideTag[arrayIndex++] = 140;
        wallPostOverrideTag[arrayIndex++] = 179;
        wallPostOverrideTag[arrayIndex++] = 264;
        for (int i = 153; i <= 158; i++) {
            wallPostOverrideTag[arrayIndex++] = i;
        }
        for (int i = 163; i <= 168; i++) {
            wallPostOverrideTag[arrayIndex++] = i;
        }
        for (int i = 408; i <= 439; i++) {
            wallPostOverrideTag[arrayIndex++] = i;
        }

        tagRewriter.addTag(RegistryType.BLOCK, "minecraft:wall_post_override", wallPostOverrideTag);
        tagRewriter.addTag(RegistryType.BLOCK, "minecraft:beacon_base_blocks", 133, 134, 148, 265);
        tagRewriter.addTag(RegistryType.BLOCK, "minecraft:climbable", 160, 241, 658);
        tagRewriter.addTag(RegistryType.BLOCK, "minecraft:fire", 142);
        tagRewriter.addTag(RegistryType.BLOCK, "minecraft:campfires", 679);
        tagRewriter.addTag(RegistryType.BLOCK, "minecraft:fence_gates", 242, 467, 468, 469, 470, 471);
        tagRewriter.addTag(RegistryType.BLOCK, "minecraft:unstable_bottom_center", 242, 467, 468, 469, 470, 471);
        tagRewriter.addTag(RegistryType.BLOCK, "minecraft:wooden_trapdoors", 193, 194, 195, 196, 197, 198);
        tagRewriter.addTag(RegistryType.ITEM, "minecraft:wooden_trapdoors", 215, 216, 217, 218, 219, 220);
        tagRewriter.addTag(RegistryType.ITEM, "minecraft:beacon_payment_items", 529, 530, 531, 760);
        tagRewriter.addTag(RegistryType.ENTITY, "minecraft:impact_projectiles", 2, 72, 71, 37, 69, 79, 83, 15, 93);

        // The client crashes if we don't send all tags it may use
        tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:guarded_by_piglins");
        tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:soul_speed_blocks");
        tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:soul_fire_base_blocks");
        tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:non_flammable_wood");
        tagRewriter.addEmptyTag(RegistryType.ITEM, "minecraft:non_flammable_wood");

        // The rest of not accessed tags added in older versions; #1830
        tagRewriter.addEmptyTags(RegistryType.BLOCK, "minecraft:bamboo_plantable_on", "minecraft:beds", "minecraft:bee_growables",
                "minecraft:beehives", "minecraft:coral_plants", "minecraft:crops", "minecraft:dragon_immune", "minecraft:flowers",
                "minecraft:portals", "minecraft:shulker_boxes", "minecraft:small_flowers", "minecraft:tall_flowers", "minecraft:trapdoors",
                "minecraft:underwater_bonemeals", "minecraft:wither_immune", "minecraft:wooden_fences", "minecraft:wooden_trapdoors");
        tagRewriter.addEmptyTags(RegistryType.ENTITY, "minecraft:arrows", "minecraft:beehive_inhabitors", "minecraft:raiders", "minecraft:skeletons");
        tagRewriter.addEmptyTags(RegistryType.ITEM, "minecraft:beds", "minecraft:coals", "minecraft:fences", "minecraft:flowers",
                "minecraft:lectern_books", "minecraft:music_discs", "minecraft:small_flowers", "minecraft:tall_flowers", "minecraft:trapdoors", "minecraft:walls", "minecraft:wooden_fences");
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_16Types.PLAYER));
    }

    @Override
    public BlockItemPackets20w14infinite getItemRewriter() {
        return this.blockItemPackets;
    }

    @Override
    public MetadataRewriter1_16to20w14infinite getEntityRewriter() {
        return this.metadataRewriter;
    }

    @Override
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

}
