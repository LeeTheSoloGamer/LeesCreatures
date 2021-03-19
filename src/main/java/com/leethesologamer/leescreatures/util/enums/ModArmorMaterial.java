package com.leethesologamer.leescreatures.util.enums;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.init.ModItems;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;


public enum ModArmorMaterial implements IArmorMaterial {

    BEASTLY_ARMOR(LeesCreatures.MOD_ID + ":beastly_armor", 16, new int[] {2,6,6,2},18,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.2f, 0.1f, ()-> { return Ingredient.fromItems(ModItems.BEASTLY_HIDE.get());
    }),

    JUNGLE_SERPENT_ARMOR(LeesCreatures.MOD_ID + ":jungle_serpent_armor", 31, new int[] {3,6,7,3},18,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.9f, 0.1f, ()-> { return Ingredient.fromItems(ModItems.JUNGLE_SERPENT_SCALES.get());
    }),

    GREEMANAR(LeesCreatures.MOD_ID + ":greemanar", 34, new int[] {3,7,8,3},19,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.2f, 0.1f, ()-> { return Ingredient.fromItems(ModItems.GREEMANAR_INGOT.get());
    }),

    DURANTIUM(LeesCreatures.MOD_ID + ":durantium", 38, new int[] {3,8,9,4},19,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 3.2f, 0.2f, ()-> { return Ingredient.fromItems(ModItems.DURANTIUM_INGOT.get());
    });

    private static final int[] MAX_DAMAGE_ARRAY = new int[] {11, 16, 15, 13 };
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairMaterial;


    ModArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] *  this.maxDamageFactor;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return this.repairMaterial.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
