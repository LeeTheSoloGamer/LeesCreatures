package com.leethesologamer.leescreatures.client.entity.model;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entitiesOLD.CosmicWhaleEntity;
import com.leethesologamer.leescreatures.entitiesOLD.SouleuronEntity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@OnlyIn(Dist.CLIENT)
public class CosmicWhaleEntityModel extends AnimatedGeoModel<CosmicWhaleEntity> {
    @Override
    public ResourceLocation getModelLocation(CosmicWhaleEntity cosmicWhaleEntity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "geo/cosmic_whale_entity.geo.json");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CosmicWhaleEntity cosmicWhaleEntity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "animations/cosmic_whale_animations.json");
    }
    @Override
    public ResourceLocation getTextureLocation(CosmicWhaleEntity cosmicWhaleEntity) {
        return null;
    }

}
