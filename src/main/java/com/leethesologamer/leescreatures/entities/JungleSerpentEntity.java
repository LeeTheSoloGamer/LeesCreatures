package com.leethesologamer.leescreatures.entities;

import com.leethesologamer.leescreatures.init.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorldReader;
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

public class JungleSerpentEntity extends TameableEntity implements IAnimatable {
    private static final DataParameter<Boolean> STRIKING = EntityDataManager.createKey(JungleSerpentEntity.class,
            DataSerializers.BOOLEAN);

    private int exampleTimer;

    public JungleSerpentEntity(EntityType<JungleSerpentEntity> entityType, World worldIn) {
        super(entityType, worldIn);
        this.setTamed(false);
    }

    private AnimationFactory factory = new AnimationFactory(this);

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !this.dataManager.get(STRIKING)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("slithering", true));
            return PlayState.CONTINUE;
        }
        if ((this.dataManager.get(STRIKING))) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
            return PlayState.CONTINUE;
        }else
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new JungleSerpentEntity.StrikeAttackGoal());
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.5D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.5D));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this, PlayerEntity.class));
        this.targetSelector.addGoal(4, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, AnimalEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 57.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 20.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.29F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 12.0D);

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

    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 3;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<JungleSerpentEntity>(this, "controller", 0,this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return super.canBeLeashedTo(player);
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(STRIKING, false);
    }

    public boolean isNotColliding(IWorldReader worldIn) {
        return worldIn.checkNoEntityCollision(this);
    }

    @Override
    public void setTamedBy(PlayerEntity player) {
        super.setTamedBy(player);
    }

    public boolean isStriking() {
        return this.dataManager.get(STRIKING);
    }

    public void setStriking(boolean striking) {
        this.dataManager.set(STRIKING,striking);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Striking", this.isStriking());

    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setStriking(compound.getBoolean("Striking"));
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        else {
            return super.attackEntityFrom(source, amount);
        }
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        if (super.attackEntityAsMob(entityIn)) {
            if (entityIn instanceof LivingEntity) {
                int i = 0;
                if (this.world.getDifficulty() == Difficulty.NORMAL) {
                    i = 10;
                } else if (this.world.getDifficulty() == Difficulty.HARD) {
                    i = 20;
                }
                ((LivingEntity)entityIn).addPotionEffect(new EffectInstance(Effects.POISON, i * 24, 0));
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CrystalWyvernEntity)) {
            if (target instanceof BoarlinEntity) {
                BoarlinEntity boarlinEntity = (BoarlinEntity) target;
                return !boarlinEntity.isTamed() || boarlinEntity.getOwner() != owner;
            } else if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity) owner).canAttackPlayer((PlayerEntity) target)) {
                return false;
            } else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity) target).isTame()) {
                return false;
            } else {
                return !(target instanceof TameableEntity) || !((TameableEntity) target).isTamed();
            }
        } else {
            return false;
        }
    }

    public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
        Item item = itemstack.getItem();
        if (this.world.isRemote) {
            boolean flag = this.isOwner(p_230254_1_) || this.isTamed() || item == ModItems.CRESTED_CRIKESTREAKER_EGG.get() && !this.isTamed();
            return flag ? ActionResultType.CONSUME : ActionResultType.PASS;
        } else {
            if (this.isTamed()) {
                if (this.isBreedingItem(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!p_230254_1_.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    this.heal((float)item.getFood().getHealing());
                    return ActionResultType.SUCCESS;
                }

                if (!(item instanceof DyeItem)) {
                    ActionResultType actionresulttype = super.func_230254_b_(p_230254_1_, p_230254_2_);
                    if ((!actionresulttype.isSuccessOrConsume() || this.isChild()) && this.isOwner(p_230254_1_)) {
                        this.func_233687_w_(!this.isSitting());
                        this.navigator.clearPath();
                        this.setAttackTarget((LivingEntity)null);
                        return ActionResultType.SUCCESS;
                    }

                    return actionresulttype;
                }

            } else if (item == ModItems.CRESTED_CRIKESTREAKER_EGG.get() ) {
                if (!p_230254_1_.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }

                if (this.rand.nextInt(5) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_230254_1_)) {
                    this.setTamedBy(p_230254_1_);
                    this.navigator.clearPath();
                    this.setAttackTarget((LivingEntity)null);
                    this.func_233687_w_(true);
                    this.world.setEntityState(this, (byte)7);
                } else {
                    this.world.setEntityState(this, (byte)6);
                }

                return ActionResultType.SUCCESS;
            }

            return super.func_230254_b_(p_230254_1_, p_230254_2_);
        }
    }

    public boolean isOnSameTeam(Entity entityIn) {
        if (this.isTamed()) {
            LivingEntity livingentity = this.getOwner();
            if (entityIn == livingentity) {
                return true;
            }
            if (entityIn instanceof TameableEntity) {
                return ((TameableEntity) entityIn).isOwner(livingentity);
            }
            if (livingentity != null) {
                return livingentity.isOnSameTeam(entityIn);
            }
        }
        return super.isOnSameTeam(entityIn);
    }

    class StrikeAttackGoal extends net.minecraft.entity.ai.goal.MeleeAttackGoal {
        public StrikeAttackGoal() {
            super(JungleSerpentEntity.this, 1.D, true);
        }

        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.func_234040_h_()) {
                this.func_234039_g_();
                this.attacker.attackEntityAsMob(enemy);
                JungleSerpentEntity.this.setStriking(true);
            } else if (distToEnemySqr <= d0 * 2.0D) {
                if (this.func_234040_h_()) {
                    JungleSerpentEntity.this.setStriking(false);
                    this.func_234039_g_();
                }

                if (this.func_234041_j_() <= 10) {
                    JungleSerpentEntity.this.setStriking(true);
                }
            }else {
                this.func_234039_g_();
                JungleSerpentEntity.this.setStriking(false);
            }
        }

        public void resetTask() {
            JungleSerpentEntity.this.setStriking(false);
            super.resetTask();
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 3.0F + attackTarget.getWidth();
        }
    }
}


