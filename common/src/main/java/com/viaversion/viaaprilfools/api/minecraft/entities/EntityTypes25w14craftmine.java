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
package com.viaversion.viaaprilfools.api.minecraft.entities;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.util.EntityTypeUtil;
import com.viaversion.viaversion.util.Key;
import java.util.Locale;

public enum EntityTypes25w14craftmine implements EntityType {

    ENTITY(null, null),

    AREA_EFFECT_CLOUD(ENTITY),
    END_CRYSTAL(ENTITY),
    EVOKER_FANGS(ENTITY),
    EXPERIENCE_ORB(ENTITY),
    EYE_OF_ENDER(ENTITY),
    FALLING_BLOCK(ENTITY),
    ITEM(ENTITY),
    TNT(ENTITY),
    OMINOUS_ITEM_SPAWNER(ENTITY),
    MARKER(ENTITY),
    LIGHTNING_BOLT(ENTITY),
    INTERACTION(ENTITY),

    DISPLAY(ENTITY, null),
    BLOCK_DISPLAY(DISPLAY),
    ITEM_DISPLAY(DISPLAY),
    TEXT_DISPLAY(DISPLAY),

    // Hanging entities
    HANGING_ENTITY(ENTITY, null),
    LEASH_KNOT(HANGING_ENTITY),
    PAINTING(HANGING_ENTITY),
    ITEM_FRAME(HANGING_ENTITY),
    GLOW_ITEM_FRAME(ITEM_FRAME),

    // Projectiles
    PROJECTILE(ENTITY, null),
    ITEM_PROJECTILE(PROJECTILE, null),
    SNOWBALL(ITEM_PROJECTILE),
    ENDER_PEARL(ITEM_PROJECTILE),
    EGG(ITEM_PROJECTILE),
    EXPERIENCE_BOTTLE(ITEM_PROJECTILE),
    FIREWORK_ROCKET(PROJECTILE),
    LLAMA_SPIT(PROJECTILE),
    SHULKER_BULLET(PROJECTILE),
    FISHING_BOBBER(PROJECTILE),
    WITHER_SKULL(PROJECTILE),
    DRAGON_FIREBALL(PROJECTILE), // Doesn't actually inherit fireball

    ABSTRACT_THROWN_POTION(ITEM_PROJECTILE, null),
    SPLASH_POTION(ABSTRACT_THROWN_POTION),
    LINGERING_POTION(ABSTRACT_THROWN_POTION),

    ABSTRACT_ARROW(PROJECTILE, null),
    ARROW(ABSTRACT_ARROW),
    SPECTRAL_ARROW(ABSTRACT_ARROW),
    TRIDENT(ABSTRACT_ARROW),

    ABSTRACT_FIREBALL(ENTITY, null),
    FIREBALL(ABSTRACT_FIREBALL),
    SMALL_FIREBALL(ABSTRACT_FIREBALL),

    ABSTRACT_WIND_CHARGE(PROJECTILE, null),
    WIND_CHARGE(ABSTRACT_WIND_CHARGE),
    BREEZE_WIND_CHARGE(ABSTRACT_WIND_CHARGE),

    // Vehicles
    VEHICLE(ENTITY, null),

    ABSTRACT_MINECART(VEHICLE, null),
    MINECART(ABSTRACT_MINECART),
    FURNACE_MINECART(ABSTRACT_MINECART),
    COMMAND_BLOCK_MINECART(ABSTRACT_MINECART),
    TNT_MINECART(ABSTRACT_MINECART),
    SPAWNER_MINECART(ABSTRACT_MINECART),

    ABSTRACT_MINECART_CONTAINER(ABSTRACT_MINECART, null),
    CHEST_MINECART(ABSTRACT_MINECART_CONTAINER),
    HOPPER_MINECART(ABSTRACT_MINECART_CONTAINER),

    ABSTRACT_BOAT(VEHICLE, null),
    ABSTRACT_CHEST_BOAT(ABSTRACT_BOAT, null),
    ACACIA_BOAT(ABSTRACT_BOAT),
    ACACIA_CHEST_BOAT(ABSTRACT_CHEST_BOAT),
    BAMBOO_CHEST_RAFT(ABSTRACT_CHEST_BOAT),
    BAMBOO_RAFT(ABSTRACT_BOAT),
    BIRCH_BOAT(ABSTRACT_BOAT),
    BIRCH_CHEST_BOAT(ABSTRACT_CHEST_BOAT),
    CHERRY_BOAT(ABSTRACT_BOAT),
    CHERRY_CHEST_BOAT(ABSTRACT_CHEST_BOAT),
    DARK_OAK_BOAT(ABSTRACT_BOAT),
    DARK_OAK_CHEST_BOAT(ABSTRACT_CHEST_BOAT),
    JUNGLE_BOAT(ABSTRACT_BOAT),
    JUNGLE_CHEST_BOAT(ABSTRACT_CHEST_BOAT),
    MANGROVE_BOAT(ABSTRACT_BOAT),
    MANGROVE_CHEST_BOAT(ABSTRACT_CHEST_BOAT),
    OAK_BOAT(ABSTRACT_BOAT),
    OAK_CHEST_BOAT(ABSTRACT_CHEST_BOAT),
    PALE_OAK_BOAT(ABSTRACT_BOAT),
    PALE_OAK_CHEST_BOAT(ABSTRACT_CHEST_BOAT),
    SPRUCE_BOAT(ABSTRACT_BOAT),
    SPRUCE_CHEST_BOAT(ABSTRACT_CHEST_BOAT),

    // Living entities as a larger subclass
    LIVING_ENTITY(ENTITY, null),
    ARMOR_STAND(LIVING_ENTITY),
    PLAYER(LIVING_ENTITY),

    // Mobs as a larger subclass
    MOB(LIVING_ENTITY, null),
    ENDER_DRAGON(MOB),

    SLIME(MOB),
    MAGMA_CUBE(SLIME),

    // Ambient mobs
    AMBIENT_CREATURE(MOB, null),
    BAT(AMBIENT_CREATURE),

    // Flying mobs
    FLYING_MOB(MOB, null),
    GHAST(FLYING_MOB),
    PHANTOM(FLYING_MOB),
    ANGRY_GHAST(FLYING_MOB),

    // Pathfinder mobs and its subclasses
    PATHFINDER_MOB(MOB, null),
    ALLAY(PATHFINDER_MOB),

    ABSTRACT_GOLEM(PATHFINDER_MOB, null),
    SNOW_GOLEM(ABSTRACT_GOLEM),
    IRON_GOLEM(ABSTRACT_GOLEM),
    SHULKER(ABSTRACT_GOLEM),

    // Ageable mobs and (tamable) animals
    ABSTRACT_AGEABLE(PATHFINDER_MOB, null),
    ABSTRACT_VILLAGER(ABSTRACT_AGEABLE, null),
    VILLAGER(ABSTRACT_VILLAGER),
    WANDERING_TRADER(ABSTRACT_VILLAGER),

    ABSTRACT_ANIMAL(ABSTRACT_AGEABLE, null),
    AXOLOTL(ABSTRACT_ANIMAL),
    CHICKEN(ABSTRACT_ANIMAL),
    PANDA(ABSTRACT_ANIMAL),
    PIG(ABSTRACT_ANIMAL),
    POLAR_BEAR(ABSTRACT_ANIMAL),
    RABBIT(ABSTRACT_ANIMAL),
    SHEEP(ABSTRACT_ANIMAL),
    BEE(ABSTRACT_ANIMAL),
    TURTLE(ABSTRACT_ANIMAL),
    FOX(ABSTRACT_ANIMAL),
    FROG(ABSTRACT_ANIMAL),
    GOAT(ABSTRACT_ANIMAL),
    HOGLIN(ABSTRACT_ANIMAL),
    STRIDER(ABSTRACT_ANIMAL),
    SNIFFER(ABSTRACT_ANIMAL),
    ARMADILLO(ABSTRACT_ANIMAL),

    ABSTRACT_COW(ABSTRACT_ANIMAL, null),
    COW(ABSTRACT_COW),
    MOOSHROOM(ABSTRACT_COW),

    TAMABLE_ANIMAL(ABSTRACT_ANIMAL, null),
    PET_CAT(TAMABLE_ANIMAL),
    OCELOT(TAMABLE_ANIMAL),
    WOLF(TAMABLE_ANIMAL),
    PARROT(TAMABLE_ANIMAL),

    ABSTRACT_HORSE(ABSTRACT_ANIMAL, null),
    HORSE(ABSTRACT_HORSE),
    SKELETON_HORSE(ABSTRACT_HORSE),
    ZOMBIE_HORSE(ABSTRACT_HORSE),
    CAMEL(ABSTRACT_HORSE),

    ABSTRACT_CHESTED_HORSE(ABSTRACT_HORSE, null),
    DONKEY(ABSTRACT_CHESTED_HORSE),
    MULE(ABSTRACT_CHESTED_HORSE),
    LLAMA(ABSTRACT_CHESTED_HORSE),
    TRADER_LLAMA(LLAMA),

    // Monsters
    ABSTRACT_MONSTER(PATHFINDER_MOB, null),
    BLAZE(ABSTRACT_MONSTER),
    CREEPER(ABSTRACT_MONSTER),
    ENDERMITE(ABSTRACT_MONSTER),
    ENDERMAN(ABSTRACT_MONSTER),
    GIANT(ABSTRACT_MONSTER),
    SILVERFISH(ABSTRACT_MONSTER),
    VEX(ABSTRACT_MONSTER),
    WITHER(ABSTRACT_MONSTER),
    BREEZE(ABSTRACT_MONSTER),
    ZOGLIN(ABSTRACT_MONSTER),
    WARDEN(ABSTRACT_MONSTER),
    CREAKING(ABSTRACT_MONSTER),

    ABSTRACT_SKELETON(ABSTRACT_MONSTER, null),
    SKELETON(ABSTRACT_SKELETON),
    STRAY(ABSTRACT_SKELETON),
    WITHER_SKELETON(ABSTRACT_SKELETON),
    BOGGED(ABSTRACT_SKELETON),

    ZOMBIE(ABSTRACT_MONSTER),
    DROWNED(ZOMBIE),
    HUSK(ZOMBIE),
    ZOMBIFIED_PIGLIN(ZOMBIE),
    ZOMBIE_VILLAGER(ZOMBIE),

    GUARDIAN(ABSTRACT_MONSTER),
    ELDER_GUARDIAN(GUARDIAN),

    SPIDER(ABSTRACT_MONSTER),
    CAVE_SPIDER(SPIDER),

    ABSTRACT_PIGLIN(ABSTRACT_MONSTER, null),
    PIGLIN(ABSTRACT_PIGLIN),
    PIGLIN_BRUTE(ABSTRACT_PIGLIN),

    // Water mobs
    AGEABLE_WATER_CREATURE(ABSTRACT_AGEABLE, null),
    DOLPHIN(AGEABLE_WATER_CREATURE),

    SQUID(AGEABLE_WATER_CREATURE),
    GLOW_SQUID(SQUID),

    WATER_ANIMAL(PATHFINDER_MOB, null),
    ABSTRACT_FISH(WATER_ANIMAL, null),
    PUFFERFISH(ABSTRACT_FISH),
    TADPOLE(ABSTRACT_FISH),

    ABSTRACT_SCHOOLING_FISH(ABSTRACT_FISH, null),
    COD(ABSTRACT_SCHOOLING_FISH),
    SALMON(ABSTRACT_SCHOOLING_FISH),
    TROPICAL_FISH(ABSTRACT_SCHOOLING_FISH),

    // Raiders
    ABSTRACT_RAIDER(ABSTRACT_MONSTER, null),
    WITCH(ABSTRACT_RAIDER),
    RAVAGER(ABSTRACT_RAIDER),

    ABSTRACT_ILLAGER(ABSTRACT_RAIDER, null),
    SPELLCASTER_ILLAGER(ABSTRACT_ILLAGER, null),
    VINDICATOR(ABSTRACT_ILLAGER),
    PILLAGER(ABSTRACT_ILLAGER),
    EVOKER(SPELLCASTER_ILLAGER),
    ILLUSIONER(SPELLCASTER_ILLAGER),

    // Pets
    ABSTRACT_PET(TAMABLE_ANIMAL, null),
    PET_ARMADILLO(ABSTRACT_PET),
    PET_AXOLOTL(ABSTRACT_PET),
    PET_BEE(ABSTRACT_PET),
    CAT(ABSTRACT_PET),
    PET_CHICKEN(ABSTRACT_PET),
    PET_COW(ABSTRACT_PET),
    PET_CREEPER(ABSTRACT_PET),
    PET_FOX(ABSTRACT_PET),
    PET_FROG(ABSTRACT_PET),
    PET_MOOSHROOM(ABSTRACT_PET),
    PET_POLAR_BEAR(ABSTRACT_PET),
    PET_SLIME(ABSTRACT_PET),
    PET_TURTLE(ABSTRACT_PET),
    PET_WOLF(ABSTRACT_PET);

    private static final EntityType[] TYPES = EntityTypeUtil.createSizedArray(values());
    private final EntityType parent;
    private final String identifier;
    private int id = -1;

    EntityTypes25w14craftmine(final EntityType parent) {
        this.parent = parent;
        this.identifier = Key.namespaced(name().toLowerCase(Locale.ROOT));
    }

    EntityTypes25w14craftmine(final EntityType parent, final String identifier) {
        this.parent = parent;
        this.identifier = identifier;
    }

    @Override
    public int getId() {
        if (id == -1) {
            throw new IllegalStateException("Ids have not been initialized yet (type " + name() + ")");
        }
        return id;
    }

    @Override
    public String identifier() {
        Preconditions.checkArgument(identifier != null, "Called identifier method on abstract type");
        return identifier;
    }

    @Override
    public EntityType getParent() {
        return parent;
    }

    @Override
    public boolean isAbstractType() {
        return identifier == null;
    }

    public static EntityType getTypeFromId(final int typeId) {
        return EntityTypeUtil.getTypeFromId(TYPES, typeId, PIG);
    }

    public static void initialize(final Protocol<?, ?, ?, ?> protocol) {
        EntityTypeUtil.initialize(values(), TYPES, protocol, (type, id) -> type.id = id);
    }
}
