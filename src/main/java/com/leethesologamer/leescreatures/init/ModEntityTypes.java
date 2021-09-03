package com.leethesologamer.leescreatures.init;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entities.AdderBackEntity;
import com.leethesologamer.leescreatures.entities.BeastDogEntity;
import com.leethesologamer.leescreatures.entities.BoarlinEntity;
import com.leethesologamer.leescreatures.entities.CosmicWhaleEntity;
import com.leethesologamer.leescreatures.entities.CrestedCrikestreakerEntity;
import com.leethesologamer.leescreatures.entities.CrystalWyvernEntity;
import com.leethesologamer.leescreatures.entities.FlorretEntity;
import com.leethesologamer.leescreatures.entities.JungleSerpentEntity;
import com.leethesologamer.leescreatures.entities.SouleuronEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, LeesCreatures.MOD_ID);

    public static final RegistryObject<EntityType<CrystalWyvernEntity>> CRYSTAL_WYVERN_ENTITY = ENTITY_TYPES
            .register("crystal_wyvern_entity",() -> EntityType.Builder.<CrystalWyvernEntity>create(CrystalWyvernEntity::new, EntityClassification.CREATURE)
                    .size(0.9f,1.6f)
                    .build(new ResourceLocation(LeesCreatures.MOD_ID, "crystal_wyvern_entity").toString()));

    public static final RegistryObject<EntityType<BoarlinEntity>> BOARLIN_ENTITY = ENTITY_TYPES
            .register("boarlin_entity",() -> EntityType.Builder.<BoarlinEntity>create(BoarlinEntity::new, EntityClassification.CREATURE)
                    .size(0.7f,1.56f)
                    .build(new ResourceLocation(LeesCreatures.MOD_ID, "boarlin_entity").toString()));

    public static final RegistryObject<EntityType<SouleuronEntity>> SOULEURON_ENTITY = ENTITY_TYPES
            .register("souleuron_entity",() -> EntityType.Builder.<SouleuronEntity>create(SouleuronEntity::new, EntityClassification.CREATURE)
                    .size(0.9f,1.9f)
                    .build(new ResourceLocation(LeesCreatures.MOD_ID, "souleuron_entity").toString()));

    public static final RegistryObject<EntityType<JungleSerpentEntity>> JUNGLE_SERPENT_ENTITY = ENTITY_TYPES
            .register("jungle_serpent_entity",() -> EntityType.Builder.<JungleSerpentEntity>create(JungleSerpentEntity::new, EntityClassification.CREATURE)
                    .size(1.23f,1.10f)
                    .build(new ResourceLocation(LeesCreatures.MOD_ID, "jungle_serpent_entity").toString()));

    public static final RegistryObject<EntityType<BeastDogEntity>> BEAST_DOG_ENTITY = ENTITY_TYPES
            .register("beast_dog_entity",() -> EntityType.Builder.<BeastDogEntity>create(BeastDogEntity::new, EntityClassification.CREATURE)
                    .size(0.9f,1.10f)
                    .build(new ResourceLocation(LeesCreatures.MOD_ID, "beast_dog_entity").toString()));

    public static final RegistryObject<EntityType<CrestedCrikestreakerEntity>> CRESTED_CRIKESTREAKER_ENTITY = ENTITY_TYPES
            .register("crested_crikestreaker_entity",() -> EntityType.Builder.<CrestedCrikestreakerEntity>create(CrestedCrikestreakerEntity::new, EntityClassification.CREATURE)
                    .size(0.9f,1.11f)
                    .build(new ResourceLocation(LeesCreatures.MOD_ID, "crested_crikestreaker_entity").toString()));

    public static final RegistryObject<EntityType<AdderBackEntity>> ADDER_BACK_ENTITY = ENTITY_TYPES
            .register("adder_back_entity",() -> EntityType.Builder.<AdderBackEntity>create(AdderBackEntity::new, EntityClassification.CREATURE)
                    .size(0.9f,1.10f)
                    .build(new ResourceLocation(LeesCreatures.MOD_ID, "adder_back_entity").toString()));

    public static final RegistryObject<EntityType<FlorretEntity>> FLORRET_ENTITY = ENTITY_TYPES
            .register("florret_entity",() -> EntityType.Builder.<FlorretEntity>create(FlorretEntity::new, EntityClassification.CREATURE)
                    .size(0.2f,1.3f)
                    .build(new ResourceLocation(LeesCreatures.MOD_ID, "florret_entity").toString()));

    public static final RegistryObject<EntityType<CosmicWhaleEntity>> COSMIC_WHALE_ENTITY = ENTITY_TYPES
            .register("cosmic_whale_entity",() -> EntityType.Builder.<CosmicWhaleEntity>create(CosmicWhaleEntity::new, EntityClassification.CREATURE)
                    .size(0.2f,1.3f)
                    .build(new ResourceLocation(LeesCreatures.MOD_ID, "cosmic_whale_entity").toString()));

}
