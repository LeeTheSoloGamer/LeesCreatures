package com.leethesologamer.leescreatures.client.entity.model;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entities.CosmicWhaleEntity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@OnlyIn(Dist.CLIENT)
public class CosmicWhaleEntityModel extends AnimatedGeoModel<CosmicWhaleEntity> {
	
	
	private ResourceLocation DEAFAULT = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/cosmic_whale/cosmic_whale_entity_default.png");
	
    @Override
    public ResourceLocation getModelLocation(CosmicWhaleEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "geo/cosmic_whale_entity.geo.json");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CosmicWhaleEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "animations/cosmic_whale_animations.json");
    }
    @Override
    public ResourceLocation getTextureLocation(CosmicWhaleEntity entity) {
        return DEAFAULT;
    }

}
