package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import entities.Enemy;
import entities.EnemyCache;
import entities.Entity;
import entities.Particle;
import entities.Ship;
import entities.Structures;

public class Game implements Runnable,ActionListener{
	/**
	 * An arraylist containing all interactable entities
	 */
	public static ArrayList<Entity> entityArray = new ArrayList<Entity>();
	/**
	 * An arraylist containing all aesthetic particles
	 */
	public static ArrayList<Particle> effectsArray = new ArrayList<Particle>();
	/**
	 * Pointer to the ship that is the player
	 */
	static Ship player;
	//Constants for team numbers, used in hit collision
	public static final int PLAYER_TEAM = 0;
	public static final int ENEMY_TEAM = 1;
	/**
	 * Flag for if the player has died yet
	 */
	static boolean gameOver = false;
	/**
	 * 	Game update timer, runs at 100hz
	 */
	static Timer timer;
	/**
	 * Array of points representing the polygon shape of the ship design. very easy to change and add new ships
	 */
	static Point[] shipStructure;
	//Arrays of points representing where bullets should be shot from/ how many. 3 levels upgrade sequentially

	/**
	 * first set of turrets, only 1
	 */
	static Point[] turretPoints1;
	/**
	 * second set of turrets, 2 turrets
	 */
	static Point[] turretPoints2;
	/**
	 * third, final set of turrets, 5 turrets
	 */
	static Point[] turretPoints3;
	/**
	 * set of points for missile launchers
	 */
	static Point[] missilePoints;
	//Initialized the ships' points and turret locations
	{
		shipStructure = new Point[7];
		shipStructure[0] = new Point(24,12);
		shipStructure[1] = new Point(8,16);
		shipStructure[2] = new Point(16,24);
		shipStructure[3] = new Point(0,16);
		shipStructure[4] = new Point(0,8);
		shipStructure[5] = new Point(16,0);
		shipStructure[6] = new Point(8,8);
		turretPoints1 = new Point[1];
		turretPoints1[0] = shipStructure[0];
		turretPoints2 = new Point[2];
		turretPoints2[0] = shipStructure[6];
		turretPoints2[1] = shipStructure[1];
		turretPoints3 = new Point[5];
		turretPoints3[0] = shipStructure[5];
		turretPoints3[1] = shipStructure[6];
		turretPoints3[2] = shipStructure[0];
		turretPoints3[3] = shipStructure[1];
		turretPoints3[4] = shipStructure[2];
		missilePoints = new Point[1];
		missilePoints[0] = shipStructure[0];

	}

	static Random rand = new Random();

	/**
	 * method called to start new game with fresh stats
	 */
	public static void startNew(){
		Properties.level = 1;
		Properties.score = 0;
		entityArray.clear();
		effectsArray.clear();
		gameOver = false;
		player = new Ship(100, 100,Structures.PLAYER,turretPoints1,missilePoints);
		player.bulletAccuracy = Properties.PLAYER_BASE_ACCURACY;
		player.maxBulletCooldown = Properties.PLAYER_BASE_COOLDOWN;
		player.team = PLAYER_TEAM;
		entityArray.add(player);
		player.color = 0;
		for(int i = 0; i < Properties.level;i++){
			double angle = rand.nextDouble() * 10;
			double x = Math.cos(angle) * 1000;
			double y = Math.sin(angle) * 1000;
			Entity e = EnemyCache.getEntity("light");
			e.xPos = x;
			e.yPos = y;
			entityArray.add(e);
		}
	}
	/**
	 * game update method, called 100 times per second to update all entities
	 */
	public static void loop(){
		if(effectsArray.size() > 5000){
			int f = effectsArray.size() - 5000;
			for(int i = 0; i < f;i++){
				effectsArray.remove(0);
			}
		}
		boolean areaCleared = true;
		for(int i = 0; i < entityArray.size();i++){
			Entity p = entityArray.get(i);
			if(p instanceof Ship && p.team == ENEMY_TEAM){
				areaCleared = false;
			}
			if(p.isDead() && p != player){
				entityArray.remove(i);
				if(p instanceof Enemy){
					Properties.score += Properties.level;
				}
				continue;
			}
			if(player.isDead() && !gameOver){
				gameOver = true;
			}
			p.update();
		}
		for(int i = 0; i < effectsArray.size();i++){
			Entity p = effectsArray.get(i);
			if(p.isDead()){
				effectsArray.remove(i);
				continue;
			}
			p.update();
		}
		Panel.updateDrawables(entityArray,effectsArray);
		if(areaCleared){
			nextLevel();
		}
	}
	/**
	 * Method called to increments the level, upgrade the player ship, and spawn in a new wave.
	 */
	public static void nextLevel(){
		Properties.level++;
		player.missiles = (Properties.level / 2) + 1;
		if(Properties.level == 5){
			player.bulletTurrets = turretPoints2;
		}
		if(Properties.level == 10){
			player.bulletAccuracy = 2;
			player.turnSpeed = 8;
			player.laserOn = true;
		}
		if(Properties.level == 20){
			player.bulletTurrets = turretPoints3;
			player.maxBulletCooldown = 10;
		}
		if(Properties.level == 30){
		}
		player.maxHealth += 10;
		player.health = player.maxHealth;
		for(int i = 0; i < Properties.level;i++){

			double angle = rand.nextDouble() * Math.PI * 2;
			double x = Math.cos(angle) * rand.nextInt(500);
			double y = Math.sin(angle) * rand.nextInt(500);
			Entity e = EnemyCache.getEntity("light");
			if(i % 2 == 0){
				e = EnemyCache.getEntity("medium");
			}
			e.xPos = x;
			e.yPos = y;
			entityArray.add(e);
		}
	}
	public void run() {
		timer = new Timer(10, this);
		timer.start();
		timer.addActionListener(this);
		startNew();

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		loop();
		Input.updateKeys();
	}
}
