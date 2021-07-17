package com.leethesologamer.leescreatures.entitiesOLD;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import com.leethesologamer.leescreatures.entities.flying.ai.TamedAiRide;

import com.leethesologamer.leescreatures.init.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

public class BoarlinEntity extends TameableEntity implements IAngerable, IAnimatable{
    private static final DataParameter<Boolean> BUCKING = EntityDataManager.createKey(BoarlinEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> ANGER_TIME = EntityDataManager.createKey(BoarlinEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> SITTING = EntityDataManager.createKey(BoarlinEntity.class, DataSerializers.BOOLEAN);
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.WHEAT, Items.POTATO, Items.BEETROOT);
    private EatGrassGoal eatGrassGoal;
    private int exampleTimer;
    private UUID lastHurtBy;
    private int riderAttackCooldown = 0;

    public BoarlinEntity(EntityType<BoarlinEntity> entityType, World worldIn) {
        super(entityType, worldIn);
        this.setTamed(false);
    }

    private AnimationFactory factory = new AnimationFactory(this);

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
            return PlayState.CONTINUE;
        }
        else if (this.dataManager.get(BUCKING)) {
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
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(0, new SitGoal(this));
        this.goalSelector.addGoal(1, new BuckingGoal());
        this.goalSelector.addGoal(1, new TamedAiRide(this, 1.1D));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.1D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 0.3));
        this.goalSelector.addGoal(5, this.eatGrassGoal);
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.1D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.1D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, CrystalWyvernEntity.class, false));
    }


    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 45.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.23F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 10.0D);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.world.isRemote) {
            this.exampleTimer = Math.max(0, this.exampleTimer - 1);
        }
        if(riderAttackCooldown > 0){
            riderAttackCooldown--;
        }
        if(this.getControllingPassenger() != null && this.getControllingPassenger() instanceof PlayerEntity){
            PlayerEntity rider = (PlayerEntity)this.getControllingPassenger();
            if(rider.getLastAttackedEntity() != null && this.getDistance(rider.getLastAttackedEntity()) < this.getWidth() + 3F && !this.isOnSameTeam(rider.getLastAttackedEntity())){
                UUID preyUUID = rider.getLastAttackedEntity().getUniqueID();
                if (!this.getUniqueID().equals(preyUUID) && riderAttackCooldown == 0) {
                    attackEntityAsMob(rider.getLastAttackedEntity());
                    riderAttackCooldown = 20;
                }
            }
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
        return 3;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    public static boolean canBoarlinEntitySpawn(EntityType<BoarlinEntity> entity, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn ){
        return worldIn.getLightSubtracted(pos, 0) > 8;
    }

    public boolean isBreedingItem(ItemStack stack) {
        Item item = stack.getItem();
        return isTamed() && item == Items.WHEAT;
    }

    public boolean isNotColliding(IWorldReader worldIn) {
        return worldIn.checkNoEntityCollision(this);
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return super.canBeLeashedTo(player);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return null;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(BUCKING, false);
        this.dataManager.register(ANGER_TIME, 0);
        this.dataManager.register(SITTING, false);
    }

    public boolean isBucking() {
        return this.dataManager.get(BUCKING);
    }

    public void setBucking(boolean bucking) {
        this.dataManager.set(BUCKING, bucking);
    }

    public boolean isSitting() {
        return this.dataManager.get(SITTING);
    }

    //looks like it needs a belly rub
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
        compound.putBoolean("Bucking", this.isBucking());
        compound.putBoolean("Sitting", this.isSitting());

    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setBucking(compound.getBoolean("Bucking"));
        this.setSitting(compound.getBoolean("Sitting"));
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
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

    public int getAngerTime() {
        return this.dataManager.get(ANGER_TIME);
    }

    public void setAngerTime(int time) {
        this.dataManager.set(ANGER_TIME, time);
    }

    public UUID getAngerTarget() {
        return this.lastHurtBy;
    }

    public void setAngerTarget(@Nullable UUID target) {
        this.lastHurtBy = target;
    }

    @Override
    public void func_230258_H__() {
    }

    @Override
    public void setTamedBy(PlayerEntity player) {
        super.setTamedBy(player);
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 3;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_PIG_STEP;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    @Nullable
    public Entity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) passenger;
                return player;
            }
        }
        return null;
    }

    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            float radius = 0;
            float angle = (0.01745329251F * this.renderYawOffset);
            double extraX = radius * MathHelper.sin((float) (Math.PI + angle));
            double extraZ = radius * MathHelper.cos(angle);
            passenger.setPosition(this.getPosX() + extraX, this.getPosY() + this.getMountedYOffset() + passenger.getYOffset(), this.getPosZ() + extraZ);
        }
    }

    public double getMountedYOffset() {
        float f = Math.min(0.25F, this.limbSwingAmount);
        float f1 = this.limbSwing;
        return (double)this.getHeight() - 0.5D + (double)(0.6F * MathHelper.cos(f1 * 1.2F) * 1.0F * f);
    }

    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        ActionResultType type = super.func_230254_b_(player, hand);
        if (!isTamed() && item == Items.WHEAT) {
            this.consumeItemFromStack(player, itemstack);
            this.playSound(SoundEvents.ENTITY_GENERIC_EAT, this.getSoundVolume(), this.getSoundPitch());
            if (getRNG().nextInt(16) == 0) {
                this.setTamedBy(player);
                this.world.setEntityState(this, (byte) 7);
            } else {
                this.world.setEntityState(this, (byte) 6);
            }
            return ActionResultType.SUCCESS;
        }

        if (isTamed() && item == Items.WHEAT && this.getHealth() < this.getMaxHealth()) {
            this.heal(6);
            this.consumeItemFromStack(player, itemstack);
            this.playSound(SoundEvents.ENTITY_GENERIC_EAT, this.getSoundVolume(), this.getSoundPitch());
            return ActionResultType.SUCCESS;
        }
        if (type != ActionResultType.SUCCESS && isTamed() && isOwner(player) && !isBreedingItem(itemstack)) {
            if(!player.isSneaking()){
                player.startRiding(this);
                setAttackTarget(null);
                return ActionResultType.SUCCESS;
            }else{
                if (this.isSitting()) {
                    this.setSitting(false);
                    return ActionResultType.SUCCESS;
                } else {
                    this.clearAI();
                    this.setSitting(true);
                    return ActionResultType.SUCCESS;
                }
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
    class BuckingGoal extends net.minecraft.entity.ai.goal.MeleeAttackGoal {
        public BuckingGoal() {
            super(BoarlinEntity.this, 1.0D, true);
        }

        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.func_234040_h_()) {
                this.func_234039_g_();
                this.attacker.attackEntityAsMob(enemy);
                BoarlinEntity.this.setBucking(false);
            } else if (distToEnemySqr <= d0 * 2.0D) {
                if (this.func_234040_h_()) {
                    BoarlinEntity.this.setBucking(false);
                    this.func_234039_g_();
                }

                if (this.func_234041_j_() <= 10) {
                    BoarlinEntity.this.setBucking(true);
                }
            }else {
                this.func_234039_g_();
                BoarlinEntity.this.setBucking(false);
            }
        }

        public void resetTask() {
            BoarlinEntity.this.setBucking(false);
            super.resetTask();
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 3.0F + attackTarget.getWidth();
        }
    }
}
