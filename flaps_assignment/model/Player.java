package nl.andrewlalis.model;

public class Player {
	private Ship ship;
	private double scaleFactor = 1.0;

	public Player(Ship ship) {
		this.ship = ship;
	}

	public Ship getShip() {
		return ship;
	}

	public double getScaleFactor() {
		return scaleFactor;
	}

	public void setScaleFactor(double scaleFactor) {
		this.scaleFactor = scaleFactor;
	}
}
