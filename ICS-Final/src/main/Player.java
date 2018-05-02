package main;

import entities.Ship;
import net.java.games.input.Controller;

public class Player {

	public Player(Controller controller){
		this.controller = controller;
	}
	@Deprecated
	public Player(Controller joy1,Controller joy2){
		
	}
	Ship target;
	Controller controller;
	int playerNum;
	String playerName;
	
	public void update(){
		
	}
	
}
