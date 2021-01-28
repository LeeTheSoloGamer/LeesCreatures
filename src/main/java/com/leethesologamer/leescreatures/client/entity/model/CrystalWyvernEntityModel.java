package com.leethesologamer.leescreatures.client.entity.model;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entities.CrystalWyvernEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CrystalWyvernEntityModel<B extends MonsterEntity> extends AnimatedGeoModel<CrystalWyvernEntity> {

    public CrystalWyvernEntityModel() {

    }

    @Override
    public ResourceLocation getModelLocation(CrystalWyvernEntity crystalWyvernEntity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "geo/crystal_wyvern_entity.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CrystalWyvernEntity crystalWyvernEntity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/crystal_wyvern_entity.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CrystalWyvernEntity crystalWyvernEntity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "animations/crystal_wyvern_walking.json");
    }
}
