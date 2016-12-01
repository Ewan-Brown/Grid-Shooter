package main;

public class Projectile extends Entity{
	double damage;
	public Projectile(double x, double y, double dX, double dY) {
		super(x, y, dX, dY);
	}
	public void onCollide(Entity e){
		if(e.team != this.team){
			e.health -= damage;
		}
	}

}
