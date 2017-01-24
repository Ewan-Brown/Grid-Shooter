package main;

import java.awt.Point;

public class Projectile extends Entity{
	double damage;
	public Projectile(double x, double y, double dX, double dY,Point[] points,int damage) {
		super(x, y, dX, dY,points);
		this.damage = damage;
	}
	public void onCollide(Entity e){
		if(e.team != this.team){
			e.health -= damage;
		}
	}

}
