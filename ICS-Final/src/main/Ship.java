package main;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Ship extends Entity{

	double turnSpeed = 4;
	double speed = 0.03;
	int cooldown = 0;
	int MAX_COOLDOWN = 20;
	double muzzleVelocity = 10;
	double maxCount = 5;
	double accuracy = 10;
	double thrustParticleTimer = maxCount;
	double strafeParticleTimer = maxCount;
	int caliber = 10;
	Point[] bullet;
	{
		bullet = new Point[4];
		bullet[0] = new Point(20,4);
		bullet[1] = new Point(4,8);
		bullet[2] = new Point(0,4);
		bullet[3] = new Point(4,0);
	}
	Point[] particle;
	{
		particle = new Point[4];
		particle[0] = new Point(0,0);
		particle[1] = new Point(4,0);
		particle[2] = new Point(4,4);
		particle[3] = new Point(0,4);
	}
	Point[] turrets;
	public Ship(double x, double y,Point[] points,Point[] turrets) {
		super(x, y,0,0,points);
		this.turrets = turrets;
	}
	public void thrust(double t){
		if(t < 0){
			t = t / 2D;
		}
		double dX = (Math.cos(Math.toRadians(realAngle)))*speed*t;
		double dY = (Math.sin(Math.toRadians(realAngle)))*speed*t;
		this.xSpeed += dX;
		this.ySpeed += dY;
		if(thrustParticleTimer < 0){
			double angle2 = realAngle + ((Math.random() - 0.5) * 70);
			double dX2 = (Math.cos(Math.toRadians(angle2)))*speed*t*10;
			double dY2 = (Math.sin(Math.toRadians(angle2)))*speed*t*10;
			Point2D p = centerPoint;
			Game.effects.add(new Particle(p.getX() + xPos,p.getY() + yPos,-dX2,-dY2,particle));
			thrustParticleTimer = maxCount;
		}
	}
	public void move(double t,double angle){
		if(t < 0){
			t = t / 2D;
		}
		double dX = (Math.cos(Math.toRadians(realAngle + angle)))*speed*t;
		double dY = (Math.sin(Math.toRadians(realAngle + angle)))*speed*t;
		this.xSpeed += dX;
		this.ySpeed += dY;
		if(thrustParticleTimer < 0){
			fumes(t, realAngle);
		}
	}
	public void strafe(double t){
		double dX = (Math.cos(Math.toRadians(realAngle + 90)))*speed*t;
		double dY = (Math.sin(Math.toRadians(realAngle + 90)))*speed*t;
		this.xSpeed += dX;
		this.ySpeed += dY;
		if(strafeParticleTimer < 0){
			fumes(t, realAngle + 90);
		}
	}
	public void fumes(double t, double angle){
		double angle2 = angle + ((Math.random() - 0.5) * 20);
		double dX2 = (Math.cos(Math.toRadians(angle2)))*speed*t*10;
		double dY2 = (Math.sin(Math.toRadians(angle2)))*speed*t*10;
		Point2D p = centerPoint;
		Game.effects.add(new Particle(p.getX() + xPos,p.getY() + yPos,-dX2,-dY2,particle));
		strafeParticleTimer = maxCount;
	}
	public void turn(double t){
		realAngle += turnSpeed * t;
	}
	public void update(){
		super.update();
		cooldown--;
		thrustParticleTimer--;
		strafeParticleTimer--;
		xSpeed -= xSpeed / 100D;
		ySpeed -= ySpeed / 100D;
	}
	public void shoot(){
		ArrayList<Bullet> bA = new ArrayList<Bullet>(turrets.length);
		if(cooldown < 0){
			cooldown = MAX_COOLDOWN;
			Point[] p = getTurrets();
			for(int i = 0; i < p.length;i++){
				double x = p[i].x;
				double y = p[i].y;
				Bullet b = new Bullet(x,y,realAngle,muzzleVelocity,this.caliber,bullet);
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
		Point2D c = centerPoint;
		AffineTransform.getRotateInstance
		(Math.toRadians(realAngle), c.getX(), c.getY())
		.transform(turrets,0,tempPoints,0,turrets.length);
		for(int i = 0; i < tempPoints.length;i++){
			Point point = tempPoints[i];
			point.x += this.xPos;
			point.y += this.yPos;
		}
		return tempPoints;
	}
	
	public void turnToTarget(double targetAngle){
		double a = targetAngle - realAngle;
		a = a % 360;
		if(a < 0){
			a += 360;
		}
		double diff = Math.abs(180 - a);
		double f = turnSpeed - turnSpeed * (diff / 180D);
		if(a > 180){
			f = -f;
		}
		turn(f);
	}
}
