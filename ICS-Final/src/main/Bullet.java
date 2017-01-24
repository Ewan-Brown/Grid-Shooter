package main;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Bullet extends Projectile{
	public void update(){
		super.update();
		for(int i = 0 ; i < Game.entities.size();i++){
			Entity e = Game.entities.get(i);
			if(e == this || e.team == this.team){
				continue;
			}
			if(e instanceof Bullet){
				continue;
			}
			Point2D p = centerPoint;
			double x1 = p.getX() + xPos;
			double y1 = p.getY() + yPos;
			if(GameMath.areColliding(this, e)){
				onCollide(e);
			}
		}
		health--;
	}
	public void onCollide(Entity e){
		super.onCollide(e);
		this.health = 0;
	}
	public Bullet(double x, double y, double angle,double speed, int damage,Point[] points) {
		super(x, y, 0,0,points,damage);
		this.damage = damage;
		realAngle = angle;
		double dX = (Math.cos(Math.toRadians(realAngle)))*speed;
		double dY = (Math.sin(Math.toRadians(realAngle)))*speed;
		this.xSpeed += dX;
		this.ySpeed += dY;
	}

}
