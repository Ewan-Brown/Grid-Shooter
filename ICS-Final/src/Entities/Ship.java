package Entities;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import main.Game;

/**
 * @author Ewan
 * Class for any objects that can move on their own and shoot.
 */
public class Ship extends Entity{

	/**
	 * max speed at which this ship can turn in either direction
	 */
	public double turnSpeed = 4;
	/**
	 * max acceleration speed
	 */
	public double speed = 0.03;
	/**
	 * current delay for how long until next bullet can be shot
	 */
	public int bulletCooldown = 0;
	/**
	 * maximum timer for delay between bullet shots
	 */
	public int maxBulletCooldown = 30;
	/**
	 * speed of bullets shot 
	 */
	public double muzzleVelocity = 7;
	/**
	 * maximum timer for delay between particle effects (too much causes painting lag)
	 */
	public static final double MAX_PARTICLE_COOLDOWN = 3;
	/**
	 * Angle in degrees by which the bullet shots can possibly miss by<p>
	 * 0 is 100% accuracy
	 */
	public double bulletAccuracy = 10;
	/**
	 * Current delay for how long until the next thrust particles can be shot
	 */
	public double thrustParticleCooldown = MAX_PARTICLE_COOLDOWN;
	/**
	 * current delay for how long until the next strafe particles can be shot
	 */
	public double strafeParticleCooldown = MAX_PARTICLE_COOLDOWN;
	
	/**
	 * damage applied from bullets shot from this ship.
	 */
	public int caliber = 10;
	
	/**
	 * Bullet structure shape points
	 */
	Point[] bullet;
	{
		bullet = new Point[4];
		bullet[0] = new Point(15,3);
		bullet[1] = new Point(3,6);
		bullet[2] = new Point(0,3);
		bullet[3] = new Point(3,0);
	}
	/**
	 * Aesthetic particle shape points
	 */
	Point[] particle;
	{
		particle = new Point[4];
		particle[0] = new Point(0,0);
		particle[1] = new Point(4,0);
		particle[2] = new Point(4,4);
		particle[3] = new Point(0,4);
	}
	/**
	 * array of points representing the points from which bullets are shot (there may be multiple)
	 */
	public Point[] turrets;
	public Ship(double x, double y,Point[] points,Point[] turrets) {
		super(x, y,0,0,points);
		this.turrets = turrets;
	}
	/**
	 * Accelerates the ship forward or backwards relative to it's angle, depending on the throttle. 
	 * @param t Throttle + for forward, - for backwards
	 */
	
	public void move(double angle){
		
		double t = 0.5 + (0.5 - (0.5 * (Math.abs(angle) / 180D)));
		double dX = (Math.cos(Math.toRadians(realAngle + angle)))*speed*t;
		double dY = (Math.sin(Math.toRadians(realAngle + angle)))*speed*t;
		this.xSpeed += dX;
		this.ySpeed += dY;
		if(thrustParticleCooldown < 0){
			fumes(t, realAngle);
			thrustParticleCooldown = MAX_PARTICLE_COOLDOWN;
		}
	}
	
	/**
	 * Ejects particle effects in the opposite angle to simulate engine smoke/fire
	 * @param t particle speed
	 * @param angle the ship is accelerating towards
	 */
	public void fumes(double t, double angle){
		double angle2 = angle + ((Math.random() - 0.5) * 50);
		double dX2 = (Math.cos(Math.toRadians(angle2)))*speed*t*10;
		double dY2 = (Math.sin(Math.toRadians(angle2)))*speed*t*10;
		Point2D p = centerPoint;
		Game.effectsArray.add(new Particle(p.getX() + xPos,p.getY() + yPos,-dX2,-dY2,particle));
	}
	public void update(){
		super.update();
		bulletCooldown--;
		thrustParticleCooldown--;
		strafeParticleCooldown--;
		xSpeed -= xSpeed / 100D;
		ySpeed -= ySpeed / 100D;
	}
	/**
	 * Shoot bullets from all turrets towards the direction the ship is facing in
	 */
	public void shootBullet(){
		if(bulletCooldown < 0){
			bulletCooldown = maxBulletCooldown;
			Point[] p = getTurrets();
			for(int i = 0; i < p.length;i++){
				double x = p[i].x;
				double y = p[i].y;
				double a = (rand.nextDouble() - 0.5) * bulletAccuracy;
				Bullet b = new Bullet(x,y,realAngle + a,muzzleVelocity,caliber,bullet);
				b.team = this.team;
				Game.entityArray.add(b);

			}
		}
	}
	/**
	 * @return all turret points translated and rotated
	 */
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
	/**Turns this entity a portion of the way towards the target angle. 
	 * The further this ship is from point at the angle, the faster it turns
	 * @param targetAngle angle in degrees of target angle
	 */
	public void turnToTarget(double targetAngle){
		double a = targetAngle - realAngle;
		a = a % 360;
		if(a < 0){
			a += 360;
		}
		double diff = Math.abs(180 - a);
		double f = turnSpeed - (turnSpeed / 2) * (diff / 180D);
		if(a > 180){
			f = -f;
		}
		realAngle += f;
	}
}
