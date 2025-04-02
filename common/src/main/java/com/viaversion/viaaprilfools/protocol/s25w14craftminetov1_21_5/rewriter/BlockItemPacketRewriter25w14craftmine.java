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
import com.viaversion.viaaprilfools.api.type.version.Types25w14craftmine;
import com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.Protocol25w14craftmineTo1_21_5;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet.ClientboundPacket25w14craftmine;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.packet.ClientboundPackets25w14craftmine;
import com.viaversion.viabackwards.api.rewriters.BackwardsStructuredItemRewriter;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_21_5;
import com.viaversion.viaversion.api.type.types.version.Types1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ClientboundPackets1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ServerboundPacket1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.packet.ServerboundPackets1_21_5;
import com.viaversion.viaversion.protocols.v1_21_4to1_21_5.rewriter.RecipeDisplayRewriter1_21_5;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeDisplayRewriter;
import com.viaversion.viaversion.util.Limit;

import static com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.rewriter.BlockItemPacketRewriter25w14craftmine.*;

public final class BlockItemPacketRewriter25w14craftmine extends BackwardsStructuredItemRewriter<ClientboundPacket25w14craftmine, ServerboundPacket1_21_5, Protocol25w14craftmineTo1_21_5> {

    public BlockItemPacketRewriter25w14craftmine(final Protocol25w14craftmineTo1_21_5 protocol) {
        super(protocol,
            Types25w14craftmine.ITEM, Types25w14craftmine.ITEM_ARRAY, Types1_21_5.ITEM, Types1_21_5.ITEM_ARRAY,
            Types25w14craftmine.ITEM_COST, Types25w14craftmine.OPTIONAL_ITEM_COST, Types1_21_5.ITEM_COST, Types1_21_5.OPTIONAL_ITEM_COST
        );
    }

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
            if (containerId == 0) {
                final Item[] items = wrapper.read(itemArrayType());
                for (int i = 0; i < items.length; i++) {
                    items[i] = handleItemToClient(wrapper.user(), items[i]);
                }

                final Item[] mappedItems = new Item[items.length - NEW_CRAFTING_SLOTS];
                for (int i = 0; i < items.length; i++) {
                    mappedItems[removeCraftingSlot(i)] = items[i];
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
        protocol.registerClientbound(ClientboundPackets25w14craftmine.CONTAINER_SET_SLOT, wrapper -> {
            final int containerId = wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT); // State id
            if (containerId == 0) {
                removeCraftingSlots(wrapper);
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
            passthroughLengthPrefixedItem(wrapper, Types25w14craftmine.LENGTH_PREFIXED_ITEM, Types1_21_5.LENGTH_PREFIXED_ITEM);
        });
        protocol.registerServerbound(ServerboundPackets1_21_5.CONTAINER_CLICK, wrapper -> {
            final int containerId = wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT); // State id
            if (containerId == 0) {
                addCraftingSlots(wrapper);
            } else {
                wrapper.passthrough(Types.SHORT); // Slot
            }
            wrapper.passthrough(Types.BYTE); // Button
            wrapper.passthrough(Types.VAR_INT); // Mode
            final int affectedItems = Limit.max(wrapper.passthrough(Types.VAR_INT), 128);
            for (int i = 0; i < affectedItems; i++) {
                if (containerId == 0) {
                    addCraftingSlots(wrapper);
                } else {
                    wrapper.passthrough(Types.SHORT); // Slot
                }
                passthroughHashedItem(wrapper);
            }
            passthroughHashedItem(wrapper); // Carried item
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
            wrapper.passthrough(Types.VAR_INT); // Container id
            wrapper.passthrough(Types.VAR_INT); // Container type id
            protocol.getComponentRewriter().passthroughAndProcess(wrapper); // Title

            // Additional data - Throw away
            final int size = wrapper.read(Types.VAR_INT);
            for (int i = 0; i < size; i++) {
                wrapper.read(Types.INT);
            }
        });
        protocol.cancelClientbound(ClientboundPackets25w14craftmine.UPDATE_SCREEN);
    }

    @Override
    public Item handleItemToClient(UserConnection connection, Item item) {
        super.handleItemToClient(connection, item);

        final StructuredDataContainer dataContainer = item.dataContainer();
        final CompoundTag backupTag = new CompoundTag();

        saveIntData(VAFStructuredDataKey.SPECIAL_MINE, dataContainer, backupTag);
        saveIntData(VAFStructuredDataKey.SKY, dataContainer, backupTag);
        saveStringData(VAFStructuredDataKey.TROPHY_TYPE, dataContainer, backupTag);
        saveStringData(VAFStructuredDataKey.DIMENSION_ID, dataContainer, backupTag);

        if (dataContainer.has(VAFStructuredDataKey.WORLD_EFFECT_UNLOCK)) {
            backupTag.putBoolean("world_effect_unlock", true);
        }
        if (dataContainer.has(VAFStructuredDataKey.WORLD_EFFECT_HINT)) {
            backupTag.putBoolean("world_effect_hint", true);
        }
        if (dataContainer.has(VAFStructuredDataKey.MINE_ACTIVE)) {
            backupTag.putBoolean("mine_active", true);
        }

        final LodestoneTracker25w14craftmine lodestoneTracker = dataContainer.get(VAFStructuredDataKey.LODESTONE_TRACKER);
        if (lodestoneTracker != null) {
            backupTag.putBoolean("lodestone_tracker|exits", lodestoneTracker.exits());
        }

        final ItemExchangeValue exchangeValue = dataContainer.get(VAFStructuredDataKey.ITEM_EXCHANGE_VALUE);
        if (exchangeValue != null) {
            backupTag.putFloat("item_exchange_value", exchangeValue.value());
        }

        final Boolean mineCompleted = dataContainer.get(VAFStructuredDataKey.MINE_COMPLETED);
        if (mineCompleted != null) {
            backupTag.putBoolean("mine_completed", mineCompleted);
        }

        final WorldModifiers worldModifiers = dataContainer.get(VAFStructuredDataKey.WORLD_MODIFIERS);
        if (worldModifiers != null) {
            final CompoundTag worldModifiersTag = new CompoundTag();
            worldModifiersTag.put("effects", new IntArrayTag(worldModifiers.effects()));
            worldModifiersTag.putBoolean("include_description", worldModifiers.includeDescription());

            backupTag.put("world_modifiers", worldModifiersTag);
        }

        final RoomerinoComponentino roomerinoComponentino = dataContainer.get(VAFStructuredDataKey.ROOM);
        if (roomerinoComponentino != null) {
            backupTag.putString("room", roomerinoComponentino.id());
        }

        final MobTrophyInfo mobTrophyInfo = dataContainer.get(VAFStructuredDataKey.MOB_TROPHY_TYPE);
        if (mobTrophyInfo != null) {
            final CompoundTag mobTrophyInfoTag = new CompoundTag();
            mobTrophyInfoTag.put("type", holderToTag(mobTrophyInfo.type(), (s, tag) -> tag.putString("id", s)));
            mobTrophyInfoTag.putBoolean("shiny", mobTrophyInfo.shiny());

            backupTag.put("mob_trophy/type", mobTrophyInfoTag);
        }

        if (!backupTag.isEmpty()) {
            saveTag(createCustomTag(item), backupTag, "backup");
        }

        downgradeItemData(item);
        return item;
    }

    @Override
    public Item handleItemToServer(UserConnection connection, Item item) {
        super.handleItemToServer(connection, item);
        restoreData(item.dataContainer());
        updateItemData(item);
        return item;
    }

    private void restoreData(final StructuredDataContainer data) {
        final CompoundTag customData = data.get(StructuredDataKey.CUSTOM_DATA);
        if (customData == null || !(customData.remove(nbtTagName("backup")) instanceof final CompoundTag backupTag)) {
            return;
        }

        restoreIntData(VAFStructuredDataKey.SPECIAL_MINE, data, customData);
        restoreIntData(VAFStructuredDataKey.SKY, data, customData);
        restoreStringData(VAFStructuredDataKey.TROPHY_TYPE, data, customData);
        restoreStringData(VAFStructuredDataKey.DIMENSION_ID, data, customData);

        if (backupTag.getBoolean("world_effect_unlock")) {
            data.set(VAFStructuredDataKey.WORLD_EFFECT_UNLOCK);
        }
        if (backupTag.getBoolean("world_effect_hint")) {
            data.set(VAFStructuredDataKey.WORLD_EFFECT_HINT);
        }
        if (backupTag.getBoolean("mine_active")) {
            data.set(VAFStructuredDataKey.MINE_ACTIVE);
        }

        final boolean lodestoneTrackerExits = backupTag.getBoolean("lodestone_tracker|exits");
        data.replace(StructuredDataKey.LODESTONE_TRACKER, VAFStructuredDataKey.LODESTONE_TRACKER, tracker -> {
            return new LodestoneTracker25w14craftmine(tracker.position(), tracker.tracked(), lodestoneTrackerExits);
        });

        final FloatTag itemExchangeValueTag = backupTag.getFloatTag("item_exchange_value");
        if (itemExchangeValueTag != null) {
            data.set(VAFStructuredDataKey.ITEM_EXCHANGE_VALUE, new ItemExchangeValue(itemExchangeValueTag.asFloat()));
        }

        final ByteTag mineCompletedTag = backupTag.getByteTag("mine_completed");
        if (mineCompletedTag != null) {
            data.set(VAFStructuredDataKey.MINE_COMPLETED, mineCompletedTag.asBoolean());
        }

        final CompoundTag worldModifiersTag = backupTag.getCompoundTag("world_modifiers");
        if (worldModifiersTag != null) {
            final IntArrayTag effectsTag = worldModifiersTag.getIntArrayTag("effects");
            if (effectsTag != null) {
                final int[] effects = effectsTag.getValue();
                final boolean includeDescription = worldModifiersTag.getBoolean("include_description");
                data.set(VAFStructuredDataKey.WORLD_MODIFIERS, new WorldModifiers(effects, includeDescription));
            }
        }

        final StringTag roomerinoComponentinoTag = backupTag.getStringTag("room");
        if (roomerinoComponentinoTag != null) {
            data.set(VAFStructuredDataKey.ROOM, new RoomerinoComponentino(roomerinoComponentinoTag.getValue()));
        }

        final CompoundTag mobTrophyInfoTag = backupTag.getCompoundTag("mob_trophy/type");
        if (mobTrophyInfoTag != null) {
            final Holder<String> type = restoreHolder(mobTrophyInfoTag, "tag", s -> s.getString("id"));
            final boolean shiny = mobTrophyInfoTag.getBoolean("shiny");
            data.set(VAFStructuredDataKey.MOB_TROPHY_TYPE, new MobTrophyInfo(type, shiny));
        }

        removeCustomTag(data, customData);
    }

}
