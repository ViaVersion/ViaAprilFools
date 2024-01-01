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
package net.raphimc.viaaprilfools.protocols.protocol1_16to20w14infinite.metadata;

import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import net.raphimc.viaaprilfools.ViaAprilFools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public enum Entity20w14infiniteTypes implements EntityType {

    ENTITY(-1),

    AREA_EFFECT_CLOUD(0, ENTITY),
    END_CRYSTAL(18, ENTITY),
    EVOKER_FANGS(22, ENTITY),
    EXPERIENCE_ORB(24, ENTITY),
    EYE_OF_ENDER(25, ENTITY),
    FALLING_BLOCK(26, ENTITY),
    FIREWORK_ROCKET(27, ENTITY),
    ITEM(35, ENTITY),
    LLAMA_SPIT(40, ENTITY),
    TNT(59, ENTITY),
    SHULKER_BULLET(64, ENTITY),
    FISHING_BOBBER(105, ENTITY),

    LIVINGENTITY(-1, ENTITY),
    ARMOR_STAND(1, LIVINGENTITY),
    PLAYER(104, LIVINGENTITY),

    ABSTRACT_INSENTIENT(-1, LIVINGENTITY),
    ENDER_DRAGON(19, ABSTRACT_INSENTIENT),

    BEE(4, ABSTRACT_INSENTIENT),

    ABSTRACT_CREATURE(-1, ABSTRACT_INSENTIENT),

    ABSTRACT_AGEABLE(-1, ABSTRACT_CREATURE),
    VILLAGER(85, ABSTRACT_AGEABLE),
    WANDERING_TRADER(89, ABSTRACT_AGEABLE),

    // Animals
    ABSTRACT_ANIMAL(-1, ABSTRACT_AGEABLE),
    DOLPHIN(14, ABSTRACT_INSENTIENT),
    CHICKEN(9, ABSTRACT_ANIMAL),
    COW(11, ABSTRACT_ANIMAL),
    MOOSHROOM(50, COW),
    PANDA(53, ABSTRACT_INSENTIENT),
    PIG(55, ABSTRACT_ANIMAL),
    POLAR_BEAR(58, ABSTRACT_ANIMAL),
    RABBIT(60, ABSTRACT_ANIMAL),
    SHEEP(62, ABSTRACT_ANIMAL),
    TURTLE(78, ABSTRACT_ANIMAL),
    FOX(28, ABSTRACT_ANIMAL),

    ABSTRACT_TAMEABLE_ANIMAL(-1, ABSTRACT_ANIMAL),
    CAT(7, ABSTRACT_TAMEABLE_ANIMAL),
    OCELOT(51, ABSTRACT_TAMEABLE_ANIMAL),
    WOLF(94, ABSTRACT_TAMEABLE_ANIMAL),

    ABSTRACT_PARROT(-1, ABSTRACT_TAMEABLE_ANIMAL),
    PARROT(54, ABSTRACT_PARROT),

    // Horses
    ABSTRACT_HORSE(-1, ABSTRACT_ANIMAL),
    CHESTED_HORSE(-1, ABSTRACT_HORSE),
    DONKEY(13, CHESTED_HORSE),
    MULE(49, CHESTED_HORSE),
    LLAMA(39, CHESTED_HORSE),
    TRADER_LLAMA(76, CHESTED_HORSE),
    HORSE(32, ABSTRACT_HORSE),
    SKELETON_HORSE(67, ABSTRACT_HORSE),
    ZOMBIE_HORSE(96, ABSTRACT_HORSE),

    // Golem
    ABSTRACT_GOLEM(-1, ABSTRACT_CREATURE),
    SNOW_GOLEM(70, ABSTRACT_GOLEM),
    IRON_GOLEM(86, ABSTRACT_GOLEM),
    SHULKER(63, ABSTRACT_GOLEM),

    // Fish
    ABSTRACT_FISHES(-1, ABSTRACT_CREATURE),
    COD(10, ABSTRACT_FISHES),
    PUFFERFISH(56, ABSTRACT_FISHES),
    SALMON(61, ABSTRACT_FISHES),
    TROPICAL_FISH(77, ABSTRACT_FISHES),

    // Monsters
    ABSTRACT_MONSTER(-1, ABSTRACT_CREATURE),
    BLAZE(5, ABSTRACT_MONSTER),
    CREEPER(12, ABSTRACT_MONSTER),
    ENDERMITE(21, ABSTRACT_MONSTER),
    ENDERMAN(20, ABSTRACT_MONSTER),
    GIANT(30, ABSTRACT_MONSTER),
    SILVERFISH(65, ABSTRACT_MONSTER),
    VEX(84, ABSTRACT_MONSTER),
    WITCH(90, ABSTRACT_MONSTER),
    WITHER(91, ABSTRACT_MONSTER),
    RAVAGER(99, ABSTRACT_MONSTER),
    HOGLIN(100, ABSTRACT_ANIMAL),
    PIGLIN(101, ABSTRACT_MONSTER),
    STRIDER(102, ABSTRACT_ANIMAL),

    // Illagers
    ABSTRACT_ILLAGER_BASE(-1, ABSTRACT_MONSTER),
    ABSTRACT_EVO_ILLU_ILLAGER(-1, ABSTRACT_ILLAGER_BASE),
    EVOKER(23, ABSTRACT_EVO_ILLU_ILLAGER),
    ILLUSIONER(34, ABSTRACT_EVO_ILLU_ILLAGER),
    VINDICATOR(87, ABSTRACT_ILLAGER_BASE),
    PILLAGER(88, ABSTRACT_ILLAGER_BASE),

    // Skeletons
    ABSTRACT_SKELETON(-1, ABSTRACT_MONSTER),
    SKELETON(66, ABSTRACT_SKELETON),
    STRAY(75, ABSTRACT_SKELETON),
    WITHER_SKELETON(92, ABSTRACT_SKELETON),

    // Guardians
    GUARDIAN(31, ABSTRACT_MONSTER),
    ELDER_GUARDIAN(17, GUARDIAN),

    // Spiders
    SPIDER(73, ABSTRACT_MONSTER),
    CAVE_SPIDER(8, SPIDER),

    // Zombies
    ZOMBIE(95, ABSTRACT_MONSTER),
    DROWNED(16, ZOMBIE),
    HUSK(33, ZOMBIE),
    ZOMBIE_PIGMAN(57, ZOMBIE),
    ZOMBIE_VILLAGER(97, ZOMBIE),

    // Flying entities
    ABSTRACT_FLYING(-1, ABSTRACT_INSENTIENT),
    GHAST(29, ABSTRACT_FLYING),
    PHANTOM(98, ABSTRACT_FLYING),

    ABSTRACT_AMBIENT(-1, ABSTRACT_INSENTIENT),
    BAT(3, ABSTRACT_AMBIENT),

    ABSTRACT_WATERMOB(-1, ABSTRACT_INSENTIENT),
    SQUID(74, ABSTRACT_WATERMOB),

    // Slimes
    SLIME(68, ABSTRACT_INSENTIENT),
    MAGMA_CUBE(41, SLIME),

    // Hangable objects
    ABSTRACT_HANGING(-1, ENTITY),
    LEASH_KNOT(38, ABSTRACT_HANGING),
    ITEM_FRAME(36, ABSTRACT_HANGING),
    PAINTING(52, ABSTRACT_HANGING),

    ABSTRACT_LIGHTNING(-1, ENTITY),
    LIGHTNING_BOLT(103, ABSTRACT_LIGHTNING),

    // Arrows
    ABSTRACT_ARROW(-1, ENTITY),
    ARROW(2, ABSTRACT_ARROW),
    SPECTRAL_ARROW(72, ABSTRACT_ARROW),
    TRIDENT(83, ABSTRACT_ARROW),

    // Fireballs
    ABSTRACT_FIREBALL(-1, ENTITY),
    DRAGON_FIREBALL(15, ABSTRACT_FIREBALL),
    FIREBALL(37, ABSTRACT_FIREBALL),
    SMALL_FIREBALL(69, ABSTRACT_FIREBALL),
    WITHER_SKULL(93, ABSTRACT_FIREBALL),

    // Projectiles
    PROJECTILE_ABSTRACT(-1, ENTITY),
    SNOWBALL(71, PROJECTILE_ABSTRACT),
    ENDER_PEARL(80, PROJECTILE_ABSTRACT),
    EGG(79, PROJECTILE_ABSTRACT),
    POTION(82, PROJECTILE_ABSTRACT),
    EXPERIENCE_BOTTLE(81, PROJECTILE_ABSTRACT),

    // Vehicles
    MINECART_ABSTRACT(-1, ENTITY),
    CHESTED_MINECART_ABSTRACT(-1, MINECART_ABSTRACT),
    CHEST_MINECART(43, CHESTED_MINECART_ABSTRACT),
    HOPPER_MINECART(46, CHESTED_MINECART_ABSTRACT),
    MINECART(42, MINECART_ABSTRACT),
    FURNACE_MINECART(45, MINECART_ABSTRACT),
    COMMAND_BLOCK_MINECART(44, MINECART_ABSTRACT),
    TNT_MINECART(48, MINECART_ABSTRACT),
    SPAWNER_MINECART(47, MINECART_ABSTRACT),
    BOAT(6, ENTITY);

    private static final EntityType[] TYPES;

    private final int id;
    private final EntityType parent;

    Entity20w14infiniteTypes(int id) {
        this.id = id;
        this.parent = null;
    }

    Entity20w14infiniteTypes(int id, EntityType parent) {
        this.id = id;
        this.parent = parent;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public EntityType getParent() {
        return parent;
    }

    @Override
    public String identifier() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAbstractType() {
        return this.id != -1;
    }

    static {
        List<Entity20w14infiniteTypes> types = new ArrayList<>();
        for (Entity20w14infiniteTypes type : values()) {
            if (type.id != -1) {
                types.add(type);
            }
        }

        types.sort(Comparator.comparingInt(Entity20w14infiniteTypes::getId));
        TYPES = types.toArray(new EntityType[0]);
    }

    public static EntityType getTypeFromId(int typeId) {
        EntityType type;
        if (typeId < 0 || typeId >= TYPES.length || (type = TYPES[typeId]) == null) {
            ViaAprilFools.getPlatform().getLogger().severe("Could not find 1.15 type id " + typeId);
            return ENTITY;
        }
        return type;
    }
}
