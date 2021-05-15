package com.leethesologamer.leescreatures.entities;

import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.leethesologamer.leescreatures.init.ModEntityTypes;
import com.leethesologamer.leescreatures.init.ModItems;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.gen.Heightmap;
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

import static net.minecraft.entity.ai.attributes.Attributes.FLYING_SPEED;
import static net.minecraft.entity.ai.attributes.Attributes.MOVEMENT_SPEED;

//Todo fix loot tables and add better ai for randomly flying
public class CrystalWyvernEntity extends TameableEntity implements IAnimatable, IFlyingAnimal {
	public static final ResourceLocation BLUE_LOOT = new ResourceLocation("leescreatures", "loot_tables/entities/crystal_wyvern_entity");
	public static final ResourceLocation LIGHT_BLUE_LOOT = new ResourceLocation("leescreatures", "loot_tables/entities/crystal_wyvern_entity_light_blue");
	public static final ResourceLocation PURPLE_LOOT = new ResourceLocation("leescreatures", "loot_tables/entities/crystal_wyvern_entity_purple");
	public static final ResourceLocation PINK_LOOT = new ResourceLocation("leescreatures", "loot_tables/entities/crystal_wyvern_entity_pink");
	public static final ResourceLocation VOILET_LOOT = new ResourceLocation("leescreatures", "loot_tables/entities/crystal_wyvern_entity_voilet");
	public static final ResourceLocation WHITE_LOOT = new ResourceLocation("leescreatures", "loot_tables/entities/crystal_wyvern_entity_white");
	private static final DataParameter<Boolean> BITEING = EntityDataManager.createKey(CrystalWyvernEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(CrystalWyvernEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> FLYING = EntityDataManager.createKey(CrystalWyvernEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> SITTING = EntityDataManager.createKey(CrystalWyvernEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<BlockPos> HOME_POS = EntityDataManager.createKey(TurtleEntity.class, DataSerializers.BLOCK_POS);
	private static final DataParameter<Boolean> GOING_HOME = EntityDataManager.createKey(TurtleEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<BlockPos> TRAVEL_POS = EntityDataManager.createKey(TurtleEntity.class, DataSerializers.BLOCK_POS);
	private static final DataParameter<Boolean> TRAVELLING = EntityDataManager.createKey(TurtleEntity.class, DataSerializers.BOOLEAN);
	public static final Predicate<LivingEntity> PREY_ENTITIES = (prey) -> {
		EntityType<?> entitytype = prey.getType();
		return entitytype == EntityType.COW || entitytype == EntityType.PIG || entitytype == ModEntityTypes.BOARLIN_ENTITY.get();
	};
	private int exampleTimer;

	public CrystalWyvernEntity(EntityType<CrystalWyvernEntity> entityType, World worldIn) {
		super(entityType, worldIn);
		this.setTamed(false);
		this.setSitting(false);
		if (this.dataManager.get(FLYING)) moveController = new FlyingMoveController(this);
		this.stepHeight = 1.0F;
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (this.isOnGround() && event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		else if (this.dataManager.get(BITEING)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
			return PlayState.CONTINUE;
		}
		else if (this.dataManager.get(SITTING)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("sitting", true));
			return PlayState.CONTINUE;
		}
		else if (this.liftOff() && this.isFlying() && this.dataManager.get(FLYING)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("flying", true));
			return PlayState.CONTINUE;
		}
		else
			event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new SitGoal(this));
		this.goalSelector.addGoal(1, new WyvernFindHomeGoal(this, 1.1D));
		this.goalSelector.addGoal(2, new WyvernTravelGoal(this, 1.1D));
		this.goalSelector.addGoal(3, new ChompAttackGoal());
		this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.1D, 10.0F, 2.0F, false));
		this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this,1.1D));
		this.goalSelector.addGoal(7, new AIRandomFly(this));
		this.applyEntityAI();
	}

	protected void applyEntityAI() {
		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this, PlayerEntity.class));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(5, new NonTamedTargetGoal<>(this, AnimalEntity.class, false, PREY_ENTITIES));
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
	public void livingTick() {
		super.livingTick();
		if (isServerWorld())
		{
			boolean flying = shouldFly();
			if (flying != isFlying()) setFlying(flying);
		}

		if (this.isAggressive()) setFlying(false);
	}

	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 10) {
			this.exampleTimer = 40;
		} else {
			super.handleStatusUpdate(id);
		}
	}

	public static boolean canCrystalWyvernSpawn(EntityType<CrystalWyvernEntity> entity, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn ){
		BlockState blockstate = worldIn.getBlockState(pos.down());
		return (blockstate.isIn(Blocks.STONE) || blockstate.isIn(Blocks.SNOW) || blockstate.isIn(Blocks.AIR)) && worldIn.getLightSubtracted(pos, 0) > 8;
	}

	@Override
	protected boolean makeFlySound() {
		return true;
	}

	public boolean isNotColliding(IWorldReader worldIn) {
		return worldIn.checkNoEntityCollision(this);
	}

	public int getMaxSpawnedInChunk() {
		return 1;
	}

	@Override
	protected int getExperiencePoints(PlayerEntity player) {
		return 3;
	}

	@Override
	public boolean canBeLeashedTo(PlayerEntity player) {
		return true;
	}

	public boolean isFlying() {
		return this.dataManager.get(FLYING);
	}

	public void setFlying(boolean flying) {
		this.dataManager.set(FLYING, flying);
		if (flying)
		{
			if (liftOff()) navigator = new FlyerPathNavigator(this);
		}
		else navigator = new GroundPathNavigator(this, world);
	}

	public void setHome(BlockPos position) {
		this.dataManager.set(HOME_POS, position);
	}

	public BlockPos getHome() {
		return this.dataManager.get(HOME_POS);
	}

	public boolean isGoingHome() {
		return this.dataManager.get(GOING_HOME);
	}

	public void setGoingHome(boolean isGoingHome) {
		this.dataManager.set(GOING_HOME, isGoingHome);
	}

	private void setTravelPos(BlockPos position) {
		this.dataManager.set(TRAVEL_POS, position);
	}

	private BlockPos getTravelPos() {
		return this.dataManager.get(TRAVEL_POS);
	}

	private boolean isTravelling() {
		return this.dataManager.get(TRAVELLING);
	}

	private void setTravelling(boolean isTravelling) {
		this.dataManager.set(TRAVELLING, isTravelling);
	}

	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
	}

	@Nullable
	protected ResourceLocation getLootTable() {
        switch (this.getVariant()) {
            case 0:
                return PURPLE_LOOT;
            case 1:
                return BLUE_LOOT;
            case 2:
                return PINK_LOOT;
            case 3:
                return WHITE_LOOT;
            case 4:
                return LIGHT_BLUE_LOOT;
            case 5:
                return VOILET_LOOT;
        }
        return null;
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
		this.dataManager.register(FLYING, false);
		this.dataManager.register(SITTING, false);
		this.dataManager.register(HOME_POS, BlockPos.ZERO);
		this.dataManager.register(TRAVEL_POS, BlockPos.ZERO);
		this.dataManager.register(GOING_HOME, false);
		this.dataManager.register(TRAVELLING, false);
	}

	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == ModItems.JEWELED_EGG.get() && isTamed();
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

	public boolean isSitting() {
		return this.dataManager.get(SITTING);
	}

	//sit bitch and dont let me have to tell you again
	public void setSitting(boolean sitting) {
		this.dataManager.set(SITTING, sitting);
	}

	@Override
	protected void jump()
	{
		super.jump();
	}


	public void clearAI()
	{
		isJumping = false;
		navigator.clearPath();
		setAttackTarget(null);
		setMoveForward(0);
		setMoveVertical(0);
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("Variant", this.getVariant());
		compound.putBoolean("Biteing", this.isBiteing());
		compound.putBoolean("Sitting", this.isSitting());
		compound.putInt("HomePosX", this.getHome().getX());
		compound.putInt("HomePosY", this.getHome().getY());
		compound.putInt("HomePosZ", this.getHome().getZ());
		compound.putInt("TravelPosX", this.getTravelPos().getX());
		compound.putInt("TravelPosY", this.getTravelPos().getY());
		compound.putInt("TravelPosZ", this.getTravelPos().getZ());
	}

	public void readAdditional(CompoundNBT compound) {
		int i = compound.getInt("HomePosX");
		int j = compound.getInt("HomePosY");
		int k = compound.getInt("HomePosZ");
		this.setHome(new BlockPos(i, j, k));
		super.readAdditional(compound);
		this.setVariant(compound.getInt("Variant"));
		this.setBiteing(compound.getBoolean("Biteing"));
		this.setFlying(compound.getBoolean("Flying"));
		this.setSitting(compound.getBoolean("Sitting"));
		int l = compound.getInt("TravelPosX");
		int i1 = compound.getInt("TravelPosY");
		int j1 = compound.getInt("TravelPosZ");
		this.setTravelPos(new BlockPos(l, i1, j1));
	}

	@Nullable
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		this.setHome(this.getPosition());
		this.setTravelPos(BlockPos.ZERO);
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
		if (this.isSitting()) {
			return false;

		} else {
			return super.attackEntityFrom(source, amount);
		}
	}

	public boolean attackEntityAsMob(Entity entityIn) {
		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this),
				(float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
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
			} else if (target instanceof PlayerEntity && owner instanceof PlayerEntity
					&& !((PlayerEntity) owner).canAttackPlayer((PlayerEntity) target)) {
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

	public PlayerEntity getRidingPlayer()
	{
		Entity entity = getControllingPassenger();
		if (entity instanceof PlayerEntity) return (PlayerEntity) entity;
		else return null;
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
		if (!isTamed() && item == ModItems.JEWELED_EGG.get()) {
			this.consumeItemFromStack(player, itemstack);
			this.playSound(SoundEvents.ENTITY_GENERIC_EAT, this.getSoundVolume(), this.getSoundPitch());
			if (getRNG().nextInt(10) == 0) {
				this.setTamedBy(player);
				this.world.setEntityState(this, (byte) 7);
			} else {
				this.world.setEntityState(this, (byte) 6);
			}
			return ActionResultType.SUCCESS;
		}

		if (isTamed() && item == ModItems.JEWELED_EGG.get() && this.getHealth() < this.getMaxHealth()) {
			this.heal(5);
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
			} else {
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

	static class FlyingMoveController extends MovementController {
		private final CrystalWyvernEntity parentEntity;
		private int courseChangeCooldown;

		public FlyingMoveController(CrystalWyvernEntity wyvern) {
			super(wyvern);
			this.parentEntity = wyvern;
		}

		public void tick() {
			if (this.action == MovementController.Action.MOVE_TO) {
				if (this.courseChangeCooldown-- <= 0) {
					this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
					Vector3d Vector3d = new Vector3d(this.posX - this.parentEntity.getPosX(),
							this.posY - this.parentEntity.getPosY(), this.posZ - this.parentEntity.getPosZ());
					double d0 = Vector3d.length();
					Vector3d = Vector3d.normalize();
					if (this.func_220673_a(Vector3d, MathHelper.ceil(d0))) {
						this.parentEntity.setMotion(this.parentEntity.getMotion().add(Vector3d.scale(0.1D)));
					} else {
						this.action = MovementController.Action.WAIT;
					}
				}

			}
		}

		private boolean func_220673_a(Vector3d p_220673_1_, int p_220673_2_) {
			AxisAlignedBB axisalignedbb = this.parentEntity.getBoundingBox();

			for (int i = 1; i < p_220673_2_; ++i) {
				axisalignedbb = axisalignedbb.offset(p_220673_1_);
				if (!this.parentEntity.world.hasNoCollisions(this.parentEntity, axisalignedbb)) {
					return false;
				}
			}

			return true;
		}
	}
	static class FlyerPathNavigator extends FlyingPathNavigator {

		public FlyerPathNavigator(CrystalWyvernEntity entity) {
			super(entity, entity.world);
		}

		@Override
		@SuppressWarnings("ConstantConditions") // IT CAN BE NULL DAMNIT
		public void tick()
		{
			if (!noPath() && canNavigate())
			{
				CrystalWyvernEntity wyvern = ((CrystalWyvernEntity) entity);
				BlockPos target = getTargetPos();
				if (target != null)
				{
					entity.getMoveHelper().setMoveTo(target.getX(), target.getY(), target.getZ(), speed);
					maxDistanceToWaypoint = entity.getWidth() * entity.getWidth() * wyvern.getYawRotationSpeed() * wyvern.getYawRotationSpeed();
					Vector3d entityPos = getEntityPosition();
					if (target.distanceSq(entityPos.x, entityPos.y, entityPos.z, true) <= maxDistanceToWaypoint)
						currentPath = null;
				}
			}
		}

		@Override
		public boolean canEntityStandOnPos(BlockPos pos)
		{
			return true;
		}
	}

	static class AIRandomFly extends Goal {
		private final CrystalWyvernEntity parentEntity;

		public AIRandomFly(CrystalWyvernEntity wyvern) {
			this.parentEntity = wyvern;
			this.setMutexFlags(EnumSet.of(Flag.MOVE));
		}

		public boolean shouldExecute() {
			MovementController entitymovehelper = this.parentEntity.getMoveHelper();

			if (parentEntity.getAttackTarget() != null)
				return false;

			if (!entitymovehelper.isUpdating()) {
				return true;
			} else {
				double d0 = entitymovehelper.getX() - this.parentEntity.getPosX();
				double d1 = entitymovehelper.getY() - this.parentEntity.getPosY();
				double d2 = entitymovehelper.getZ() - this.parentEntity.getPosZ();
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				return d3 < 1.0D || d3 > 3600.0D;
			}
		}

		public boolean shouldContinueExecuting() {
			return false;
		}

		public void startExecuting() {
			Random random = this.parentEntity.getRNG();
			double d0 = this.parentEntity.getPosX() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 24.0F);
			double d1 = this.parentEntity.getPosY() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d2 = this.parentEntity.getPosZ() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 24.0F);
			this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2,
					parentEntity.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
		}
	}

	static class WyvernFindHomeGoal extends Goal {
		private final CrystalWyvernEntity wyvern;
		private final double speed;
		private boolean field_203129_c;
		private int field_203130_d;

		WyvernFindHomeGoal(CrystalWyvernEntity wyvern, double speedIn) {
			this.wyvern = wyvern;
			this.speed = speedIn;
		}

		public boolean shouldExecute() {
			if (this.wyvern.isChild()) {
				return false;
			} else if (this.wyvern.getRNG().nextInt(200) != 0) {
				return false;
			} else {
				return !this.wyvern.getHome().withinDistance(this.wyvern.getPositionVec(), 64.0D);
			}
		}

		public void startExecuting() {
			this.wyvern.setGoingHome(true);
			this.field_203129_c = false;
			this.field_203130_d = 0;
		}

		public void resetTask() {
			this.wyvern.setGoingHome(false);
		}

		public boolean shouldContinueExecuting() {
			return !this.wyvern.getHome().withinDistance(this.wyvern.getPositionVec(), 7.0D) && !this.field_203129_c && this.field_203130_d <= 600;
		}

		public void tick() {
			BlockPos blockpos = this.wyvern.getHome();
			boolean flag = blockpos.withinDistance(this.wyvern.getPositionVec(), 16.0D);
			if (flag) {
				++this.field_203130_d;
			}

			if (this.wyvern.getNavigator().noPath()) {
				Vector3d vector3d = Vector3d.copyCenteredHorizontally(blockpos);
				Vector3d vector3d1 = RandomPositionGenerator.findRandomTargetTowardsScaled(this.wyvern, 16, 3, vector3d, (double)((float)Math.PI / 10F));
				if (vector3d1 == null) {
					vector3d1 = RandomPositionGenerator.findRandomTargetBlockTowards(this.wyvern, 8, 7, vector3d);
				}

				if (vector3d1 != null && !flag && !this.wyvern.world.getBlockState(new BlockPos(vector3d1)).isIn(Blocks.AIR)) {
					vector3d1 = RandomPositionGenerator.findRandomTargetBlockTowards(this.wyvern, 16, 5, vector3d);
				}

				if (vector3d1 == null) {
					this.field_203129_c = true;
					return;
				}

				this.wyvern.getNavigator().tryMoveToXYZ(vector3d1.x, vector3d1.y, vector3d1.z, this.speed);
			}

		}
	}

	static class WyvernTravelGoal extends Goal {
		private final CrystalWyvernEntity wyvern;
		private final double speed;
		private boolean field_203139_c;

		WyvernTravelGoal(CrystalWyvernEntity wyvern, double speedIn) {
			this.wyvern = wyvern;
			this.speed = speedIn;
		}

		public boolean shouldExecute() {
			return !this.wyvern.isGoingHome() && this.wyvern.isFlying();
		}

		public void startExecuting() {
			int i = 512;
			int j = 4;
			Random random = this.wyvern.rand;
			int k = random.nextInt(1025) - 512;
			int l = random.nextInt(9) - 4;
			int i1 = random.nextInt(1025) - 512;
			if ((double)l + this.wyvern.getPosY() > (double)(this.wyvern.world.getHeight() - 1)) {
				l = 0;
			}

			BlockPos blockpos = new BlockPos((double)k + this.wyvern.getPosX(), (double)l + this.wyvern.getPosY(), (double)i1 + this.wyvern.getPosZ());
			this.wyvern.setTravelPos(blockpos);
			this.wyvern.setTravelling(true);
			this.field_203139_c = false;
		}

		public void tick() {
			if (this.wyvern.getNavigator().noPath()) {
				Vector3d vector3d = Vector3d.copyCenteredHorizontally(this.wyvern.getTravelPos());
				Vector3d vector3d1 = RandomPositionGenerator.findRandomTargetTowardsScaled(this.wyvern, 16, 3, vector3d, (double)((float)Math.PI / 10F));
				if (vector3d1 == null) {
					vector3d1 = RandomPositionGenerator.findRandomTargetBlockTowards(this.wyvern, 8, 7, vector3d);
				}

				if (vector3d1 != null) {
					int i = MathHelper.floor(vector3d1.x);
					int j = MathHelper.floor(vector3d1.z);
					int k = 34;
					if (!this.wyvern.world.isAreaLoaded(i - 34, 0, j - 34, i + 34, 0, j + 34)) {
						vector3d1 = null;
					}
				}

				if (vector3d1 == null) {
					this.field_203139_c = true;
					return;
				}

				this.wyvern.getNavigator().tryMoveToXYZ(vector3d1.x, vector3d1.y, vector3d1.z, this.speed);
			}

		}

		public boolean shouldContinueExecuting() {
			return !this.wyvern.getNavigator().noPath() && !this.field_203139_c && !this.wyvern.isGoingHome() && !this.wyvern.isInLove();
		}

		public void resetTask() {
			this.wyvern.setTravelling(false);
			super.resetTask();
		}
	}


	@Override
	@SuppressWarnings("ConstantConditions")
	public void travel(Vector3d vec3d) {
		float speed = getTravelSpeed();

		if (canPassengerSteer()) // Were being controlled; override ai movement
		{
			LivingEntity entity = (LivingEntity) getControllingPassenger();
			double moveY = vec3d.y;
			double moveX = entity.moveStrafing * 0.5;
			double moveZ = entity.moveForward;

			// rotate head to match driver. rotationYaw is handled relative to this.
			rotationYawHead = entity.rotationYawHead;
			rotationPitch = entity.rotationPitch * 0.5f;

			if (isFlying())
			{
				if (entity.moveForward != 0) moveY = entity.getLookVec().y * speed * 18;
				moveX = vec3d.x;

			}
			else
			{
				speed *= 0.35f;
				if (isJumping && canFly()) setFlying(true);

			}

			setAIMoveSpeed(speed);
			vec3d = new Vector3d(moveX, moveY, moveZ);
		}

		if (isFlying())
		{
			// Move relative to rotationYaw - handled in the move controller or by the passenger
			moveRelative(speed, vec3d);
			move(MoverType.SELF, getMotion());
			setMotion(getMotion().scale(0.88f));

			// hover in place
			Vector3d motion = getMotion();
			if (motion.length() < 0.04f) setMotion(motion.add(0, Math.cos(ticksExisted * 0.1f) * 0.02f, 0));

			return;
		}
		super.travel(vec3d);
	}

	public float getTravelSpeed()
	{
		//@formatter:off
		return isFlying()? (float) getAttributeValue(FLYING_SPEED) : (float) getAttributeValue(MOVEMENT_SPEED);
		//@formatter:on
	}

	public boolean shouldFly()
	{
		return canFly() && getAltitude() > getFlightThreshold();
	}

	public boolean canFly()
	{
		return !isChild() && !canSwim() && !getLeashed();
	}

	public void setRotation(float yaw, float pitch)
	{
		this.rotationYaw = yaw % 360.0F;
		this.rotationPitch = pitch % 360.0F;
	}

	public double getAltitude()
	{
		BlockPos.Mutable pos = getPosition().toMutable();

		// cap to the world void (y = 0)
		while (pos.getY() > 0 && !world.getBlockState(pos.down()).getMaterial().isSolid()) pos.move(0, -1, 0);
		return getPosY() - pos.getY();
	}

	public int getYawRotationSpeed()
	{
		return isFlying()? 6 : 75;
	}
	@Override
	public boolean isOnLadder()
	{
		return false;
	}

	@Override
	protected float getJumpUpwardsMotion()
	{
		if (canFly()) return (getHeight() * getJumpFactor()) * 0.6f;
		else return super.getJumpUpwardsMotion();
	}

	public boolean liftOff()
	{
		if (!canFly()) return false;
		if (!onGround) return true; // We can't lift off the ground in the air...

		int heightDiff = world.getHeight(Heightmap.Type.MOTION_BLOCKING, (int) getPosX(), (int) getPosZ()) - (int) getPosY();
		if (heightDiff > 0 && heightDiff <= getFlightThreshold())
			return false; // position has too low of a ceiling, can't fly here.

		setSitting(false);
		jump();
		return true;
	}

	@Override // Disable fall calculations if we can fly (fall damage etc.)
	public boolean onLivingFall(float distance, float damageMultiplier)
	{
		if (canFly()) return false;
		return super.onLivingFall(distance - (int) (getHeight() * 0.8), damageMultiplier);
	}

	public int getFlightThreshold()
	{
		return (int) getHeight();
	}
}