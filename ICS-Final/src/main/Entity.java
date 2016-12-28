package main;

import java.awt.Color;
import java.util.Random;

public class Entity extends Drawable implements Cloneable{
	double dX = 0;
	double dY = 0;
	double turnMomentum = 0;
	double health = 100;
	double team = 0;
	Random rand = new Random();
	public Entity(double x, double y,double dX,double dY){
		this.x = x;
		this.y = y;
		this.dX = dX;
		this.dY = dY;
		c = Color.RED;
	}
	public boolean isDead(){
		return (health <= 0);
	}
	public void update(){
		x += dX;
		y += dY;
		realAngle += turnMomentum;
		turnMomentum -= turnMomentum / 20;
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
