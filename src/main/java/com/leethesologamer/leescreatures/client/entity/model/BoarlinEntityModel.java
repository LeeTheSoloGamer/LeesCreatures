package com.leethesologamer.leescreatures.client.entity.model;
import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entities.BoarlinEntity;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;
@OnlyIn(Dist.CLIENT)
public class BoarlinEntityModel<B extends AnimalEntity> extends AnimatedGeoModel<BoarlinEntity> {
	
	
	private ResourceLocation DEAFULT = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/boarlin/boarlin_entity.png");
	
    @Override
    public ResourceLocation getModelLocation(BoarlinEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "geo/boarlin.geo.json");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BoarlinEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "animations/boarlin_entity.json");
    }
    
    @Override
    public ResourceLocation getTextureLocation(BoarlinEntity entity) {
        return DEAFULT;
    }
}
