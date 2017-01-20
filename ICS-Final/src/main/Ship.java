package main;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Ship extends Entity{

	double turnSpeed = 0.4;
	double speed = 0.1;
	double strafeSpeed = 0.1;
	int cooldown = 0;
	int MAX_COOLDOWN = 10;
	double muzzleVelocity = 10;
	double maxCount = 5;
	double accuracy = 10;
	double thrustParticleTimer = maxCount;
	double strafeParticleTimer = maxCount;
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
		turrets[0] = new Point(points[0]);
		turrets[0].x -= 20;
		turrets[0].y -= 2;
	}
	public Ship(double x, double y) {
		super(x, y,0,0);
	}
	public void thrust(double t){
		double dX = (Math.cos(Math.toRadians(realAngle)))*speed*t;
		double dY = (Math.sin(Math.toRadians(realAngle)))*speed*t;
		this.dX += dX;
		this.dY += dY;
		if(thrustParticleTimer < 0){
			double angle2 = realAngle + ((Math.random() - 0.5) * 70);
			double dX2 = (Math.cos(Math.toRadians(angle2)))*speed*t*10;
			double dY2 = (Math.sin(Math.toRadians(angle2)))*speed*t*10;
			Point2D p = getCenter();
			Game.effects.add(new Particle(p.getX() + x,p.getY() + y,-dX2,-dY2));
			thrustParticleTimer = maxCount;
		}
	}
	public void move(double t,double angle){
		double dX = (Math.cos(Math.toRadians(realAngle + angle)))*speed*t;
		double dY = (Math.sin(Math.toRadians(realAngle + angle)))*speed*t;
		this.dX += dX;
		this.dY += dY;
		if(thrustParticleTimer < 0){
			double angle2 = angle + realAngle + ((Math.random() - 0.5) * 70);
			double dX2 = (Math.cos(Math.toRadians(angle2)))*speed*t*10;
			double dY2 = (Math.sin(Math.toRadians(angle2)))*speed*t*10;
			Point2D p = getCenter();
			Game.effects.add(new Particle(p.getX() + x,p.getY() + y,-dX2,-dY2));
			thrustParticleTimer = maxCount;
		}
	}
	public void turn(double t){
		turnMomentum += turnSpeed * t;
	}
	public void update(){
		super.update();
		cooldown--;
		thrustParticleTimer--;
		strafeParticleTimer--;
		dX -= dX / 100D;
		dY -= dY / 100D;
	}
	public void shoot(){
		ArrayList<Bullet> bA = new ArrayList<Bullet>(turrets.length);
		if(cooldown < 0){
			cooldown = MAX_COOLDOWN;
			Point[] p = getTurrets();
			for(int i = 0; i < p.length;i++){
				double x = p[i].x + this.x;
				double y = p[i].y + this.y;
				Bullet b = new Bullet(x,y,realAngle + (rand.nextDouble() - 0.5) * accuracy,muzzleVelocity,this.caliber);
				b.team = this.team;
				bA.add(b);
			}
			Game.entities.addAll(bA);
		}
	}
	public Point[] getTurrets(){
		Point[] tempPoints = new Point[turrets.length];
		for(int i = 0; i < turrets.length;i++){
			tempPoints[i] = new Point(0,0);
		}
		Point2D p = getCenter();
		AffineTransform.getRotateInstance
		(Math.toRadians(realAngle), p.getX(), p.getY())
		.transform(turrets,0,tempPoints,0,turrets.length);
		return tempPoints;
	}
	public void strafe(double t){
		double dX = (Math.cos(Math.toRadians(realAngle + 90)))*speed*t;
		double dY = (Math.sin(Math.toRadians(realAngle + 90)))*speed*t;
		this.dX += dX;
		this.dY += dY;
		if(strafeParticleTimer < 0){
			double angle2 = realAngle + ((Math.random() - 0.5) * 20);
			double dX2 = (Math.cos(Math.toRadians(angle2 + 90)))*speed*t*10;
			double dY2 = (Math.sin(Math.toRadians(angle2 + 90)))*speed*t*10;
			Point2D p = getCenter();
			Game.effects.add(new Particle(p.getX() + x,p.getY() + y,-dX2,-dY2));
			strafeParticleTimer = maxCount;
		}
	}
	public void turnToTarget(double targetAngle){
		double a = targetAngle - realAngle;
		a = a % 360;
		if(a < 0){
			a += 360;
		}
		if(a < 180){
			turn(1);
		}
		else if (a > 180){
			turn(-1);
		}
	}
}
