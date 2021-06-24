package nl.andrewlalis.model;

import nl.andrewlalis.physics.Vec2;
import nl.andrewlalis.view.view_model.ShipViewModel;
import nl.andrewlalis.view.view_model.ViewModel;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Ship extends PhysicsObject implements ViewModelled {
	private static final int TRAIL_SIZE = 256;

	private double thrust;
	private final ShipViewModel viewModel;
	private Deque<Vec2> trail;

	public boolean forwardThrusterEnabled = false;
	public boolean turningRight = false;
	public boolean turningLeft = false;

	public Ship(double mass, double size, double thrust) {
		super(mass, size);
		this.thrust = thrust;
		this.trail = new ConcurrentLinkedDeque<>();
		this.viewModel = new ShipViewModel(this);
	}

	@Override
	public void updatePosition(double deltaT) {
		if (this.trail.size() > TRAIL_SIZE) {
			this.trail.removeLast();
		}
		this.trail.addFirst(new Vec2(this.getPosition().x, this.getPosition().y));
		super.updatePosition(deltaT);
	}

	public Deque<Vec2> getTrail() {
		return trail;
	}

	public void updateMovement(double deltaT) {
		if (this.forwardThrusterEnabled) {
			double a = deltaT * this.thrust / this.mass;
			double ax = a * Math.cos(this.orientation - (Math.PI / 2));
			double ay = a * Math.sin(this.orientation - (Math.PI / 2));
			this.velocity.acc(new Vec2(ax, ay));
		}

		if (this.turningRight) {
			this.angularVelocity += 2 * Math.PI * deltaT;
		}
		if (this.turningLeft) {
			this.angularVelocity -= 2 * Math.PI * deltaT;
		}
		if (!this.turningRight && !this.turningLeft) {
			if (this.angularVelocity > 0) {
				this.angularVelocity = Math.max(0, this.angularVelocity - 2 * Math.PI * deltaT);
			} else if (this.angularVelocity < 0) {
				this.angularVelocity = Math.min(0, this.angularVelocity + 2 * Math.PI * deltaT);
			}
		}
	}

	@Override
	public ViewModel getViewModel() {
		return this.viewModel;
	}
}
