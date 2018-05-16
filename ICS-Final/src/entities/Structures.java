package entities;

import java.awt.Point;

public class Structures {

	static Point[][] structures = new Point[12][];
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
	public static int STAR = 11;
	public static void init(){
		Point[] bullet_medium = new Point[4];
		bullet_medium[0] = new Point(15,3);
		bullet_medium[1] = new Point(3,6);
		bullet_medium[2] = new Point(0,3);
		bullet_medium[3] = new Point(3,0);
		Point[] bullet_big = new Point[4];
		bullet_big[0] = new Point(20,4);
		bullet_big[1] = new Point(4,8);
		bullet_big[2] = new Point(0,4);
		bullet_big[3] = new Point(4,0);
		Point[] laser = new Point[4];
		laser[0] = new Point(0,0);
		laser[1] = new Point(2000,0);
		laser[2] = new Point(2000,2);
		laser[3] = new Point(0,2);
		Point[] lightStructure = new Point[7];
		lightStructure[0] = new Point(24,12);
		lightStructure[1] = new Point(8,16);
		lightStructure[2] = new Point(16,24);
		lightStructure[3] = new Point(0,16);
		lightStructure[4] = new Point(0,8);
		lightStructure[5] = new Point(16,0);
		lightStructure[6] = new Point(8,8);
		Point[] starStructure = new Point[8];
		starStructure[0] = new Point(12,24);
		starStructure[1] = new Point(16,16);
		starStructure[2] = new Point(24,12);
		starStructure[3] = new Point(16,8);
		starStructure[4] = new Point(12,0);
		starStructure[5] = new Point(8,8);
		starStructure[6] = new Point(0,12);
		starStructure[7] = new Point(8,16);
		Point[] mediumStructure = new Point[12];
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
		structures[PLAYER] = lightStructure;
		structures[LIGHT] = lightStructure;
		structures[MEDIUM] = mediumStructure;
		structures[BULLET] = bullet_medium;
		structures[BIG_BULLET] = bullet_big;
		structures[LASER] = laser;
		structures[STAR] = starStructure;
		Point[][] particles = new Point[4][4];
		for(int i = 0; i < particles.length;i++){
			int s = 3 + i;
			particles[i][0] = new Point(0,0);
			particles[i][1] = new Point(s,0);
			particles[i][2] = new Point(s,s);
			particles[i][3] = new Point(0,s);
			structures[7+i] = particles[i];
		}
	}
	public static Point[] getStructure(int s){
		try{
			return structures[s];
		}catch(NullPointerException e){
			return null;
		}
	}

}
