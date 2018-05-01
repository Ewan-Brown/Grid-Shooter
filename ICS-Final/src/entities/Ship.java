package entities;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import main.Game;

/**
 * @author Ewan Class for any objects that can move on their own and shoot.
 */
public class Ship extends Entity {
	// TODO Rewrite weapon code with Turrets - Polymorphous!
	public boolean laserOn = false;
	public double turnSpeed = 8;
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
	public static final int MAX_AJAY_DRIVE_COOLDOWN = 100;
	public int ajayDriveCooldown = MAX_AJAY_DRIVE_COOLDOWN;
	public int caliber = 100;
	public int missiles = 0;
	{
		super.transparency = true;
	}
	/**
	 * Aesthetic particle shape points
	 */
	/**
	 * array of points representing the points from which bullets are shot
	 * (there may be multiple)
	 */
	public Point[] bulletTurrets;
	public Point[] missileTurrets;

	public Ship(double x, double y, int shape, Point[] turrets, Point[] missileTurrets) {
		super(x, y, 0, 0, shape);
		this.bulletTurrets = turrets;
		this.missileTurrets = missileTurrets;
	}

	/**
	 * Accelerates the ship forward or backwards relative to it's angle,
	 * depending on the throttle.
	 * 
	 * @param t
	 *            Throttle + for forward, - for backwards
	 */
	public void boost(double angle) {
		if (ajayDriveCooldown < 0) {
			move(angle, 10, 20);
		}
	}

	public void move(double angle) {

		move(angle, 0.5 + (0.5 - (0.5 * (Math.abs(angle) / 180D))), 1);
	}

	public void move(double angle, double t, double particleT) {
		double dX = (Math.cos(Math.toRadians(realAngle + angle))) * speed * t;
		double dY = (Math.sin(Math.toRadians(realAngle + angle))) * speed * t;
		this.xSpeed += dX;
		this.ySpeed += dY;
		if (thrustParticleCooldown < 0) {
			fumes(particleT, realAngle);
			thrustParticleCooldown = MAX_PARTICLE_COOLDOWN;
		}
	}

	/**
	 * Ejects particle effects in the opposite angle to simulate engine
	 * smoke/fire
	 * 
	 * @param t
	 *            particle speed
	 * @param angle
	 *            the ship is accelerating towards
	 */
	public void fumes(double t, double angle) {
		Point2D p = centerPoint;
//		 double angle2 = angle + ((Math.random() - 0.5) * 50);
		double dX2 = (Math.cos(Math.toRadians(angle))) * speed * t;
		double dY2 = (Math.sin(Math.toRadians(angle))) * speed * t;
		if (this.team == Game.PLAYER_TEAM) {
			// Game.addEffects((new VoxelParticle(p.getX() + xPos,p.getY() +
			// yPos,-dX2,-dY2,0,2,40)));

			// Game.addParticles(ParticleEffects.explode((float)(p.getX() +
			// xPos),(float)(p.getY() + yPos), 2, 20,80));

			// Game.addParticles(
			// ParticleEffects.helixInverted((float) (p.getX() + xPos), (float)
			// (p.getY() + yPos), angle, 2, 120));
			Game.addParticles(ParticleEffects.helix((float) (p.getX() + xPos), (float) (p.getY() + yPos), angle, 20, getLife()*80));
		}
	}

	public void update() {
		super.update();
		bulletCooldown--;
		missileCooldown--;
		thrustParticleCooldown--;
		strafeParticleCooldown--;
		ajayDriveCooldown--;
		xSpeed -= xSpeed / 100D;
		ySpeed -= ySpeed / 100D;
	}

	public void onDeath() {
		ArrayList<VoxelParticle> a = new ArrayList<VoxelParticle>();
		for (int i = 0; i < 10; i++) {
			int r = rand.nextInt(10);
			Point[] deadParticles;
			deadParticles = new Point[4];
			deadParticles[0] = new Point(0, 0);
			deadParticles[1] = new Point(r, 0);
			deadParticles[2] = new Point(r, r);
			deadParticles[3] = new Point(0, r);
			VoxelParticle p = new VoxelParticle(this.getX(), this.getY(), (rand.nextDouble() - 0.5) * 2,
					(rand.nextDouble() - 0.5) * 2, (rand.nextDouble() - 0.5) * 100, this.color, 100);
			a.add(p);
		}

		Game.addParticles(a);
	}

	/**
	 * Shoot bullets from all turrets towards the direction the ship is facing
	 * in
	 */
	public void shootBullet() {
		if (bulletCooldown < 0) {
			bulletCooldown = maxBulletCooldown;
			Point[] p = getTurrets();
			for (int i = 0; i < p.length; i++) {
				double x = p[i].x;
				double y = p[i].y;
				double a = (rand.nextDouble() - 0.5) * bulletAccuracy;
				Entity b;
				b = new Bullet(x, y, realAngle + a, muzzleVelocity, Structures.BULLET, caliber);
				if (laserOn) {
					b = new Laser(x, y, Structures.LASER, 0, 1);
					b.realAngle = realAngle;
				}
				b.team = this.team;
				Game.entityArray.add(b);

			}
		}
	}

	public void shootMissile() {
		if (missileCooldown < 0) {
			if (missiles > 0) {
				missiles--;
				missileCooldown = MAX_MISSILE_COUNTDOWN;
				Point[] p = getMissileTurrets();
				for (int i = 0; i < p.length; i++) {
					double x = p[i].x;
					double y = p[i].y;
					Missile m = new Missile(x, y, realAngle, Structures.BULLET, 1000);
					m.team = this.team;
					Game.entityArray.add(m);

				}
			}
		}
	}

	/**
	 * @return all turret points translated and rotated
	 */
	public Point[] getTurrets() {
		Point[] tempPoints = new Point[bulletTurrets.length];
		for (int i = 0; i < bulletTurrets.length; i++) {
			tempPoints[i] = new Point(0, 0);
		}
		Point2D c = centerPoint;
		AffineTransform.getRotateInstance(Math.toRadians(realAngle), c.getX(), c.getY()).transform(bulletTurrets, 0,
				tempPoints, 0, bulletTurrets.length);
		for (int i = 0; i < tempPoints.length; i++) {
			Point point = tempPoints[i];
			point.x += this.xPos;
			point.y += this.yPos;
		}
		return tempPoints;
	}

	public Point[] getMissileTurrets() {
		Point[] tempPoints = new Point[missileTurrets.length];
		for (int i = 0; i < missileTurrets.length; i++) {
			tempPoints[i] = new Point(0, 0);
		}
		Point2D c = centerPoint;
		AffineTransform.getRotateInstance(Math.toRadians(realAngle), c.getX(), c.getY()).transform(missileTurrets, 0,
				tempPoints, 0, missileTurrets.length);
		for (int i = 0; i < tempPoints.length; i++) {
			Point point = tempPoints[i];
			point.x += this.xPos;
			point.y += this.yPos;
		}
		return tempPoints;
	}

	/**
	 * Turns this entity a portion of the way towards the target angle. The
	 * further this ship is from point at the angle, the faster it turns
	 * 
	 * @param targetAngle
	 *            angle in degrees of target angle
	 */
	public void turnToTarget(double targetAngle) {
		double a = targetAngle - realAngle;
		double f = 0;
		// a = a % 360;
		while (a > 180) {
			a -= 360;
		}
		while (a < -180) {
			a += 360;
		}
		if (a > 0)
			f = Math.min(a, turnSpeed / 5.0);
		else
			f = Math.max(a, -turnSpeed / 5.0);
		// f /= 50;
		/*
		 * double diff = Math.abs(180 - a); double f = turnSpeed - (turnSpeed /
		 * 2) * (diff / 180D); if(a > 180){ f = -f; }
		 */

		realAngle += f;
	}
}
