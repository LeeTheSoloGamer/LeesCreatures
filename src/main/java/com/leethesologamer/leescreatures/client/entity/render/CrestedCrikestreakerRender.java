package com.leethesologamer.leescreatures.client.entity.render;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.client.entity.model.CrestedCrikestreakerModel;
import com.leethesologamer.leescreatures.entitiesOLD.CrestedCrikestreakerEntity;
import com.leethesologamer.leescreatures.entitiesOLD.CrystalWyvernEntity;
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
public class CrestedCrikestreakerRender extends GeoEntityRenderer<CrestedCrikestreakerEntity> {
    public CrestedCrikestreakerRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CrestedCrikestreakerModel());
    }

    private static final ResourceLocation ORANGE = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/crested_crikestreaker/crested_crikestreaker_entity_orange.png");
    private static final ResourceLocation YELLOW = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/crested_crikestreaker/crested_crikestreaker_entity_yellow.png");
    private static final ResourceLocation RED = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/crested_crikestreaker/crested_crikestreaker_entity_red.png");

    @Override
    public RenderType getRenderType(CrestedCrikestreakerEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityTranslucent(this.getEntityTexture(animatable));
    }

    @Override
    public ResourceLocation getEntityTexture(CrestedCrikestreakerEntity entity) {
        if (entity.getVariant() ==1){
            return YELLOW;
        } else if (entity.getVariant() ==2){
            return RED;
        } else {
            return ORANGE;
        }
    }
}
