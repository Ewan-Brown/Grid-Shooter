package entities.turrets;

import entities.Ship;
import entities.projectiles.Projectile;

public class Turret{

	Ship owner;
	int cooldown;
	int maxCooldown;
	double angleRelativeToShip = 0;
	double x;
	double y;
	enum TurretAttachmentType{
		FIXED, //Glued to ship angle
		FREE,  //Free from ship angle, full range
		SEMI,  //Can move relative to ship angle
		SEMI_LIMIT //Can move relative to ship angle within a given range
	}
	
	public Turret(double x, double y,Ship owner) {
		this.owner = owner;
	}
	public void update(){
		cooldown--;
	}
	public Projectile getProjectile(){
		return null;
	}
	
}
