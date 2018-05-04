package entities.projectiles;

import java.awt.geom.Point2D;

import entities.Entity;
import main.Game;
import tools.GameMath;

public class Laser extends Projectile{

	{
		super.transparency = true;
	}
	public Laser(double x, double y,int shape,double angle,int damage) {
		super(x, y, 0, 0, shape,damage);
		this.realAngle = angle;
		color = 1;
		maxHealth = 30;
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
	public void onCollide(Entity e){
		if(e.team != this.team){
			//damages opposing entity, kills itself.
			e.health -= damage;
		}
	}
	
}
