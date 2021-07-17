package com.leethesologamer.leescreatures.client.entity.render;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.client.entity.model.AdderBackEntityModel;
import com.leethesologamer.leescreatures.client.entity.model.SouleuronEntityModel;
import com.leethesologamer.leescreatures.entitiesOLD.AdderBackEntity;
import com.leethesologamer.leescreatures.entitiesOLD.SouleuronEntity;
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
public class AdderBackEntityRender extends GeoEntityRenderer<AdderBackEntity> {
    public AdderBackEntityRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new AdderBackEntityModel());
    }

    @Override
    public RenderType getRenderType(AdderBackEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityTranslucent(this.getEntityTexture(animatable));
    }
    @Override
    public ResourceLocation getEntityTexture(AdderBackEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/adder_back/adder_back_entity.png");
    }
}