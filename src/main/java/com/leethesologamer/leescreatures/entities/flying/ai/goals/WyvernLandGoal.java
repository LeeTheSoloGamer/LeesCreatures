package com.leethesologamer.leescreatures.entities.flying.ai.goals;

import java.util.EnumSet;
import java.util.Random;

import com.leethesologamer.leescreatures.entities.CrystalWyvernEntity;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.Heightmap;

public class WyvernLandGoal extends Goal {

	private final CrystalWyvernEntity wyvern;
	private BlockPos landingPos;

	public WyvernLandGoal(CrystalWyvernEntity wyvern) {
		this.wyvern = wyvern;
		setMutexFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.TARGET));

	}

	@Override
    public boolean shouldExecute()
    {
        return wyvern.isElytraFlying() && wyvern.getRidingEntity() == null && findLandingBlock();
    }
	@Override
	public boolean shouldContinueExecuting() {
		return wyvern.isElytraFlying() && wyvern.getRidingEntity() == null && !wyvern.getNavigator().noPath();
	}

	@Override
	public void tick() {
		if (wyvern.getNavigator().noPath())
			startExecuting();
	}

	@Override
	public void startExecuting() {
		if (!wyvern.getNavigator().tryMoveToXYZ(landingPos.getX(), landingPos.getY(), landingPos.getZ(), 1))
			wyvern.getNavigator().tryMoveToXYZ(wyvern.getPosX(), wyvern.getPosY() - 4, wyvern.getPosZ(), 1);
	}

	private boolean findLandingBlock() {
		Random rand = wyvern.getRNG();
		landingPos = wyvern.getPosition();

		int followRange = MathHelper.floor(wyvern.getAttribute(Attributes.FOLLOW_RANGE).getValue());
		int ox = followRange - rand.nextInt(followRange) * 2;
		int oz = followRange - rand.nextInt(followRange) * 2;
		landingPos = landingPos.add(ox, 0, oz);

		landingPos = wyvern.world.getHeight(Heightmap.Type.WORLD_SURFACE, landingPos);

		return wyvern.world.getBlockState(landingPos.down()).getMaterial().isSolid();
	}
}
