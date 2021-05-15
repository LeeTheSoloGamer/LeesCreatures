package com.leethesologamer.leescreatures.client.entity.model;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entities.CrestedCrikestreakerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@OnlyIn(Dist.CLIENT)
public class CrestedCrikestreakerModel extends AnimatedGeoModel<CrestedCrikestreakerEntity> {

    @Override
    public ResourceLocation getModelLocation(CrestedCrikestreakerEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "geo/crested_crikestreaker_entity.geo.json");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CrestedCrikestreakerEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "animations/crested_crikestreaker_animation.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CrestedCrikestreakerEntity entity) {
        return null;
    }
}
