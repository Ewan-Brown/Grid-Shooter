package main;

import java.util.ArrayList;

import com.ivan.xinput.XInputDevice14;
import com.ivan.xinput.exceptions.XInputNotLoadedException;

public class InputGeneral{

	static ArrayList<XInputDevice14> XInputDevices = new ArrayList<>();
	static ArrayList<Player> players = new ArrayList<>();

	public static void checkXInputDevices() {
		XInputDevices.clear(); // TODO Maybe dont have this hard reset?
		players.clear();
		for (int i = 0; i < 1; i++) {
			XInputDevice14 c;
			try {
				c = XInputDevice14.getDeviceFor(0);
				XInputDevices.add(c);
				players.add(new Player(c));
			} catch (XInputNotLoadedException e) {
				e.printStackTrace();
			}
			
		}
	}

	public static void updatePlayers() {
		for (Player p : players) {
			p.update();
		}
	}

}
