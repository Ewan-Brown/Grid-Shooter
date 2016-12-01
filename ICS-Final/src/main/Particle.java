package main;

import java.awt.Point;

public class Particle extends Entity{

	public Particle(double x, double y, double dX, double dY) {
		super(x, y, dX, dY);
		points = new Point[4];
		tempPoints = new Point[4];
		points[0] = new Point(0,0);
		points[1] = new Point(10,0);
		points[2] = new Point(10,10);
		points[3] = new Point(0,10);
		for(int i = 0; i < points.length;i++){
			tempPoints[i] = new Point(0,0);
		}

	}

	
	
}
