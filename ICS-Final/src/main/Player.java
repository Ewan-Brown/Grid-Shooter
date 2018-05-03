package main;

import com.ivan.xinput.XInputDevice;
import com.ivan.xinput.enums.XInputAxis;
import com.ivan.xinput.enums.XInputButton;

import entities.Enemy;
import entities.Entity;
import entities.Ship;
import tools.GameMath;

public class Player extends com.ivan.xinput.listener.SimpleXInputDeviceListener {

	public Player(XInputDevice XInputDevice) {
		this.XInputDevice = XInputDevice;
		XInputDevice.addListener(this);
	}

	@Deprecated
	public Player(XInputDevice joy1, XInputDevice joy2) {

	}

	Ship playerShip;
	XInputDevice XInputDevice;
	int playerNum;
	String playerName;
	boolean readyForNextRound = false;

	public void update() {
		//TODO If dead skip the controls but make the control alternate vibrations!
		//++This is messy move to player class please

		if (playerShip.isDead()) {
			double z = System.currentTimeMillis() % 1000; 
//			int zInt = (int)((z/500) * 65535d);
			if(z > 500){
				XInputDevice.setVibration(65535, 0);
			}
			else{
				XInputDevice.setVibration(0, 65535);
			}
			return;
		}
		XInputDevice.poll();
		// Moving
		float deadZone = 0.2f;
		float x = XInputDevice.getComponents().getAxes().get(XInputAxis.LEFT_THUMBSTICK_X);
		float y = XInputDevice.getComponents().getAxes().get(XInputAxis.LEFT_THUMBSTICK_Y);
		x = (deadZone > Math.abs(x)) ? 0 : x;
		y = (deadZone > Math.abs(y)) ? 0 : y;
		if (!(x == 0 && y == 0)) {
			double movementAngle = Math.atan2(x, y);
			// TODO Fix Arcade mode
			playerShip.move(movementAngle, false);
		}

		// Turning
		float x2 = XInputDevice.getComponents().getAxes().get(XInputAxis.RIGHT_THUMBSTICK_X);
		x2 = (deadZone > Math.abs(x2)) ? 0 : x2;
		if (x2 != 0) {
			double throttle = x2;
			playerShip.turn(throttle);
		}

		// Boosting

		// Shooting
		float rt = XInputDevice.getComponents().getAxes().get(XInputAxis.RIGHT_TRIGGER);
		float lt = XInputDevice.getComponents().getAxes().get(XInputAxis.LEFT_TRIGGER);
		 XInputDevice.setVibration((int) (lt * 65535f / 2f), (int) (rt *65535f / 2f));
		if (rt > 0) {
			playerShip.shootBullet();
		}

		// Targetting
		updateTarget();
		if (isTargetting && target != null) {
			double xT = target.xPos - playerShip.xPos;
			double yT = target.yPos - playerShip.yPos;
			playerShip.turnToTarget(Math.toDegrees(Math.atan2(yT, xT)));
		}

	}

	public void connected() {
	}

	@Override
	public void disconnected() {
		System.err.println("Player.jar - A Controller has been disconnected?");
	}

	Ship target = null;
	boolean isTargetting = false;

	public void buttonChanged(final XInputButton button, final boolean pressed) {
		if (button == XInputButton.LEFT_SHOULDER && pressed) {
			isTargetting = !isTargetting;
		}
		if (playerShip.isDead()) {
			readyForNextRound = true;
		}
	}

	public void updateTarget() {
		if (isTargetting && (target == null || target.isDead())) {
			double d = Double.MAX_VALUE;
			Ship c = null;
			for (Entity e : Game.entityArray) {
				if (e instanceof Enemy && !e.isDead()) {
					double d2 = GameMath.getDistance(playerShip, e);
					if (d2 < d) {
						d = d2;
						c = (Ship) e;
					}
				}
			}
			target = c;
		} else if (!isTargetting) {
			target = null;
		}
	}
}
