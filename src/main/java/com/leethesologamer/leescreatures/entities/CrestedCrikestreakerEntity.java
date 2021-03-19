package com.leethesologamer.leescreatures.entities;

import com.leethesologamer.leescreatures.init.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
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

public class CrestedCrikestreakerEntity extends CreatureEntity implements IAnimatable {

    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(CrestedCrikestreakerEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(CrestedCrikestreakerEntity.class, DataSerializers.VARINT);

    private int exampleTimer;
    public int timeUntilNextCrestedEgg = this.rand.nextInt(6000) + 6000;

    public CrestedCrikestreakerEntity(EntityType<CrestedCrikestreakerEntity> entityType, World worldIn) {
        super(entityType, worldIn);

    }

    private AnimationFactory factory = new AnimationFactory(this);

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !this.dataManager.get(ATTACKING)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
            return PlayState.CONTINUE;
        }
        if ((this.dataManager.get(ATTACKING))) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
            return PlayState.CONTINUE;
        }
        else
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new CrestedCrikestreakerEntity.AttackGoal());
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.8D));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, BoarlinEntity.class, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, SheepEntity.class, false));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 47.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 10D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.45F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 13.0D);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.world.isRemote) {
            this.exampleTimer = Math.max(0, this.exampleTimer - 1);
        }

        if (!this.world.isRemote && this.isAlive() && !this.isChild() && --this.timeUntilNextCrestedEgg <= 0) {
            this.entityDropItem(ModItems.CRESTED_CRIKESTREAKER_EGG.get());
            this.timeUntilNextCrestedEgg = this.rand.nextInt(6000) + 6000;
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
        animationData.addAnimationController(new AnimationController<CrestedCrikestreakerEntity>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VARIANT, 0);
        this.dataManager.register(ATTACKING, false);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return super.canBeLeashedTo(player);
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 3;
    }

    public boolean isNotColliding(IWorldReader worldIn) {
        return worldIn.checkNoEntityCollision(this);
    }

    public int getVariant() {
        return this.dataManager.get(VARIANT);
    }

    public void setVariant(int type) {
        this.dataManager.set(VARIANT, type);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isAttacking() {
        return this.dataManager.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.dataManager.set(ATTACKING, attacking);
    }


    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Variant", this.getVariant());
        compound.putBoolean("Attacking", this.isAttacking());
        compound.putInt("EggDropTime", this.timeUntilNextCrestedEgg);

    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setVariant(compound.getInt("Variant"));
        this.setAttacking(compound.getBoolean("Attacking"));
        if (compound.contains("EggDropTime")) {
            this.timeUntilNextCrestedEgg = compound.getInt("EggDropTime");
        }
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        int type;
        Random random = new Random();
        if (random.nextFloat() > 0.9) {
            type = 1;
        } else if (random.nextFloat() > 0.9) {
            type = 2;
        } else {
            type = 0;
        }
        this.setVariant(type);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
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
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    class AttackGoal extends net.minecraft.entity.ai.goal.MeleeAttackGoal {
        public AttackGoal() {
            super(CrestedCrikestreakerEntity.this, 1.2D, true);
        }

        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.func_234040_h_()) {
                this.func_234039_g_();
                this.attacker.attackEntityAsMob(enemy);
                CrestedCrikestreakerEntity.this.setAttacking(false);
            } else if (distToEnemySqr <= d0 * 2.0D) {
                if (this.func_234040_h_()) {
                    CrestedCrikestreakerEntity.this.setAttacking(false);
                    this.func_234039_g_();
                }

                if (this.func_234041_j_() <= 10) {
                    CrestedCrikestreakerEntity.this.setAttacking(true);
                }
            }else {
                this.func_234039_g_();
                CrestedCrikestreakerEntity.this.setAttacking(false);
            }
        }

        public void resetTask() {
            CrestedCrikestreakerEntity.this.setAttacking(false);
            super.resetTask();
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 3.0F + attackTarget.getWidth();
        }
    }
}
