package com.leethesologamer.leescreatures.client.entity.model;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entitiesOLD.AdderBackEntity;
import com.leethesologamer.leescreatures.entitiesOLD.SouleuronEntity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@OnlyIn(Dist.CLIENT)
public class AdderBackEntityModel extends AnimatedGeoModel<AdderBackEntity> {
    @Override
    public ResourceLocation getModelLocation(AdderBackEntity adderBackEntity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "geo/adder_back_entity.geo.json");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AdderBackEntity adderBackEntity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "animations/adder_back_animation.json");
    }
    @Override
    public ResourceLocation getTextureLocation(AdderBackEntity adderBackEntity) {
        return null;
    }

}
