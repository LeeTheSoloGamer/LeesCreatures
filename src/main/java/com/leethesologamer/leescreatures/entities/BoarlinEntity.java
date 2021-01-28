package com.leethesologamer.leescreatures.entities;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class BoarlinEntity extends AnimalEntity implements IAnimatable {

    public static AnimationBuilder IDLE_ANIM = new AnimationBuilder().addAnimation("idle");
    public static AnimationBuilder WALK_ANIM = new AnimationBuilder().addAnimation("walk");

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.WHEAT, Items.POTATO, Items.BEETROOT);
    private EatGrassGoal eatGrassGoal;
    private int exampleTimer;

    private static final DataParameter<Boolean> IDLE = EntityDataManager.createKey(BoarlinEntity.class,
            DataSerializers.BOOLEAN);

    public BoarlinEntity(EntityType<BoarlinEntity> entityType, World worldIn) {
        super(entityType, worldIn);

    }

    private AnimationFactory factory = new AnimationFactory(this);

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
        }
        else
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(3, this.eatGrassGoal);
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.10D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, PlayerEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, CrystalWyvernEntity.class, 10, true, true, (Predicate)null));
    }

    public static AttributeModifierMap.MutableAttribute func_234219_eI_() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 45.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 20.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.10D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 10.0D);
    }
    @Override
    public void livingTick() {
        if (this.world.isRemote) {
            this.exampleTimer = Math.max(0, this.exampleTimer - 1);
        }
        super.livingTick();
}

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.exampleTimer = 40;
        } else {
            super.handleStatusUpdate(id);
        }

    }

    public void onStruckByLightning(LightningBoltEntity lightningBolt) {
        this.setGlowing(true);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<BoarlinEntity>(this, "controller", 0,this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isIdle() {
        return this.dataManager.get(IDLE);
    }

    public void setIdle(boolean idle) {
        this.dataManager.set(IDLE, idle);
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return null;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(IDLE, false);
    }
    public boolean attackEntityAsMob(Entity p_70652_1_) {
        boolean lvt_2_1_ = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (lvt_2_1_) {
            this.applyEnchantments(this, p_70652_1_);
        }

        return lvt_2_1_;
    }
}