package main;

import java.awt.Color;
import java.awt.Point;

public class Particle extends Entity{
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
				xSpeed -= xSpeed / 100D;
				ySpeed -= ySpeed / 100D;
				health--;
	}



}
