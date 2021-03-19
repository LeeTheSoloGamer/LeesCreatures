package com.leethesologamer.leescreatures.objects.items;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class FoodInit {

    public static final Food BOARLIN_MEEAT = new Food.Builder()
            .effect(() -> new EffectInstance(Effects.STRENGTH, 300, 1), 1.0f).hunger(8).meat().saturation(1.3f)
            .setAlwaysEdible().build();

    public static final Food RAW_BOARLIN = new Food.Builder()
            .effect(() -> new EffectInstance(Effects.POISON, 100, 2), 1.0f).hunger(2).meat().saturation(0.1f)
            .setAlwaysEdible().build();

}
