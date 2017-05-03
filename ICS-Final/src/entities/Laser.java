package entities;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

import main.Game;
import tools.GameMath;

public class Laser extends Projectile{

	public Laser(double x, double y,Point[] points,double angle,int damage) {
		super(x, y, 0, 0, points,damage);
		this.realAngle = angle;
		color = Color.RED;
		maxHealth = 1000;
		health = maxHealth;
		centerPoint = new Point2D.Double(0, 1);
		this.xPos = x;
	}
	public void update(){
		super.update();
		health--;
		for(int i = 0 ; i < Game.entityArray.size();i++){
			Entity e = Game.entityArray.get(i);
			if(e == this || e.team == this.team){
				continue;
			}
			if(e instanceof Laser){
				continue;
			}
			if(GameMath.areColliding(this, e)){
				onCollide(e);
			}
		}
	}
	public Color getColor(){
		return new Color(color.getRed(),color.getGreen(),color.getBlue(),(int)Math.abs((255 *((double)health / (double)maxHealth))));
	}
	public void onCollide(Entity e){
		System.out.println("hey");
		if(e.team != this.team){
			//damages opposing entity, kills itself.
			e.health -= damage;
		}
	}
	
}
