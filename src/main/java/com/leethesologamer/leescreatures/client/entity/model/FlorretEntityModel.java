package com.leethesologamer.leescreatures.client.entity.model;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entities.FlorretEntity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@OnlyIn(Dist.CLIENT)
public class FlorretEntityModel extends AnimatedGeoModel<FlorretEntity> {
	
	
	private ResourceLocation DEFAULT = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/florret/florret_entity_default.png");
	
    @Override
    public ResourceLocation getModelLocation(FlorretEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "geo/florret_entity.geo.json");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FlorretEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "animations/florret_animation.json");
    }
    
    @Override
    public ResourceLocation getTextureLocation(FlorretEntity entity) {
        return DEFAULT;
    }

}
