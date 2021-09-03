package com.leethesologamer.leescreatures.entities;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
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

import javax.annotation.Nullable;
import java.util.Random;
//Todo make florrets be immune to stings.
public class FlorretEntity extends TameableEntity implements IAnimatable {
    private static final DataParameter<Boolean> SITTING = EntityDataManager.createKey(FlorretEntity.class, DataSerializers.BOOLEAN);
    private int exampleTimer;

    public FlorretEntity(EntityType<FlorretEntity> entityType, World worldIn) {
        super(entityType, worldIn);
        this.setTamed(false);
        this.setSitting(false);
    }

    private AnimationFactory factory = new AnimationFactory(this);

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
            return PlayState.CONTINUE;
        }
        if (this.dataManager.get(SITTING)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("sit", true));
            return PlayState.CONTINUE;
        }else
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, BeastDogEntity.class, 6.0F, 1.10D, 1.4D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, JungleSerpentEntity.class, 6.0F, 1.10D, 1.4D));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.4D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(4, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.2D));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 20.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.23F);
    }

    @Override
    public void livingTick() {
        if (this.world.isRemote) {
            this.exampleTimer = Math.max(0, this.exampleTimer - 1);
        }
        super.livingTick();
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.exampleTimer = 40;
        } else {
            super.handleStatusUpdate(id);
        }

    }

    public static boolean canFlorretSpawn(EntityType<FlorretEntity> entity, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn ){
        return worldIn.getLightSubtracted(pos, 0) > 8;
    }

    public boolean isNotColliding(IWorldReader worldIn) {
        return worldIn.checkNoEntityCollision(this);
    }

    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<FlorretEntity>(this, "controller", 0,this::predicate));
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 3;
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return true;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return null;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SITTING, false);

    }

    public boolean isBreedingItem(ItemStack stack) {
        Item item = stack.getItem();
        return isTamed() && item == Items.HONEY_BOTTLE;
    }

    @Override
    public void setTamedBy(PlayerEntity player) {
        super.setTamedBy(player);
    }

    public boolean isSitting() {
        return this.dataManager.get(SITTING);
    }

    //awww its so cute when it sits
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
        compound.putBoolean("Sitting", this.isSitting());

    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setSitting(compound.getBoolean("Sitting"));
    }

    public ActionResultType getEntityInteractionResult(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        ActionResultType type = super.getEntityInteractionResult(player, hand);
        if (!isTamed() && item == Items.HONEY_BOTTLE) {
            this.consumeItemFromStack(player, itemstack);
            this.playSound(SoundEvents.ENTITY_GENERIC_DRINK, this.getSoundVolume(), this.getSoundPitch());
            if (getRNG().nextInt(6) == 0) {
                this.setTamedBy(player);
                this.world.setEntityState(this, (byte) 7);
            } else {
                this.world.setEntityState(this, (byte) 6);
            }
            return ActionResultType.SUCCESS;
        }

        if (isTamed() && item == Items.HONEY_BOTTLE && this.getHealth() < this.getMaxHealth()) {
            this.heal(5);
            this.consumeItemFromStack(player, itemstack);
            this.playSound(SoundEvents.ENTITY_GENERIC_DRINK, this.getSoundVolume(), this.getSoundPitch());
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
    
    public void travel(Vector3d vec3d) {
        if (this.isSitting()) {
            if (this.getNavigator().getPath() != null) {
                this.getNavigator().clearPath();
            }
            vec3d = Vector3d.ZERO;
        }
        super.travel(vec3d);
    }
}


