package main;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.ivan.xinput.XInputDevice;
import com.ivan.xinput.exceptions.XInputNotLoadedException;

import main.Panel.CustColor;
import tools.GameMath;

public class InputGeneral {

	static ArrayList<XInputDevice> XInputDevices = new ArrayList<>();
	static ArrayList<Player> players = new ArrayList<>();

	public static void init() {
		checkXInputDevices();

	}

	public static void checkXInputDevices() {
		XInputDevices.clear(); // TODO Maybe dont have this hard reset?
		players.clear();
		int i = 0;
		while(true){
			XInputDevice c;
			try {
				c = XInputDevice.getDeviceFor(i);
 				if(!c.isConnected()){
					return;
				}
				XInputDevices.add(c);
				players.add(new Player(c, CustColor.values()[CustColor.PLAYER1.ordinal() + i]));
			} catch (XInputNotLoadedException e) {
				e.printStackTrace();
			}
			i++;
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
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).playerShip.isDead()) {
				continue;
			}
				for (int j = i + 1; j < players.size(); j++) {
					if (players.get(j).playerShip.isDead()) {
						continue;
					}
					double dC = GameMath.getDistance(players.get(i).playerShip, players.get(j).playerShip);
					if (dC > d) {
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
