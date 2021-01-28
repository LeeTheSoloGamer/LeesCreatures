package com.leethesologamer.leescreatures.client.entity.render;

import com.leethesologamer.leescreatures.client.entity.model.CrystalWyvernEntityModel;
import com.leethesologamer.leescreatures.entities.CrystalWyvernEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CrystalWyvernEntityRender extends GeoEntityRenderer<CrystalWyvernEntity> {

    public CrystalWyvernEntityRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CrystalWyvernEntityModel<>());
    }

    @Override
    public RenderType getRenderType(CrystalWyvernEntity animatable, float partialTicks, MatrixStack stack,
                                    IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.getEntityTranslucent(getTextureLocation(animatable));
    }

}
