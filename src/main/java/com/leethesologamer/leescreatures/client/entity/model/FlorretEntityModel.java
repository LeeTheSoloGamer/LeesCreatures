package com.leethesologamer.leescreatures.client.entity.model;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entitiesOLD.FlorretEntity;
import com.leethesologamer.leescreatures.entitiesOLD.SouleuronEntity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@OnlyIn(Dist.CLIENT)
public class FlorretEntityModel extends AnimatedGeoModel<FlorretEntity> {
    @Override
    public ResourceLocation getModelLocation(FlorretEntity florretEntity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "geo/florret_entity.geo.json");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FlorretEntity florretEntity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "animations/florret_animation.json");
    }
    @Override
    public ResourceLocation getTextureLocation(FlorretEntity florretEntity) {
        return null;
    }

}
