package com.leethesologamer.leescreatures.client.entity.model;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entities.CrystalWyvernEntity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@OnlyIn(Dist.CLIENT)
public class CrystalWyvernEntityModel extends AnimatedGeoModel<CrystalWyvernEntity> {
    
	
	private ResourceLocation PURPLE = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/crystal_wyvern/crystal_wyvern_entity_purple_female.png");
    private ResourceLocation BLUE = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/crystal_wyvern/crystal_wyvern_entity_bule_female.png");
    private ResourceLocation PINK = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/crystal_wyvern/crystal_wyvern_entity_pink_female.png");
    private ResourceLocation ALBINO = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/crystal_wyvern/crystal_wyvern_entity_albino_female.png");
    private ResourceLocation LIGHT_BLUE = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/crystal_wyvern/crystal_wyvern_entity_light_blue_female.png");
    private ResourceLocation LIGHT_PURPLE = new ResourceLocation(LeesCreatures.MOD_ID, "textures/entity/crystal_wyvern/crystal_wyvern_entity_light_purple_female.png");
	
	
    @Override
    public ResourceLocation getModelLocation(CrystalWyvernEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "geo/crystal_wyvern_entity.geo.json");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CrystalWyvernEntity entity) {
        return new ResourceLocation(LeesCreatures.MOD_ID, "animations/crystal_wyvern_animation.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CrystalWyvernEntity entity) {
        if (entity.getVariant() ==1) {
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
