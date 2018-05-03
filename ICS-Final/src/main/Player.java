package main;

import entities.Ship;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;

public class Player {

	public Player(Controller controller) {
		this.controller = controller;
	}

	@Deprecated
	public Player(Controller joy1, Controller joy2) {

	}

	Ship target;
	Controller controller;
	int playerNum;
	String playerName;

	public void update() {
		controller.poll();
		if (controller.getType() == Controller.Type.GAMEPAD) {
			float deadZone = 0.2f;
			float x = controller.getComponent(Identifier.Axis.X).getPollData();
			float y = -controller.getComponent(Identifier.Axis.Y).getPollData();
			x = (deadZone > Math.abs(x)) ? 0 : x;
			y = (deadZone > Math.abs(y)) ? 0 : y;
			if (!(x == 0 && y == 0)) {
				double movementAngle = Math.atan2(x, y);
				//TODO Fix Arcade mode
				target.move(movementAngle, false);
			}
			float x2 = controller.getComponent(Identifier.Axis.RX).getPollData();
//			float y2 = -controller.getComponent(Identifier.Axis.RY).getPollData();
			x2 = (deadZone > Math.abs(x2)) ? 0 : x2;
//			y2 = (deadZone > Math.abs(y2)) ? 0 : y2;
			if (x2 != 0) {
				double throttle = x2;
				target.turn(throttle);
			}
		}
	}

}
