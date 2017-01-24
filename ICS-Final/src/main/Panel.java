package main;

import java.awt.Color;
import java.awt.Font;import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import Entities.Drawable;

/**
 * @author Ewan
 * Game panel class
 */
public class Panel extends JPanel implements Runnable,ActionListener{

	/**
	 * 
	 */
	long t1 = 0;
	double zoom;
	/**
	 * Custom large font for screen messages
	 */
	Font customFont = new Font("myFont",Font.BOLD,30);
	Font customFont2 = new Font("myFont",Font.BOLD,100);
	static ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	static ArrayList<Drawable> effects = new ArrayList<Drawable>();
	private static final long serialVersionUID = 1L;
	public static final int GRID_SIZE = 80;
	/**
	 * panel update timer, runs at 60hz
	 */
	static Timer timer;
	DecimalFormat df = new DecimalFormat("0.00");
	/**
	 * Static pointer for the panel, used for mouse input code
	 */
	public static Panel instance;
	//Use this is a static instance
	public void paint(Graphics g1){
		super.paint(g1);
		Graphics2D g2 = (Graphics2D)g1;
		//Antialiasing makes the game look much much better.
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		//spacing between grid lines
		double lineSpace = (double)GRID_SIZE * Properties.zoom;
		
		//Variables to keep things less cluttered,also less method calls
		int h = getHeight();
		int w = getWidth();
		//Draws the background grid, applies zoom to grid.
		double y = 0;
		double x = 0;
		y = (int) -((Properties.zoom * Game.player.yPos - getHeight() / 2D + lineSpace*1000) % lineSpace);
		x = (int) -((Properties.zoom * Game.player.xPos - getWidth() / 2D + lineSpace*1000) % lineSpace);
		do{
			g2.drawLine(0, (int)y, w, (int)y);
			y += lineSpace;
		}while(y < h);
		do{
			g2.drawLine((int)x, 0, (int)x, h);
			x += lineSpace;
		}while(x < w);

		//Draws effects first, and the entities on top. The only reason for separation is aesthetic
		for(int i = 0; i < effects.size();i++){
			Drawable d = effects.get(i);
			g2.setColor(d.getColor());
			Polygon p = transformPolygon(d.getRotatedPolygon());
			g2.fillPolygon(p);
		}
		for(int i = 0; i < drawables.size();i++){
			Drawable d = drawables.get(i);
			g2.setColor(d.getColor());
			Polygon p = null;
			p = transformPolygon(d.getRotatedPolygon());
			g2.fillPolygon(p);
		}
		//Draws the player health bar, scaled to screen size
		double hp = (double)Game.player.health / (double)Game.player.maxHealth;
		g2.setColor(Color.RED);
		int ws = w - 200;
		g2.fillRect((w/2) - (ws / 2),h - 100,ws,60);
		if(hp > 0){
			int ws2 = (int)((double)ws * hp);
			g2.setColor(Color.GREEN);
			g2.fillRect((w / 2) - (ws2 / 2),h - 100,ws2,60);
		}
		//If the game is over, keep it running but show the 'end screen' with score
		g2.setColor(Color.BLUE);
		String levelString = "Wave : ";
		String levelNum = Integer.toString(Properties.level);
		g2.setFont(customFont2);
		g2.drawString(levelNum, 130, 90);
		g2.setFont(customFont);
		g2.drawString(levelString, 20, 70);
		if(Game.gameOver){
			String s1 = "Game Over! Press Space to Continue";
			String s2 = "Your score was "+Properties.score;
			g2.drawString(s1, w/2 - (g2.getFontMetrics().stringWidth(s1) / 2), h/2 - 100);
			g2.drawString(s2, w/2 - (g2.getFontMetrics().stringWidth(s2) / 2), h/2 - 200);

		}

	}
	/**
	 * @param p Polygon to transform
	 * @return Polygon translated and zoom applied
	 */
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
	/**
	 * panel update method, called at 60hz
	 */
	public void loop(){
		repaint();
	}
	/**
	 * @param newEntities
	 * @param newEffets
	 *  Method to update the panel's drawings so that even if the game freezes the panel can continue 
	 *  painting as usual and does not freeze functionally. 90% of the workload is on painting so this also allows the game to run smoothly
	 *  if the painting is slow.
	 */
	public static void updateDrawables(ArrayList<? extends Drawable> newEntities,ArrayList<? extends Drawable> newEffets){
		ArrayList<Drawable> b = new ArrayList<Drawable>();
		for(int i = 0; i < newEntities.size();i++){
			Drawable d = newEntities.get(i);
			b.add(d);
		}
		drawables = b;
		ArrayList<Drawable> b2 = new ArrayList<Drawable>();
		for(int i = 0; i < newEffets.size();i++){
			Drawable d = newEffets.get(i);
			b2.add(d);
		}
		effects = b2;
	}
	@Override
	public void run() {
		timer = new Timer(10, this);
		t1 = System.nanoTime();
		timer.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		loop();
	}
}
