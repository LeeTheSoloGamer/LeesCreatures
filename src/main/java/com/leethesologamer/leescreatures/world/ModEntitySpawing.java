package com.leethesologamer.leescreatures.world;

import com.leethesologamer.leescreatures.init.ModEntityTypes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEntitySpawing {
    @SubscribeEvent
    public static void onBiomesLoad(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.EXTREME_HILLS || event.getCategory() == Biome.Category.SAVANNA || event.getCategory() == Biome.Category.PLAINS )
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.BOARLIN_ENTITY.get(), 9, 2, 3));

        if (event.getCategory() == Biome.Category.NETHER)
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.SOULEURON_ENTITY.get(), 10, 1, 1));

        if (event.getCategory() == Biome.Category.EXTREME_HILLS)
            event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(ModEntityTypes.CRYSTAL_WYVERN_ENTITY.get(), 3, 1, 1));

        if (event.getCategory() == Biome.Category.JUNGLE)
            event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(ModEntityTypes.JUNGLE_SERPENT_ENTITY.get(), 4, 1, 1));

        if (event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.PLAINS)
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.BEAST_DOG_ENTITY.get(), 8, 2, 2));

        if (event.getCategory() == Biome.Category.MESA)
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.CRESTED_CRIKESTREAKER_ENTITY.get(), 5, 3, 3));
    }
}
