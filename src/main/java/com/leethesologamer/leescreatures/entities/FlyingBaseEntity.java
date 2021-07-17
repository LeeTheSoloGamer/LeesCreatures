package com.leethesologamer.leescreatures.entities;

import com.leethesologamer.leescreatures.entities.controller.FlightController;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class FlyingBaseEntity extends MonsterEntity implements IFlyingAnimal {

	protected BlockPos spawnPos 		     = null;
	protected BlockPos currentFlightTarget   = null;
	
	protected FlightController controller = new FlightController(this, 5);
	
	public FlyingBaseEntity(EntityType<? extends MonsterEntity> p_i48553_1_, World p_i48553_2_) {
		super(p_i48553_1_, p_i48553_2_);		
	}

	@Override
	public void tick() {
		super.tick();
		if (isServerWorld()) {
			controller.update();			
		}
	}
	
	public void setSpawnPos(BlockPos spawnPos) {
		this.spawnPos = spawnPos;
	}
	
	public void setCurrentFlightTarget(BlockPos currentFlightTarget) {
		this.currentFlightTarget = currentFlightTarget;
	}
	
	public BlockPos getSpawnPos() {
		return spawnPos;
	}
	
	public BlockPos getCurrentFlightTarget() {
		return currentFlightTarget;
	}
	
	@Override
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
			ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
		setSpawnPos(getPosition().up(5));
		return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}
	
}
