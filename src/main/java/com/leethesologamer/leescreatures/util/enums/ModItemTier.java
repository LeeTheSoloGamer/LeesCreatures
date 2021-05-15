package com.leethesologamer.leescreatures.util.enums;

import com.leethesologamer.leescreatures.init.ModItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModItemTier implements IItemTier {


    DURANTIUM( 3, 2031, 8.0f,2.0f, 12, ()->{
        return Ingredient.fromItems(ModItems.DURANTIUM_SWORD.get());
    }),

    DURANTIUM_AXE( 3, 1561, 8.0f,5.0f, 12, ()->{
        return Ingredient.fromItems(ModItems.DURANTIUM_AXE.get());
    }),

    DURANTIUM_SHOVEL( 3, 2031, 8.0f,4.0f, 12, ()->{
        return Ingredient.fromItems(ModItems.DURANTIUM_SHOVEL.get());
    }),

    DURANTIUM_HOE( 3, 2031, 8.0f,1.0f, 12, ()->{
        return Ingredient.fromItems(ModItems.DURANTIUM_HOE.get());
    }),

    DAGGER( 2, 250, 5.0f,2.3f, 10, ()->{
        return Ingredient.fromItems(ModItems.SERPENT_FANG_DAGGER.get());
    }),

    POISON_DAGGER( 2, 250, 5.0f,2.3f, 10, ()->{
        return Ingredient.fromItems(ModItems.SERPENT_FANG_DAGGER.get());
    }),

    GREEMANAR( 3, 1500, 6.0f,3.9f, 12, ()->{
        return Ingredient.fromItems(ModItems.GREEMANAR_LONG_SWORD.get());
    });


    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getMaxUses() {
        return maxUses;
    }

    @Override
    public float getEfficiency() {
        return efficiency;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return repairMaterial.get();
    }

}
