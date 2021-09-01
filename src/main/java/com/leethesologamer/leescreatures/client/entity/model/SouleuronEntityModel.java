package com.leethesologamer.leescreatures.client.entity.model;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entities.SouleuronEntity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;
@OnlyIn(Dist.CLIENT)
public class SouleuronEntityModel extends AnimatedGeoModel<SouleuronEntity> {
	
	private ResourceLocation DEFAULT = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/souleuron/souleuron_entity.png");
	
    @Override
    public ResourceLocation getModelLocation(SouleuronEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "geo/souleuron_entity.geo.json");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SouleuronEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "animations/souleuron_walking.json");
    }
    
    @Override
    public ResourceLocation getTextureLocation(SouleuronEntity entity) {
        return DEFAULT;
    }
}
