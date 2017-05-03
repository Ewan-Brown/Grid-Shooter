package entities;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import main.Game;

public class Entity extends Drawable{

	double xSpeed = 0;
	double ySpeed = 0;
	public double maxHealth = 100;
	public double health = maxHealth;
	public double team = 0;
	//Random object, used for AI,effects and shooting accuracy
	Random rand = new Random();

	public Entity(double x, double y,double dX,double dY,Point[] points){
		super(x,y,points);
		health = maxHealth;
		this.xSpeed = dX;
		this.ySpeed = dY;
		color = Color.RED;
	}
	public boolean isDead(){
		boolean b = (health <= 0);
		if(b){
			onDeath();
		}
		return b;
	}
	public void onDeath(){
	}
	public double getTargetAngle(Entity e){
		return Math.atan2(e.getY() - this.getY(),e.getX()-this.getX());
	}
	/**
	 * Updates entity physics, AI, and anything else that needs to be changed every game tick
	 */
	public void update(){
		xPos += xSpeed;
		yPos += ySpeed;

	}

}
