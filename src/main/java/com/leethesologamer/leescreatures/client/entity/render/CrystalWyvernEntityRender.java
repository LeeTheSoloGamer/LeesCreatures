package com.leethesologamer.leescreatures.client.entity.render;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.client.entity.model.CrystalWyvernEntityModel;
import com.leethesologamer.leescreatures.entities.CrystalWyvernEntity;
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
public class CrystalWyvernEntityRender extends GeoEntityRenderer<CrystalWyvernEntity> {
    public CrystalWyvernEntityRender(EntityRendererManager renderManager) {
        super(renderManager, new CrystalWyvernEntityModel());
    }

    private static final ResourceLocation PURPLE = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/crystal_wyvern/crystal_wyvern_entity_purple_female.png");
    private static final ResourceLocation BLUE = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/crystal_wyvern/crystal_wyvern_entity_bule_female.png");
    private static final ResourceLocation PINK = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/crystal_wyvern/crystal_wyvern_entity_pink_female.png");
    private static final ResourceLocation ALBINO = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/crystal_wyvern/crystal_wyvern_entity_albino_female.png");
    private static final ResourceLocation LIGHT_BLUE = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/crystal_wyvern/crystal_wyvern_entity_light_blue_female.png");
    private static final ResourceLocation LIGHT_PURPLE = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/crystal_wyvern/crystal_wyvern_entity_light_purple_female.png");

    @Override
    public RenderType getRenderType(CrystalWyvernEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityTranslucent(this.getEntityTexture(animatable));
    }

    @Override
        public ResourceLocation getEntityTexture(CrystalWyvernEntity entity) {
        if (entity.getVariant() ==1){
            return BLUE;
        } else if (entity.getVariant() ==2){
            return PINK;
        } else if (entity.getVariant() ==3){
            return ALBINO;
        } else if (entity.getVariant() ==4){
            return LIGHT_BLUE;
        } else if (entity.getVariant() ==5){
            return LIGHT_PURPLE;
        } else {
            return PURPLE;
            }
        }
    }