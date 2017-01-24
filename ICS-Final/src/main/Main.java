package main;

import java.awt.Color;
import java.awt.Frame;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;

/**
 * @author Ewan
 *	Main class where threads are started
 */
class Main {

	public static void main(String[] args){
		//The game code and panel code are independantly run on separate threads, for maximum performance.
		ExecutorService e = Executors.newCachedThreadPool();
		
		JFrame frame = new JFrame("Grid Shooter");
		Panel.instance = new Panel();
		Panel.instance.setBackground(Color.BLACK);
		
		//Setup methods for frame, action listeners, size, settings
		frame.setSize(1920, 1080);
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
