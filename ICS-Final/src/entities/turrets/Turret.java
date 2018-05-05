package entities.turrets;

import java.awt.Point;

import entities.Entity;
import entities.Ship;
import entities.Structures;
import entities.projectiles.Projectile;

public class Turret extends Entity {

	Ship owner;
	int cooldown;
	int maxCooldown;
	static Point[] points = new Point[4];
	{
		points[0] = new Point(0,0);
		points[1] = new Point(4,0);
		points[2] = new Point(4,4);
		points[3] = new Point(0,4);
	}
	
	public Turret(double x, double y,Ship owner) {
		super(x, y, 0,0, Structures.TURRET,owner.color);
		this.owner = owner;
	}
	public void update(){
		cooldown--;
	}
	public Projectile getProjectile(){
		return null;
	}
	
}
