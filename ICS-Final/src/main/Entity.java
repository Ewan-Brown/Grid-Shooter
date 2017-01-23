package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;

public class Entity extends Drawable implements Cloneable{
	double xSpeed = 0;
	double ySpeed = 0;
	double spin = 0;
	double maxHealth = 100;
	double health = maxHealth;
	double team = 0;
	Random rand = new Random();
	public Entity(double x, double y,double dX,double dY,Point[] points){
		super(x,y,points);

		this.xSpeed = dX;
		this.ySpeed = dY;
		color = Color.RED;
	}
	public boolean isDead(){
		return (health <= 0);
	}
	public void update(){
		xPos += xSpeed;
		yPos += ySpeed;
		realAngle += spin;
		spin -= spin / 20;
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

}
