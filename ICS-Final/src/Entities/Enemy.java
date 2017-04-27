package Entities;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import main.Game;
import tools.GameMath;

public class Enemy extends Ship{
	//Strafe timer to go left or right instead of just randomly bouncing back and forward vibrating
	int strafeCount = 100;
	//Chance to switch strafe direction
	static final double STRAFE_CHANCE = 50;
	//Strafe direction, 1 is right
	double strafe = 1;
	//Target must be within these distances to strafe and begin circling, if too far go forward , if too close go back
	static final int maxDist = 300;
	static final int minDist = 150;
	Entity target = null;
	public static Random rand = new Random();
	{
		bullet = new Point[4];
		bullet[0] = new Point(10,2);
		bullet[1] = new Point(2,4);
		bullet[2] = new Point(0,2);
		bullet[3] = new Point(2,0);
		maxHealth = 40;
		health = maxHealth;
	}
	public Enemy(double x, double y,Point[] points,Point[] turrets,Point[] missileTurrets) {
		super(x, y,points,turrets,missileTurrets);
		team = Game.ENEMY_TEAM;
		caliber = 1;
	}
	public void strafe(double t){
		double dX = (Math.cos(Math.toRadians(realAngle + 90)))*speed*t;
		double dY = (Math.sin(Math.toRadians(realAngle + 90)))*speed*t;
		this.xSpeed += dX;
		this.ySpeed += dY;
		if(strafeParticleCooldown < 0){
			fumes(t, realAngle + 90);
			strafeParticleCooldown = MAX_PARTICLE_COOLDOWN;
		}
	}
	public void thrust(double t){
		double dX = (Math.cos(Math.toRadians(realAngle)))*speed*t;
		double dY = (Math.sin(Math.toRadians(realAngle)))*speed*t;
		this.xSpeed += dX;
		this.ySpeed += dY;
		if(thrustParticleCooldown < 0){
			fumes(t, realAngle);
			thrustParticleCooldown = MAX_PARTICLE_COOLDOWN;
		}
	}
	public void update(){
		super.update();
		updateTarget(Game.entityArray);
		Double targetAngle = getTargetAngle();
		if(targetAngle != null){
			turnToTarget(targetAngle);
			moveToTarget(targetAngle);
		}
	}
	
	public void moveToTarget(double targetAngle){
		//If there is no target, quit method
		if(target == null){
			return;
		}
		//Get the difference between this ship's real angle and the direction it needs to go in
		double diffAngle = Math.abs((targetAngle - realAngle) % 360);
		if(diffAngle > 180){
			diffAngle -= 360;
		}
		double d = GameMath.getDistance(this, target); 
		double tempStrafe = (rand.nextDouble() - 0.5) / 4;
		//If the target is too far away, thrust forward, if too close thrust backwards. Strafe at the same time to simulate dodging
		if(d > maxDist){
			thrust(1);
			strafe(tempStrafe * 5);
		}
		else if(d < minDist){
			//If target is in shooting range, shoot
			if(diffAngle < 20){
				shootBullet();
			}
			thrust(-1);
			strafe(tempStrafe * 2);
		}
		//If target is at good distance, attempt to shoot and then strafe to simulate dodging/circling
		else{
			//If target is in shooting range, shoot
			if(diffAngle < 20){
				shootBullet();
			}
			strafeCount--;
			if(strafeCount > 0){
				strafe(strafe);
			}
			else if(rand.nextInt() < STRAFE_CHANCE){
				strafeCount = rand.nextInt(100);
				if(rand.nextBoolean()){
					strafe = -1;
				}
				else{
					strafe = 1;
				}
			}
		}
	}
	public void updateTarget(ArrayList<Entity> array){
		target = null;
		double dist = 0;
		double prevDist = Double.MAX_VALUE;
		for(int i = 0; i < array.size();i++){
			Entity e = array.get(i);
			if(e != this && e.team != this.team && e instanceof Ship){
				dist = GameMath.getDistance(this, e);
				if(dist < prevDist){
					target = array.get(i);
					prevDist = dist;
				}
			}
		}
	}
	public Double getTargetAngle(){
			if(target != null){
				return Math.toDegrees(getTargetAngle(target));
			}
			return null;
	}

}
