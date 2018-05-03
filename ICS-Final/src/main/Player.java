package main;

import com.ivan.xinput.XInputDevice;
import com.ivan.xinput.enums.XInputAxis;

import entities.Ship;

public class Player {

	public Player(XInputDevice XInputDevice) {
		this.XInputDevice = XInputDevice;
	}

	@Deprecated
	public Player(XInputDevice joy1, XInputDevice joy2) {

	}

	Ship target;
	XInputDevice XInputDevice;
	int playerNum;
	String playerName;

	public void update() {
		XInputDevice.poll();
		
			//Moving
			float deadZone = 0.2f;
			float x = XInputDevice.getComponents().getAxes().get(XInputAxis.LEFT_THUMBSTICK_X);
			float y = XInputDevice.getComponents().getAxes().get(XInputAxis.LEFT_THUMBSTICK_Y);
			x = (deadZone > Math.abs(x)) ? 0 : x;
			y = (deadZone > Math.abs(y)) ? 0 : y;
			if (!(x == 0 && y == 0)) {
				double movementAngle = Math.atan2(x, y);
				//TODO Fix Arcade mode
				target.move(movementAngle, false);
			}
			
			//Turning
			float x2 = XInputDevice.getComponents().getAxes().get(XInputAxis.RIGHT_THUMBSTICK_X);
			x2 = (deadZone > Math.abs(x2)) ? 0 : x2;
			if (x2 != 0) {
				double throttle = x2;
				target.turn(throttle);
			}
			
			//Boosting
			
			//Shooting
//			float rt = XInputDevice.getComponents().getAxes().get(XInputAxis.RIGHT_TRIGGER);
//			XInputDevice.setVibration((int)(rt * 65535f/2f), (int)(rt * 65535f/2f));
//			System.out.println(rt);
	}

}
