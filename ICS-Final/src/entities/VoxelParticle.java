package entities;

import main.Panel.CustColor;

/**
 * @author Ewan
 *	Class for Aesthetic Particles
 */
public class VoxelParticle extends Entity{
	double spin;
	public VoxelParticle(double x, double y, double dX, double dY,double spin, CustColor c, double health) {
		super(x, y, dX, dY,Structures.PARTICLE1,c);
		maxHealth = health;
		this.health = maxHealth;
		this.spin = spin;

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
