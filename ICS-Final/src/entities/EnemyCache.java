package entities;

import java.awt.Point;
import java.awt.geom.Point2D;
//import java.awt.Shape;
import java.util.Hashtable;

import main.Game;
import tools.GameMath;

public class EnemyCache {

	private static Hashtable<String, Enemy> entityMap = new Hashtable<String, Enemy>();
	public static String[] types = { "light", "medium", "kamikaze", "sniper" };
	static Point[] lightTurrets;
	static Point[] lightLaunchers;
	static Point[] mediumTurrets;
	static Point[] mediumLaunchers;

	public static void loadCache() {
		lightTurrets = new Point[1];
		mediumTurrets = new Point[0];
		lightLaunchers = new Point[0];
		mediumLaunchers = new Point[0];
		Enemy light = new Enemy(0, 0, Structures.LIGHT, lightTurrets, lightLaunchers) {
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
				caliber = 10;
				
			}

			public void fumes(double t, double angle) {
				Point2D p = centerPoint;
				 double angle2 = angle + ((Math.random() - 0.5) * 50);
				 Game.addParticles(ParticleEffects.fishtail(getX(), getY(), angle, 10,40));
//				Game.addParticles(ParticleEffects.helix((float) (p.getX() + xPos), (float) (p.getY() + yPos), angle, 20,
//						getLife() * 80));
			}
		};
		Enemy medium = new Enemy(0, 0, Structures.MEDIUM, mediumTurrets, mediumLaunchers) {
			{
				int z = 0;
				for (int i = 0; i < Structures.getStructure(Structures.MEDIUM).length; i++) {
					Point p = Structures.getStructure(Structures.MEDIUM)[i];
					int c = (int) GameMath.getDistance(0, 0, p.getX(), p.getY());
					if (c > z) {
						z = c;
					}
				}
				radius = z;
				maxHealth = 400;
				health = 400;
				caliber = 60;
				maxBulletCooldown = 60;
				bulletAccuracy = 8;
				speed = 0.02;
				minDist = 200;
				maxDist = 400;
				strafeChance = 20;
			}
			public void fumes(double t, double angle) {
				Point2D p = centerPoint;
				Game.addParticles(ParticleEffects.helix((float) (p.getX() + xPos), (float) (p.getY() + yPos), angle, 10,
						30));
			}
		};
		entityMap.put("light", light);
		entityMap.put("medium", medium);

	}

	public static Enemy getEntity(String id) {
		Enemy cachedEntity = entityMap.get(id);
		return (Enemy) cachedEntity.clone();
	}

}
