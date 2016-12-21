package main;

import java.awt.Point;

public class Ship extends Entity{

	double turnSpeed = 5;
	double speed = 0.1;
	double strafeSpeed = 0.1;
	int cooldown = 0;
	int MAX_COOLDOWN = 10;
	double muzzleVelocity = 40;
	int caliber = 10;
	Point[] turrets;
	{
		points = new Point[7];
		points[0] = new Point(24,12);
		points[1] = new Point(6,16);
		points[2] = new Point(16,24);
		points[3] = new Point(0,16);
		points[4] = new Point(0,8);
		points[5] = new Point(16,0);
		points[6] = new Point(8,8);
		tempPoints = new Point[points.length];
		for(int i = 0; i < points.length;i++){
			tempPoints[i] = new Point(0,0);
		}
		turrets = new Point[1];
		turrets[0] = points[0];
	}
	public Ship(double x, double y) {
		super(x, y,0,0);
	}
	public void thrust(double t){
		double dX = (Math.cos(Math.toRadians(realAngle)))*speed*t;
		double dY = (Math.sin(Math.toRadians(realAngle)))*speed*t;
		this.dX += dX;
		this.dY += dY;
	}
	public void turn(double t){
		realAngle += turnSpeed * t;
	}
	public void shoot(){
		Bullet[] bA = new Bullet[turrets.length];
		if(cooldown < 0){
			cooldown = MAX_COOLDOWN;
			Point[] p = turrets;
			for(int i = 0; i < p.length;i++){
				double x = p[i].x + getTrueX();
				double y = p[i].y + getTrueY();
				Bullet b = new Bullet(x,y,realAngle,muzzleVelocity,this.caliber);
				bA[i] = b;
			}
			Game.entities.addAll(bA);
		}
	}
	public void strafe(double t){
		double dX = (Math.cos(Math.toRadians(realAngle + 90)))*speed*t;
		double dY = (Math.sin(Math.toRadians(realAngle + 90)))*speed*t;
		this.dX += dX;
		this.dY += dY;
	}
}
