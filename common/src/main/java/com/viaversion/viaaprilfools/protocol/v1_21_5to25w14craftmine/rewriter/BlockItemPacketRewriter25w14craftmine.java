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

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaaprilfools.api.minecraft.item.LodestoneTracker25w14craftmine;
import com.viaversion.viaaprilfools.api.minecraft.item.StructuredDataKeys25w14craftmine;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.Protocol1_21_5To_25w14craftmine;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet.ClientboundPackets25w14craftmine;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet.ServerboundPacket25w14craftmine;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet.ServerboundPackets25w14craftmine;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.StructuredItem;
import com.viaversion.viaversion.api.minecraft.item.data.LodestoneTracker;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ClientboundPacket1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ClientboundPackets1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.rewriter.RecipeDisplayRewriter1_21_5;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeDisplayRewriter;
import com.viaversion.viaversion.rewriter.StructuredItemRewriter;
import com.viaversion.viaversion.util.Limit;
import java.util.List;

public final class BlockItemPacketRewriter25w14craftmine extends StructuredItemRewriter<ClientboundPacket1_21_5, ServerboundPacket25w14craftmine, Protocol1_21_5To_25w14craftmine> {

    public static final List<StructuredDataKey<?>> NEW_DATA_TO_REMOVE = List.of(
        StructuredDataKeys25w14craftmine.ITEM_EXCHANGE_VALUE, StructuredDataKeys25w14craftmine.WORLD_EFFECT_UNLOCK, StructuredDataKeys25w14craftmine.WORLD_EFFECT_HINT,
        StructuredDataKeys25w14craftmine.MINE_ACTIVE, StructuredDataKeys25w14craftmine.SPECIAL_MINE, StructuredDataKeys25w14craftmine.MINE_COMPLETED,
        StructuredDataKeys25w14craftmine.WORLD_MODIFIERS, StructuredDataKeys25w14craftmine.DIMENSION_ID, StructuredDataKeys25w14craftmine.ROOM, StructuredDataKeys25w14craftmine.SKY,
        StructuredDataKeys25w14craftmine.TROPHY_TYPE, StructuredDataKeys25w14craftmine.MOB_TROPHY_TYPE
    );

    public static final int NEW_CRAFTING_SLOTS = 5;
    public static final int PLAYER_INVENTORY_ID = 0;

    static final int THIRD_CRAFTING_SLOT = 3;
    static final int FOURTH_CRAFTING_SLOT = 4;
    static final int FIFTH_CRAFTING_SLOT = 5;
    static final int SIXTH_CRAFTING_SLOT = 6;
    static final int SEVENTH_CRAFTING_SLOT = 7;
    static final int EIGHTH_CRAFTING_SLOT = 8;
    static final int NINTH_CRAFTING_SLOT = 9;

    public BlockItemPacketRewriter25w14craftmine(final Protocol1_21_5To_25w14craftmine protocol) {
        super(protocol);
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
        registerCooldown1_21_2(ClientboundPackets1_21_5.COOLDOWN);
        registerSetEquipment(ClientboundPackets1_21_5.SET_EQUIPMENT);
        registerMerchantOffers1_20_5(ClientboundPackets1_21_5.MERCHANT_OFFERS);

        final RecipeDisplayRewriter<ClientboundPacket1_21_5> recipeRewriter = new RecipeDisplayRewriter1_21_5<>(protocol);
        recipeRewriter.registerUpdateRecipes(ClientboundPackets1_21_5.UPDATE_RECIPES);
        recipeRewriter.registerRecipeBookAdd(ClientboundPackets1_21_5.RECIPE_BOOK_ADD);
        recipeRewriter.registerPlaceGhostRecipe(ClientboundPackets1_21_5.PLACE_GHOST_RECIPE);

        protocol.registerClientbound(ClientboundPackets1_21_5.SET_PLAYER_INVENTORY, wrapper -> {
            int slot = wrapper.read(Types.VAR_INT);
            slot = addCraftingSlot(slot);
            wrapper.write(Types.VAR_INT, slot);
            passthroughClientboundItem(wrapper);
        });
        protocol.registerClientbound(ClientboundPackets1_21_5.CONTAINER_SET_CONTENT, wrapper -> {
            final int containerId = wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT); // State id
            if (containerId == PLAYER_INVENTORY_ID) {
                final Item[] items = wrapper.read(itemArrayType());
                for (int i = 0; i < items.length; i++) {
                    items[i] = handleItemToClient(wrapper.user(), items[i]);
                }

                final Item[] mappedItems = new Item[items.length + NEW_CRAFTING_SLOTS];
                mappedItems[THIRD_CRAFTING_SLOT] = StructuredItem.empty();
                mappedItems[SIXTH_CRAFTING_SLOT] = StructuredItem.empty();
                mappedItems[SEVENTH_CRAFTING_SLOT] = StructuredItem.empty();
                mappedItems[EIGHTH_CRAFTING_SLOT] = StructuredItem.empty();
                mappedItems[NINTH_CRAFTING_SLOT] = StructuredItem.empty();
                for (int i = 0; i < items.length; i++) {
                    mappedItems[addCraftingSlot(i)] = items[i];
                }
                wrapper.write(mappedItemArrayType(), mappedItems);
            } else {
                final Item[] items = wrapper.passthroughAndMap(itemArrayType(), mappedItemArrayType());
                for (int i = 0; i < items.length; i++) {
                    items[i] = handleItemToClient(wrapper.user(), items[i]);
                }
            }

            passthroughClientboundItem(wrapper);
        });
        protocol.registerClientbound(ClientboundPackets1_21_5.CONTAINER_SET_SLOT, wrapper -> {
            final int containerId = wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT); // State id
            if (containerId == PLAYER_INVENTORY_ID) {
                addCraftingSlots(wrapper);
            } else {
                wrapper.passthrough(Types.SHORT); // Slot
            }
            passthroughClientboundItem(wrapper);
        });
        protocol.registerServerbound(ServerboundPackets25w14craftmine.SET_CREATIVE_MODE_SLOT, wrapper -> {
            if (!protocol.getEntityRewriter().tracker(wrapper.user()).canInstaBuild()) {
                // Mimic server/client behavior
                wrapper.cancel();
                return;
            }

            removeCraftingSlots(wrapper);
            passthroughLengthPrefixedItem(wrapper);
        });
        protocol.registerServerbound(ServerboundPackets25w14craftmine.CONTAINER_CLICK, wrapper -> {
            final int containerId = wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT); // State id
            if (containerId == PLAYER_INVENTORY_ID) {
                removeCraftingSlots(wrapper);
            } else {
                wrapper.passthrough(Types.SHORT); // Slot
            }
            wrapper.passthrough(Types.BYTE); // Button
            wrapper.passthrough(Types.VAR_INT); // Mode
            final int affectedItems = Limit.max(wrapper.passthrough(Types.VAR_INT), 128);
            for (int i = 0; i < affectedItems; i++) {
                if (containerId == PLAYER_INVENTORY_ID) {
                    removeCraftingSlots(wrapper);
                } else {
                    wrapper.passthrough(Types.SHORT); // Slot
                }
                passthroughHashedItem(wrapper);
            }
            passthroughHashedItem(wrapper); // Carried item
        });

        protocol.registerClientbound(ClientboundPackets1_21_5.UPDATE_ADVANCEMENTS, wrapper -> {
            wrapper.passthrough(Types.BOOLEAN); // Reset/clear
            final int size = wrapper.passthrough(Types.VAR_INT); // Mapping size
            for (int i = 0; i < size; i++) {
                wrapper.passthrough(Types.STRING); // Identifier
                wrapper.passthrough(Types.OPTIONAL_STRING); // Parent

                // Display data
                if (wrapper.passthrough(Types.BOOLEAN)) {
                    final Tag title = wrapper.passthrough(Types.TAG);
                    final Tag description = wrapper.passthrough(Types.TAG);
                    protocol.getComponentRewriter().processTag(wrapper.user(), title);
                    protocol.getComponentRewriter().processTag(wrapper.user(), description);
                    final CompoundTag hint = new CompoundTag();
                    hint.putString("text", "");
                    wrapper.write(Types.TAG, hint);

                    passthroughClientboundItem(wrapper); // Icon
                    wrapper.passthrough(Types.VAR_INT); // Frame type
                    final int flags = wrapper.passthrough(Types.INT); // Flags
                    if ((flags & 1) != 0) {
                        wrapper.passthrough(Types.STRING); // Background texture
                    }
                    wrapper.passthrough(Types.FLOAT); // X
                    wrapper.passthrough(Types.FLOAT); // Y
                }

                final int requirements = wrapper.passthrough(Types.VAR_INT);
                for (int array = 0; array < requirements; array++) {
                    wrapper.passthrough(Types.STRING_ARRAY);
                }

                wrapper.passthrough(Types.BOOLEAN); // Send telemetry
            }
        });

        protocol.registerClientbound(ClientboundPackets1_21_5.OPEN_SCREEN, ClientboundPackets25w14craftmine.OPEN_WINDOW, wrapper -> {
            wrapper.passthrough(Types.VAR_INT); // Container id
            handleMenuType(wrapper);
            protocol.getComponentRewriter().passthroughAndProcess(wrapper); // Title
            wrapper.write(Types.VAR_INT, 0); // Additional data - none
        });
    }

    public static void addCraftingSlots(final PacketWrapper wrapper) {
        short slot = wrapper.read(Types.SHORT);
        slot = ((short) addCraftingSlot(slot));
        wrapper.write(Types.SHORT, slot);
    }

    public static int addCraftingSlot(final int slot) {
        if (slot == THIRD_CRAFTING_SLOT) {
            return FOURTH_CRAFTING_SLOT;
        } else if (slot == FOURTH_CRAFTING_SLOT) {
            return FIFTH_CRAFTING_SLOT;
        } else if (slot >= FIFTH_CRAFTING_SLOT) {
            return slot + NEW_CRAFTING_SLOTS;
        } else {
            return slot;
        }
    }

    public static void removeCraftingSlots(final PacketWrapper wrapper) {
        short slot = wrapper.read(Types.SHORT);
        slot = ((short) removeCraftingSlot(slot));
        wrapper.write(Types.SHORT, slot);
    }

    public static int removeCraftingSlot(final int slot) {
        if (slot == FOURTH_CRAFTING_SLOT) {
            return THIRD_CRAFTING_SLOT;
        } else if (slot == FIFTH_CRAFTING_SLOT) {
            return FOURTH_CRAFTING_SLOT;
        } else if (slot >= FIFTH_CRAFTING_SLOT + NEW_CRAFTING_SLOTS) {
            return slot - NEW_CRAFTING_SLOTS;
        } else {
            return slot;
        }
    }

    @Override
    protected void handleItemDataComponentsToClient(UserConnection connection, Item item, StructuredDataContainer container) {
        super.handleItemDataComponentsToClient(connection, item, container);

        upgradeItemData(item, container);
    }

    public static void upgradeItemData(final Item item, final StructuredDataContainer container) {
        container.replace(StructuredDataKey.LODESTONE_TRACKER, StructuredDataKeys25w14craftmine.LODESTONE_TRACKER,
                tracker -> new LodestoneTracker25w14craftmine(tracker.position(), tracker.tracked(), false));
    }

    @Override
    protected void handleItemDataComponentsToServer(UserConnection connection, Item item, StructuredDataContainer container) {
        super.handleItemDataComponentsToServer(connection, item, container);

        downgradeItemData(item, container);
    }

    public static void downgradeItemData(final Item item, final StructuredDataContainer container) {
        container.replace(StructuredDataKeys25w14craftmine.LODESTONE_TRACKER, StructuredDataKey.LODESTONE_TRACKER,
                tracker -> new LodestoneTracker(tracker.position(), tracker.tracked()));

        container.remove(NEW_DATA_TO_REMOVE);
    }

}
