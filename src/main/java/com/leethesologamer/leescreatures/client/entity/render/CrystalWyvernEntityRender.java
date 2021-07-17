package com.leethesologamer.leescreatures.client.entity.render;

import com.leethesologamer.leescreatures.client.entity.model.CrystalWyvernEntityModel;
import com.leethesologamer.leescreatures.entities.CrystalWyvernEntity;
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
public class CrystalWyvernEntityRender extends GeoEntityRenderer<CrystalWyvernEntity> {
    
	public CrystalWyvernEntityRender(EntityRendererManager renderManager) {
        super(renderManager, new CrystalWyvernEntityModel());
    }
	
    @Override
    public RenderType getRenderType(CrystalWyvernEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityTranslucent(this.getEntityTexture(animatable));
    }

	@Override
	public ResourceLocation getEntityTexture(CrystalWyvernEntity entity) {
		return this.getTextureLocation(entity);
	}
	
    @Override
    protected void applyRotations(CrystalWyvernEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityLiving.rotationPitch));
    }
}