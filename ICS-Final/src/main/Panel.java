package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements Runnable,ActionListener{

	/**
	 * 
	 */
	long t1 = 0;
	double zoom;
	static ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	static ArrayList<Drawable> effects = new ArrayList<Drawable>();
	private static final long serialVersionUID = 1L;
	static Timer timer;
	DecimalFormat df = new DecimalFormat("0.00");
	public static Panel instance;
	//Use this is a static instance
	public void paint(Graphics g1){
		super.paint(g1);
		Graphics2D g2 = (Graphics2D)g1;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int lineSpace = (int)(80D * Properties.zoom);
		int h = getHeight();
		int w = getWidth();
		//TODO Undo these negatives for a trippy effect!
		int y = (int) -((Properties.zoom * Game.player.yPos - getHeight() / 2) % lineSpace);
		int x = (int) -((Properties.zoom * Game.player.xPos - getWidth() / 2) % lineSpace);
		do{
			g2.drawLine(0, y, w, y);
			y += lineSpace;
		}while(y < h);
		do{
			g2.drawLine(x, 0, x, h);
			x += lineSpace;
		}while(x < w);
		
		for(int i = 0; i < effects.size();i++){
			Drawable d = effects.get(i);
			g2.setColor(d.getColor());
			g2.fillPolygon(transformPolygon(d.getRotatedPolygon()));
		}
		for(int i = 0; i < drawables.size();i++){
			Drawable d = drawables.get(i);
			g2.setColor(d.getColor());
			g2.fillPolygon(transformPolygon(d.getRotatedPolygon()));
		}
		g2.setColor(Color.WHITE);
		g2.drawString(Properties.zoom+" ", w/2, h/2);
//		System.out.println("hey");
	}
	public Polygon transformPolygon(Polygon p){
		Point2D center = Game.player.centerPoint;
		double pX = Game.player.xPos + center.getX();
		double pY = Game.player.yPos + center.getY();
		for(int ix = 0 ; ix < p.xpoints.length;ix++){
			p.xpoints[ix] = (int) ((double)(p.xpoints[ix] - pX) * Properties.zoom);
			p.ypoints[ix] = (int) ((double)(p.ypoints[ix] - pY) * Properties.zoom);
			p.xpoints[ix] += this.getWidth() / 2;
			p.ypoints[ix] += this.getHeight() / 2;
		}
		return p;
	}
	public void loop(){
		repaint();
	}
	public static void updateDrawables(ArrayList<? extends Drawable> a,ArrayList<? extends Drawable> e){
		ArrayList<Drawable> b = new ArrayList<Drawable>();
		for(int i = 0; i < a.size();i++){
			Drawable d = a.get(i);
			b.add(d);
		}
		drawables = b;
		ArrayList<Drawable> b2 = new ArrayList<Drawable>();
		for(int i = 0; i < e.size();i++){
			Drawable d = e.get(i);
			b2.add(d);
		}
		effects = b2;
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
