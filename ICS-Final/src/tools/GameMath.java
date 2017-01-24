package tools;

import java.awt.geom.Line2D;

import Entities.Entity;

public class GameMath {

	public static double getDistance(double x1, double y1, double x2, double y2){
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	public static double getDistance(Entity e1,Entity e2){
		return getDistance(e1.xPos,e1.yPos,e2.xPos,e2.yPos);
	}
	/**
	 * @param e1 Entity 1
	 * @param e2 Entity 2
	 * @return Compares entity1 and entity2 to see if they are currently colliding. 
	 */
	public static boolean areColliding(Entity e1,Entity e2){
		Line2D[] l1 = e1.getRotatedSides();
		Line2D[] l2 = e2.getRotatedSides();
		for(int i = 0; i < l1.length;i++){
			for(int j = 0; j < l2.length;j++){
				if(l1[i].intersectsLine(l2[j])){
					return true;
				}
			}
		}
		return false;
	}
	
}
