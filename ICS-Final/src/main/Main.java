package main;

import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import entities.EnemyCache;

/**
 * @author Ewan
 *	Main class where threads are started
 */
class Main {

	public static void main(String[] args){
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
						+ "Left click - shoot");
		optionPane.setMessageType(JOptionPane.OK_CANCEL_OPTION);
		final JDialog dialog = optionPane.createDialog(new JFrame(), "ICS");
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		if(optionPane.getValue() == "CANCEL" || optionPane.getValue() == null){
			System.exit(0);
		}
		EnemyCache.loadCache();
		JFrame frame = new JFrame("Grid Shooter");
		Panel.instance = new Panel();
		Panel.instance.setBackground(Color.BLACK);

		//Setup methods for frame, action listeners, size, settings
		frame.setSize(1920*2, 1080);
		frame.add(Panel.instance);
		frame.addKeyListener(new Input());
		frame.addMouseListener(new Input());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.toFront();

		e.submit(new Game());
		e.submit(Panel.instance);

	}

}
