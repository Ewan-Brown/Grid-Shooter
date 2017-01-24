package main;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.BitSet;

/**
 * @author Ewan
 *	Input class that deals with keyboard and mouse actions
 */
public class Input implements KeyListener,MouseListener{
	/**
	 * 256 bits representing all possible keys
	 */
	static BitSet keySet = new BitSet(256);
	/**
	 * boolean for if left mouse button is currently held down
	 */
	static boolean mouseLMBClicked = false;
	/**
	 * @param k from KeyEvent.keyCode
	 * @return corresponding boolean to that k in keycode. true if the key is currently pressed
	 */
	public static boolean getKey(int k){
		return keySet.get(k);
	}
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		keySet.set(e.getKeyCode(),true);

	}
	@Override
	public void keyReleased(KeyEvent e) {
		keySet.set(e.getKeyCode(),false);

	}
	/**
	 * updates all key actions, called from game timer
	 */
	public static void updateKeys(){
		if(Game.gameOver){
			if(keySet.get(KeyEvent.VK_SPACE)){
				Game.startNew();
			}
			return;
		}
		int x = 0;
		int y = 0;
		if(keySet.get(KeyEvent.VK_W)){
			x += 1;
		}
		if(keySet.get(KeyEvent.VK_A)){
			y -= 1;
		}
		if(keySet.get(KeyEvent.VK_S)){
			x -= 1;
		}
		if(keySet.get(KeyEvent.VK_D)){
			y += 1;
		}
		if(keySet.get(KeyEvent.VK_LEFT)){
			Properties.zoom -= 0.01;
			if(Properties.zoom < 0.4){
				Properties.zoom = 0.4;
			}
		}
		if(keySet.get(KeyEvent.VK_RIGHT)){
			Properties.zoom += 0.01;
			if(Properties.zoom > 10){
				Properties.zoom = 10;
			}
		}
		Point2D p1 = MouseInfo.getPointerInfo().getLocation();
		Point2D p2 = Panel.instance.getLocationOnScreen();
		Point2D p = new Point2D.Double(p1.getX() - p2.getX(), p1.getY() - p2.getY());
		double mouseAngle = Math.atan2(p.getY() - ((Panel.instance.getHeight() / 2D)),p.getX() - ((Panel.instance.getWidth() / 2D)));
		Game.player.turnToTarget(Math.toDegrees(mouseAngle));
		if(mouseLMBClicked){
			Game.player.shootBullet();
		}
		if(x == 0 && y == 0){
			return;
		}
		double movementAngle = Math.atan2(y, x);
		Game.player.move(Math.toDegrees(movementAngle));
	}
	@Override
	public void mouseClicked(MouseEvent e) {

	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			mouseLMBClicked = true;
		}
		if(Game.gameOver){
			Game.startNew();
			return;
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			mouseLMBClicked = false;
		}

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		mouseLMBClicked = false;
	}
	@Override
	public void mouseExited(MouseEvent e) {
		mouseLMBClicked = false;
	}



}
