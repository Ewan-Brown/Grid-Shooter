package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public abstract class Drawable {

	public double x;
	public double y;
	public Point[] points;
	public Point[] tempPoints;
	public double realAngle = 0;
	public Color c;
	public Point[] updatePoints(Point[] p){
		Point[] newPoints = new Point[p.length];
		for(int i = 0; i < p.length;i++){
			newPoints[i] = new Point((int)(p[i].x + x),(int)(p[i].y + y));
		}
		return newPoints;
	}
	public Polygon getRotatedPolygon(){

		rotatePointMatrix(points,realAngle,tempPoints);
		return getPolygon(tempPoints);

	}
	public Polygon getPolygon(Point[] p){

		Point[] p2 = updatePoints(p);

		Polygon tempPoly = new Polygon();

		for(int  i=0; i < p2.length; i++){
			tempPoly.addPoint(p2[i].x, p2[i].y);
		}

		return tempPoly;
	}
	public void rotatePointMatrix(Point2D[] origPoints, double angle, Point2D[] storeTo){

		/* We ge the original points of the polygon we wish to rotate
		 *  and rotate them with affine transform to the given angle. 
		 *  After the operation is complete the points are stored to the 
		 *  array given to the method.
		 */
		Point2D p = getCenter();
		AffineTransform.getRotateInstance
		(Math.toRadians(angle), p.getX(), p.getY())
		.transform(origPoints,0,storeTo,0,points.length);

	}
	public Point2D getCenter(){
		double centreX = 0;
		double centreY = 0;
		double signedArea = 0.0;
		double x0 = 0.0; // Current vertex X
		double y0 = 0.0; // Current vertex Y
		double x1 = 0.0; // Next vertex X
		double y1 = 0.0; // Next vertex Y
		double a = 0.0;  // Partial signed area

		// For all points except last
		int i=0;
		for (i=0; i<points.length-1; ++i)
		{
			x0 = points[i].x;
			y0 = points[i].y;
			x1 = points[i+1].x;
			y1 = points[i+1].y;
			a = x0*y1 - x1*y0;
			signedArea += a;
			centreX += (x0 + x1)*a;
			centreY += (y0 + y1)*a;
		}

		// Do last vertex separately to avoid performing an expensive
		// modulus operation in each iteration.
		x0 = points[i].x;
		y0 = points[i].y;
		x1 = points[0].x;
		y1 = points[0].y;
		a = x0*y1 - x1*y0;
		signedArea += a;
		centreX += (x0 + x1)*a;
		centreY += (y0 + y1)*a;

		signedArea *= 0.5;
		centreX /= (6.0*signedArea);
		centreY /= (6.0*signedArea);
		return new Point2D.Double(centreX, centreY);

	}
	
}
