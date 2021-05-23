package com.leethesologamer.leescreatures.data.client;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, LeesCreatures.MOD_ID, exFileHelper);

    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.CRYSTAL_BLOCK.get());
        simpleBlock(ModBlocks.DURANTIUM_ORE.get());
        simpleBlock(ModBlocks.DURANTIUM_BLOCK.get());
        simpleBlock(ModBlocks.GREEMANAR_BLOCK.get());
        simpleBlock(ModBlocks.GREEMANAR_ORE.get());
    }
}
