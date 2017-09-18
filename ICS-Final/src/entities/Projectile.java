package entities;

import java.awt.Point;

/**
 * @author Ewan
 *	Base class for any entities who's sole purpose is to damage other entities
 */
public class Projectile extends Entity{
	/**
	 * Damage to be inflicted on any victims
	 */
	double damage;
	public Projectile(double x, double y, double dX, double dY,Point[] points,int damage) {
		super(x, y, dX, dY,points);
		this.damage = 1000;
	}
	/**
	 * @param e Entity that has collided with this entity
	 * 
	 * Called on collision between this entity and another entity
	 */
	public void onCollide(Entity e){
		if(e.team != this.team){
			//damages opposing entity, kills itself.
			e.health -= damage;
			this.health = 0;
		}
	}

}
