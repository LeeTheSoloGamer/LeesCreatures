package com.leethesologamer.leescreatures.client.entity.render;

import com.leethesologamer.leescreatures.client.entity.model.CosmicWhaleEntityModel;
import com.leethesologamer.leescreatures.entities.CosmicWhaleEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class CosmicWhaleEntityRender extends GeoEntityRenderer<CosmicWhaleEntity> {
    public CosmicWhaleEntityRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CosmicWhaleEntityModel());
    }

    @Override
    public RenderType getRenderType(CosmicWhaleEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityTranslucent(this.getEntityTexture(animatable));
    }
    @Override
    public ResourceLocation getEntityTexture(CosmicWhaleEntity entity) {
    	return this.getTextureLocation(entity);
    }
}