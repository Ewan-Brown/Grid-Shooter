package main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Ship{
	int strafeCount = 100;
	double strafeChance = 50;
	double strafe = 1;
	int maxDist = 300;
	int minDist = 150;
	Entity target = null;
	public static Random rand = new Random();
	public Enemy(double x, double y,Point[] points,Point[] turrets) {
		super(x, y,points,turrets);
		team = Game.ENEMY_TEAM;
	}
	public void update(){
		super.update();
		updateTarget(Game.entities);
		Double targetAngle = getTargetAngle();
		if(targetAngle != null){
			turnToTarget(targetAngle);
			moveToTarget(targetAngle);
		}
	}
	public void moveToTarget(double targetAngle){
		if(target == null){
			return;
		}
		double diffAngle = Math.abs((targetAngle - realAngle) % 360);
		if(diffAngle > 180){
			diffAngle -= 360;
		}
		double d = GameMath.getDistance(this, target); 
		double tempStrafe = (rand.nextDouble() - 0.5) / 4;
		if(d > maxDist){
			thrust(1);
			strafe(tempStrafe * 5);
		}
		else if(d < minDist){
			if(diffAngle < 20){
				shoot();
			}
			thrust(-1);
			strafe(tempStrafe * 2);
		}
		else{
			if(diffAngle < 20){
				shoot();
			}
			strafeCount--;
			if(strafeCount > 0){
				strafe(strafe);
			}
			else if(rand.nextInt() < strafeChance){
				strafeCount = rand.nextInt(100);
				if(rand.nextBoolean()){
					strafe = -1;
				}
				else{
					strafe = 1;
				}
			}
		}
		//		}
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
				double xD = target.xPos - xPos;
				double yD = target.yPos - yPos;
				return Math.toDegrees(Math.atan2(yD, xD));
			}
			return null;
	}

}
