package main;

import java.awt.Point;

public class Projectile extends Entity{
	double damage;
	public Projectile(double x, double y, double dX, double dY,Point[] points) {
		super(x, y, dX, dY,points);
	}
	public void onCollide(Entity e){
		if(e.team != this.team){
			e.health -= damage;
		}
	}

}
