package com.leethesologamer.leescreatures.init;

import com.leethesologamer.leescreatures.LeesCreatures;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LeesCreatures.MOD_ID);

    // blocks
    public static final RegistryObject<Block> DURANTIUM_BLOCK = BLOCKS
            .register("durantium_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON)
                    .hardnessAndResistance(5.0f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(3)
                    .sound(SoundType.METAL).setRequiresTool()));

    public static final RegistryObject<Block> GREEMANAR_BLOCK = BLOCKS
            .register("greemanar_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON)
                    .hardnessAndResistance(3.0f, 4.0f).harvestTool(ToolType.PICKAXE).harvestLevel(3)
                    .sound(SoundType.METAL).setRequiresTool()));

    //ore
    public static final RegistryObject<Block> DURANTIUM_ORE = BLOCKS.register("durantium_ore",
            () -> new Block(AbstractBlock.Properties.from(Blocks.OBSIDIAN)));

    public static final RegistryObject<Block> GREEMANAR_ORE = BLOCKS.register("greemanar_ore",
            () -> new Block(AbstractBlock.Properties.from(Blocks.IRON_ORE)));
}
