package main;

import java.util.ArrayList;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;


public class InputGeneral {
	
	static ArrayList<Controller> controllers = new ArrayList<>();
	static ArrayList<Player> players = new ArrayList<>();
	public static void checkControllers(){
		controllers.clear(); //TODO Maybe dont have this hard reset?
		players.clear();
		Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
		for(int i = 0; i < ca.length;i++){
			Controller c = ca[i];
			if(c.getType() == Type.GAMEPAD){
				controllers.add(c);
				players.add(new Player(c));
			}
		}
	}
	public static void updatePlayers(){
		for(Player p : players){
			p.update();
		}
	}
}
