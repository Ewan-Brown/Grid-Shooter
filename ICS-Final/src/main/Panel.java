package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements Runnable,ActionListener{

	/**
	 * 
	 */
	long t1 = 0;
	static ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	private static final long serialVersionUID = 1L;
	static Timer timer;

	public void paint(Graphics g1){
		super.paint(g1);
		Graphics2D g2 = (Graphics2D)g1;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		for(int i = 0; i < drawables.size();i++){
			Drawable d = drawables.get(i);
			g2.setColor(d.c);
			g2.fillPolygon(transformPolygon(d.getRotatedPolygon()));
		}
	}
	public Polygon transformPolygon(Polygon p){
		double pX = Game.player.x;
		double pY = Game.player.y;
		for(int ix = 0 ; ix < p.xpoints.length;ix++){
			p.xpoints[ix] = (int) (p.xpoints[ix] - pX) + this.getWidth() / 2;
			p.ypoints[ix] = (int) (p.ypoints[ix] - pY) + this.getHeight() / 2;
		}
		return p;
	}
	public void loop(){
		repaint();
	}
	public static void updateDrawables(ArrayList<? extends Drawable> a){
		ArrayList<Drawable> b = new ArrayList<Drawable>();
		for(int i = 0; i < a.size();i++){
			Drawable d = a.get(i);
			b.add(d);
		}
		drawables = b;
	}
	@Override
	public void run() {
		timer = new Timer(16, this);
		t1 = System.nanoTime();
		timer.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		loop();
	}
}
