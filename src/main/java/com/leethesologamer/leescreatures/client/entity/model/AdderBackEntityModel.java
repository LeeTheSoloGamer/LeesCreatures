package com.leethesologamer.leescreatures.client.entity.model;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entities.AdderBackEntity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@OnlyIn(Dist.CLIENT)
public class AdderBackEntityModel extends AnimatedGeoModel<AdderBackEntity> {
	
	
	private ResourceLocation DEFAULT = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/adder_back/adder_back_entity.png");
	
    @Override
    public ResourceLocation getModelLocation(AdderBackEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "geo/adder_back_entity.geo.json");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AdderBackEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "animations/adder_back_animation.json");
    }
    
    @Override
    public ResourceLocation getTextureLocation(AdderBackEntity entity) {
        return DEFAULT;
    }
}
