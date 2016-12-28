package main;

public class GameMath {

	public static double getDistance(double x1, double y1, double x2, double y2){
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	public static double getDistance(Entity e1,Entity e2){
		return getDistance(e1.x,e1.y,e2.x,e2.y);
	}
	
}
