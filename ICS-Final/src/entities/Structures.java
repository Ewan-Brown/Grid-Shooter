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
	/*
	 * mediumStructure = new Point[12];
		mediumStructure[0] = new Point(20,4);
		mediumStructure[1] = new Point(8,6);
		mediumStructure[2] = new Point(8,10);
		mediumStructure[3] = new Point(12,12);
		mediumStructure[4] = new Point(12,20);
		mediumStructure[5] = new Point(8,22);
		mediumStructure[6] = new Point(8,26);
		mediumStructure[7] = new Point(20,28);
		mediumStructure[8] = new Point(4,32);
		mediumStructure[9] = new Point(0,28);
		mediumStructure[10] = new Point(0,4);
		mediumStructure[11] = new Point(4,0);
		mediumTurrets = new Point[2];
		mediumTurrets[0] = mediumStructure[0];
		mediumTurrets[1] = mediumStructure[7];
		mediumLaunchers = new Point[0];
		lightStructure = new Point[7];
		lightStructure[0] = new Point(24,12);
		lightStructure[1] = new Point(8,16);
		lightStructure[2] = new Point(16,24);
		lightStructure[3] = new Point(0,16);
		lightStructure[4] = new Point(0,8);
		lightStructure[5] = new Point(16,0);
		lightStructure[6] = new Point(8,8);
		Point[] bullet;
		{
			bullet = new Point[4];
			bullet[0] = new Point(15,3);
			bullet[1] = new Point(3,6);
			bullet[2] = new Point(0,3);
			bullet[3] = new Point(3,0);
			MEDIUM BULLET
			bullet = new Point[4];
			bullet[0] = new Point(20,4);
			bullet[1] = new Point(4,8);
			bullet[2] = new Point(0,4);
			bullet[3] = new Point(4,0);
	Point[] particle;
	{
		particle = new Point[4];
		particle[0] = new Point(0,0);
		particle[1] = new Point(4,0);
		particle[2] = new Point(4,4);
		particle[3] = new Point(0,4);
	}


	static Point[] laser;
	{

		laser = new Point[4];
		laser[0] = new Point(0,0);
		laser[1] = new Point(2000,0);
		laser[2] = new Point(2000,2);
		laser[3] = new Point(0,2);
	}
	*/


}
