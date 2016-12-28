package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements Runnable,ActionListener{

	/**
	 * 
	 */
	long t1 = 0;
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
		int lineSpace = 80;
		int h = getHeight();
		int w = getWidth();
		int yM = (int) -(Game.player.y % lineSpace);
		int y = yM;
		int xM = (int) -(Game.player.x % lineSpace);
		int x = xM;
		do{
			g2.drawLine(0, y - (int)(Game.player.dY * 5), getWidth(), y - (int)(Game.player.dY * 5));
			y += lineSpace;
		}while(y < h);
		do{
			g2.drawLine(x - (int)(Game.player.dX * 5), 0, x  - (int)(Game.player.dX * 5), getHeight());
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
		g2.drawString(df.format(Game.player.dX) + " " + df.format(Game.player.dY), getWidth() / 2, getHeight() / 2);
	}
	public Polygon transformPolygon(Polygon p){
		double pX = Game.player.x + (Game.player.dX * 5);
		double pY = Game.player.y + (Game.player.dY * 5);
		for(int ix = 0 ; ix < p.xpoints.length;ix++){
			p.xpoints[ix] = (int) (p.xpoints[ix] - pX) + this.getWidth() / 2;
			p.ypoints[ix] = (int) (p.ypoints[ix] - pY) + this.getHeight() / 2;
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
