package entities;

import java.awt.Point;

public class Structures {
	
	static Point[][] structures;
	public static int PLAYER = 0;
	public static int LIGHT = 1;
	public static int MEDIUM = 2;
	public static int TURRET = 3;
	public static int BULLET = 4;
	public static int BIG_BULLET = 5;
	public static int LASER = 6;
	public static int PARTICLE1 = 7; //3-3
	public static int PARTICLE2 = 8; //5-5
	public static int PARTICLE3 = 9; //6-6
	public static int PARTICLE4 = 10;//8-8

	
	
	public static Point[] getStructure(int s){
		return structures[s];
	}


}
