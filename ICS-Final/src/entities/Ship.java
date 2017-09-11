package entities;

import java.awt.Color;
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

	public boolean laserOn = false;
	public double turnSpeed = 4;
	public double speed = 0.03;
	public int bulletCooldown = 0;
	public int missileCooldown = 0;
	public int laserCooldown = 30;
	public int maxBulletCooldown = 30;
	public double muzzleVelocity = 7;
	public static final int MAX_MISSILE_COUNTDOWN = 50;
	public static final int MAX_PARTICLE_COOLDOWN = 5;
	public double bulletAccuracy = 10;
	public double thrustParticleCooldown = MAX_PARTICLE_COOLDOWN;
	public double strafeParticleCooldown = MAX_PARTICLE_COOLDOWN;
	public int caliber = 100;
	public int missiles = 0;
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
	static Point[] laser;
	{

		laser = new Point[4];
		laser[0] = new Point(0,0);
		laser[1] = new Point(2000,0);
		laser[2] = new Point(2000,2);
		laser[3] = new Point(0,2);
	}
	/**
	 * array of points representing the points from which bullets are shot (there may be multiple)
	 */
	public Point[] bulletTurrets;
	public Point[] missileTurrets;
	public Ship(double x, double y,Point[] points,Point[] turrets,Point[] missileTurrets) {
		super(x, y,0,0,points);
		this.bulletTurrets = turrets;
		this.missileTurrets = missileTurrets;
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
		Game.effectsArray.add(new Particle(p.getX() + xPos,p.getY() + yPos,-dX2,-dY2,particle,(rand.nextDouble() - 0.5) * 30,Color.ORANGE,40));
	}
	public void update(){
		super.update();
		bulletCooldown--;
		missileCooldown--;
		thrustParticleCooldown--;
		strafeParticleCooldown--;
		xSpeed -= xSpeed / 100D;
		ySpeed -= ySpeed / 100D;
	}
	public Color getColor(){
		Color c = new Color(color.getRed(),color.getGreen(),color.getBlue(),50 + (int)(200*getHealthPercent()));
		return c;
	}
	public void onDeath(){
		ArrayList<Particle> a = new ArrayList<Particle>();
		for(int i = 0; i < 10;i++){
			int r = rand.nextInt(10);
			Point[] deadParticles;
			deadParticles = new Point[4];
			deadParticles[0] = new Point(0,0);
			deadParticles[1] = new Point(r,0);
			deadParticles[2] = new Point(r,r);
			deadParticles[3] = new Point(0,r);
			Particle p = new Particle(this.getX(),this.getY(),(rand.nextDouble()- 0.5)*2,(rand.nextDouble()- 0.5)*2,deadParticles,(rand.nextDouble()-0.5) * 100,this.color,100);
			a.add(p);
		}

		Game.effectsArray.addAll(a);
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
				Entity b;
				b = new Bullet(x,y,realAngle + a,muzzleVelocity,bullet,caliber);
				if(laserOn){
					b = new Laser(x,y,laser,0,1);
					b.realAngle = realAngle;
				}
				b.team = this.team;
				Game.entityArray.add(b);

			}
		}
	}
	public void shootMissile(){
		if(missileCooldown < 0){
			if(missiles > 0){
				missiles--;
				missileCooldown = MAX_MISSILE_COUNTDOWN;
				Point[] p = getMissileTurrets();
				for(int i = 0; i < p.length;i++){
					double x = p[i].x;
					double y = p[i].y;
					double a = (rand.nextDouble() - 0.5) * bulletAccuracy;
					Missile m = new Missile(x,y,realAngle,bullet,1000);
					m.team = this.team;
					Game.entityArray.add(m);

				}
			}
		}
	}
	/**
	 * @return all turret points translated and rotated
	 */
	public Point[] getTurrets(){
		Point[] tempPoints = new Point[bulletTurrets.length];
		for(int i = 0; i < bulletTurrets.length;i++){
			tempPoints[i] = new Point(0,0);
		}
		Point2D c = centerPoint;
		AffineTransform.getRotateInstance
		(Math.toRadians(realAngle), c.getX(), c.getY())
		.transform(bulletTurrets,0,tempPoints,0,bulletTurrets.length);
		for(int i = 0; i < tempPoints.length;i++){
			Point point = tempPoints[i];
			point.x += this.xPos;
			point.y += this.yPos;
		}
		return tempPoints;
	}
	public Point[] getMissileTurrets(){
		Point[] tempPoints = new Point[missileTurrets.length];
		for(int i = 0; i < missileTurrets.length;i++){
			tempPoints[i] = new Point(0,0);
		}
		Point2D c = centerPoint;
		AffineTransform.getRotateInstance
		(Math.toRadians(realAngle), c.getX(), c.getY())
		.transform(missileTurrets,0,tempPoints,0,missileTurrets.length);
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
		double f=0;
		//a = a % 360;
		while(a >180){
			a -= 360;
		}
		while(a < -180){
			a += 360;
		}
		if (a>0)
			f=Math.min(a,turnSpeed/5.0);
		else
			f=Math.max(a,-turnSpeed/5.0);
		//f /= 50;
		/*double diff = Math.abs(180 - a);
		double f = turnSpeed - (turnSpeed / 2) * (diff / 180D);
		if(a > 180){
			f = -f;
		}*/
		
		realAngle += f;
	}
}
