package entities;

import java.util.ArrayList;

import main.Game;
import tools.GameMath;

public class BasicAI extends ShipAI{
	// Strafe timer to go left or right instead of just randomly bouncing back
		// and forward vibrating
		int strafeCount = 100;
		// Chance to switch strafe direction
		double strafeChance = 50;
		// Strafe direction, 1 is right
		double strafe = 1;
		// Target must be within these distances to strafe and begin circling, 
		// if too far: go forward , if too close: go back
		int maxDist = 300;
		int minDist = 150;
		Entity target = null;
	public BasicAI(Ship parent) {
		super(parent);
	}

	public void update(){
		super.update();
		updateTarget(Game.entityArray);
		Double targetAngle = getTargetAngle();
		if (targetAngle != null) {
			turnToTarget(targetAngle);
			moveToTarget(targetAngle);
		}
	}
	public void moveToTarget(double targetAngle) {
		// If there is no target, quit method
		if (target == null) {
			return;
		}
		// Get the difference between this ship's real angle and the direction
		// it needs to go in
		double diffAngle = Math.abs((targetAngle - parent.realAngle) % 360);
		if (diffAngle > 180) {
			diffAngle -= 360;
		}
		double d = GameMath.getDistance(parent, target);
		double tempStrafe = (parent.rand.nextDouble() - 0.5) / 4;
		// If the target is too far away, thrust forward, if too close thrust
		// backwards. Strafe at the same time to simulate dodging
		if (d > maxDist) {
			parent.thrust(1);
			parent.strafe(tempStrafe * 5);
		} else if (d < minDist) {
			// If target is in shooting range, shoot and thrust backwards!
			if (diffAngle < 20) {
				// XXX Shoot
			}
			parent.thrust(-1);
			parent.strafe(tempStrafe * 2);
		}
		// If target is at good distance, attempt to shoot and then strafe to
		// simulate dodging/circling
		else {
			// If target is in shooting range, shoot
			if (diffAngle < 20) {
				// XXX SHOOT
			}
			strafeCount--;
			if (strafeCount > 0) {
				parent.strafe(strafe);
			} else if (parent.rand.nextInt() < strafeChance) {
				strafeCount = parent.rand.nextInt(100);
				if (parent.rand.nextBoolean()) {
					strafe = -1;
				} else {
					strafe = 1;
				}
			}
		}
	}


	public void updateTarget(ArrayList<Entity> array) {
		target = null;
		double dist = 0;
		double prevDist = Double.MAX_VALUE;
		for (int i = 0; i < array.size(); i++) {
			Entity e = array.get(i);
			if (e != parent && e.team != parent.team && e instanceof Ship && !e.isDead()) {
				dist = GameMath.getDistance(parent, e);
				if (dist < prevDist) {
					target = array.get(i);
					prevDist = dist;
				}
			}
		}
	}

	public Double getTargetAngle() {
		if (target != null) {
			return Math.toDegrees(parent.getTargetAngle(target));
		}
		return null;
	}
	
}
