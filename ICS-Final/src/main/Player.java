package main;

import java.awt.Color;
import java.awt.geom.Point2D;

import com.ivan.xinput.XInputDevice;
import com.ivan.xinput.enums.XInputAxis;
import com.ivan.xinput.enums.XInputButton;

import entities.Enemy;
import entities.Entity;
import entities.Ship;
import main.Panel.CustColor;
import tools.GameMath;

public class Player extends com.ivan.xinput.listener.SimpleXInputDeviceListener {

	public Player(XInputDevice XInputDevice, CustColor c) {
		this.controller = XInputDevice;
		XInputDevice.addListener(this);
		playerColor = c;
	}
	CustColor playerColor;
	@Deprecated
	public Player(XInputDevice joy1, XInputDevice joy2) {

	}

	boolean enableVibration = false;
	Ship playerShip;
	XInputDevice controller;
	int playerNum;
	String playerName;
	boolean playerReady = false; // TODO Maybe move this into game logic?

	public void reset(Ship ship) {
		playerShip = ship;
		playerShip.isPlayerControlled = true;
//		playerShip.color = ;
		playerShip.maxHealth = Properties.PLAYER_BASE_HEALTH;
		playerShip.bulletAccuracy = Properties.PLAYER_BASE_ACCURACY;
		playerShip.maxBulletCooldown = Properties.PLAYER_BASE_COOLDOWN;
		playerShip.caliber = Properties.PLAYER_BASE_CALIBER;
		playerShip.health = playerShip.maxHealth;
		playerShip.team = Game.PLAYER_TEAM;
		target = null;
	}

	public void update() {
		// TODO If dead skip the controls but make the control alternate
		// vibrations!
		// ++This is messy move to player class please
		controller.poll();
		if (playerShip.isDead()) {
			if (enableVibration) {
				double z = System.currentTimeMillis() % 1000;
				if (z > 500) {
					controller.setVibration(65535, 0);
				} else {
					controller.setVibration(0, 65535);
				}
			}
			return;
		}
		// Moving
		float deadZone = 0.2f;
		float x = controller.getComponents().getAxes().get(XInputAxis.LEFT_THUMBSTICK_X);
		float y = controller.getComponents().getAxes().get(XInputAxis.LEFT_THUMBSTICK_Y);
		x = (deadZone > Math.abs(x)) ? 0 : x;
		y = (deadZone > Math.abs(y)) ? 0 : y;
		if (!(x == 0 && y == 0)) {
			double movementAngle = Math.atan2(x, y);
			// TODO Fix Arcade mode
			playerShip.move(movementAngle, false);
		}

		// Turning
		float x2 = controller.getComponents().getAxes().get(XInputAxis.RIGHT_THUMBSTICK_X);
		x2 = (deadZone > Math.abs(x2)) ? 0 : x2;
		if (x2 != 0) {
			double throttle = x2;
			playerShip.turn(throttle);
		}

		// Boosting
		if (lastBoost != null) {
			playerShip.boost(Math.atan2(lastBoost.getX(), lastBoost.getY()));
			lastBoost = null;
		}

		// Shooting
		float rt = controller.getComponents().getAxes().get(XInputAxis.RIGHT_TRIGGER);
		float lt = controller.getComponents().getAxes().get(XInputAxis.LEFT_TRIGGER);
		if(enableVibration){
		controller.setVibration(0, (int) (rt * 65535f / 2f));
		}
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
	Point2D lastBoost = null;

	public void buttonChanged(final XInputButton button, final boolean pressed) {
		if (button == XInputButton.LEFT_SHOULDER && pressed) {
			isTargetting = !isTargetting;
		}
		if (button == XInputButton.RIGHT_SHOULDER && pressed) {
			lastBoost = new Point2D.Double(controller.getComponents().getAxes().get(XInputAxis.LEFT_THUMBSTICK_X),
					controller.getComponents().getAxes().get(XInputAxis.LEFT_THUMBSTICK_Y));
		}
		if(button == XInputButton.START && pressed){
			Game.lowPerformanceMode = !Game.lowPerformanceMode;
		}
		if (playerShip.isDead()) {
			playerReady = true;
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
