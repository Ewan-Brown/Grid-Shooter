package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;

public class Input implements KeyListener{
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
		if(keySet.get(KeyEvent.VK_W)){
			Game.player.thrust(1);
		}
		if(keySet.get(KeyEvent.VK_A)){
			Game.player.strafe(-1);
		}
		if(keySet.get(KeyEvent.VK_S)){
			Game.player.thrust(-1);
		}
		if(keySet.get(KeyEvent.VK_D)){
			Game.player.strafe(1);		}
		if(keySet.get(KeyEvent.VK_LEFT)){
			Game.player.turn(-1);
		}
		if(keySet.get(KeyEvent.VK_RIGHT)){
			Game.player.turn(1);
		}
		if(keySet.get(KeyEvent.VK_SPACE)){
			Game.player.shoot();
		}
	}



}
