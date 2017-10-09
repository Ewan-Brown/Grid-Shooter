package entities;

import java.awt.Point;
//import java.awt.Shape;
import java.util.Hashtable;

public class EnemyCache {

	private static Hashtable<String, Enemy> entityMap  = new Hashtable<String, Enemy>();
	public static String[] types = {"light","medium","kamikaze","sniper"};
	static Point[] lightStructure;
	static Point[] mediumStructure;
	//Arrays of points representing where bullets should be shot from/ how many. 3 levels upgrade sequentially

	static Point[] lightTurrets;
	static Point[] lightLaunchers;
	static Point[] mediumTurrets;
	static Point[] mediumLaunchers;
	//Initialized the ships' points and turret locations
	//		points = new Point[11];
	//		points[0] = new Point(0,0);
	//		points[1] = new Point(8,0);
	//		points[2] = new Point(8,4);
	//		points[3] = new Point(12,8);
	//		points[4] = new Point(12,16);
	//		points[5] = new Point(8,20);
	//		points[6] = new Point(24,20);
	//		points[7] = new Point(24,24);
	//		points[8] = new Point(8,24);
	//		points[9] = new Point(4,28);
	//		points[10] = new Point(0,28);
	//		turrets = new Point[1];
	//		turrets[0] = points[8];
	//	points = new Point[12];
	//	points[0] = new Point(20,4);
	//	points[1] = new Point(8,6);
	//	points[2] = new Point(8,10);
	//	points[3] = new Point(12,12);
	//	points[4] = new Point(12,20);
	//	points[5] = new Point(8,22);
	//	points[6] = new Point(8,26);
	//	points[7] = new Point(20,28);
	//	points[8] = new Point(4,32);
	//	points[9] = new Point(0,28);
	//	points[10] = new Point(0,4);
	//	points[11] = new Point(4,0);
	//	turrets = new Point[2];
	//	turrets[0] = points[0];
	//	turrets[1] = points[7];
	public static void loadCache(){
		
		lightTurrets = new Point[0];
		mediumTurrets = new Point[0];
		lightLaunchers = new Point[0];
		mediumLaunchers = new Point[0];
		Enemy light = new Enemy(0,0,Structures.LIGHT,lightTurrets,lightLaunchers){{
			maxHealth = 100;
			health = 100;
		}};
		Enemy medium = new Enemy(0,0,Structures.MEDIUM, mediumTurrets, mediumLaunchers){{
			maxHealth = 400;
			health = 400;
			caliber = 150;
			maxBulletCooldown =  60;
			bulletAccuracy = 8;
			speed = 0.02;
			minDist = 200;
			maxDist = 400;
			strafeChance = 20;
		}};
		entityMap.put("light", light);
		entityMap.put("medium", medium);		



	}
	public static Enemy getEntity(String id) {
		Enemy cachedEntity = entityMap.get(id);
		return (Enemy) cachedEntity.clone();
	}

}
