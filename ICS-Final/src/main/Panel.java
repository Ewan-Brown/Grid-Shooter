package main;

import java.awt.Color;
import java.awt.Font;
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

import entities.Drawable;

/**
 * @author Ewan Game panel class
 */
public class Panel extends JPanel implements Runnable, ActionListener {

	static Font customFont1 = new Font("myFont1", Font.BOLD, 30);
	static Font customFont2 = new Font("myFont2", Font.BOLD, 100);
	static Font customFont3 = new Font("myFont3", Font.BOLD, 20);
	static ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	static ArrayList<Drawable> effects = new ArrayList<Drawable>();
	static int targetCirclePadding = 4;

	static int currentCenterX = 0;
	static int currentCenterY = 0;
	static final double KP_PAN = 0.02;
	
	static double currentZoom = Properties.zoom;
	static final double KP_ZOOM = 0.02;

	private static final long serialVersionUID = 1L;
	public static final int GRID_SIZE = 80;
	static Timer timer;
	DecimalFormat df = new DecimalFormat("0.00");
	public static Panel panelInstance;
	// Use this is a static instance
	public enum CustColor{
		
		PLAYER1(Color.GREEN),PLAYER2(Color.YELLOW),PLAYER3(Color.CYAN),
		PLAYER4(Color.PINK),ENEMY(Color.RED),PROJECTILE(Color.RED),PARTICLE(Color.ORANGE);
		CustColor(Color col){
			c = col;
		}
		Color c;
		public Color getColor(){
			return c;
		}
	}

	public void paintGrid(Graphics g2, double xC, double yC, double zoom) {
		double lineSpace = (double) GRID_SIZE * zoom;
		int h = getHeight();
		int w = getWidth();
		double yP = 0;
		double xP = 0;
		g2.setColor(Color.GRAY);
		yP = (int) -((zoom * yC - getHeight() / 2D + lineSpace * 1000) % lineSpace);
		xP = (int) -((zoom * xC - getWidth() / 2D + lineSpace * 1000) % lineSpace);
		do {
			g2.drawLine(0, (int) yP, w, (int) yP);
			yP += lineSpace;
		} while (yP < h);
		do {
			g2.drawLine((int) xP, 0, (int) xP, h);
			xP += lineSpace;
		} while (xP < w);
	}

	public void paint(Graphics g1) {
		super.paint(g1);
		Graphics2D g2 = (Graphics2D) g1;
		// Antialiasing makes the game look much much better.
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// spacing between grid lines
		// Player pl = InputGeneral.players.get(0);
		double centerX = 0;
		double centerY = 0;
		double range = 0;
		try {
			centerX = InputGeneral.getCenterOfPlayers().getX();
			centerY = InputGeneral.getCenterOfPlayers().getY();
			range = InputGeneral.getMaxPlayerSeparationDist();
		} catch (NullPointerException npe) {
			centerX = currentCenterX;
			centerY = currentCenterY;
			range = currentZoom;
		}
		double zoom = Properties.zoom;
		double baseDist = getWidth() / 3D;
		double mult = (range / 2 > baseDist) ? (range / 2) / baseDist : 1;
		zoom /= mult;
		currentCenterX += (centerX - currentCenterX) * KP_PAN;
		currentCenterY += (centerY - currentCenterY) * KP_PAN;
		currentZoom += (zoom - currentZoom) * KP_ZOOM;
		// if(getWidth() < range){
		// zoom /= 2;
		// }
		// Draws effects first, and the entities on top. The only reason for
		// separation is aesthetic
		paintGrid(g2, currentCenterX, currentCenterY, currentZoom);
		if (!Game.lowPerformanceMode) {
			for (int i = 0; i < effects.size(); i++) {
				Drawable d = effects.get(i);
				Color c = d.color.getColor();
				g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), d.getAlpha()));
				Polygon p = transformPolygon(d.getRotatedPolygon(), currentCenterX, currentCenterY, currentZoom);
				g2.fillPolygon(p);
			}
		}
		for (int i = 0; i < drawables.size(); i++) {
			Drawable d = drawables.get(i);
			Color c = d.color.getColor();
			g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), d.getAlpha()));
			Polygon p = null;
			p = transformPolygon(d.getRotatedPolygon(), currentCenterX, currentCenterY, currentZoom);
			g2.fillPolygon(p);
		}
		// Draws the player health bar, scaled to screen size
		// if (Game.player != null) {
		// double healthPercentage = (double) Game.player.health / (double)
		// Game.player.maxHealth;
		// int height = 50;
		// int windowSpacing = 100;
		// int rectSpacing = 50;
		// int rects = 10;
		// int rectSize = (w - ((windowSpacing * 2) + rectSpacing * (rects -
		// 1))) / rects;
		// int x = windowSpacing;
		// double f = 1D / rects;
		// healthPercentage = (int) (healthPercentage * 100) / 100D;
		// double p = healthPercentage % f;
		// double d = healthPercentage / f;
		// int m = (int) Math.ceil(d);
		// for (int i = 0; i < m; i++) {
		// g2.setColor(Color.GREEN);
		// int l = rectSize;
		// if (i == m - 1) {
		// l = (int) ((double) l * (p) * 10);
		// }
		// g2.fillRect(x, h - 150, l, height);
		// x += rectSpacing + rectSize;
		// }
		// }
		// Draw a circle around target
		// if (Input.targetting && Input.targeted != null) {
		// g2.setColor(Color.MAGENTA);// Why magenta?
		// int x = (int) (((targeted.getX() - player.getX() - targeted.radius /
		// 2) * (zoom)) + getWidth() / 2)
		// - targetCirclePadding / 2;
		// int y = (int) (((targeted.getY() - player.getY() - targeted.radius /
		// 2) * (zoom)) + getHeight() / 2)
		// - targetCirclePadding / 2;
		// int size = (int) ((float) (targeted.radius) * zoom) +
		// targetCirclePadding;
		// g2.drawOval(x, y, size, size);
		// }
		// If the game is over, keep it running but show the 'end screen' with
		// score
		g2.setColor(Color.BLUE);
		String levelString = "Wave : ";
		String levelNum = Integer.toString(Properties.level);
		g2.setFont(customFont2);
		g2.drawString(levelNum, 130, 90);
		g2.setFont(customFont1);
		g2.drawString(levelString, 20, 70);
		int h = getHeight();
		int w = getWidth();
		if (Game.gameOver) {
			String s1 = "Game Over! Press any button to Continue";
			String s2 = "Your score was " + Properties.score;
			g2.drawString(s1, w / 2 - (g2.getFontMetrics().stringWidth(s1) / 2), h / 2 - 100);
			g2.drawString(s2, w / 2 - (g2.getFontMetrics().stringWidth(s2) / 2), h / 2 - 200);

		}
		// g2.setFont(customFont3);
		// g2.setColor(new Color(10,10,10));
		// g2.drawString("Made by Ewan Brown for his Comp.Sci final",20,h -
		// 110);
	}

	public Polygon transformPolygon(Polygon p, double xC, double yC, double zoom) {
		for (int ix = 0; ix < p.xpoints.length; ix++) {
			p.xpoints[ix] = (int) ((double) (p.xpoints[ix] - xC) * zoom);
			p.ypoints[ix] = (int) ((double) (p.ypoints[ix] - yC) * zoom);
			p.xpoints[ix] += this.getWidth() / 2;
			p.ypoints[ix] += this.getHeight() / 2;
		}
		return p;
	}

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
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		loop();
	}
}
