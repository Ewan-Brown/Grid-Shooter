package entities.turrets;

import java.awt.Point;

import entities.Entity;
import entities.Projectile;
import entities.Ship;
import entities.Structures;

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
	
//	bullet = new Point[4];
//	bullet[0] = new Point(10,2);
//	bullet[1] = new Point(2,4);
//	bullet[2] = new Point(0,2);
//	bullet[3] = new Point(2,0);
	
	public Turret(double x, double y,Ship owner) {
		super(x, y, 0,0, Structures.TURRET);
		this.owner = owner;
	}
	public void update(){
		cooldown--;
	}
	public Projectile getProjectile(){
		return null;
	}
	
}
