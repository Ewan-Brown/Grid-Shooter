package entities;

import java.awt.Color;
import java.awt.Point;

/**
 * @author Ewan
 *	Class for Aesthetic Particles
 */
public class Particle extends Entity{
	double spin;
	public Particle(double x, double y, double dX, double dY,Point[] points,double s, int c, double health) {
		super(x, y, dX, dY,points);
		maxHealth = health;
		this.health = maxHealth;
		spin = s;
		this.color = c;

	}
	public void update(){
		super.update();
		xSpeed -= xSpeed / 300D;
		ySpeed -= ySpeed / 300D;
		realAngle += spin;
		spin -= spin / 20;
		health--;
	}



}
