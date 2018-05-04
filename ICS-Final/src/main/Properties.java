package main;

/**
 * @author Ewan
 * Static class for any static hardcoded properties
 */
public class Properties {

	//Base stats to restart new game for player
	static final int PLAYER_BASE_HEALTH = 1000;
	static final int PLAYER_BASE_CALIBER = 30;
	static final int PLAYER_BASE_ACCURACY = 5; 
	static final int PLAYER_BASE_COOLDOWN = 20;
	/**
	 * Current game Zoom value
	 */
	static double zoom = 1;
	/**
	 * Current player score
	 */
	static int score;
	/**
	 * Current level the player is on
	 */
	static int level = 1;
}
