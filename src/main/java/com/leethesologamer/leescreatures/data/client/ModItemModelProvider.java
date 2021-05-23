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
        withExistingParent("durantium_block", modLoc("block/durantium_block"));
        withExistingParent("crystal_block", modLoc("block/crystal_block"));
        withExistingParent("greemanar_block", modLoc("block/greemanar_block"));
        withExistingParent("durantium_ore", modLoc("block/durantium_ore"));
        withExistingParent("greemanar_ore", modLoc("block/greemanar_ore"));

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




    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
    private ItemModelBuilder tool(ModelFile itemhandHeld, String name) {
        return getBuilder(name).parent(itemhandHeld).texture("layer0",  "item/" + name);
    }
}
