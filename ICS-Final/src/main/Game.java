package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Game implements Runnable,ActionListener{
	//TODO only need one arraylist of entity, later callinga generic update method with access to all everything,
	//XXX maybe create a properties class (?)	
	static ArrayList<Particle> particles = new ArrayList<Particle>();
	static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	static Ship player;
	
	static long t1 = 0;
	static Timer timer = new Timer(16, new Game());
	public static void start(){
		particles.add(new Particle(100,100, 0.1, 0));
		particles.add(new Particle(100,200, 0, 0.1));
		bullets.add(new Bullet(100,200, 0, 1, 0));
	}
	public static void loop(){
		for(int i = 0; i < bullets.size();i++){
			Bullet p = bullets.get(i);
			p.update();
		}
		ArrayList<Drawable> d = new ArrayList<Drawable>();
		d.addAll(bullets);
		d.addAll(particles);
		Panel.updateDrawables(d);
	}
	public void run() {
		start();
		timer.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		loop();
	}
}
