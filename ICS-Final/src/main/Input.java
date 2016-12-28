package main;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.BitSet;

public class Input implements KeyListener,MouseListener{
	static BitSet keySet = new BitSet(256);
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
	public static void updateKeys(){
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
//		if(keySet.get(KeyEvent.VK_LEFT)){
//			Game.player.turn(-1);
//		}
//		if(keySet.get(KeyEvent.VK_RIGHT)){
//			Game.player.turn(1);
//		}
		Point2D p1 = MouseInfo.getPointerInfo().getLocation();
		Point2D p2 = Panel.instance.getLocationOnScreen();
		Point2D p = new Point2D.Double(p1.getX() - p2.getX(), p1.getY() - p2.getY());
		double mouseAngle = Math.atan2(p.getY() - ((Panel.instance.getHeight() / 2D) - (Game.player.dY * 5)),p.getX() - ((Panel.instance.getWidth() / 2D) - (Game.player.dX * 5)));
		Game.player.turnToTarget(Math.toDegrees(mouseAngle));
		if(keySet.get(KeyEvent.VK_SPACE)){
			Game.player.shoot();
		}
		if(x == 0 && y == 0){
			return;
		}
		double movementAngle = Math.atan2(y, x);
		Game.player.move(1, Math.toDegrees(movementAngle));
	}
	@Override
	public void mouseClicked(MouseEvent e) {

	}
	@Override
	public void mousePressed(MouseEvent e) {

	}
	@Override
	public void mouseReleased(MouseEvent e) {

	}
	@Override
	public void mouseEntered(MouseEvent e) {

	}
	@Override
	public void mouseExited(MouseEvent e) {

	}



}
