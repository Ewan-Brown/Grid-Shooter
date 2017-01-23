package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public abstract class Drawable {

	public double xPos;
	public double yPos;
	public Point[] polygonPoints;
	public double realAngle = 0;
	public Color color;
	public Point2D centerPoint;
	public Drawable(double x, double y, Point[] points){
		this.xPos = x;
		this.yPos = y;
		this.polygonPoints = points;
		centerPoint = getCenter(points);
		this.xPos -= centerPoint.getX();
		this.yPos -= centerPoint.getY();
	}
	public Color getColor(){
		return color;
	}
	public Point[] updatePoints(Point[] p){
		Point[] newPoints = new Point[p.length];
		for(int i = 0; i < p.length;i++){
			newPoints[i] = new Point((int)(p[i].x + xPos),(int)(p[i].y + yPos));
		}
		return newPoints;
	}
	public Point[] getRotatedPoints(){
		Polygon poly = getRotatedPolygon();
		Point[] p = new Point[poly.npoints];
		for(int i = 0; i < p.length;i++){
			p[i] = new Point(poly.xpoints[i],poly.ypoints[i]);
		}
		return p;
	}
	public Line2D[] getRotatedSides(){
		Point[] p = getRotatedPoints();
		Line2D[] lines = new Line2D[p.length];
		for(int i = 0; i < p.length - 1;i++){
			lines[i] = new Line2D.Double(p[i],p[i+1]);
		}
		lines[p.length - 1] = new Line2D.Double(p[p.length - 1], p[0]);
		return lines;
		
	}
	public Polygon getRotatedPolygon(){
		Point[] tempPoints = new Point[polygonPoints.length];
		for(int i = 0; i < tempPoints.length;i++){
			tempPoints[i] = new Point(0,0);
		}
		rotatePointMatrix(polygonPoints,realAngle,tempPoints);
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
		Point2D p = centerPoint;
		AffineTransform.getRotateInstance
		(Math.toRadians(angle), p.getX(), p.getY())
		.transform(origPoints,0,storeTo,0,polygonPoints.length);

	}
	public static Point2D getCenter(Point[] points){
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
