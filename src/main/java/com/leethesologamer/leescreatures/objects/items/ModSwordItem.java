package com.leethesologamer.leescreatures.objects.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.init.ModItems;
import com.leethesologamer.leescreatures.util.enums.ModItemTier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ModSwordItem extends SwordItem {
    private final float attackDamage;
    private final ModItemTier toolMaterial;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public ModSwordItem(ModItemTier toolmaterial, int attackDamageIn, float attackSpeedIn) {
        super(toolmaterial, 3, -2.4F, new Item.Properties().group(LeesCreatures.LEES_CREATURES_GROUP));
        this.toolMaterial = toolmaterial;
        this.attackDamage = (float)attackDamageIn + toolMaterial.getAttackDamage();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    public Multimap<net.minecraft.entity.ai.attributes.Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (toolMaterial == ModItemTier.POISON_DAGGER) {
            if(this == ModItems.POISON_FANG_DAGGER.get()){
                target.addPotionEffect(new EffectInstance(Effects.POISON, 200, 1));
            }
        }
        return super.hitEntity(stack, target, attacker);
    }
}
