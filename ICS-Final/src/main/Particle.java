package main;

import java.awt.Color;
import java.awt.Point;

public class Particle extends Entity{
	int maxLife = 100;
	int life = maxLife;
	Color c = Color.ORANGE;
	static int size = 6;
	{
		points = new Point[4];
		tempPoints = new Point[4];
		points[0] = new Point(0,0);
		points[1] = new Point(size,0);
		points[2] = new Point(size,size);
		points[3] = new Point(0,size);
		for(int i = 0; i < points.length;i++){
			tempPoints[i] = new Point(0,0);
		}
	}
	
	public Color getColor(){
		return new Color(c.getRed(),c.getGreen(),c.getBlue(),(int)Math.abs((255 *((double)life / (double)maxLife))));
	}
	public Particle(double x, double y, double dX, double dY) {
		super(x - size / 2, y - size / 2, dX, dY);
		turnMomentum = (rand.nextDouble() - 0.5) * 30;

	}
	public void update(){
		super.update();
				dX -= dX / 100D;
				dY -= dY / 100D;
				life--;
	}
	public boolean isDead(){
		if(life < 0){
			return true;
		}
		return false;
	}



}
