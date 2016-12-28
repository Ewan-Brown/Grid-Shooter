package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Game implements Runnable,ActionListener{
	 //TODO only need one arraylist of entity, later callinga generic update method with access to all everything,
	//XXX maybe create a properties class (?)	
	static ArrayList<Entity> entities = new ArrayList<Entity>();
	static ArrayList<Particle> effects = new ArrayList<Particle>();
	static Ship player;
	
	static long t1 = 0;
	static Timer timer = new Timer(16, new Game());
	public static void start(){
		player = new Ship(100, 100);
		player.team = 0;
		entities.add(player);
		player.c = Color.GREEN;
		entities.add(new Enemy(400,400));
		entities.add(new Enemy(400,400));
		entities.add(new Enemy(400,400));
		entities.add(new Enemy(400,400));
		entities.add(new Enemy(400,400));
		entities.add(new Enemy(400,400));
		entities.add(new Enemy(400,400));
		entities.add(new Enemy(400,400));
		entities.add(new Enemy(400,400));
		entities.add(new Enemy(400,400));
		entities.add(new Enemy(400,400));
		entities.add(new Enemy(400,400));

	}
	public static void loop(){
		Input.updateKeys();
		ArrayList<Entity> e = new ArrayList<Entity>(entities);
		e.addAll(effects);
		for(int i = 0; i < e.size();i++){
			Entity p = e.get(i);
			if(p.isDead()){
				e.remove(i);
				continue;
			}
			p.update();
		}
		Panel.updateDrawables(entities,effects);
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
