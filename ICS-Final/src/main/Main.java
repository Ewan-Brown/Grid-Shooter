package main;

import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import entities.EnemyCache;
import entities.Structures;

/**
 * @author Ewan
 *	Main class where threads are started
 */
class Main {

	public static void main(String[] args){
		InputGeneral.checkXInputDevices();
		Structures.init();
		Game.init();
		System.setProperty("sun.java2d.opengl","True");
		//The game code and panel code are independantly run on separate threads, for maximum performance and to prevent freezing.
		ExecutorService e = Executors.newCachedThreadPool();
		final JOptionPane optionPane = new JOptionPane();
		optionPane.setMessage(	
				"Grid Shooter - By Ewan Brown\n"
						+ "Culminating project\n"
						+ "Use WASD and mouse to move and shoot\n"
						+ "W - forward\n"
						+ "S - backwards\n"
						+ "A - strafe left\n"
						+ "D - strafe right\n"
						+ "Mouse - turn ship\n"
						+ "Left click - shoot\n"
						+ "Q - toggle auto-aiming\n"
						+ "E - Toggle control mode");
		optionPane.setMessageType(JOptionPane.OK_CANCEL_OPTION);
		final JDialog dialog = optionPane.createDialog(new JFrame(), "ICS");
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		if(optionPane.getValue() == "CANCEL" || optionPane.getValue() == null){
			System.exit(0);
		}
		EnemyCache.loadCache();
		JFrame frame = new JFrame("Grid Shooter");
		Panel.panelInstance = new Panel();
		Panel.panelInstance.setBackground(Color.BLACK);

		//Setup methods for frame, action listeners, size, settings
		frame.setSize(1000, 1000);
		frame.add(Panel.panelInstance);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.toFront();

		e.submit(new Game());
		e.submit(Panel.panelInstance);

	}

}
