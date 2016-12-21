package main;

import javax.swing.JFrame;

class Main {

	//This is my first 'challenging' game, please don't hate
	//This will also be used for my final ICSU4 project, so plagiarism is a no-no
	//It's essentially an amalgamation of my previous projects, into one big pile of fun*
	
	//* disclaimer game may or may not actually be fun
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		Panel panel = new Panel();
		frame.add(panel);
		frame.addKeyListener(new Input());
		frame.setVisible(true);
		frame.setSize(500, 500);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Thread t1 = new Thread(new Game());
		Thread t2 = new Thread(panel);
		t1.start();
		t2.start();
	}
	
}
