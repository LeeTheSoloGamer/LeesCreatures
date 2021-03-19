package com.leethesologamer.leescreatures.client.entity.model;
import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entities.JungleSerpentEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;
@OnlyIn(Dist.CLIENT)
public class JungleSerpentEntityModel extends AnimatedGeoModel<JungleSerpentEntity> {
    @Override
    public ResourceLocation getModelLocation(JungleSerpentEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "geo/jungle_serpent_entity.geo.json");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(JungleSerpentEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "animations/jungle_serpent_animation.json");
    }
    @Override
    public ResourceLocation getTextureLocation(JungleSerpentEntity entity) {
        return null;
    }
}
