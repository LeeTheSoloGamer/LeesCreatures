package com.leethesologamer.leescreatures.entities.controller;

import java.util.Random;

import com.leethesologamer.leescreatures.entities.FlyingBaseEntity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class FlightController {

    protected FlyingBaseEntity entity;
    private World world;
    private Random random;
    private int angleIncreass;
    private float currentAngle = 0;
    
    public FlightController(FlyingBaseEntity entity, int angleIncreass) {
        this.entity         = entity;
        this.world          = entity.world;
        this.random         = world.rand;
        this.angleIncreass  = angleIncreass;
    }

    public void update() {    
    	entity.setNoGravity(true);
    	goFly();    
    }

    private void flyToTarget() {
    	BlockPos target = entity.getCurrentFlightTarget();
        if (target != null) {

            // ====== motion ====== //        	
        	facePos(target, angleIncreass, angleIncreass);
        	entity.setMotion(Vector3d.fromPitchYaw(entity.rotationPitch, entity.rotationYaw).scale(0.55f));

            entity.rotationPitch = (float) (entity.getMotion().y * 10);
        }
    }
    
    public void facePos(BlockPos pos, float maxYawIncrease, float maxPitchIncrease) {
        double d0 = pos.getX() - entity.getPosX();
        double d1 = pos.getY() - entity.getPosY();
        double d2 = pos.getZ() - entity.getPosZ();

        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
        float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
        float f1 = (float)(-(MathHelper.atan2(d1, d3) * (double)(180F / (float)Math.PI)));
        
        entity.rotationPitch = updateRotation(entity, entity.rotationPitch, f1, maxPitchIncrease);
        entity.rotationYaw  = updateRotation(entity, entity.rotationYaw, f, maxYawIncrease);
     }
    
    private float updateRotation(LivingEntity entity, float angle, float targetAngle, float maxIncrease) {
        float f = MathHelper.wrapDegrees(targetAngle - angle);
        if (f > maxIncrease) {
           f = maxIncrease;
        }

        if (f < -maxIncrease) {
           f = -maxIncrease;
        }
        return angle + f;
     }

    private void goFly() {    	
    	
    	if (entity.getSpawnPos() == null) 
    		entity.setSpawnPos(entity.getPosition());
    	
    	if (entity.getCurrentFlightTarget() == null) {
    		int x = random.nextInt(20) - 10;
    		int y = random.nextInt(9) - 4;
    		int z = random.nextInt(20) - 10;
    		
    		entity.setCurrentFlightTarget(entity.getSpawnPos().add(x,y,z));
    	}
    	
    	BlockPos target = entity.getCurrentFlightTarget();
    	
    	if (entity.getDistanceSq(target.getX() + 0.5, target.getY() + 0.5f, target.getZ() + 0.5f) < entity.getWidth() * entity.getWidth()) {
    		entity.setCurrentFlightTarget(null);
    	}
    	
    	if (entity.collidedHorizontally || entity.collidedVertically || random.nextInt(20) == 0) {
    		entity.setCurrentFlightTarget(null);
    	}
    	
    	
        flyToTarget();
    }
	
}
