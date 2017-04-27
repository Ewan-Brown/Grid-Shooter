package Entities;

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
		mediumStructure = new Point[12];
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
		lightTurrets = new Point[1];
		lightTurrets[0] = lightStructure[0];
		lightLaunchers = new Point[1];
		lightLaunchers[0] = lightStructure[0];
		Enemy light = new Enemy(0,0,lightStructure,lightTurrets,lightLaunchers){{

		}};
		Enemy medium = new Enemy(0,0,mediumStructure, mediumTurrets, mediumLaunchers){{
			
		}};
		//		Enemy sniper = new Enemy(0,0,lightStructure,turretPoints1,missilePoints){{
		//		}};
		//		Enemy medium = new Enemy(0,0,lightStructure,turretPoints1,missilePoints){{
		//		}};
		entityMap.put("light", light);
				entityMap.put("medium", medium);		
		//		entityMap.put("sniper", sniper);



	}
	public static Enemy getEntity(String id) {
		Enemy cachedEntity = entityMap.get(id);
		return (Enemy) cachedEntity.clone();
	}

}
