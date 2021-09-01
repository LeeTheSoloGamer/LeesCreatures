package com.leethesologamer.leescreatures.client.entity.render;

import com.leethesologamer.leescreatures.client.entity.model.BeastDogModel;
import com.leethesologamer.leescreatures.entities.BeastDogEntity;
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
public class BeastDogRender extends GeoEntityRenderer<BeastDogEntity> {
	
	public BeastDogRender(EntityRendererManager renderManager) {
        super(renderManager, new BeastDogModel());
    }
	
    @Override
    public RenderType getRenderType(BeastDogEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityTranslucent(this.getEntityTexture(animatable));
    }

    @Override
    public ResourceLocation getEntityTexture(BeastDogEntity entity) {
    	return this.getTextureLocation(entity);
    }
}
