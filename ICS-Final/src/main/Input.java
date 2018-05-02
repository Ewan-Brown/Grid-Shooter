package main;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.BitSet;
import static main.Game.player;

import entities.Enemy;
import entities.Entity;
import entities.ParticleEffects;
import entities.Ship;
import entities.VoxelParticle;
import tools.GameMath;

/**
 * @author Ewan Input class that deals with keyboard and mouse actions
 */
public class Input implements KeyListener, MouseListener {
	/**
	 * 256 bits representing all possible keys
	 */
	static BitSet keySet = new BitSet(256);
	static final long DOUBLE_TAP_COOLDOWN = 300000000;
	static ArrayList<Integer> doubleTaps = new ArrayList<Integer>(10);
	// Time when key was last pressed down
	static long[] keyTimes = new long[256];
	{
		for (int i = 0; i < keyTimes.length; i++) {
			keyTimes[i] = 0;
		}
	}
	/**
	 * boolean for if left mouse button is currently held down
	 */
	static boolean mouseLMBClicked = false;
	static boolean mouseRMBClicked = false;
	static boolean mouseMMBClicked = false;

	/**
	 * @param k
	 *            from KeyEvent.keyCode
	 * @return corresponding boolean to that k in keycode. true if the key is
	 *         currently pressed
	 */
	public static boolean getKey(int k) {
		return keySet.get(k);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		long l = System.nanoTime();
		int k = e.getKeyCode();
		if (l - keyTimes[k] < DOUBLE_TAP_COOLDOWN) {
			doubleTaps.add(k);
		}
		if (!keySet.get(k)) {
			keyTimes[k] = l;
		}
		keySet.set(k, true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		keySet.set(k, false);
	}

	/**
	 * updates all key actions, called from game timer
	 */
	public static int[] cooldowns = new int[256];
	{
		for(int i = 0; i < cooldowns.length;i++){
			cooldowns[i] = 0;
		}
		cooldownMaxes[KeyEvent.VK_Q] = 50;
	}
	public static boolean keyIsFresh(int keyCode){
		return keySet.get(keyCode) && cooldowns[keyCode] < 0; 
	}
	public static void useKey(int keyCode){
		cooldowns[keyCode] = cooldownMaxes[keyCode];
	}
	public static int[] cooldownMaxes = new int[256];
	public static void updateKeys() {
		for(int i = 0; i < cooldowns.length;i++){
			cooldowns[i]--;
		}
		if (doubleTaps.size() > 0 && player.boostDriveCooldown < 0) {
			player.boostDriveCooldown = player.MAX_BOOST_DRIVE_COOLDOWN;
			int k = doubleTaps.get(doubleTaps.size() - 1);
			int x = 0;
			int y = 0;
			if (k == KeyEvent.VK_W) {
				x = 1;
			}
			if (k == KeyEvent.VK_A) {
				y = -1;
			}
			if (k == KeyEvent.VK_S) {
				x = -1;
			}
			if (k == KeyEvent.VK_D) {
				y = 1;
			}
			double movementAngle = Math.atan2(y, x);
			Ship p = player;
			p.boost(movementAngle);
			doubleTaps.clear();
		}
		if (Game.gameOver) {
			if (keySet.get(KeyEvent.VK_SPACE)) {
				Game.startNew();
			}
			return;
		}
		int x = 0;
		int y = 0;
		if (keyIsFresh(KeyEvent.VK_Q)) {
			useKey(KeyEvent.VK_Q);
			targetting = !targetting;
		}
		if (targetting) {
			updateTarget();
		}
		if (keySet.get(KeyEvent.VK_W)) {
			x += 1;
		}
		if (keySet.get(KeyEvent.VK_A)) {
			y -= 1;
		}
		if (keySet.get(KeyEvent.VK_S)) {
			x -= 1;
		}
		if (keySet.get(KeyEvent.VK_D)) {
			y += 1;
		}
		if (keySet.get(KeyEvent.VK_LEFT)) {
			Properties.zoom -= 0.01;
			if (Properties.zoom < 0.4) {
				Properties.zoom = 0.4;
			}
		}
		if (keySet.get(KeyEvent.VK_RIGHT)) {
			Properties.zoom += 0.01;
			if (Properties.zoom > 10) {
				Properties.zoom = 10;
			}
		}
		// TODO Add arcade controls!
		Point2D p = getPlayerTarget();
		double mouseAngle = Math.atan2(p.getY(), p.getX());
		player.turnToTarget(Math.toDegrees(mouseAngle));
		if (mouseLMBClicked) {
			player.shootBullet();
		}
		if (mouseRMBClicked) {
			player.shootMissile();
		}
		if (x == 0 && y == 0) {
			return;
		}
		double movementAngle = Math.atan2(y, x);
		player.move(movementAngle);
	}

	public static Ship targeted = null;
	public static boolean targetting = false;

	public static void updateTarget() {
		if (targeted == null || targeted.isDead()) {
			double d = Double.MAX_VALUE;
			Ship target = null;
			for (Entity e : Game.entityArray) {
				if (e instanceof Enemy && !e.isDead()) {
					double d2 = GameMath.getDistance(player, e);
					if (d2 < d) {
						d = d2;
						target = (Ship) e;
					}
				}
			}
			targeted = target;
		} else {
		}
	}

	public static Point2D getPlayerTarget() {
		if (!targetting || targeted == null) {
			Point2D p1 = MouseInfo.getPointerInfo().getLocation();
			Point2D p2 = Panel.instance.getLocationOnScreen();
			return new Point2D.Double(p1.getX() - p2.getX() - (Panel.instance.getHeight() / 2D),
					p1.getY() - p2.getY() - (Panel.instance.getWidth() / 2D));
		} else {
			return new Point2D.Double(targeted.getX() - player.xPos, targeted.getY() - player.yPos);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			mouseLMBClicked = true;
			break;
		case MouseEvent.BUTTON2:
			mouseMMBClicked = true;
			break;
		case MouseEvent.BUTTON3:
			mouseRMBClicked = true;
			break;
		}
		if (Game.gameOver) {
			Game.startNew();
			return;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			mouseLMBClicked = false;
			break;
		case MouseEvent.BUTTON2:
			mouseMMBClicked = false;
			break;
		case MouseEvent.BUTTON3:
			mouseRMBClicked = false;
			break;
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseLMBClicked = false;
		mouseRMBClicked = false;
		mouseMMBClicked = false;

	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseLMBClicked = false;
		mouseRMBClicked = false;
		mouseMMBClicked = false;

	}

}
