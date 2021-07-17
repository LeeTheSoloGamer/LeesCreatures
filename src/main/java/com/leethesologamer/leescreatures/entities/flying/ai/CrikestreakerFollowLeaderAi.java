package com.leethesologamer.leescreatures.entities.flying.ai;

import com.leethesologamer.leescreatures.LeesCreatures;
import com.leethesologamer.leescreatures.entitiesOLD.CrestedCrikestreakerEntity;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.vector.Vector3d;

import javax.swing.text.html.parser.Entity;
import java.util.EnumSet;
import java.util.List;

public class CrikestreakerFollowLeaderAi extends Goal {
    public final CrestedCrikestreakerEntity crikestreaker;
    private double speedModifier;
    private int distCheckCounter;

    public CrikestreakerFollowLeaderAi(CrestedCrikestreakerEntity llamaIn, double speedModifierIn) {
        this.crikestreaker = llamaIn;
        this.speedModifier = speedModifierIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean shouldExecute() {
        if (!this.crikestreaker.isLeader() && !this.crikestreaker.inCaravan()) {
            double dist = 15D;
            List<CrestedCrikestreakerEntity> list = crikestreaker.world.getEntitiesWithinAABB(CrestedCrikestreakerEntity.class, crikestreaker.getBoundingBox().grow(dist, dist / 2, dist));
            CrestedCrikestreakerEntity crikestreaker = null;
            double d0 = Double.MAX_VALUE;

            for (CrestedCrikestreakerEntity entity : list) {
                CrestedCrikestreakerEntity crikestreaker1 = (CrestedCrikestreakerEntity) entity;
                if (crikestreaker1.inCaravan() && !crikestreaker1.hasCaravanTrail()) {
                    double d1 = this.crikestreaker.getDistanceSq(crikestreaker1);
                    if (!(d1 > d0)) {
                        d0 = d1;
                        crikestreaker = crikestreaker1;
                    }
                }
            }

            if (crikestreaker == null) {
                for (CrestedCrikestreakerEntity entity1 : list) {
                    CrestedCrikestreakerEntity llamaentity2 = (CrestedCrikestreakerEntity) entity1;
                    if (llamaentity2.isLeader() && !llamaentity2.hasCaravanTrail()) {
                        double d2 = this.crikestreaker.getDistanceSq(llamaentity2);
                        if (!(d2 > d0)) {
                            d0 = d2;
                            crikestreaker = llamaentity2;
                        }
                    }
                }
            }

            if (crikestreaker == null) {
                return false;
            } else if (d0 < 2.0D) {
                return false;
            } else if (!crikestreaker.isLeader() && !this.firstIsLeader(crikestreaker, 1)) {
                return false;
            } else {
                this.crikestreaker.joinCaravan(crikestreaker);
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        if (this.crikestreaker.inCaravan() && this.crikestreaker.getCaravanHead().isAlive() && this.firstIsLeader(this.crikestreaker, 0)) {
            double d0 = this.crikestreaker.getDistanceSq(this.crikestreaker.getCaravanHead());
            if (d0 > 676.0D) {
                if (this.speedModifier <= 1.5D) {
                    this.speedModifier *= 1.2D;
                    this.distCheckCounter = 40;
                    return true;
                }

                if (this.distCheckCounter == 0) {
                    return false;
                }
            }

            if (this.distCheckCounter > 0) {
                --this.distCheckCounter;
            }

            return true;
        } else {
            return false;
        }
    }

    public void resetTask() {
        this.crikestreaker.leaveCaravan();
        this.speedModifier = 1.5D;
    }

    public void tick() {
        if (this.crikestreaker.inCaravan()) {
            CrestedCrikestreakerEntity llamaentity = this.crikestreaker.getCaravanHead();
            if (llamaentity != null) {
                double d0 = this.crikestreaker.getDistance(llamaentity);
                Vector3d vector3d = (new Vector3d(llamaentity.getPosX() - this.crikestreaker.getPosX(), llamaentity.getPosY() - this.crikestreaker.getPosY(), llamaentity.getPosZ() - this.crikestreaker.getPosZ())).normalize().scale(Math.max(d0 - 2.0D, 0.0D));
                if(crikestreaker.getNavigator().noPath()) {
                    try {
                        this.crikestreaker.getNavigator().tryMoveToXYZ(this.crikestreaker.getPosX() + vector3d.x, this.crikestreaker.getPosY() + vector3d.y, this.crikestreaker.getPosZ() + vector3d.z, this.speedModifier);
                    } catch (NullPointerException e) {
                        LeesCreatures.LOGGER.warn("gorilla encountered issue following caravan head");
                    }
                }
            }
        }
    }

    private boolean firstIsLeader(CrestedCrikestreakerEntity llama, int p_190858_2_) {
        if (p_190858_2_ > 8) {
            return false;
        } else if (llama.inCaravan()) {
            if (llama.getCaravanHead().isLeader()) {
                return true;
            } else {
                CrestedCrikestreakerEntity llamaentity = llama.getCaravanHead();
                ++p_190858_2_;
                return this.firstIsLeader(llamaentity, p_190858_2_);
            }
        } else {
            return false;
        }
    }
}