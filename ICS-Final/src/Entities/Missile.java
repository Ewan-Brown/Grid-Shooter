package Entities;

import java.awt.Point;
import java.util.ArrayList;

import main.Game;
import tools.GameMath;

public class Missile extends Projectile{
	public double missileSpeed = 0.06;
	public double missileTurnSpeed = 3;
	public Missile(double x, double y,double angle,Point[] points, int damage) {
		super(x, y,0,0,points, damage);
		maxHealth = 1000;
		health = maxHealth;
		this.realAngle = angle;
	}

	public void update(){
		super.update();
		health--;

		ArrayList<Entity> eList = Game.entityArray;
		Entity t = null;
		double lastDist = 0;
		for(int i = 0; i < eList.size();i++){
			Entity e = eList.get(i);
			if(e instanceof Projectile || e.team == this.team){
				continue;
			}
			if(GameMath.areColliding(e, this)){
				this.onCollide(e);
				return;
			}
			double newDist = GameMath.getDistance(this, e);
			double targetAngle = Math.toDegrees(getTargetAngle(e));
			double a = targetAngle - realAngle;
			a = a % 360;
			if(a < 0){
				a += 360;
			}
			double diff = 180 - Math.abs(a - 180);	
			if(diff > 90){
				continue;
			}
			if(t == null || newDist < lastDist){
				t = e;
				lastDist = newDist;
				continue;
			}
			

		}
		double f = 1;
		if(t != null){
			double targetAngle = Math.toDegrees(getTargetAngle(t));
			double a = targetAngle - realAngle;
			a = a % 360;
			if(a < 0){
				a += 360;
			}
			double diff = Math.abs(180 - a);
			f = missileTurnSpeed - (missileTurnSpeed / 2) * (diff / 180D);
			if(a > 180){
				f = -f;
			}
			if(health > 200){
				realAngle += f;
			}
			f /= missileTurnSpeed;
			f = Math.abs(f);

		}
		double dX = Math.cos(Math.toRadians(realAngle)) * missileSpeed * f;
		double dY = Math.sin(Math.toRadians(realAngle)) * missileSpeed * f;
		xSpeed += dX;
		ySpeed += dY;
		xSpeed -= xSpeed / 100D;
		ySpeed -= ySpeed / 100D;
	}
}
