package Entities;

import java.awt.Color;
import java.awt.Point;

/**
 * @author Ewan
 *	Class for Aesthetic Particles
 */
public class Particle extends Entity{
	double spin;
	Color c = Color.ORANGE;
	{
		health = maxHealth;
	}
	public Color getColor(){
		return new Color(c.getRed(),c.getGreen(),c.getBlue(),(int)Math.abs((255 *((double)health / (double)maxHealth))));
	}
	public Particle(double x, double y, double dX, double dY,Point[] points) {
		super(x, y, dX, dY,points);
		spin = (rand.nextDouble() - 0.5) * 30;

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
