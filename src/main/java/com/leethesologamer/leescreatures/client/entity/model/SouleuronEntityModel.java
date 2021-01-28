package com.leethesologamer.leescreatures.client.entity.model;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entities.SouleuronEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SouleuronEntityModel<B extends AnimalEntity> extends AnimatedGeoModel<SouleuronEntity> {

    public SouleuronEntityModel() {

    }

    @Override
    public ResourceLocation getModelLocation(SouleuronEntity souleuronEntity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "geo/souleuron_entity.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SouleuronEntity souleuronEntity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/souleuron_entity.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SouleuronEntity souleuronEntity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "animations/souleuron_walking.json");
    }
}
