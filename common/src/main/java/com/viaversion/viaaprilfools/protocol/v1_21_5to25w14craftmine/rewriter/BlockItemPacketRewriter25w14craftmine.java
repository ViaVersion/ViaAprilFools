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
package com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.rewriter;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaaprilfools.api.minecraft.item.LodestoneTracker25w14craftmine;
import com.viaversion.viaaprilfools.api.minecraft.item.VAFStructuredDataKey;
import com.viaversion.viaaprilfools.api.type.version.Types25w14craftmine;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.Protocol1_21_5To_25w14craftmine;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet.ClientboundPackets25w14craftmine;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet.ServerboundPacket25w14craftmine;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet.ServerboundPackets25w14craftmine;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.data.LodestoneTracker;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_21_5;
import com.viaversion.viaversion.api.type.types.version.Types1_21_5;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ClientboundPacket1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ClientboundPackets1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.rewriter.RecipeDisplayRewriter1_21_5;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeDisplayRewriter;
import com.viaversion.viaversion.rewriter.StructuredItemRewriter;
import com.viaversion.viaversion.util.Limit;
import com.viaversion.viaversion.util.SerializerVersion;

import java.util.List;

public final class BlockItemPacketRewriter25w14craftmine extends StructuredItemRewriter<ClientboundPacket1_21_5, ServerboundPacket25w14craftmine, Protocol1_21_5To_25w14craftmine> {

    public static final List<StructuredDataKey<?>> NEW_DATA_TO_REMOVE = List.of(
            VAFStructuredDataKey.ITEM_EXCHANGE_VALUE, VAFStructuredDataKey.WORLD_EFFECT_UNLOCK, VAFStructuredDataKey.WORLD_EFFECT_HINT,
            VAFStructuredDataKey.MINE_ACTIVE, VAFStructuredDataKey.SPECIAL_MINE, VAFStructuredDataKey.WORLD_MODIFIERS,
            VAFStructuredDataKey.DIMENSION_ID, VAFStructuredDataKey.ROOM, VAFStructuredDataKey.SKY,
            VAFStructuredDataKey.TROPHY_TYPE, VAFStructuredDataKey.MOB_TROPHY_TYPE
    );

    public BlockItemPacketRewriter25w14craftmine(final Protocol1_21_5To_25w14craftmine protocol) {
        super(protocol,
            Types1_21_5.ITEM, Types1_21_5.ITEM_ARRAY, Types25w14craftmine.ITEM, Types25w14craftmine.ITEM_ARRAY,
            Types1_21_5.ITEM_COST, Types1_21_5.OPTIONAL_ITEM_COST, Types25w14craftmine.ITEM_COST, Types25w14craftmine.OPTIONAL_ITEM_COST
        );
    }

    @Override
    public void registerPackets() {
        final BlockRewriter<ClientboundPacket1_21_5> blockRewriter = BlockRewriter.for1_20_2(protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_21_5.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_21_5.BLOCK_UPDATE);
        blockRewriter.registerSectionBlocksUpdate1_20(ClientboundPackets1_21_5.SECTION_BLOCKS_UPDATE);
        blockRewriter.registerLevelEvent1_21(ClientboundPackets1_21_5.LEVEL_EVENT, 2001);
        blockRewriter.registerLevelChunk1_19(ClientboundPackets1_21_5.LEVEL_CHUNK_WITH_LIGHT, ChunkType1_21_5::new);
        blockRewriter.registerBlockEntityData(ClientboundPackets1_21_5.BLOCK_ENTITY_DATA);

        protocol.registerClientbound(ClientboundPackets1_21_5.SET_CURSOR_ITEM, this::passthroughClientboundItem);
        registerSetPlayerInventory(ClientboundPackets1_21_5.SET_PLAYER_INVENTORY);
        registerCooldown1_21_2(ClientboundPackets1_21_5.COOLDOWN);
        registerSetContent1_21_2(ClientboundPackets1_21_5.CONTAINER_SET_CONTENT);
        registerSetSlot1_21_2(ClientboundPackets1_21_5.CONTAINER_SET_SLOT);
        registerSetEquipment(ClientboundPackets1_21_5.SET_EQUIPMENT);
        registerMerchantOffers1_20_5(ClientboundPackets1_21_5.MERCHANT_OFFERS);
        registerSetCreativeModeSlot1_21_5(ServerboundPackets25w14craftmine.SET_CREATIVE_MODE_SLOT, Types1_21_5.LENGTH_PREFIXED_ITEM, Types25w14craftmine.LENGTH_PREFIXED_ITEM);

        final RecipeDisplayRewriter<ClientboundPacket1_21_5> recipeRewriter = new RecipeDisplayRewriter1_21_5<>(protocol);
        recipeRewriter.registerUpdateRecipes(ClientboundPackets1_21_5.UPDATE_RECIPES);
        recipeRewriter.registerRecipeBookAdd(ClientboundPackets1_21_5.RECIPE_BOOK_ADD);
        recipeRewriter.registerPlaceGhostRecipe(ClientboundPackets1_21_5.PLACE_GHOST_RECIPE);

        // TODO Shift inventory slots since crafting grid is 3x3, all ids are shifted
        protocol.registerServerbound(ServerboundPackets25w14craftmine.CONTAINER_CLICK, wrapper -> {
            wrapper.passthrough(Types.VAR_INT); // Container id
            wrapper.passthrough(Types.VAR_INT); // State id
            wrapper.passthrough(Types.SHORT); // Slot
            wrapper.passthrough(Types.BYTE); // Button
            wrapper.passthrough(Types.VAR_INT); // Mode
            final int affectedItems = Limit.max(wrapper.passthrough(Types.VAR_INT), 128);
            for (int i = 0; i < affectedItems; i++) {
                wrapper.passthrough(Types.SHORT); // Slot
                passthroughHashedItem(wrapper);
            }
            passthroughHashedItem(wrapper); // Carried item
        });

        protocol.registerClientbound(ClientboundPackets1_21_5.UPDATE_ADVANCEMENTS, wrapper -> {
            wrapper.passthrough(Types.BOOLEAN); // Reset/clear
            int size = wrapper.passthrough(Types.VAR_INT); // Mapping size
            for (int i = 0; i < size; i++) {
                wrapper.passthrough(Types.STRING); // Identifier
                wrapper.passthrough(Types.OPTIONAL_STRING); // Parent

                // Display data
                if (wrapper.passthrough(Types.BOOLEAN)) {
                    final Tag title = wrapper.passthrough(Types.TAG);
                    final Tag description = wrapper.passthrough(Types.TAG);
                    protocol.getComponentRewriter().processTag(wrapper.user(), title);
                    protocol.getComponentRewriter().processTag(wrapper.user(), description);
                    final Tag hint = SerializerVersion.V1_21_5.toTag(new StringComponent(""));
                    wrapper.write(Types.TAG, hint);

                    passthroughClientboundItem(wrapper); // Icon
                    wrapper.passthrough(Types.VAR_INT); // Frame type
                    int flags = wrapper.passthrough(Types.INT); // Flags
                    if ((flags & 1) != 0) {
                        wrapper.passthrough(Types.STRING); // Background texture
                    }
                    wrapper.passthrough(Types.FLOAT); // X
                    wrapper.passthrough(Types.FLOAT); // Y
                }

                int requirements = wrapper.passthrough(Types.VAR_INT);
                for (int array = 0; array < requirements; array++) {
                    wrapper.passthrough(Types.STRING_ARRAY);
                }

                wrapper.passthrough(Types.BOOLEAN); // Send telemetry
            }
        });

        protocol.registerClientbound(ClientboundPackets1_21_5.OPEN_SCREEN, ClientboundPackets25w14craftmine.OPEN_WINDOW, wrapper -> {
            wrapper.passthrough(Types.VAR_INT); // Container id
            wrapper.passthrough(Types.VAR_INT); // Container type id
            protocol.getComponentRewriter().passthroughAndProcess(wrapper); // Title
            wrapper.write(Types.VAR_INT, 0); // Additional data - none
        });
    }

    @Override
    public Item handleItemToClient(UserConnection connection, Item item) {
        super.handleItemToClient(connection, item);
        updateItemData(item);

        return item;
    }

    @Override
    public Item handleItemToServer(UserConnection connection, Item item) {
        super.handleItemToServer(connection, item);
        downgradeItemData(item);

        return item;
    }

    public static void updateItemData(final Item item) {
        final StructuredDataContainer dataContainer = item.dataContainer();

        dataContainer.replace(StructuredDataKey.LODESTONE_TRACKER, VAFStructuredDataKey.LODESTONE_TRACKER, tracker -> new LodestoneTracker25w14craftmine(tracker.position(), tracker.tracked(), false));
    }

    public static void downgradeItemData(final Item item) {
        final StructuredDataContainer dataContainer = item.dataContainer();
        dataContainer.replace(VAFStructuredDataKey.LODESTONE_TRACKER, StructuredDataKey.LODESTONE_TRACKER, tracker -> new LodestoneTracker(tracker.position(), tracker.tracked()));

        dataContainer.remove(NEW_DATA_TO_REMOVE);
    }

}
