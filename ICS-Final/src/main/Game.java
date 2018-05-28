package main;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import com.ivan.xinput.enums.XInputButton;

import entities.Enemy;
import entities.EnemyCache;
import entities.Entity;
import entities.ParticleEffects;
import entities.Ship;
import entities.Structures;
import entities.VoxelParticle;
import input.InputGeneral;
import input.Player;
import tools.Debugger;

public class Game implements Runnable, ActionListener {
	public static ArrayList<Entity> entityArray = new ArrayList<Entity>();
	private static ArrayList<VoxelParticle> effectsArray = new ArrayList<VoxelParticle>();

	public static final int PLAYER_TEAM = 0;
	public static final int ENEMY_TEAM = 1;
	public static boolean gameOver = false;
	public static boolean debugPause = false;
	static Timer timer;


	static Random rand = new Random();

	/**
	 * method called to start new game with fresh stats
	 */
	public static Ship generatePlayer(){
		return null; //CREATE PLAYER
	}
	public static void startNew() {
		Properties.level = 5;
		Properties.score = 0;
		entityArray.clear();
		effectsArray.clear();
		gameOver = false;
		for (Player p : InputGeneral.players) {
//			Ship playerShip = new Ship(100, 100, Structures.PLAYER, turretPoints1, missilePoints, p.playerColor);
			Ship playerShip = generatePlayer();

			entityArray.add(playerShip);

			p.reset(playerShip);
		}

		for (int i = 0; i < Properties.level; i++) {
			double angle = rand.nextDouble() * Math.PI * 2;
			double x = Math.cos(angle) * (rand.nextInt(1000)+300);
			double y = Math.sin(angle) * (rand.nextInt(1000)+300);
			Entity e = EnemyCache.getEntity("light");
			if (i % 2 == 0) {
				e = EnemyCache.getEntity("medium");
			}
			e.xPos = x;
			e.yPos = y;
			entityArray.add(e);
		}
	}

	public static void addParticles(ArrayList<VoxelParticle> p) {
		effectsArray.addAll(p);
	}

	public static void addParticle(VoxelParticle p) {
		effectsArray.add(p);
	}
	public static void loop() {
		long t0 = System.nanoTime();
		InputGeneral.updatePlayers();
		ArrayList<Entity> lastArray = new ArrayList<Entity>();
		for (int i = 0; i < entityArray.size(); i++) {
			Entity p = entityArray.get(i);
			lastArray.add(p);
			if (p.isDead() && !p.isPlayerControlled) {
				entityArray.remove(i);
				if (p instanceof Enemy) {
					Properties.score += Properties.level;
				}
				continue;
			}
			if(!debugPause){
				p.update();
			}
			else if(p.isPlayerControlled){
				p.update();
			}
		}
		if (!gameOver) {
			gameOver = true;
			for (Player pl : InputGeneral.players) {
				if (!pl.playerShip.isDead()) {
					gameOver = false;
				}
			}
		} else {
			boolean allPlayersReady = true;
			for (Player pl : InputGeneral.players) {
				if (!pl.playerReady) {
					allPlayersReady = false;
				}
			}
			if (allPlayersReady) {
				startNew();
			}
		}
		boolean areaCleared = true;
		for (int i = 0; i < entityArray.size(); i++) {
			Entity p = entityArray.get(i);
			if (p.team == ENEMY_TEAM && p instanceof Enemy) {
				areaCleared = false;
			}
		}
		for (int i = 0; i < effectsArray.size(); i++) {
			Entity p = effectsArray.get(i);
			if (p.isDead()) {
				effectsArray.remove(i);
				continue;
			}
			p.update();
		}
		Panel.updateDrawables(entityArray, effectsArray);
		if (areaCleared) {
			nextLevel();
		}
		Debugger.lastGameDelay = System.nanoTime() - t0;
	}

	/**
	 * Method called to increments the level, upgrade the player ship, and spawn
	 * in a new wave.
	 */
	public static void nextLevel() {
		Properties.level++;
		// Clear the dead!
		for (int i = 0; i < entityArray.size(); i++) {
			Entity e = entityArray.get(i);
			if (e.isDead() && !e.isPlayerControlled)
				entityArray.remove(i);
		}
		for (Player p : InputGeneral.players) {
			if (!p.playerShip.isDead()) {
				addParticles(ParticleEffects.explode(p.playerShip.xPos, p.playerShip.yPos, 3, 40, 80));
				doPlayerUpgrades();
			}
		}
		for (int i = 0; i < Properties.level; i++) {
			double angle = rand.nextDouble() * Math.PI * 2;
			double x = Math.cos(angle) * (rand.nextInt(1000)+300);
			double y = Math.sin(angle) * (rand.nextInt(1000)+300);
			Entity e = EnemyCache.getEntity("light");
			if (i % 2 == 0) {
				e = EnemyCache.getEntity("medium");
			}
			e.xPos = x;
			e.yPos = y;
			entityArray.add(e);
		}
	}
	public static void doPlayerUpgrades(){
		//XXX player Upgrades
	}
	public void run() {
		timer = new Timer(13 , this);
		timer.start();
		timer.addActionListener(this);
		startNew();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		loop();
	}
}
