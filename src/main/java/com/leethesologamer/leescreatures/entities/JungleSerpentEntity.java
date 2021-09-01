package com.leethesologamer.leescreatures.entities;

import javax.annotation.Nullable;

import com.leethesologamer.leescreatures.entities.flying.ai.SwimerPathNavigator;
import com.leethesologamer.leescreatures.entities.flying.ai.WaterMoveController;
import com.leethesologamer.leescreatures.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
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

import java.util.Random;

public class JungleSerpentEntity extends TameableEntity implements IAnimatable {
    private static final DataParameter<Boolean> STRIKING = EntityDataManager.createKey(JungleSerpentEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SITTING = EntityDataManager.createKey(CrystalWyvernEntity.class, DataSerializers.BOOLEAN);
    private int exampleTimer;
    private boolean isLandNavigator;

    public JungleSerpentEntity(EntityType<JungleSerpentEntity> entityType, World worldIn) {
        super(entityType, worldIn);
        this.setPathPriority(PathNodeType.WATER, 0.1F);
        this.setTamed(false);
        this.setSitting(false);
        switchNavigator(false);
        this.stepHeight = 1.0F;
    }

    private AnimationFactory factory = new AnimationFactory(this);

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("slithering", true));
            return PlayState.CONTINUE;
        }
        if (this.isInWater() && this.dataManager.get(STRIKING)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("water_attacking", true));
            return PlayState.CONTINUE;
        }
        else if (this.dataManager.get(STRIKING)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
            return PlayState.CONTINUE;
        }
        else if (this.dataManager.get(SITTING)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("sitting", true));
            return PlayState.CONTINUE;
        }
        else
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new StrikeAttackGoal());
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.5D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(6, new RandomWalkingGoal(this, 1.5D));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this, PlayerEntity.class));
        this.targetSelector.addGoal(4, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, AnimalEntity.class, true));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, AbstractFishEntity.class, true));
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

    public void tick() {
        super.tick();
        boolean ground = !this.isInWater();
        if (!ground && this.isLandNavigator) {
            switchNavigator(false);
        }
        if (ground && !this.isLandNavigator) {
            switchNavigator(true);
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

    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 3;
    }

    public static boolean canJungleSerpentEntitySpawn( EntityType<JungleSerpentEntity> entity, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn ){
        BlockState blockstate = worldIn.getBlockState(pos.down());
        return (worldIn.hasWater(pos)) || (blockstate.isIn((ITag<Block>) Blocks.WATER)) || (blockstate.isIn((ITag<Block>) Blocks.GRASS_BLOCK)) || (blockstate.isIn(BlockTags.LEAVES)) && worldIn.getLightSubtracted(pos, 0) > 8;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<JungleSerpentEntity>(this, "controller", 0,this::predicate));
    }

    public boolean isPushedByWater() {
        return false;
    }

    public boolean canBreatheUnderwater() {
        return true;
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
    public AgeableEntity createChild(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(STRIKING, false);
        this.dataManager.register(SITTING, false);
    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveController = new MovementController(this);
            this.navigator = new GroundPathNavigator(this, world);
            this.isLandNavigator = true;
        } else {
            this.moveController = new WaterMoveController(this, 1F);
            this.navigator = new SwimerPathNavigator(this, world);
            this.isLandNavigator = false;
        }
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

    public boolean isSitting() {
        return this.dataManager.get(SITTING);
    }

    //sit bitch and dont let me have to tell you again
    public void setSitting(boolean sitting) {
        this.dataManager.set(SITTING, sitting);
    }

    public void clearAI()
    {
        isJumping = false;
        navigator.clearPath();
        setAttackTarget(null);
        setRevengeTarget(null);
        setMoveForward(0);
        setMoveVertical(0);
    }


    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Striking", this.isStriking());
        compound.putBoolean("Sitting", this.isSitting());
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setStriking(compound.getBoolean("Striking"));
        this.setSitting(compound.getBoolean("Sitting"));
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        else {
            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source == DamageSource.DROWN || source == DamageSource.IN_WALL || source == DamageSource.FALLING_BLOCK || super.isInvulnerableTo(source);
    }

    public boolean isPotionApplicable(EffectInstance potioneffectIn) {
        if (potioneffectIn.getPotion() == Effects.POISON) {
            return false;
        }
        return super.isPotionApplicable(potioneffectIn);
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
                ((LivingEntity)entityIn).addPotionEffect(new EffectInstance(Effects.POISON, i * 24, 1));
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

    public ActionResultType getEntityInteractionResult(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        ActionResultType type = super.getEntityInteractionResult(player, hand);
        if (!isTamed() && item == ModItems.CRESTED_CRIKESTREAKER_EGG.get()) {
            this.consumeItemFromStack(player, itemstack);
            this.playSound(SoundEvents.ENTITY_GENERIC_EAT, this.getSoundVolume(), this.getSoundPitch());
            if (getRNG().nextInt(3) == 0) {
                this.setTamedBy(player);
                this.world.setEntityState(this, (byte) 7);
            } else {
                this.world.setEntityState(this, (byte) 6);
            }
            return ActionResultType.SUCCESS;
        }

        if (isTamed() && item == ModItems.CRESTED_CRIKESTREAKER_EGG.get() && this.getHealth() < this.getMaxHealth()) {
            this.heal(5);
            this.consumeItemFromStack(player, itemstack);
            this.playSound(SoundEvents.ENTITY_GENERIC_EAT, this.getSoundVolume(), this.getSoundPitch());
            return ActionResultType.SUCCESS;
        }
        if (type != ActionResultType.SUCCESS && isTamed() && isOwner(player) && !isBreedingItem(itemstack)) {
            if (this.isSitting()) {
                this.setSitting(false);
                return ActionResultType.SUCCESS;
            } else {
                this.clearAI();
                this.setSitting(true);
                return ActionResultType.SUCCESS;
            }
        }
        return type;
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
            if (distToEnemySqr <= d0 && this.isSwingOnCooldown()) {
                this.resetSwingCooldown();
                this.attacker.attackEntityAsMob(enemy);
                JungleSerpentEntity.this.setStriking(true);
            } else if (distToEnemySqr <= d0 * 2.0D) {
                if (this.isSwingOnCooldown()) {
                    JungleSerpentEntity.this.setStriking(false);
                    this.resetSwingCooldown();
                }

                if (this.getSwingCooldown() <= 10) {
                    JungleSerpentEntity.this.setStriking(true);
                }
            }else {
                this.resetSwingCooldown();
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

    public void travel(Vector3d travelVector) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(this.getAIMoveSpeed(), travelVector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9D));
            if (this.getAttackTarget() == null) {
                this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
            }
            if (this.isSitting()) {
                if (this.getNavigator().getPath() != null) {
                    this.getNavigator().clearPath();
                }
                travelVector = Vector3d.ZERO;
            }
        } else {
            super.travel(travelVector);
        }
    }
}
    