package main;

import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Game implements Runnable,ActionListener{
	 //TODO only need one arraylist of entity, later callinga generic update method with access to all everything,
	//XXX maybe create a properties class (?)	
	static ArrayList<Entity> entities = new ArrayList<Entity>();
	static Ship player;
	
	static long t1 = 0;
	static Timer timer = new Timer(16, new Game());
	public static void start(){
		entities.add(player = new Ship(100, 100));
	}
	public static void loop(){
		Input.updateKeys();
		for(int i = 0; i < entities.size();i++){
			Entity p = entities.get(i);
			p.update();
		}
		ArrayList<Drawable> d = new ArrayList<Drawable>(entities);
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
