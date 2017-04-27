package Entities;

import java.awt.Color;
import java.awt.Point;

/**
 * @author Ewan
 *	Class for Aesthetic Particles
 */
public class Particle extends Entity{
	double spin;
	public Color getColor(){
		return new Color(color.getRed(),color.getGreen(),color.getBlue(),(int)Math.abs((255 *((double)health / (double)maxHealth))));
	}
	public Particle(double x, double y, double dX, double dY,Point[] points,double s, Color c) {
		super(x, y, dX, dY,points);
		health = maxHealth;
		spin = s;
		this.color = new Color(c.getRGB());

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
