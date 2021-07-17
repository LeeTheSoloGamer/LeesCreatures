package com.leethesologamer.leescreatures.client.entity.render;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.client.entity.model.JungleSerpentEntityModel;
import com.leethesologamer.leescreatures.entitiesOLD.BeastDogEntity;
import com.leethesologamer.leescreatures.entitiesOLD.JungleSerpentEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
@OnlyIn(Dist.CLIENT)
public class JungleSerpentEntityRender extends GeoEntityRenderer<JungleSerpentEntity> {
    public JungleSerpentEntityRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new JungleSerpentEntityModel());
    }

    @Override
    public RenderType getRenderType(JungleSerpentEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityTranslucent(this.getEntityTexture(animatable));
    }
    @Override
    public ResourceLocation getEntityTexture(JungleSerpentEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/jungle_serpent/jungle_serpent_entity.png");
    }
}