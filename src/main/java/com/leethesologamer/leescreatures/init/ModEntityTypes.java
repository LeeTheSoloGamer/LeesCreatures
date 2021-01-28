package com.leethesologamer.leescreatures.init;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entities.BoarlinEntity;
import com.leethesologamer.leescreatures.entities.CrystalWyvernEntity;
import com.leethesologamer.leescreatures.entities.SouleuronEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, LeesCreatures.MOD_ID);

    public static final RegistryObject<EntityType<BoarlinEntity>> BOARLIN_ENTITY = ENTITY_TYPES
            .register("boarlin_entity",() -> EntityType.Builder.<BoarlinEntity>create(BoarlinEntity::new, EntityClassification.CREATURE)
                                                                .size(0.9f,1.9f)
                                                                .build(new ResourceLocation(LeesCreatures.MOD_ID, "boarlin_entity").toString()));

    public static final RegistryObject<EntityType<SouleuronEntity>> SOULEURON_ENTITY = ENTITY_TYPES
            .register("souleuron_entity",() -> EntityType.Builder.<SouleuronEntity>create(SouleuronEntity::new, EntityClassification.CREATURE)
                    .size(0.9f,1.9f)
                    .build(new ResourceLocation(LeesCreatures.MOD_ID, "souleuron_entity").toString()));

    public static final RegistryObject<EntityType<CrystalWyvernEntity>> CRYSTAL_WYVERN_ENTITY = ENTITY_TYPES
            .register("crystal_wyvern_entity",() -> EntityType.Builder.<CrystalWyvernEntity>create(CrystalWyvernEntity::new, EntityClassification.MONSTER)
                    .size(0.9f,1.9f)
                    .build(new ResourceLocation(LeesCreatures.MOD_ID, "crystal_wyvern_entity").toString()));
}
