package main;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.ivan.xinput.XInputDevice14;
import com.ivan.xinput.exceptions.XInputNotLoadedException;

import tools.GameMath;

public class InputGeneral {

	static ArrayList<XInputDevice14> XInputDevices = new ArrayList<>();
	static ArrayList<Player> players = new ArrayList<>();

	public static void checkXInputDevices() {
		XInputDevices.clear(); // TODO Maybe dont have this hard reset?
		players.clear();
		for (int i = 0; i < 2; i++) {
			XInputDevice14 c;
			try {
				c = XInputDevice14.getDeviceFor(i);
				XInputDevices.add(c);
				players.add(new Player(c));
			} catch (XInputNotLoadedException e) {
				e.printStackTrace();
			}

		}
	}

	public static Point2D getCenterOfPlayers() {
		double x = 0;
		double y = 0;
		int playerCount = 0;
		for (Player p : players) {
			if (!p.playerShip.isDead()) {
				playerCount++;
				x += p.playerShip.getX();
				y += p.playerShip.getY();
			}
		}
		return new Point2D.Double(x / playerCount, y / playerCount);
	}

	public static double getMaxPlayerSeparationDist() {
		double d = 0;
		for(int i = 0; i < players.size();i++){
			for(int j = 0; j < players.size();j++){
				double dC = GameMath.getDistance(players.get(i).playerShip, players.get(j).playerShip);
				if(dC > d){
					d = dC;
				}
			}
		}
		return d;
	}

	public static void updatePlayers() {
		for (Player p : players) {
			p.update();
		}
	}

}
