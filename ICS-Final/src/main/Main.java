package main;

import java.awt.Color;
import java.awt.Frame;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;

class Main {

	public static void main(String[] args){
				ExecutorService e = Executors.newCachedThreadPool();
				System.setProperty("sun.java2d.opengl","True");
				JFrame frame = new JFrame();
				Panel.instance = new Panel();
				Panel.instance.setBackground(Color.BLACK);
				frame.setExtendedState(Frame.MAXIMIZED_BOTH);
				frame.add(Panel.instance);
				frame.addKeyListener(new Input());
				frame.addMouseListener(new Input());
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				Thread t1 = new Thread(new Game());
//				Thread t2 = new Thread(Panel.instance);
//				Thread t3 = new Thread(r);
//				t1.start();
//				t2.start();
//				t3.start();
				e.submit(new Game());
				e.submit(Panel.instance);

	}

}
