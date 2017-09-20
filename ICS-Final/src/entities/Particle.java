package entities;

/**
 * @author Ewan
 *	Class for Aesthetic Particles
 */
public class Particle extends Entity{
	double spin;
	public Particle(double x, double y, double dX, double dY,double s, int c, double health) {
		super(x, y, dX, dY,Structures.PARTICLE1);
		maxHealth = health;
		this.health = maxHealth;
		spin = s;
		this.color = c;

	}
	public void update(){
		super.update();
		xSpeed -= xSpeed / 300D;
		ySpeed -= ySpeed / 300D;
		realAngle += spin;
		spin -= spin / 20;
		health--;
	}



}
