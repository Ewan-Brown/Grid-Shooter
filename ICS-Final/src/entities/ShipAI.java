package entities;

import java.util.ArrayList;

import main.Game;
import tools.GameMath;

public class ShipAI {

	public ShipAI(Ship parent) {
		this.parent = parent;
	}

	Ship parent;

	public void update() {
	}
	public void turnToTarget(double targetAngle) {
		double a = targetAngle - parent.realAngle;
		double f = 0;
		// a = a % 360;
		while (a > 180) {
			a -= 360;
		}
		while (a < -180) {
			a += 360;
		}
		if (a > 0)
			f = Math.min(a, parent.turnSpeed / 5.0); // TODO MAGIC NUMBERS HERE
		else
			f = Math.max(a, -parent.turnSpeed / 5.0);

		parent.realAngle += f;
	}
}
