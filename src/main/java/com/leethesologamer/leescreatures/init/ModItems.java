package com.leethesologamer.leescreatures.init;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.objects.items.FoodInit;
import com.leethesologamer.leescreatures.objects.items.ModSpawnEggItem;
import com.leethesologamer.leescreatures.util.enums.ModArmorMaterial;
import com.leethesologamer.leescreatures.util.enums.ModItemTier;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LeesCreatures.MOD_ID);

    // items
    public static final RegistryObject<Item> JUNGLE_SERPENT_FANG = ITEMS.register("jungle_serpent_fang",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<Item> DURANTIUM_INGOT = ITEMS.register("durantium_ingot",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<Item> COIN = ITEMS.register("coin",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<Item> DURANTIUM_STEEL = ITEMS.register("durantium_steel",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<Item> DURANTIUM_STICK = ITEMS.register("durantium_stick",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<Item> DURANTIUM_NUGGET = ITEMS.register("durantium_nugget",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<Item> UNDEAD_ESSENCE = ITEMS.register("undead_essence",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<Item> BOTTLED_UNDEAD_ESSENCE = ITEMS.register("bottled_undead_essence",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<Item> ROTTEN_BONE = ITEMS.register("rotten_bone",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<Item> GREEMANAR_INGOT = ITEMS.register("greemanar_ingot",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<Item> POISONOUS_GLAND = ITEMS.register("poisonous_gland",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<Item> CRESTED_CRIKESTREAKER_EGG = ITEMS.register("crested_crikestreaker_egg",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<Item> CRESTED_CRIKESTREAKER_FEATHER = ITEMS.register("crested_crikestreaker_feather",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<Item> JEWELED_EGG = ITEMS.register("jeweled_egg",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<Item> JUNGLE_SERPENT_SCALES = ITEMS.register("jungle_serpent_scales",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<Item> BEASTLY_HIDE = ITEMS.register("beastly_hide",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<Item> BOTTLE_OF_POISON = ITEMS.register("bottle_of_poison",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    //food
    public static final RegistryObject<Item> BOARLIN_MEAT = ITEMS.register("boarlin_meat",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP).food(FoodInit.BOARLIN_MEEAT)));

    public static final RegistryObject<Item> RAW_BOARLIN = ITEMS.register("raw_boarlin",
            () -> new Item(new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP).food(FoodInit.RAW_BOARLIN)));

    // blocks items
    public static final RegistryObject<BlockItem> DURANTIUM_BLOCK_ITEM = ITEMS.register("durantium_block",
            () -> new BlockItem(ModBlocks.DURANTIUM_BLOCK.get(),
                    new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<BlockItem> DURANTIUM_ORE_ITEM = ITEMS.register("durantium_ore",
            () -> new BlockItem(ModBlocks.DURANTIUM_ORE.get(),
                    new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<BlockItem> GREEMANAR_ORE_ITEM = ITEMS.register("greemanar_ore",
            () -> new BlockItem(ModBlocks.GREEMANAR_ORE.get(),
                    new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<BlockItem> GREEMANAR_BLOCK_ITEM = ITEMS.register("greemanar_block",
            () -> new BlockItem(ModBlocks.GREEMANAR_BLOCK.get(),
                    new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));


    // tools

    public static final RegistryObject<SwordItem> DURANTIUM_SWORD = ITEMS.register("durantium_sword", () ->
            new SwordItem(ModItemTier.DURANTIUM, 6, -2.4f, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<SwordItem> GREEMANAR_LONG_SWORD = ITEMS.register("greemanar_long_sword", () ->
            new SwordItem(ModItemTier.GREEMANAR, 4, -2.4f, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<PickaxeItem> DURANTIUM_PICKAXE = ITEMS.register("durantium_pickaxe", () ->
            new PickaxeItem(ModItemTier.DURANTIUM, 4, -2.8f, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<ShovelItem> DURANTIUM_SHOVEL = ITEMS.register("durantium_shovel", () ->
            new ShovelItem(ModItemTier.DURANTIUM, 1.6f, -3.0f, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<AxeItem> DURANTIUM_AXE = ITEMS.register("durantium_axe", () ->
            new AxeItem(ModItemTier.DURANTIUM, 5.1f, -3.1f, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    public static final RegistryObject<HoeItem> DURANTIUM_HOE = ITEMS.register("durantium_hoe", () ->
            new HoeItem(ModItemTier.DURANTIUM, -5, -0.0f, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));


    //Spawn Egg
    public static final RegistryObject<ModSpawnEggItem> BOARLIN_SPAWN_EGG = ITEMS.register("boarlin_spawn_egg",
            () -> new ModSpawnEggItem(ModEntityTypes.BOARLIN_ENTITY, 0xC4AA79, 0x7A5F22,
                    new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP).maxStackSize(16)));

    public static final RegistryObject<ModSpawnEggItem> SOULEURON_SPAWN_EGG = ITEMS.register("souleuron_spawn_egg",
            () -> new ModSpawnEggItem(ModEntityTypes.SOULEURON_ENTITY, 0x896A26, 0xCE0600,
                    new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP).maxStackSize(16)));

    public static final RegistryObject<ModSpawnEggItem> CRYSTAL_WYVERN_SPAWN_EGG = ITEMS.register("crystal_wyvern_spawn_egg",
            () -> new ModSpawnEggItem(ModEntityTypes.CRYSTAL_WYVERN_ENTITY, 0xDBD9D9, 0xCA34EF,
                    new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP).maxStackSize(16)));


    public static final RegistryObject<ModSpawnEggItem> JUNGLE_SERPENT_SPAWN_EGG = ITEMS.register("jungle_serpent_spawn_egg",
            () -> new ModSpawnEggItem(ModEntityTypes.JUNGLE_SERPENT_ENTITY, 0x97BE43, 0x2E293C,
                    new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP).maxStackSize(16)));

    public static final RegistryObject<ModSpawnEggItem> BEAST_DOG_SPAWN_EGG = ITEMS.register("beast_dog_spawn_egg",
            () -> new ModSpawnEggItem(ModEntityTypes.BEAST_DOG_ENTITY, 0x7A7A7A, 0x1E1E1E,
                    new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP).maxStackSize(16)));

    public static final RegistryObject<ModSpawnEggItem> CREASTED_CRIKESTREAKER_SPAWN_EGG = ITEMS.register("creasted_crikestreaker_spawn_egg",
            () -> new ModSpawnEggItem(ModEntityTypes.CRESTED_CRIKESTREAKER_ENTITY, 0x7A7A7A, 0x1E1E1E,
                    new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP).maxStackSize(16)));


    //Armor
    private static final RegistryObject<ArmorItem> GREEMANAR_HELMET = ITEMS.register("greemanar_helmet", () ->
            new ArmorItem(ModArmorMaterial.GREEMANAR, EquipmentSlotType.HEAD, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    private static final RegistryObject<ArmorItem> GREEMANAR_CHESTPLATE = ITEMS.register("greemanar_chestplate", () ->
            new ArmorItem(ModArmorMaterial.GREEMANAR, EquipmentSlotType.CHEST, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    private static final RegistryObject<ArmorItem> GREEMANAR_LEGGINGS = ITEMS.register("greemanar_leggings", () ->
            new ArmorItem(ModArmorMaterial.GREEMANAR, EquipmentSlotType.LEGS, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    private static final RegistryObject<ArmorItem> GREEMANAR_BOOTS = ITEMS.register("greemanar_boots", () ->
            new ArmorItem(ModArmorMaterial.GREEMANAR, EquipmentSlotType.FEET, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    private static final RegistryObject<ArmorItem> DURANTIUM_HELMET = ITEMS.register("durantium_helmet", () ->
            new ArmorItem(ModArmorMaterial.DURANTIUM, EquipmentSlotType.HEAD, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    private static final RegistryObject<ArmorItem> DURANTIUM_CHESTPLATE = ITEMS.register("durantium_chestplate", () ->
            new ArmorItem(ModArmorMaterial.DURANTIUM, EquipmentSlotType.CHEST, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    private static final RegistryObject<ArmorItem> DURANTIUM_LEGGINGS = ITEMS.register("durantium_leggings", () ->
            new ArmorItem(ModArmorMaterial.DURANTIUM, EquipmentSlotType.LEGS, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    private static final RegistryObject<ArmorItem> DURANTIUM_BOOTS = ITEMS.register("durantium_boots", () ->
            new ArmorItem(ModArmorMaterial.DURANTIUM, EquipmentSlotType.FEET, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    private static final RegistryObject<ArmorItem> BEASTLY_HELMET = ITEMS.register("beastly_helmet", () ->
            new ArmorItem(ModArmorMaterial.BEASTLY_ARMOR, EquipmentSlotType.HEAD, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    private static final RegistryObject<ArmorItem> BEASTLY_CHESTPLATE = ITEMS.register("beastly_chestplate", () ->
            new ArmorItem(ModArmorMaterial.BEASTLY_ARMOR, EquipmentSlotType.CHEST, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    private static final RegistryObject<ArmorItem> BEASTLY_LEGGINGS = ITEMS.register("beastly_leggings", () ->
            new ArmorItem(ModArmorMaterial.BEASTLY_ARMOR, EquipmentSlotType.LEGS, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    private static final RegistryObject<ArmorItem> BEASTLY_BOOTS = ITEMS.register("beastly_boots", () ->
            new ArmorItem(ModArmorMaterial.BEASTLY_ARMOR, EquipmentSlotType.FEET, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    private static final RegistryObject<Item> JUNGLE_SERPENT_HELMET = ITEMS.register("jungle_serpent_helmet", () ->
            new ArmorItem(ModArmorMaterial.JUNGLE_SERPENT_ARMOR, EquipmentSlotType.HEAD, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    private static final RegistryObject<ArmorItem> JUNGLE_SERPENT_CHESTPLATE = ITEMS.register("jungle_serpent_chestplate", () ->
            new ArmorItem(ModArmorMaterial.JUNGLE_SERPENT_ARMOR, EquipmentSlotType.CHEST, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    private static final RegistryObject<ArmorItem> JUNGLE_SERPENT_LEGGINGS = ITEMS.register("jungle_serpent_leggings", () ->
            new ArmorItem(ModArmorMaterial.JUNGLE_SERPENT_ARMOR, EquipmentSlotType.LEGS, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));

    private static final RegistryObject<ArmorItem> JUNGLE_SERPENT_BOOTS = ITEMS.register("jungle_serpent_boots", () ->
            new ArmorItem(ModArmorMaterial.JUNGLE_SERPENT_ARMOR, EquipmentSlotType.FEET, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP)));


}
