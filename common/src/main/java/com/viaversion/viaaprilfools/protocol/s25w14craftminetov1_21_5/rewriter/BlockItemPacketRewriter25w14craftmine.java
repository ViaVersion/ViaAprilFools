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
package com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.rewriter;

import com.viaversion.nbt.tag.*;
import com.viaversion.viaaprilfools.api.minecraft.item.*;
import com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.Protocol25w14craftmineTo1_21_5;
import com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.storage.CurrentContainer;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet.ClientboundPacket25w14craftmine;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet.ClientboundPackets25w14craftmine;
import com.viaversion.viabackwards.api.rewriters.BackwardsStructuredItemRewriter;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.StructuredItem;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ClientboundPackets1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ServerboundPacket1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ServerboundPackets1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.rewriter.RecipeDisplayRewriter1_21_5;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeDisplayRewriter;
import com.viaversion.viaversion.util.Limit;

import static com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.storage.CurrentContainer.*;
import static com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.rewriter.BlockItemPacketRewriter25w14craftmine.*;

public final class BlockItemPacketRewriter25w14craftmine extends BackwardsStructuredItemRewriter<ClientboundPacket25w14craftmine, ServerboundPacket1_21_5, Protocol25w14craftmineTo1_21_5> {

    public BlockItemPacketRewriter25w14craftmine(final Protocol25w14craftmineTo1_21_5 protocol) {
        super(protocol);
    }

    static final int INVENTORY_ROW_WIDTH = 9;
    static final int SECOND_ROW_END = 18;
    static final int GENERIC_9X6_SIZE = 54;

    static final int TO_UNLOCK_EFFECTS_START = 51;
    static final int TO_DISCOVER_EFFECTS_START = 159;

    static final int SUPER_CHARGE_LEVEL = 4;

    @Override
    public void registerPackets() {
        final BlockRewriter<ClientboundPacket25w14craftmine> blockRewriter = BlockRewriter.for1_20_2(protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets25w14craftmine.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets25w14craftmine.BLOCK_UPDATE);
        blockRewriter.registerSectionBlocksUpdate1_20(ClientboundPackets25w14craftmine.SECTION_BLOCKS_UPDATE);
        blockRewriter.registerLevelEvent1_21(ClientboundPackets25w14craftmine.LEVEL_EVENT, 2001);
        blockRewriter.registerLevelChunk1_19(ClientboundPackets25w14craftmine.LEVEL_CHUNK_WITH_LIGHT, ChunkType1_21_5::new);
        blockRewriter.registerBlockEntityData(ClientboundPackets25w14craftmine.BLOCK_ENTITY_DATA);

        protocol.registerClientbound(ClientboundPackets25w14craftmine.SET_CURSOR_ITEM, this::passthroughClientboundItem);
        registerCooldown1_21_2(ClientboundPackets25w14craftmine.COOLDOWN);
        registerSetEquipment(ClientboundPackets25w14craftmine.SET_EQUIPMENT);
        registerMerchantOffers1_20_5(ClientboundPackets25w14craftmine.MERCHANT_OFFERS);

        final RecipeDisplayRewriter<ClientboundPacket25w14craftmine> recipeRewriter = new RecipeDisplayRewriter1_21_5<>(protocol);
        recipeRewriter.registerUpdateRecipes(ClientboundPackets25w14craftmine.UPDATE_RECIPES);
        recipeRewriter.registerRecipeBookAdd(ClientboundPackets25w14craftmine.RECIPE_BOOK_ADD);
        recipeRewriter.registerPlaceGhostRecipe(ClientboundPackets25w14craftmine.PLACE_GHOST_RECIPE);

        protocol.registerClientbound(ClientboundPackets25w14craftmine.SET_PLAYER_INVENTORY, wrapper -> {
            int slot = wrapper.read(Types.VAR_INT);
            slot = removeCraftingSlot(slot);
            wrapper.write(Types.VAR_INT, slot);
            passthroughClientboundItem(wrapper);
        });
        protocol.registerClientbound(ClientboundPackets25w14craftmine.CONTAINER_SET_CONTENT, wrapper -> {
            final int containerId = wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT); // State id

            final Item[] items = wrapper.read(itemArrayType());
            for (int i = 0; i < items.length; i++) {
                items[i] = handleItemToClient(wrapper.user(), items[i]);
            }
            final CurrentContainer currentContainer = wrapper.user().get(CurrentContainer.class);
            if (containerId == PLAYER_INVENTORY_ID) {
                final Item[] mappedItems = new Item[items.length - NEW_CRAFTING_SLOTS];
                for (int i = 0; i < items.length; i++) {
                    mappedItems[removeCraftingSlot(i)] = items[i];
                }
                wrapper.write(mappedItemArrayType(), mappedItems);
            } else if (currentContainer.isOpen(MAP_MAKING, containerId)) {
                final Item[] mappedItems = StructuredItem.emptyArray(54);
                for (int i = 0; i < items.length; i++) {
                    final int actualSlot = removeMapMakingContainerSlot(i);
                    if (actualSlot == -1) {
                        continue;
                    }

                    mappedItems[actualSlot] = items[i];
                }
                wrapper.write(mappedItemArrayType(), mappedItems);
            } else {
                wrapper.write(mappedItemArrayType(), items);
            }

            passthroughClientboundItem(wrapper);
        });
        protocol.registerClientbound(ClientboundPackets25w14craftmine.CONTAINER_SET_SLOT, wrapper -> {
            final CurrentContainer currentContainer = wrapper.user().get(CurrentContainer.class);
            final int containerId = wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT); // State id
            if (containerId == PLAYER_INVENTORY_ID) {
                removeCraftingSlots(wrapper);
            } else if (currentContainer.isOpen(MAP_MAKING, containerId)) {
                removeMapMakingContainerSlots(wrapper);
            } else {
                wrapper.passthrough(Types.SHORT); // Slot
            }
            passthroughClientboundItem(wrapper);
        });
        protocol.registerServerbound(ServerboundPackets1_21_5.SET_CREATIVE_MODE_SLOT, wrapper -> {
            if (!protocol.getEntityRewriter().tracker(wrapper.user()).canInstaBuild()) {
                // Mimic server/client behavior
                wrapper.cancel();
                return;
            }

            addCraftingSlots(wrapper);
            passthroughLengthPrefixedItem(wrapper);
        });
        protocol.registerServerbound(ServerboundPackets1_21_5.CONTAINER_CLICK, wrapper -> {
            final CurrentContainer currentContainer = wrapper.user().get(CurrentContainer.class);
            final int containerId = wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT); // State id
            if (containerId == PLAYER_INVENTORY_ID) {
                addCraftingSlots(wrapper);
            } else if (currentContainer.isOpen(MAP_MAKING, containerId)) {
                addMapMakingContainerSlots(wrapper);
            } else {
                wrapper.passthrough(Types.SHORT); // Slot
            }
            wrapper.passthrough(Types.BYTE); // Button
            wrapper.passthrough(Types.VAR_INT); // Mode
            final int affectedItems = Limit.max(wrapper.passthrough(Types.VAR_INT), 128);
            for (int i = 0; i < affectedItems; i++) {
                if (containerId == PLAYER_INVENTORY_ID) {
                    addCraftingSlots(wrapper);
                } else if (currentContainer.isOpen(MAP_MAKING, containerId)) {
                    addMapMakingContainerSlots(wrapper);
                } else {
                    wrapper.passthrough(Types.SHORT); // Slot
                }
                passthroughHashedItem(wrapper);
            }
            passthroughHashedItem(wrapper); // Carried item
        });
        protocol.registerClientbound(ClientboundPackets25w14craftmine.CONTAINER_SET_DATA, wrapper -> {
            final short containerId = wrapper.passthrough(Types.UNSIGNED_BYTE);
            final short property = wrapper.passthrough(Types.SHORT);

            final CurrentContainer currentContainer = wrapper.user().get(CurrentContainer.class);
            if (currentContainer.isOpen(FURNACE, containerId) || currentContainer.isOpen(BLAST_FURNACE, containerId)) {
                if (property == SUPER_CHARGE_LEVEL) {
                    wrapper.cancel();
                }
            }
        });

        protocol.registerClientbound(ClientboundPackets25w14craftmine.UPDATE_ADVANCEMENTS, wrapper -> {
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
                    wrapper.read(Types.TAG); // Hint - Not used

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

        protocol.registerClientbound(ClientboundPackets25w14craftmine.OPEN_WINDOW, ClientboundPackets1_21_5.OPEN_SCREEN, wrapper -> {
            final int containerId = wrapper.passthrough(Types.VAR_INT);
            final int containerTypeId = wrapper.read(Types.VAR_INT);
            final int mappedId = protocol.getMappingData().getMenuMappings().getNewId(containerTypeId);
            wrapper.write(Types.VAR_INT, mappedId);

            final CurrentContainer currentContainer = wrapper.user().get(CurrentContainer.class);
            currentContainer.openContainer(containerId, containerTypeId);

            protocol.getComponentRewriter().passthroughAndProcess(wrapper); // Title

            // Additional data - Throw away
            final int size = wrapper.read(Types.VAR_INT);
            for (int i = 0; i < size; i++) {
                wrapper.read(Types.INT);
            }
        });
        protocol.cancelClientbound(ClientboundPackets25w14craftmine.UPDATE_SCREEN);
    }

    private void removeMapMakingContainerSlots(final PacketWrapper wrapper) {
        short slot = wrapper.read(Types.SHORT);
        slot = ((short) removeMapMakingContainerSlot(slot));
        wrapper.write(Types.SHORT, slot);
    }

    private int removeMapMakingContainerSlot(final int slot) {
        // Try to change the layout to work without custom screen,
        // Items *will* be lost
        if (slot == 0) {
            return INVENTORY_ROW_WIDTH - 1;
        } else if (slot < TO_UNLOCK_EFFECTS_START) {
            return slot - 1;
        } else if (slot < TO_DISCOVER_EFFECTS_START) {
            final int slotIndex = SECOND_ROW_END + (slot - TO_UNLOCK_EFFECTS_START);
            if (slotIndex >= GENERIC_9X6_SIZE) {
                return -1;
            }
            return slotIndex;
        } else {
            return -1;
        }
    }

    private void addMapMakingContainerSlots(final PacketWrapper wrapper) {
        short slot = wrapper.read(Types.SHORT);
        slot = ((short) addMapMakingContainerSlot(slot));
        wrapper.write(Types.SHORT, slot);
    }

    private int addMapMakingContainerSlot(final int slot) {
        if (slot == INVENTORY_ROW_WIDTH - 1) {
            return 0;
        } else if (slot < INVENTORY_ROW_WIDTH) {
            return slot + 1;
        } else if (slot >= SECOND_ROW_END && slot < GENERIC_9X6_SIZE) {
            return TO_UNLOCK_EFFECTS_START + (slot - SECOND_ROW_END);
        } else {
            return -1;
        }
    }

    @Override
    protected void backupInconvertibleData(UserConnection connection, Item item, StructuredDataContainer dataContainer, CompoundTag backupTag) {
        super.backupInconvertibleData(connection, item, dataContainer, backupTag);
        rewriteWorldModifiers(connection, dataContainer);

        saveIntData(StructuredDataKeys25w14craftmine.SPECIAL_MINE, dataContainer, backupTag);
        saveIntData(StructuredDataKeys25w14craftmine.SKY, dataContainer, backupTag);
        saveStringData(StructuredDataKeys25w14craftmine.TROPHY_TYPE, dataContainer, backupTag);
        saveStringData(StructuredDataKeys25w14craftmine.DIMENSION_ID, dataContainer, backupTag);

        if (dataContainer.has(StructuredDataKeys25w14craftmine.WORLD_EFFECT_UNLOCK)) {
            backupTag.putBoolean("world_effect_unlock", true);
        }
        if (dataContainer.has(StructuredDataKeys25w14craftmine.WORLD_EFFECT_HINT)) {
            backupTag.putBoolean("world_effect_hint", true);
        }
        if (dataContainer.has(StructuredDataKeys25w14craftmine.MINE_ACTIVE)) {
            backupTag.putBoolean("mine_active", true);
        }

        final LodestoneTracker25w14craftmine lodestoneTracker = dataContainer.get(StructuredDataKeys25w14craftmine.LODESTONE_TRACKER);
        if (lodestoneTracker != null) {
            backupTag.putBoolean("lodestone_tracker|exits", lodestoneTracker.exits());
        }

        final ItemExchangeValue exchangeValue = dataContainer.get(StructuredDataKeys25w14craftmine.ITEM_EXCHANGE_VALUE);
        if (exchangeValue != null) {
            backupTag.putFloat("item_exchange_value", exchangeValue.value());
        }

        final Boolean mineCompleted = dataContainer.get(StructuredDataKeys25w14craftmine.MINE_COMPLETED);
        if (mineCompleted != null) {
            backupTag.putBoolean("mine_completed", mineCompleted);
        }

        final WorldModifiers worldModifiers = dataContainer.get(StructuredDataKeys25w14craftmine.WORLD_MODIFIERS);
        if (worldModifiers != null) {
            final CompoundTag worldModifiersTag = new CompoundTag();
            worldModifiersTag.put("effects", new IntArrayTag(worldModifiers.effects()));
            worldModifiersTag.putBoolean("include_description", worldModifiers.includeDescription());

            backupTag.put("world_modifiers", worldModifiersTag);
        }

        final RoomerinoComponentino roomerinoComponentino = dataContainer.get(StructuredDataKeys25w14craftmine.ROOM);
        if (roomerinoComponentino != null) {
            backupTag.putString("room", roomerinoComponentino.id());
        }

        final MobTrophyInfo mobTrophyInfo = dataContainer.get(StructuredDataKeys25w14craftmine.MOB_TROPHY_TYPE);
        if (mobTrophyInfo != null) {
            final CompoundTag mobTrophyInfoTag = new CompoundTag();
            mobTrophyInfoTag.put("type", holderToTag(mobTrophyInfo.type(), (s, tag) -> tag.putString("id", s)));
            mobTrophyInfoTag.putBoolean("shiny", mobTrophyInfo.shiny());

            backupTag.put("mob_trophy/type", mobTrophyInfoTag);
        }

        if (!backupTag.isEmpty()) {
            saveTag(createCustomTag(item), backupTag, "backup");
        }
    }

    // Try to reconstruct the item data, this is everything else than 1:1 but should be enough
    private void rewriteWorldModifiers(final UserConnection connection, final StructuredDataContainer dataContainer) {
        final WorldModifiers worldModifiers = dataContainer.get(StructuredDataKeys25w14craftmine.WORLD_MODIFIERS);
        if (worldModifiers == null) {
            return;
        }

        for (final int effect : worldModifiers.effects()) {
            final CompoundTag effectData = this.protocol.getMappingData().getWorldEffect(effect);
            if (effectData == null) {
                continue;
            }

            final String itemModel = effectData.getString("item_model");
            if (itemModel != null) {
                final Tag name = effectData.get("name");
                final Tag description = effectData.get("description");
                protocol.getComponentRewriter().processTag(connection, name);
                protocol.getComponentRewriter().processTag(connection, description);
                dataContainer.set(StructuredDataKey.CUSTOM_NAME, name);
                dataContainer.set(StructuredDataKey.LORE, new Tag[] { description });
                dataContainer.set(StructuredDataKey.ITEM_MODEL, itemModel);
                break;
            }
        }
    }

    @Override
    protected void handleItemDataComponentsToClient(UserConnection connection, Item item, StructuredDataContainer container) {
        super.handleItemDataComponentsToClient(connection, item, container);
        downgradeItemData(item, container);
    }

    @Override
    protected void handleItemDataComponentsToServer(UserConnection connection, Item item, StructuredDataContainer container) {
        super.handleItemDataComponentsToServer(connection, item, container);
        upgradeItemData(item, container);
    }

    @Override
    protected void restoreBackupData(Item item, StructuredDataContainer container, CompoundTag customData) {
        super.restoreBackupData(item, container, customData);
        if (!(customData.remove(nbtTagName("backup")) instanceof final CompoundTag backupTag)) {
            return;
        }

        restoreIntData(StructuredDataKeys25w14craftmine.SPECIAL_MINE, container, customData);
        restoreIntData(StructuredDataKeys25w14craftmine.SKY, container, customData);
        restoreStringData(StructuredDataKeys25w14craftmine.TROPHY_TYPE, container, customData);
        restoreStringData(StructuredDataKeys25w14craftmine.DIMENSION_ID, container, customData);

        if (backupTag.getBoolean("world_effect_unlock")) {
            container.set(StructuredDataKeys25w14craftmine.WORLD_EFFECT_UNLOCK);
        }
        if (backupTag.getBoolean("world_effect_hint")) {
            container.set(StructuredDataKeys25w14craftmine.WORLD_EFFECT_HINT);
        }
        if (backupTag.getBoolean("mine_active")) {
            container.set(StructuredDataKeys25w14craftmine.MINE_ACTIVE);
        }

        final boolean lodestoneTrackerExits = backupTag.getBoolean("lodestone_tracker|exits");
        container.replace(StructuredDataKey.LODESTONE_TRACKER, StructuredDataKeys25w14craftmine.LODESTONE_TRACKER,
                tracker -> new LodestoneTracker25w14craftmine(tracker.position(), tracker.tracked(), lodestoneTrackerExits));

        final FloatTag itemExchangeValueTag = backupTag.getFloatTag("item_exchange_value");
        if (itemExchangeValueTag != null) {
            container.set(StructuredDataKeys25w14craftmine.ITEM_EXCHANGE_VALUE, new ItemExchangeValue(itemExchangeValueTag.asFloat()));
        }

        final ByteTag mineCompletedTag = backupTag.getByteTag("mine_completed");
        if (mineCompletedTag != null) {
            container.set(StructuredDataKeys25w14craftmine.MINE_COMPLETED, mineCompletedTag.asBoolean());
        }

        final CompoundTag worldModifiersTag = backupTag.getCompoundTag("world_modifiers");
        if (worldModifiersTag != null) {
            final IntArrayTag effectsTag = worldModifiersTag.getIntArrayTag("effects");
            if (effectsTag != null) {
                final int[] effects = effectsTag.getValue();
                final boolean includeDescription = worldModifiersTag.getBoolean("include_description");
                container.set(StructuredDataKeys25w14craftmine.WORLD_MODIFIERS, new WorldModifiers(effects, includeDescription));
            }
        }

        final StringTag roomerinoComponentinoTag = backupTag.getStringTag("room");
        if (roomerinoComponentinoTag != null) {
            container.set(StructuredDataKeys25w14craftmine.ROOM, new RoomerinoComponentino(roomerinoComponentinoTag.getValue()));
        }

        final CompoundTag mobTrophyInfoTag = backupTag.getCompoundTag("mob_trophy/type");
        if (mobTrophyInfoTag != null) {
            final Holder<String> type = restoreHolder(mobTrophyInfoTag, "tag", s -> s.getString("id"));
            final boolean shiny = mobTrophyInfoTag.getBoolean("shiny");
            container.set(StructuredDataKeys25w14craftmine.MOB_TROPHY_TYPE, new MobTrophyInfo(type, shiny));
        }

        removeCustomTag(container, customData);
    }

}
