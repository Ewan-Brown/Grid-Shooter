package main;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;

class Main {

	//This is my first 'challenging' game, please don't hate
	//This will also be used for my final ICSU4 project, so plagiarism is a no-no
	//It's essentially an amalgamation of my previous projects, into one big pile of fun*

	//* disclaimer game may or may not actually be fun

	public static void main(String[] args){
		Point2D[] points = new Point[7];
		points[0] = new Point(24,12);
		points[1] = new Point(6,16);
		points[2] = new Point(16,24);
		points[3] = new Point(0,16);
		points[4] = new Point(0,8);
		points[5] = new Point(16,0);
		points[6] = new Point(8,8);
		Polygon p1 = new Polygon();
			long t0 = System.nanoTime();
			int x = 0;
			int y = 0;
			for(; x < 100;x++){
				for(; y < 100;y++){
					Point p2 = new Point(x,y);
					p1.contains(p2);
				}
			}
			long t1 = System.nanoTime();
			System.out.println(((double)t1 - (double)t0)/1000000D);
		//		System.setProperty("sun.java2d.opengl","True");
		//		JFrame frame = new JFrame();
		//		Panel.instance = new Panel();
		//		Panel.instance.setBackground(Color.BLACK);
		//		frame.add(Panel.instance);
		//		frame.addKeyListener(new Input());
		//		frame.setVisible(true);
		//		frame.setSize(500, 500);		
		//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		Thread t1 = new Thread(new Game());
		//		Thread t2 = new Thread(Panel.instance);
		//		t1.start();
		//		t2.start();
	}

}
