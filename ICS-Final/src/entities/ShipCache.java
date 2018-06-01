package entities;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
//import java.awt.Shape;
import java.util.Hashtable;

import entities.turrets.Turret;
import main.Game;
import main.Panel.CustColor;
import tools.GameMath;

public class ShipCache {

	private static Hashtable<String, Ship> entityMap = new Hashtable<>();
	public static String[] types = { "light", "medium", "kamikaze", "sniper" };
//	public static void loadCache() {
//		lightTurrets = new Point[1];
//		mediumTurrets = new Point[0];
//		lightLaunchers = new Point[0];
//		mediumLaunchers = new Point[0];
//		Enemy light = new Enemy(0, 0, Structures.LIGHT, lightTurrets, lightLaunchers) {
//			{
//				int z = 0;
//				for (int i = 0; i < Structures.getStructure(Structures.LIGHT).length; i++) {
//					Point p = Structures.getStructure(Structures.LIGHT)[i];
//					int c = (int) GameMath.getDistance(0, 0, p.getX(), p.getY());
//					if (c > z) {
//						z = c;
//					}
//				}
//				radius = z;
//				maxHealth = 100;
//				health = 100;
////				caliber = 10;
//				
//			}
//
//			public void fumes(double t, double angle) {
//				Point2D p = centerPoint;
//				 double angle2 = angle + ((Math.random() - 0.5) * 50);
//				 Game.addParticles(ParticleEffects.fishtail(getX(), getY(), angle, 10,40));
////				Game.addParticles(ParticleEffects.helix((float) (p.getX() + xPos), (float) (p.getY() + yPos), angle, 20,
////						getLife() * 80));
//			}
//		};
//		Enemy medium = new Enemy(0, 0, Structures.MEDIUM, mediumTurrets, mediumLaunchers) {
//			{
//				int z = 0;
//				for (int i = 0; i < Structures.getStructure(Structures.MEDIUM).length; i++) {
//					Point p = Structures.getStructure(Structures.MEDIUM)[i];
//					int c = (int) GameMath.getDistance(0, 0, p.getX(), p.getY());
//					if (c > z) {
//						z = c;
//					}
//				}
//				radius = z;
//				maxHealth = 400;
//				health = 400;
////				caliber = 60;
////				maxBulletCooldown = 60;
////				bulletAccuracy = 8;
//				speed = 0.02;
//				minDist = 200;
//				maxDist = 400;
//				strafeChance = 20;
//				turnSpeed = 6;
//			}
//
//			public void fumes(double t, double angle) {
//				Point2D p = centerPoint;
//				Game.addParticles(ParticleEffects.helix((float) (p.getX() + xPos), (float) (p.getY() + yPos), angle, 10,
//						30));
//			}
//		};
//		Enemy sniper = new Enemy(0, 0, Structures.MEDIUM, mediumTurrets, mediumLaunchers) {
//			{
//				int z = 0;
//				for (int i = 0; i < Structures.getStructure(Structures.MEDIUM).length; i++) {
//					Point p = Structures.getStructure(Structures.MEDIUM)[i];
//					int c = (int) GameMath.getDistance(0, 0, p.getX(), p.getY());
//					if (c > z) {
//						z = c;
//					}
//				}
//				radius = z;
//				maxHealth = 300;
//				health = 300;
////				caliber = 100;
////				muzzleVelocity = 14;
////				maxBulletCooldown = 200;
////				bulletAccuracy = 1;
//				speed = 0.01;
//				minDist = 400;
//				maxDist = 1000;
//				strafeChance = 20;
//				turnSpeed = 4;
//			}
//		};
////		entityMap.put("sniper", sniper);
//		entityMap.put("light", light);
////		entityMap.put("medium", medium);
//		
//		entityMap.put("medium", sniper);
//
//	}
	public static Ship getEntity(String id, CustColor c, boolean player){
		Ship e = null;
		ShipAI ai = (player) ? new ShipAI(e) : new BasicAI(e);
		switch(id){
		case "light":
			ArrayList<Turret> tList = new ArrayList<Turret>(0);
			e = new Ship(0, 0, Structures.LIGHT, tList, c, ai) {
				{
					int z = 0;
					for (int i = 0; i < Structures.getStructure(Structures.LIGHT).length; i++) {
						Point p = Structures.getStructure(Structures.LIGHT)[i];
						int c = (int) GameMath.getDistance(0, 0, p.getX(), p.getY());
						if (c > z) {
							z = c;
						}
					}
					radius = z;
					maxHealth = 100;
					health = 100;
//					caliber = 10;
					
				}

				public void fumes(double t, double angle) {
					Point2D p = centerPoint;
					 double angle2 = angle + ((Math.random() - 0.5) * 50);
					 Game.addParticles(ParticleEffects.fishtail(getX(), getY(), angle, 10,40));
				}
			};
			break;
		case "medium":
			
			break;
		case "sniper":
			break;
			
		}
		e.team = (player) ? Game.PLAYER_TEAM : Game.ENEMY_TEAM;
		return e;
	}

}
