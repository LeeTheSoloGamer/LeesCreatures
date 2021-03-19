package com.leethesologamer.leescreatures.client.entity.model;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entities.CrystalWyvernEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@OnlyIn(Dist.CLIENT)
public class CrystalWyvernEntityModel extends AnimatedGeoModel<CrystalWyvernEntity> {
    @Override
    public ResourceLocation getModelLocation(CrystalWyvernEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "geo/crystal_wyvern_entity.geo.json");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CrystalWyvernEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "animations/crystal_wyvern_animation.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CrystalWyvernEntity entity) {
        return null;
    }
}
