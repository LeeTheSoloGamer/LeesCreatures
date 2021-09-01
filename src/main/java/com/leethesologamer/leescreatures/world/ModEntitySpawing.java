package com.leethesologamer.leescreatures.world;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;

import com.leethesologamer.leescreatures.init.ModEntityTypes;
import com.leethesologamer.leescreatures.entities.CrystalWyvernEntity;
import com.leethesologamer.leescreatures.entities.AdderBackEntity;
import com.leethesologamer.leescreatures.entities.BeastDogEntity;
import com.leethesologamer.leescreatures.entities.CrestedCrikestreakerEntity;
import com.leethesologamer.leescreatures.entities.FlorretEntity;
import com.leethesologamer.leescreatures.entities.JungleSerpentEntity;
import com.leethesologamer.leescreatures.entities.BoarlinEntity;
import com.leethesologamer.leescreatures.entities.SouleuronEntity;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEntitySpawing {
    @SubscribeEvent
    public static void onBiomesLoad(BiomeLoadingEvent event) {
    	
        if (event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.EXTREME_HILLS || event.getCategory() == Biome.Category.SAVANNA || event.getCategory() == Biome.Category.PLAINS || event.getCategory() == Biome.Category.MESA)
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.BOARLIN_ENTITY.get(), 8, 1, 3));

        if (event.getCategory() == Biome.Category.NETHER)
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.SOULEURON_ENTITY.get(), 10, 0, 1));

        if (event.getCategory() == Biome.Category.EXTREME_HILLS)
            event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(ModEntityTypes.CRYSTAL_WYVERN_ENTITY.get(), 2, 0, 1));

        if (event.getCategory() == Biome.Category.JUNGLE)
            event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(ModEntityTypes.JUNGLE_SERPENT_ENTITY.get(), 2, 0, 1));

        if (event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.PLAINS)
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.BEAST_DOG_ENTITY.get(), 5, 1, 2));

        if (event.getCategory() == Biome.Category.MESA)
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.CRESTED_CRIKESTREAKER_ENTITY.get(), 2, 3, 3));

        if (event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.JUNGLE || event.getCategory() == Biome.Category.PLAINS)
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.FLORRET_ENTITY.get(), 20, 0, 1));
 
    }

    public static void entitySpawnPlacementRegistry ()
    {
    	
        EntitySpawnPlacementRegistry.register(ModEntityTypes.JUNGLE_SERPENT_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, JungleSerpentEntity::canJungleSerpentEntitySpawn);

        EntitySpawnPlacementRegistry.register(ModEntityTypes.BEAST_DOG_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BeastDogEntity::canBeastDogEntitySpawn);

        EntitySpawnPlacementRegistry.register(ModEntityTypes.BOARLIN_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BoarlinEntity::canBoarlinEntitySpawn);

        EntitySpawnPlacementRegistry.register(ModEntityTypes.FLORRET_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlorretEntity::canFlorretSpawn);

        EntitySpawnPlacementRegistry.register(ModEntityTypes.CRESTED_CRIKESTREAKER_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrestedCrikestreakerEntity::canCrikestreakerSpawn);

        EntitySpawnPlacementRegistry.register(ModEntityTypes.CRYSTAL_WYVERN_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrystalWyvernEntity::canCrystalWyvernSpawn);

        EntitySpawnPlacementRegistry.register(ModEntityTypes.ADDER_BACK_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AdderBackEntity::canAdderBackEntitySpawn);

        EntitySpawnPlacementRegistry.register(ModEntityTypes.SOULEURON_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SouleuronEntity::canSouleuronSpawn);
    	
    }
}
