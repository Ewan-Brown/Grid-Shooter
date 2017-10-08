package entities;

import java.awt.Point;

import main.Game;
import tools.GameMath;

/**
 * @author Ewan
 * Class for the bullets shot by the player and enemies
 */
public class Bullet extends Projectile{
	public void update(){
		super.update();
		//Loops through all other entites, ignores allies, itself, and other projectiles
		for(int i = 0 ; i < Game.entityArray.size();i++){
			Entity e = Game.entityArray.get(i);
			if(e == this || e.team == this.team){
				continue;
			}
			if(e instanceof Projectile){
				continue;
			}
			if(GameMath.areColliding(this, e)){
				onCollide(e);
			}
		}
		health--;
	}
	public Bullet(double x, double y, double angle,double speed,int points,int damage) {
		super(x, y, 0,0,points,damage);
		this.damage = damage;
		realAngle = angle;
		double dX = (Math.cos(Math.toRadians(realAngle)))*speed;
		double dY = (Math.sin(Math.toRadians(realAngle)))*speed;
		this.xSpeed += dX;
		this.ySpeed += dY;
	}

}
