package com.leethesologamer.leescreatures.entities;

import static net.minecraft.entity.ai.attributes.Attributes.FLYING_SPEED;
import static net.minecraft.entity.ai.attributes.Attributes.MOVEMENT_SPEED;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CrystalWyvernEntity extends FlyingBaseEntity implements IAnimatable {

	private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(CrystalWyvernEntity.class, DataSerializers.VARINT);
	private AnimationFactory factory = new AnimationFactory(this);
	
	
	public CrystalWyvernEntity(EntityType<? extends MonsterEntity> p_i48553_1_, World p_i48553_2_) {
		super(p_i48553_1_, p_i48553_2_);
		// TODO Auto-generated constructor stub
	}
	
	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 60.0D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 36.0D)
				.createMutableAttribute(MOVEMENT_SPEED, 0.23F)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 22.0D)
				.createMutableAttribute(FLYING_SPEED, 0.2F)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 40.0D);
	}

	@Override
	protected void registerData() {
		dataManager.register(VARIANT, 0);
		super.registerData();
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("flying", true));
		return PlayState.CONTINUE;
	}
	
	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
	}
	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	public int getVariant() {
		return this.dataManager.get(VARIANT);
	}

	public void setVariant(int type) {
		this.dataManager.set(VARIANT, type);
	}

}
