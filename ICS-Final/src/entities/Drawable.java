package entities;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import main.Panel.CustColor;



/**
 * @author Ewan Brown
 *	
 *	Base class for any object that moves on the global co-ordinates and can be drawn.
 *	Contains methods for rotating, getting polygons, and getting the center
 */
public abstract class Drawable implements Cloneable{
	
	public double xPos;
	public double yPos;
	public int structType;
	public double realAngle = 0;
	public boolean transparency = false;
	public Point2D centerPoint;
	public CustColor color;
	public boolean fillMe = true;
	public boolean outlineMe = false;
	public Drawable(double x, double y, int shape,CustColor c){
		this.xPos = x;
		this.yPos = y;
		this.structType = shape;;
		centerPoint = getCenter(Structures.getStructure(shape));
		this.xPos -= centerPoint.getX();
		this.yPos -= centerPoint.getY();
		this.color = c;
	}
	public boolean isToBeFilled(){
		return fillMe;
	}
	public boolean isToBeOutlined(){
		return outlineMe;
	}
	public int getAlpha(){
		return 255;
	}
	public double getX(){
		return xPos + centerPoint.getX();
		
	}
	public double getY(){
		return yPos + centerPoint.getY();
	}
	public Point[] translatePoints(Point[] p){
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
		Point[] tempPoints = new Point[Structures.getStructure(structType).length];
		for(int i = 0; i < tempPoints.length;i++){
			tempPoints[i] = new Point(0,0);
		}
		rotatePoints(Structures.getStructure(structType),realAngle,tempPoints);
		return getPolygon(tempPoints);

	}
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
	*    From: Username TRU7H on Stackoverflow<p>
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
		.transform(origPoints,0,storeTo,0,Structures.getStructure(structType).length);

	}
	//Not mine, used it in an old project and never referenced it :( probably from somewhere on Stack Overflow
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
