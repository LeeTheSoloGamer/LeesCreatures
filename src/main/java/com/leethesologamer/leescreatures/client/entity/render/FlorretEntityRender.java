package com.leethesologamer.leescreatures.client.entity.render;

import com.leethesologamer.leescreatures.client.entity.model.FlorretEntityModel;
import com.leethesologamer.leescreatures.entities.FlorretEntity;
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
public class FlorretEntityRender extends GeoEntityRenderer<FlorretEntity> {
    public FlorretEntityRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FlorretEntityModel());
    }

    @Override
    public RenderType getRenderType(FlorretEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityTranslucent(this.getEntityTexture(animatable));
    }
    
    @Override
    public ResourceLocation getEntityTexture(FlorretEntity entity) {
    	return this.getTextureLocation(entity);
    }
}