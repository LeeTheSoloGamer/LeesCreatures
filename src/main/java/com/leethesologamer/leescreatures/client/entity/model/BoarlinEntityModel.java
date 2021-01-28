package com.leethesologamer.leescreatures.client.entity.model;
import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entities.BoarlinEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BoarlinEntityModel<B extends AnimalEntity> extends AnimatedGeoModel<BoarlinEntity> {

    public BoarlinEntityModel() {

    }

    @Override
    public ResourceLocation getModelLocation(BoarlinEntity boarlinEntity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "geo/boarlin.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BoarlinEntity boarlinEntity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/boarlin_entity.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BoarlinEntity boarlinEntity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "animations/boarlin_entity.json");
    }
}
