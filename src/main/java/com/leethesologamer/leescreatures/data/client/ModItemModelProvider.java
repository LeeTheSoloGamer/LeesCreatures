package com.leethesologamer.leescreatures.data.client;

import com.leethesologamer.leescreatures.LeesCreatures;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
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
    }
}
