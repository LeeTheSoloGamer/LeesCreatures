package com.leethesologamer.leescreatures.entities;

import com.leethesologamer.leescreatures.init.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.PigEntity;
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
import net.minecraft.util.*;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
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
import java.util.Random;


public class CrystalWyvernEntity extends TameableEntity implements IAnimatable {
    private static final DataParameter<Boolean> BITEING = EntityDataManager.createKey(CrystalWyvernEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(CrystalWyvernEntity.class, DataSerializers.VARINT);
    private int exampleTimer;

    public CrystalWyvernEntity(EntityType<CrystalWyvernEntity> entityType, World worldIn) {
        super(entityType, worldIn);
        this.setTamed(false);    }

    private AnimationFactory factory = new AnimationFactory(this);

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
            return PlayState.CONTINUE;
        }
        if (this.dataManager.get(BITEING)){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
            return PlayState.CONTINUE;
        }else
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new CrystalWyvernEntity.ChompAttackGoal());
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.1D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.2D));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this, PlayerEntity.class));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, BoarlinEntity.class, true));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, PigEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 60.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 36.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.23F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 22.0D);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.world.isRemote) {
            this.exampleTimer = Math.max(0, this.exampleTimer - 1);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.exampleTimer = 40;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    public boolean isNotColliding(IWorldReader worldIn) {
        return worldIn.checkNoEntityCollision(this);
    }

    public int getMaxSpawnedInChunk() {
        return 2;
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 3;
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return super.canBeLeashedTo(player);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VARIANT, 0);
        this.dataManager.register(BITEING, false);
    }

    public int getVariant() {
        return this.dataManager.get(VARIANT);
    }

    public void setVariant(int type) {
        this.dataManager.set(VARIANT, type);
    }

    public boolean isBiteing() {
        return this.dataManager.get(BITEING);
    }

    public void setBiteing(boolean biteing) {
        this.dataManager.set(BITEING, biteing);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Variant", this.getVariant());
        compound.putBoolean("Biteing", this.isBiteing());
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setVariant(compound.getInt("Variant"));
        this.setBiteing(compound.getBoolean("Biteing"));
    }


    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        int type;
        Random random = new Random();
        if (random.nextFloat() > 0.5) {
            type = 1;
        } else if (random.nextFloat() > 0.6) {
            type = 2;
        } else if (random.nextFloat() > 0.9) {
            type = 3;
        } else if (random.nextFloat() > 0.10) {
            type = 4;
        } else if (random.nextFloat() > 0.11) {
            type = 5;
        } else {
            type = 0;
        }
        this.setVariant(type);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }

        if(this.isSitting()) {
            return false;

        } else {
            return super.attackEntityFrom(source, amount);
        }
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof BoarlinEntity)) {
            if (target instanceof CrystalWyvernEntity) {
                CrystalWyvernEntity crystalWyvernEntity = (CrystalWyvernEntity) target;
                return !crystalWyvernEntity.isTamed() || crystalWyvernEntity.getOwner() != owner;
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
            boolean flag = this.isOwner(p_230254_1_) || this.isTamed() || item == ModItems.JEWELED_EGG.get() && !this.isTamed();
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

            } else if (item == ModItems.JEWELED_EGG.get() ) {
                if (!p_230254_1_.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }

                if (this.rand.nextInt(10) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_230254_1_)) {
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

    class ChompAttackGoal extends net.minecraft.entity.ai.goal.MeleeAttackGoal {
        public ChompAttackGoal() {
            super(CrystalWyvernEntity.this, 1.2D, true);
        }

        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.func_234040_h_()) {
                this.func_234039_g_();
                this.attacker.attackEntityAsMob(enemy);
                CrystalWyvernEntity.this.setBiteing(false);
            } else if (distToEnemySqr <= d0 * 2.0D) {
                if (this.func_234040_h_()) {
                    CrystalWyvernEntity.this.setBiteing(false);
                    this.func_234039_g_();
                }

                if (this.func_234041_j_() <= 10) {
                    CrystalWyvernEntity.this.setBiteing(true);
                }
            }else {
                this.func_234039_g_();
                CrystalWyvernEntity.this.setBiteing(false);
            }
        }

        public void resetTask() {
            CrystalWyvernEntity.this.setBiteing(false);
            super.resetTask();
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 3.0F + attackTarget.getWidth();
        }
    }
}