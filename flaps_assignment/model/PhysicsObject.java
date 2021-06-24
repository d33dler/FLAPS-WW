package nl.andrewlalis.model;

import nl.andrewlalis.physics.Vec2;

import java.awt.geom.AffineTransform;

public class PhysicsObject {
	private static final double G = 6.674E-11;

	// Mass in Kg.
	protected double mass;
	// Bounding radius, in meters.
	protected double radius;
	// Position in meters.
	protected Vec2 position;
	// Velocity in m/s
	protected Vec2 velocity;
	// Orientation in the 2D plane, in radians, [0, 2 * PI)
	protected double orientation;
	// Rotational speed, in rad/s.
	protected double angularVelocity;

	public PhysicsObject(double mass, double radius) {
		this.mass = mass;
		this.radius = radius;
		this.position = new Vec2();
		this.velocity = new Vec2();
		this.angularVelocity = 0;
	}

	public void gravitateTowards(PhysicsObject other, double deltaT) {
		double radius = this.position.hyp(other.position);
		radius = Math.max(radius, 0.01); // Set a min-bound for radius to prevent black-hole scenarios.
		double angle = this.position.angleTo(other.position);
		Vec2 acceleration = new Vec2(Math.cos(angle), Math.sin(angle))
				.mul(deltaT * G * other.mass / (Math.pow(radius, 2)));
		this.velocity.acc(acceleration);
	}

	public void updatePosition(double deltaT) {
		this.position.acc(this.velocity.mul(deltaT));
		this.orientation += this.angularVelocity * deltaT;
		this.normalizeOrientation();
	}

	/**
	 * Normalizes the object's orientation to between 0 and 2 pi.
	 */
	private void normalizeOrientation() {
		while (this.orientation >= 2 * Math.PI) {
			orientation -= 2 * Math.PI;
		}
		while (this.orientation < 0) {
			orientation += 2 * Math.PI;
		}
	}

	public double getMass() {
		return mass;
	}

	public double getRadius() {
		return radius;
	}

	public Vec2 getPosition() {
		return position;
	}

	public void setPosition(Vec2 position) {
		this.setPosition(position.x, position.y);
	}

	public void setPosition(double x, double y) {
		this.position.x = x;
		this.position.y = y;
	}

	public Vec2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vec2 velocity) {
		this.setVelocity(velocity.x, velocity.y);
	}

	public void setVelocity(double x, double y) {
		this.velocity.x = x;
		this.velocity.y = y;
	}

	public double getOrientation() {
		return orientation;
	}

	public void setOrientation(double orientation) {
		this.orientation = orientation;
		this.normalizeOrientation();
	}

	public double getAngularVelocity() {
		return angularVelocity;
	}

	public void setAngularVelocity(double angularVelocity) {
		this.angularVelocity = angularVelocity;
	}

	/**
	 * @return A transform that can be applied to a graphics context so that
	 * this object can be rendered in local coordinates.
	 */
	public AffineTransform getTransform() {
		var tx = AffineTransform.getTranslateInstance(this.position.x, this.position.y);
		tx.rotate(this.orientation);
		return tx;
	}
}
