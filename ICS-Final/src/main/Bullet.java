package main;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Bullet extends Projectile{
	{
		points = new Point[4];
		points[0] = new Point(20,4);
		points[1] = new Point(4,8);
		points[2] = new Point(0,4);
		points[3] = new Point(4,0);
		tempPoints = new Point[points.length];
		for(int i = 0; i < points.length;i++){
			tempPoints[i] = new Point(0,0);
		}
	}
	public void update(){
		super.update();
		for(int i = 0 ; i < Game.entities.size();i++){
			Entity e = Game.entities.get(i);
			if(e == this || e.team == this.team){
				continue;
			}
			if(e instanceof Bullet){
				continue;
			}
			Point2D p = getCenter();
			double x1 = p.getX() + x;
			double y1 = p.getY() + y;
			if(e.getRotatedPolygon().contains(new Point2D.Double(x1,y1))){
				e.health -= 100;
			}
		}
		health--;
	}
	public Bullet(double x, double y, double angle,double speed, double damage) {
		super(x, y, 0,0);
		this.damage = damage;
		realAngle = angle;
		double dX = (Math.cos(Math.toRadians(realAngle)))*speed;
		double dY = (Math.sin(Math.toRadians(realAngle)))*speed;
		this.dX += dX;
		this.dY += dY;
	}
	
}
