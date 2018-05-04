package entities;

import java.text.DecimalFormat;
import java.util.Random;

public class Entity extends Drawable{

	protected double xSpeed = 0;
	protected double ySpeed = 0;
	public double maxHealth = 1000;
	public double health = maxHealth;
	public int team = 0;
	public boolean isPlayerControlled = false;
	static DecimalFormat df = new DecimalFormat("0.00");
	Random rand = new Random();
	boolean deadFlag = false;
//	public String toString(){
//		double d = health/maxHealth;
//		String h = df.format(d);
//		return "HP: ["+h+"]";
//	}
	{
		super.transparency = true;
	}
	public double getLife(){
		return health/maxHealth;
	}
	public Entity(double x, double y,double dX,double dY,int shape){
		super(x,y,shape);
		health = maxHealth;
		this.xSpeed = dX;
		this.ySpeed = dY;
		color = 1;
	}
	public boolean isDead(){
		boolean b = (health <= 0);
		if(b && !deadFlag){
			deadFlag = true;
			onDeath();
		}
		return b;
	}
	public int getAlpha(){
		if(transparency){
			int a = (int) (50 + (200 *((double)health / (double)maxHealth)));
			return (a < 0) ? 0 : a;
		}
		else{
			return 255;
		}
	}
	public void onDeath(){
	}
	public double getTargetAngle(Entity e){
		return Math.atan2(e.getY() - this.getY(),e.getX()-this.getX());
	}
	public double getHealthPercent(){
		if(health < 0){
			return 0;
		}

		return health/maxHealth;
	}
	/**
	 * Updates entity physics, AI, and anything else that needs to be changed every game tick
	 */
	public void update(){
		xPos += getXSpeed();
		yPos += getYSpeed();
	}
	public double getXSpeed(){
		return xSpeed;
	}
	public double getYSpeed(){
		return ySpeed;
	}

}
