package entities;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;



/**
 * @author Ewan Brown
 *	
 *	Base class for any object that moves on the global co-ordinates and can be drawn.
 *	Contains methods for rotating, getting polygons, and getting the center
 */
public abstract class Drawable implements Cloneable{
	
	public double xPos;
	public double yPos;
	/**
	 * an array of Points that make up the structure of the polygon
	 */
	public Point[] polygonPoints;
	
	/** 
	 * The angle in degrees of this drawable relative to the global 2D Plane, 0 degrees would be 'east'
	 */
	public double realAngle = 0;
	public Color color;
	/**
	 * Precalculated center point of the drawable's non-translated/rotated polygon
	 */
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
	public double getX(){
		return xPos + centerPoint.getX();
		
	}
	public double getY(){
		return yPos + centerPoint.getY();
	}
	/**
	 * @param p Point[] points to be translated
	 * @return p points translated to the drawable's current location
	 */
	public Point[] translatePoints(Point[] p){
		Point[] newPoints = new Point[p.length];
		for(int i = 0; i < p.length;i++){
			newPoints[i] = new Point((int)(p[i].x + xPos),(int)(p[i].y + yPos));
		}
		return newPoints;
	}
	/**
	 * @return this drawable's points, translated and rotated
	 */
	public Point[] getRotatedPoints(){
		Polygon poly = getRotatedPolygon();
		Point[] p = new Point[poly.npoints];
		for(int i = 0; i < p.length;i++){
			p[i] = new Point(poly.xpoints[i],poly.ypoints[i]);
		}
		return p;
	}
	/**
	 * @return get this drawable's sides, translated and rotated
	 */
	public Line2D[] getRotatedSides(){
		Point[] p = getRotatedPoints();
		Line2D[] lines = new Line2D[p.length];
		for(int i = 0; i < p.length - 1;i++){
			lines[i] = new Line2D.Double(p[i],p[i+1]);
		}
		lines[p.length - 1] = new Line2D.Double(p[p.length - 1], p[0]);
		return lines;
		
	}
	/**
	 * @return this drawable's points as a Polygon object, translated and rotated
	 */
	public Polygon getRotatedPolygon(){
		Point[] tempPoints = new Point[polygonPoints.length];
		for(int i = 0; i < tempPoints.length;i++){
			tempPoints[i] = new Point(0,0);
		}
		rotatePoints(polygonPoints,realAngle,tempPoints);
		return getPolygon(tempPoints);

	}
	/**
	 * @param p points to be translated
	 * @return translates points to drawable's location then returns as a polygon
	 */
	public Polygon getPolygon(Point[] p){

		Point[] p2 = translatePoints(p);

		Polygon tempPoly = new Polygon();

		for(int  i=0; i < p2.length; i++){
			tempPoly.addPoint(p2[i].x, p2[i].y);
		}

		return tempPoly;
	}
	public Object clone() {
		Object clone = null;

		try {
			clone = super.clone();

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return clone;
	}
	
	/***************************************************************************************
	*    Forum post "How to rotate a polygon/points around a point in Java"<p>
	*    
	*    From: Usernam TRU7H on Stackoverflow<p>
	*    
	*    URL: http://stackoverflow.com/a/13788646<p>
	*
	***************************************************************************************/
	public void rotatePoints(Point2D[] origPoints, double angle, Point2D[] storeTo){

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
