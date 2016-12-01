package main;

public class Ship extends Entity{

	double turnSpeed;
	double speed;
	double strafeSpeed;
	int cooldown = 0;
	int MAX_COOLDOWN = 10;
	double muzzleVelocity = 40;
	int caliber = 10;
	public Ship(double x, double y, double dX, double dY) {
		super(x, y, dX, dY);
	}
	public void thrust(double t){
		double dX = (Math.cos(Math.toRadians(realAngle)))*speed*t;
		double dY = (Math.sin(Math.toRadians(realAngle)))*speed*t;
		this.dX += dX;
		this.dY += dY;
	}
	public void strafe(double t){
		double dX = (Math.cos(Math.toRadians(realAngle + 90)))*speed*t;
		double dY = (Math.sin(Math.toRadians(realAngle + 90)))*speed*t;
		this.dX += dX;
		this.dY += dY;
	}
	

}
