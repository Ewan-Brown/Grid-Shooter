package entities;

import java.awt.Point;
//import java.awt.Shape;
import java.util.Hashtable;

public class EnemyCache {

	private static Hashtable<String, Enemy> entityMap  = new Hashtable<String, Enemy>();
	public static String[] types = {"light","medium","kamikaze","sniper"};
	static Point[] lightTurrets;
	static Point[] lightLaunchers;
	static Point[] mediumTurrets;
	static Point[] mediumLaunchers;
	public static void loadCache(){
		lightTurrets = new Point[1];
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
