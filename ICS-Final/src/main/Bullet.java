package main;

import java.awt.Point;

public class Bullet extends Projectile{
	{
		points = new Point[4];
		points[0] = new Point(10,2);
		points[1] = new Point(2,4);
		points[2] = new Point(0,2);
		points[3] = new Point(2,0);
		tempPoints = new Point[points.length];
		for(int i = 0; i < points.length;i++){
			tempPoints[i] = new Point(0,0);
		}
	}
	public Bullet(double x, double y, double angle,double speed, double damage) {
		super(x, y, 0,0);
		this.damage = damage;
		realAngle = angle;
		double dX = (Math.cos(Math.toRadians(realAngle)))*speed;
		double dY = (Math.sin(Math.toRadians(realAngle)))*speed;
		this.dX += dX;
		this.dY += dY;
	}
}
