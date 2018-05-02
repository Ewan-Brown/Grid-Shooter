package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import static main.Input.targeted;
import static main.Properties.zoom;
import javax.swing.JPanel;
import javax.swing.Timer;
import static main.Game.player;
import entities.Drawable;
import entities.Enemy;
import entities.Ship;

/**
 * @author Ewan Game panel class
 */
public class Panel extends JPanel implements Runnable, ActionListener {

	/**
	 * 
	 */
	long t1 = 0;
	/**
	 * Custom large font for screen messages
	 */
	Point[] arrow = new Point[3];
	{
		arrow[0] = new Point(0, 4);
		arrow[1] = new Point(0, 0);
		arrow[2] = new Point(5, 2);
	}
	Font customFont1 = new Font("myFont1", Font.BOLD, 30);
	Font customFont2 = new Font("myFont2", Font.BOLD, 100);
	Font customFont3 = new Font("myFont3", Font.BOLD, 20);
	static ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	static ArrayList<Drawable> effects = new ArrayList<Drawable>();
	static int targetCirclePadding = 4;
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
	// Use this is a static instance
	Color[] colorList = new Color[4];
	{
		colorList[0] = Color.GREEN;
		colorList[1] = Color.RED;
		colorList[2] = Color.ORANGE;
		colorList[3] = Color.CYAN;
	}

	public Color getColor(int c) {
		return colorList[c];
	}

	public void paint(Graphics g1) {
		super.paint(g1);
		Graphics2D g2 = (Graphics2D) g1;
		// Antialiasing makes the game look much much better.
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// spacing between grid lines
		double lineSpace = (double) GRID_SIZE * zoom;

		// Variables to keep things less cluttered,also less method calls
		int h = getHeight();
		int w = getWidth();
		// Draws the background grid, applies zoom to grid.
		double yP = 0;
		double xP = 0;
		// because the game and the panel are on separate threads, there is a
		// possibility Game.player may not be initialized when
		// this is called, so there is a check first.
		if (Game.player != null) {
			yP = (int) -((zoom * Game.player.yPos - getHeight() / 2D + lineSpace * 1000) % lineSpace);
			xP = (int) -((zoom * Game.player.xPos - getWidth() / 2D + lineSpace * 1000) % lineSpace);
		}
		do {
			g2.drawLine(0, (int) yP, w, (int) yP);
			yP += lineSpace;
		} while (yP < h);
		do {
			g2.drawLine((int) xP, 0, (int) xP, h);
			xP += lineSpace;
		} while (xP < w);
		// Draws effects first, and the entities on top. The only reason for
		// separation is aesthetic
		if (!Game.lowPerformanceMode) {
			for (int i = 0; i < effects.size(); i++) {
				Drawable d = effects.get(i);
				Color c = getColor(d.color);
				g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), d.getAlpha()));
				Polygon p = transformPolygon(d.getRotatedPolygon());
				g2.fillPolygon(p);
			}
		}
		for (int i = 0; i < drawables.size(); i++) {
			Drawable d = drawables.get(i);
			Color c = getColor(d.color);
			g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), d.getAlpha()));
			Polygon p = null;
			p = transformPolygon(d.getRotatedPolygon());
			g2.fillPolygon(p);
		}
		// Draws the player health bar, scaled to screen size
		if (Game.player != null) {
			double healthPercentage = (double) Game.player.health / (double) Game.player.maxHealth;
			int height = 50;
			int windowSpacing = 100;
			int rectSpacing = 50;
			int rects = 10;
			int rectSize = (w - ((windowSpacing * 2) + rectSpacing * (rects - 1))) / rects;
			int x = windowSpacing;
			double f = 1D / rects;
			healthPercentage = (int) (healthPercentage * 100) / 100D;
			double p = healthPercentage % f;
			double d = healthPercentage / f;
			int m = (int) Math.ceil(d);
			for (int i = 0; i < m; i++) {
				g2.setColor(Color.GREEN);
				int l = rectSize;
				if (i == m - 1) {
					l = (int) ((double) l * (p) * 10);
				}
				g2.fillRect(x, h - 150, l, height);
				x += rectSpacing + rectSize;
			}
		}
		// Draw a circle around target
		if (Input.targetting  && Input.targeted != null) {
			g2.setColor(Color.MAGENTA);// Why magenta?
			int x = (int) (((targeted.getX() - player.getX() - targeted.radius/2) * (zoom)) + getWidth()/2)-targetCirclePadding/2;
			int y = (int) (((targeted.getY() - player.getY() - targeted.radius/2) * (zoom)) + getHeight()/2)-targetCirclePadding/2;
			int size = (int)((float)(targeted.radius)*zoom)+targetCirclePadding;
			g2.drawOval(x,y,size,size);
		}
		// If the game is over, keep it running but show the 'end screen' with
		// score
		g2.setColor(Color.BLUE);
		String levelString = "Wave : ";
		String levelNum = Integer.toString(Properties.level);
		g2.setFont(customFont2);
		g2.drawString(levelNum, 130, 90);
		g2.setFont(customFont1);
		g2.drawString(levelString, 20, 70);
		if (Game.gameOver) {
			String s1 = "Game Over! Press Space to Continue";
			String s2 = "Your score was " + Properties.score;
			g2.drawString(s1, w / 2 - (g2.getFontMetrics().stringWidth(s1) / 2), h / 2 - 100);
			g2.drawString(s2, w / 2 - (g2.getFontMetrics().stringWidth(s2) / 2), h / 2 - 200);

		}
		// g2.setFont(customFont3);
		// g2.setColor(new Color(10,10,10));
		// g2.drawString("Made by Ewan Brown for his Comp.Sci final",20,h -
		// 110);
	}

	/**
	 * @param p
	 *            Polygon to transform
	 * @return Polygon translated and zoom applied
	 */
	public Polygon transformPolygon(Polygon p) {
		Point2D center = Game.player.centerPoint;
		double pX = Game.player.xPos + center.getX();
		double pY = Game.player.yPos + center.getY();
		for (int ix = 0; ix < p.xpoints.length; ix++) {
			p.xpoints[ix] = (int) ((double) (p.xpoints[ix] - pX) * zoom);
			p.ypoints[ix] = (int) ((double) (p.ypoints[ix] - pY) * zoom);
			p.xpoints[ix] += this.getWidth() / 2;
			p.ypoints[ix] += this.getHeight() / 2;
		}
		return p;
	}

	/**
	 * panel update method, called at 60hz
	 */
	public void loop() {
		repaint();
	}

	/**
	 * @param newEntities
	 * @param newEffets
	 *            Method to update the panel's drawings so that even if the game
	 *            freezes the panel can continue painting as usual and does not
	 *            freeze functionally. 90% of the workload is on painting so
	 *            this also allows the game to run smoothly if the painting is
	 *            slow.
	 */
	public static void updateDrawables(ArrayList<? extends Drawable> newEntities,
			ArrayList<? extends Drawable> newEffets) {
		ArrayList<Drawable> b = new ArrayList<Drawable>();
		for (int i = 0; i < newEntities.size(); i++) {
			Drawable d = newEntities.get(i);
			b.add(d);
		}
		drawables = b;
		ArrayList<Drawable> b2 = new ArrayList<Drawable>();
		for (int i = 0; i < newEffets.size(); i++) {
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
