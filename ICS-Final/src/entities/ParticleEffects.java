package entities;

import java.util.ArrayList;
import java.util.Random;

import main.Panel.CustColor;

public class ParticleEffects {

	public enum PerformanceMode {
		// mmmmmmmmmmmmmmmmmm?
		HIGH(Integer.MAX_VALUE,1),LOW(500,0.6),POTATO(100,0.3),NADA(0,0);
		int maxParticles;
		double particlePercent;
		PerformanceMode(int m,double p){
			maxParticles = m;
			particlePercent = p;
		}
	}
	public static PerformanceMode pm = PerformanceMode.NADA;
	static Random rand = new Random();
	public static ArrayList<VoxelParticle> explode(double x, double y, double force, int amount, int life) {
		amount = (int) Math.floor(amount * pm.particlePercent);
		ArrayList<VoxelParticle> particles = new ArrayList<VoxelParticle>(amount);
		double theta = (double) Math.PI * 2 / (double) amount;
		for (int i = 0; i < amount; i++) {
			double vX = (double) (force * Math.cos(theta * i));
			double vY = (double) (force * Math.sin(theta * i));
			VoxelParticle p = new VoxelParticle(x, y, vX, vY, Math.random() * 5, CustColor.PARTICLE, life);
			particles.add(p);
		}
		return particles;
	}
	//Like explode but particles slow down as they near death
	public static ArrayList<VoxelParticle> explode2(double x, double y, double force, int amount, int life) {
		amount = (int) Math.floor(amount * pm.particlePercent);
		ArrayList<VoxelParticle> particles = new ArrayList<VoxelParticle>(amount);
		double theta = (double) Math.PI * 2 / (double) amount;
		for (int i = 0; i < amount; i++) {
			double i2 = i;
			VoxelParticle p = new VoxelParticle(x, y, 0, 0, Math.random() * 5, CustColor.PARTICLE, life) {
				double z = i2;
				public double getYSpeed() {
					return force * (getLife()) * Math.cos(theta * z);
				}

				public double getXSpeed() {
					return force * (getLife()) * Math.sin(theta * z);
				}
			};
			particles.add(p);
		}
		return particles;
	}
	public static ArrayList<VoxelParticle> stretchedExplode(double x, double y, double force, int amount, int life) {
		amount = (int) Math.floor(amount * pm.particlePercent);
		ArrayList<VoxelParticle> particles = new ArrayList<VoxelParticle>(amount);
		double theta = (double) Math.PI * 2 / (double) amount;
		for (int i = 0; i < amount; i++) {
			double i2 = i;
			VoxelParticle p = new VoxelParticle(x, y, 0, 0, Math.random() * 5, CustColor.PARTICLE, life) {
				double z = i2;
				public double getYSpeed() {
					return force * (1-getLife()) * Math.cos(theta * z);
				}

				public double getXSpeed() {
					return force * (1-getLife()) * Math.sin(theta * z);
				}
			};
			particles.add(p);
		}
		return particles;
	}
	public static ArrayList<VoxelParticle> implode(double x, double y, double force, int amount, int life,double amplitude) {
		amount = (int) Math.floor(amount * pm.particlePercent);
		ArrayList<VoxelParticle> particles = new ArrayList<VoxelParticle>(amount);
		double theta = (double) Math.PI * 2 / (double) amount;
		for (int i = 0; i < amount; i++) {
			double i2 = i;
			VoxelParticle p = new VoxelParticle(x, y, 0, 0, Math.random() * 5, CustColor.PARTICLE, life) {
				double z = i2;
				public double getYSpeed() {
					return force * (0.5-getLife()) * amplitude * Math.cos(theta * z);
				}

				public double getXSpeed() {
					return force * (0.5-getLife()) * amplitude * Math.sin(theta * z);
				}
			};
			particles.add(p);
		}
		return particles;
	}
	public static ArrayList<VoxelParticle> fishtail(double x, double y, double angle, int amount, int life) {
		amount = (int) Math.floor(amount * pm.particlePercent);
		ArrayList<VoxelParticle> particles = new ArrayList<VoxelParticle>(amount);
		for (int i = 0; i < amount; i++) {
			double s = (i % 2 == 0) ? 1 : -1;
			VoxelParticle p = new VoxelParticle(x, y, 0, 0, Math.random() * 5, CustColor.PARTICLE, life) {
				double startingAngle = Math.toRadians(angle);
				double flipped = s;

				public double getYSpeed() {
					return flipped * Math.cos(startingAngle) * Math.sin(getLife() * Math.PI) * 2;
				}

				public double getXSpeed() {
					return flipped * Math.sin(startingAngle) * Math.sin(getLife() * Math.PI) * 2;
				}
			};
			particles.add(p);
		}
		return particles;
	}
	public static ArrayList<VoxelParticle> helix(double x, double y, double angle, int amount,double life) {
		amount = (int) Math.floor(amount * pm.particlePercent);
		ArrayList<VoxelParticle> particles = new ArrayList<VoxelParticle>(amount);
		for (int i = 0; i < amount; i++) {
			double s = (i % 2 == 0) ? 1 : -1;
			VoxelParticle p = new VoxelParticle(x, y, 0, 0, Math.random() * 5, CustColor.PARTICLE, life) {
				double startingAngle = angle + (Math.random()-0.5)*2;
				double flipped = s;
				public double getYSpeed() {
					return flipped * Math.cos(startingAngle) * Math.cos(getLife() * Math.PI*3)*1.5;
				}

				public double getXSpeed() {
					return flipped * Math.sin(startingAngle) * Math.cos(getLife() * Math.PI*3 + Math.PI)*1.5;
				}
			};
			particles.add(p);
		}
		return particles;
	}
	public static ArrayList<VoxelParticle> helixInverted(double x, double y, double angle, int amount, int life) {
		amount = (int) Math.floor(amount * pm.particlePercent);
		ArrayList<VoxelParticle> particles = new ArrayList<VoxelParticle>(amount);
		for (int i = 0; i < amount; i++) {
			double s = (i % 2 == 0) ? 1 : -1;
			VoxelParticle p = new VoxelParticle(x, y, 0, 0, Math.random() * 5, CustColor.PARTICLE, life) {
				double startingAngle = Math.toRadians(angle);
				double flipped = s;

				public double getYSpeed() {
					return flipped * Math.cos(startingAngle) * Math.sin(getLife() * Math.PI * 4) * 2;
				}

				public double getXSpeed() {
					return flipped * Math.sin(startingAngle) * Math.sin(getLife() * Math.PI * 4 + Math.PI) * 2;
				}
			};
			particles.add(p);
		}
		return particles;
	}
	public static ArrayList<VoxelParticle> current(double x, double y, double angle, int amount, int life) {
		amount = (int) Math.floor(amount * pm.particlePercent);
		ArrayList<VoxelParticle> particles = new ArrayList<VoxelParticle>(amount);
		for (int i = 0; i < amount; i++) {
			double s = (i % 2 == 0) ? 1 : -1;
			VoxelParticle p = new VoxelParticle(x, y, 0, 0, Math.random() * 5, CustColor.PARTICLE, life) {
				double startingAngle = Math.toRadians(angle);
				double flipped = s;

				public double getYSpeed() {
					return flipped * Math.cos(startingAngle) * Math.sin(getLife() * Math.PI) * 2;
				}

				public double getXSpeed() {
					return flipped * Math.sin(startingAngle) * Math.sin(getLife() * Math.PI) * 2;
				}
			};
			particles.add(p);
		}
		return particles;
	}
}
