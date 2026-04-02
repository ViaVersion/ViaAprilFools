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
package com.viaversion.viaaprilfools.protocol.v26_1tos26w14a.rewriter;

import com.viaversion.viaaprilfools.api.minecraft.item.VAFStructuredDataKeys;
import com.viaversion.viaaprilfools.protocol.v26_1tos26w14a.Protocol26_1To26w14a;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType26_1;
import com.viaversion.viaversion.protocols.v1_21_11to26_1.packet.ClientboundPacket26_1;
import com.viaversion.viaversion.protocols.v1_21_11to26_1.packet.ClientboundPackets26_1;
import com.viaversion.viaversion.protocols.v1_21_11to26_1.packet.ServerboundPacket26_1;
import com.viaversion.viaversion.protocols.v1_21_11to26_1.packet.ServerboundPackets26_1;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.rewriter.RecipeDisplayRewriter1_21_5;
import com.viaversion.viaversion.protocols.v1_21_7to1_21_9.packet.ClientboundConfigurationPackets1_21_9;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeDisplayRewriter;
import com.viaversion.viaversion.rewriter.StructuredItemRewriter;
import com.viaversion.viaversion.rewriter.block.BlockRewriter1_21_5;

public final class BlockItemPacketRewriter26w14a extends StructuredItemRewriter<ClientboundPacket26_1, ServerboundPacket26_1, Protocol26_1To26w14a> {

    public BlockItemPacketRewriter26w14a(final Protocol26_1To26w14a protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        final BlockRewriter<ClientboundPacket26_1> blockRewriter = new BlockRewriter1_21_5<>(protocol, ChunkType26_1::new);
        blockRewriter.registerBlockEvent(ClientboundPackets26_1.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets26_1.BLOCK_UPDATE);
        blockRewriter.registerSectionBlocksUpdate1_20(ClientboundPackets26_1.SECTION_BLOCKS_UPDATE);
        blockRewriter.registerLevelEvent1_21(ClientboundPackets26_1.LEVEL_EVENT);
        blockRewriter.registerBlockEntityData1_18(ClientboundPackets26_1.BLOCK_ENTITY_DATA);
        blockRewriter.registerLevelChunk1_18(ClientboundPackets26_1.LEVEL_CHUNK_WITH_LIGHT);

        registerSetCursorItem(ClientboundPackets26_1.SET_CURSOR_ITEM);
        registerSetPlayerInventory(ClientboundPackets26_1.SET_PLAYER_INVENTORY);
        registerCooldown1_21_2(ClientboundPackets26_1.COOLDOWN);
        registerSetContent1_21_2(ClientboundPackets26_1.CONTAINER_SET_CONTENT);
        registerSetSlot1_21_2(ClientboundPackets26_1.CONTAINER_SET_SLOT);
        registerAdvancements1_20_3(ClientboundPackets26_1.UPDATE_ADVANCEMENTS);
        registerSetEquipment(ClientboundPackets26_1.SET_EQUIPMENT);
        registerMerchantOffers1_20_5(ClientboundPackets26_1.MERCHANT_OFFERS);
        registerContainerClick1_21_5(ServerboundPackets26_1.CONTAINER_CLICK);
        registerSetCreativeModeSlot1_21_5(ServerboundPackets26_1.SET_CREATIVE_MODE_SLOT);
        registerShowDialog(ClientboundPackets26_1.SHOW_DIALOG);
        registerShowDialogDirect(ClientboundConfigurationPackets1_21_9.SHOW_DIALOG);

        final RecipeDisplayRewriter<ClientboundPacket26_1> recipeRewriter = new RecipeDisplayRewriter1_21_5<>(protocol);
        recipeRewriter.registerUpdateRecipes(ClientboundPackets26_1.UPDATE_RECIPES);
        recipeRewriter.registerRecipeBookAdd(ClientboundPackets26_1.RECIPE_BOOK_ADD);
        recipeRewriter.registerPlaceGhostRecipe(ClientboundPackets26_1.PLACE_GHOST_RECIPE);
    }

    @Override
    protected void handleItemDataComponentsToServer(final UserConnection connection, final Item item, final StructuredDataContainer container) {
        downgradeData(container);
        super.handleItemDataComponentsToServer(connection, item, container);
    }

    public static void downgradeData(final StructuredDataContainer container) {
        container.remove(VAFStructuredDataKeys.FOLLOW);
    }

}
