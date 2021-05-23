package com.leethesologamer.leescreatures.data.client;

import com.leethesologamer.leescreatures.LeesCreatures;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider  extends ItemModelProvider {


    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, LeesCreatures.MOD_ID, existingFileHelper);
    }
    @Override
    protected void registerModels() {
        withExistingParent("durantium_block", modLoc("blocks/durantium_block"));
        withExistingParent("crystal_block", modLoc("blocks/crystal_block"));
        withExistingParent("greemanar_block", modLoc("blocks/greemanar_block"));
        withExistingParent("durantium_ore", modLoc("blocks/durantium_ore"));
        withExistingParent("greemanar_ore", modLoc("blocks/greemanar_ore"));

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        ModelFile itemhandHeld = getExistingFile(mcLoc("item/handheld"));

        //items
        builder(itemGenerated, "greemanar_helmet");
        builder(itemGenerated, "greemanar_chestplate");
        builder(itemGenerated, "greemanar_leggings");
        builder(itemGenerated, "greemanar_boots");
        builder(itemGenerated, "durantium_helmet");
        builder(itemGenerated, "durantium_chestplate");
        builder(itemGenerated, "durantium_leggings");
        builder(itemGenerated, "durantium_boots");
        builder(itemGenerated, "beastly_helmet");
        builder(itemGenerated, "beastly_chestplate");
        builder(itemGenerated, "beastly_leggings");
        builder(itemGenerated, "beastly_boots");
        builder(itemGenerated, "jungle_serpent_helmet");
        builder(itemGenerated, "jungle_serpent_chestplate");
        builder(itemGenerated, "jungle_serpent_leggings");
        builder(itemGenerated, "jungle_serpent_boots");
        builder(itemGenerated, "blue_crystal_wyvern_helmet");
        builder(itemGenerated, "blue_crystal_wyvern_chestplate");
        builder(itemGenerated, "blue_crystal_wyvern_leggings");
        builder(itemGenerated, "blue_crystal_wyvern_boots");
        builder(itemGenerated, "light_blue_crystal_wyvern_helmet");
        builder(itemGenerated, "light_blue_crystal_wyvern_chestplate");
        builder(itemGenerated, "light_blue_crystal_wyvern_leggings");
        builder(itemGenerated, "light_blue_crystal_wyvern_boots");
        builder(itemGenerated, "purple_crystal_wyvern_helmet");
        builder(itemGenerated, "purple_crystal_wyvern_chestplate");
        builder(itemGenerated, "purple_crystal_wyvern_leggings");
        builder(itemGenerated, "purple_crystal_wyvern_boots");
        builder(itemGenerated, "pink_crystal_wyvern_helmet");
        builder(itemGenerated, "pink_crystal_wyvern_chestplate");
        builder(itemGenerated, "pink_crystal_wyvern_leggings");
        builder(itemGenerated, "pink_crystal_wyvern_boots");
        builder(itemGenerated, "voilet_crystal_wyvern_helmet");
        builder(itemGenerated, "voilet_crystal_wyvern_chestplate");
        builder(itemGenerated, "voilet_crystal_wyvern_leggings");
        builder(itemGenerated, "voilet_crystal_wyvern_boots");
        builder(itemGenerated, "white_crystal_wyvern_helmet");
        builder(itemGenerated, "white_crystal_wyvern_chestplate");
        builder(itemGenerated, "white_crystal_wyvern_leggings");
        builder(itemGenerated, "white_crystal_wyvern_boots");
        builder(itemGenerated, "boarlin_meat");
        builder(itemGenerated, "raw_boarlin");
        builder(itemGenerated, "jungle_serpent_fang");
        builder(itemGenerated, "durantium_ingot");
        builder(itemGenerated, "coin");
        builder(itemGenerated, "durantium_steel");
        builder(itemGenerated, "durantium_rod");
        builder(itemGenerated, "durantium_nugget");
        builder(itemGenerated, "undead_essence");
        builder(itemGenerated, "bottled_undead_essence");
        builder(itemGenerated, "rotten_bone");
        builder(itemGenerated, "greemanar_ingot");
        builder(itemGenerated, "poisonous_gland");
        builder(itemGenerated, "crested_crikestreaker_egg");
        builder(itemGenerated, "crested_crikestreaker_feather");
        builder(itemGenerated, "jeweled_egg");
        builder(itemGenerated, "jungle_serpent_scales");
        builder(itemGenerated, "beastly_hide");
        builder(itemGenerated, "bottle_of_poison");
        builder(itemGenerated, "crystal");
        builder(itemGenerated, "blue_crystal");
        builder(itemGenerated, "pink_crystal");
        builder(itemGenerated, "purple_crystal");
        builder(itemGenerated, "white_crystal");
        builder(itemGenerated, "voilet_crystal");
        builder(itemGenerated, "blue_crystal_wyvern_scales");
        builder(itemGenerated, "light_blue_crystal_wyvern_scales");
        builder(itemGenerated, "purple_crystal_wyvern_scales");
        builder(itemGenerated, "pink_crystal_wyvern_scales");
        builder(itemGenerated, "voilet_crystal_wyvern_scales");
        builder(itemGenerated, "white_crystal_wyvern_scales");
        builder(itemGenerated, "boarlin_spawn_egg");
        builder(itemGenerated, "souleuron_spawn_egg");
        builder(itemGenerated, "crystal_wyvern_spawn_egg");
        builder(itemGenerated, "jungle_serpent_spawn_egg");
        builder(itemGenerated, "beast_dog_spawn_egg");
        builder(itemGenerated, "creasted_crikestreaker_spawn_egg");
        builder(itemGenerated, "florret_spawn_egg");





        //tools
        tool(itemhandHeld, "durantium_sword");
        tool(itemhandHeld, "serpent_fang_dagger");
        tool(itemhandHeld, "poison_fang_dagger");
        tool(itemhandHeld, "greemanar_long_sword");
        tool(itemhandHeld, "durantium_pickaxe");
        tool(itemhandHeld, "durantium_shovel");
        tool(itemhandHeld, "durantium_axe");
        tool(itemhandHeld, "durantium_hoe");

    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "items/" + name);
    }
    private ItemModelBuilder tool(ModelFile itemhandHeld, String name) {
        return getBuilder(name).parent(itemhandHeld).texture("layer0",  "items/" + name);
    }
}
