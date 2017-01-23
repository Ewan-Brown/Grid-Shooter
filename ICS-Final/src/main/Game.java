package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

public class Game implements Runnable,ActionListener{
	static ArrayList<Entity> entities = new ArrayList<Entity>();
	static ArrayList<Particle> effects = new ArrayList<Particle>();
	static Ship player;
	static final int PLAYER_TEAM = 0;
	static final int ENEMY_TEAM = 1;
	static int score;
	static int level = 1;
	static long t1 = 0;
	static boolean gameOver = false;
	static Timer timer = new Timer(10, new Game());
	static Point[] points;
	static Point[] turrets;
	static Random rand = new Random();
	{
		points = new Point[7];
		points[0] = new Point(24,12);
		points[1] = new Point(8,16);
		points[2] = new Point(16,24);
		points[3] = new Point(0,16);
		points[4] = new Point(0,8);
		points[5] = new Point(16,0);
		points[6] = new Point(8,8);
		turrets = new Point[1];
		turrets[0] = points[0];
	}
	public static void startNew(){
		entities.clear();
		effects.clear();
		level = 1;
		gameOver = false;
		player = new Ship(100, 100,points,turrets);
		player.team = PLAYER_TEAM;
		entities.add(player);
		player.color = Color.GREEN;
		player.accuracy = Properties.PLAYER_BASE_ACCURACY;
		player.MAX_COOLDOWN = Properties.PLAYER_BASE_COOLDOWN;
		for(int i = 0; i < level;i++){
			double angle = rand.nextDouble() * Math.PI * 2;
			double x = Math.cos(angle) * rand.nextInt(400) + 600;
			double y = Math.sin(angle) * rand.nextInt(400) + 600;
			entities.add(new Enemy(player.xPos + x,player.yPos + y,points,turrets));
		}

	}
	public static void loop(){
		boolean areaCleared = true;
		for(int i = 0; i < entities.size();i++){
			Entity p = entities.get(i);
			if(p.team == ENEMY_TEAM){
				areaCleared = false;
			}
			if(p.isDead() && p != player){
				entities.remove(i);
				continue;
			}
			if(player.isDead() && !gameOver){
				gameOver = true;
			}
			p.update();
		}
		for(int i = 0; i < effects.size();i++){
			Entity p = effects.get(i);
			if(p.isDead()){
				effects.remove(i);
				continue;
			}
			p.update();
		}
		Panel.updateDrawables(entities,effects);
		if(areaCleared){
			nextLevel();
		}
	}
	public static void nextLevel(){
		level++;
		player.maxHealth += 10;
		player.health = player.maxHealth;
		for(int i = 0; i < level;i++){
			double angle = rand.nextDouble() * Math.PI * 2;
			double x = Math.cos(angle) * rand.nextInt(400) + 600;
			double y = Math.sin(angle) * rand.nextInt(400) + 600;
			entities.add(new Enemy(player.xPos + x,player.yPos + y,points,turrets));
		}
	}
	public void run() {
		startNew();
		timer.start();
		timer.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		loop();
		Input.updateKeys();
	}
}
