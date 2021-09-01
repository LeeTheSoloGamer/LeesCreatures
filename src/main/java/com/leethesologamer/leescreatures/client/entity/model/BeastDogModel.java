package com.leethesologamer.leescreatures.client.entity.model;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entities.BeastDogEntity;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;
@OnlyIn(Dist.CLIENT)
public class BeastDogModel<B extends AnimalEntity> extends AnimatedGeoModel<BeastDogEntity> {
	
	
	private ResourceLocation DEFAULT = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/beast_dog/beast_dog_entity.png");
	
    @Override
    public ResourceLocation getModelLocation(BeastDogEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "geo/beast_dog.geo.json");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BeastDogEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "animations/beast_dog_animation.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BeastDogEntity entity) {
        return DEFAULT;
    }
}