package nl.andrewlalis.model;

import nl.andrewlalis.physics.Vec2;
import nl.andrewlalis.view.GamePanel;

public class GameUpdater extends Thread {
	public static final double PHYSICS_FPS = 60.0;
	public static final double MILLISECONDS_PER_PHYSICS_TICK = 1000.0 / PHYSICS_FPS;
	public static final double PHYSICS_SPEED = 1.0;

	public static final double DISPLAY_FPS = 60.0;
	public static final double MILLISECONDS_PER_DISPLAY_FRAME = 1000.0 / DISPLAY_FPS;

	private final GameModel model;
	private final GamePanel gamePanel;
	private volatile boolean running = true;

	public GameUpdater(GameModel model, GamePanel gamePanel) {
		this.model = model;
		this.gamePanel = gamePanel;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void run() {
		long lastPhysicsUpdate = System.currentTimeMillis();
		long lastDisplayUpdate = System.currentTimeMillis();
		while (this.running) {
			long currentTime = System.currentTimeMillis();
			long timeSinceLastPhysicsUpdate = currentTime - lastPhysicsUpdate;
			long timeSinceLastDisplayUpdate = currentTime - lastDisplayUpdate;
			if (timeSinceLastPhysicsUpdate >= MILLISECONDS_PER_PHYSICS_TICK) {
				double elapsedSeconds = timeSinceLastPhysicsUpdate / 1000.0;
				this.updateModelPhysics(elapsedSeconds * PHYSICS_SPEED);
				lastPhysicsUpdate = currentTime;
				timeSinceLastPhysicsUpdate = 0L;
			}
			if (timeSinceLastDisplayUpdate >= MILLISECONDS_PER_DISPLAY_FRAME) {
				this.gamePanel.repaint();
				lastDisplayUpdate = currentTime;
				timeSinceLastDisplayUpdate = 0L;
			}
			long timeUntilNextPhysicsUpdate = (long) (MILLISECONDS_PER_PHYSICS_TICK - timeSinceLastPhysicsUpdate);
			long timeUntilNextDisplayUpdate = (long) (MILLISECONDS_PER_DISPLAY_FRAME - timeSinceLastDisplayUpdate);

			// Sleep to reduce CPU usage.
			try {
				Thread.sleep(Math.min(timeUntilNextPhysicsUpdate, timeUntilNextDisplayUpdate));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void updateModelPhysics(double deltaT) {
		for (PhysicsObject object : this.model.getObjects()) {
			for (PhysicsObject other : this.model.getObjects()) {
				if (object != other) {
					object.gravitateTowards(other, deltaT);
					if (this.checkCollision(object, other)) {
						// Perfectly elastic collision: https://en.wikipedia.org/wiki/Elastic_collision
						Vec2 a = object.getPosition().sub(other.getPosition()).mul(
								(object.getVelocity().sub(other.getVelocity()).dot(object.getPosition().sub(other.getPosition()))
								/ Math.pow(object.getPosition().sub(other.getPosition()).mag(), 2))
						);
						Vec2 v1 = object.getVelocity().sub(a.mul(2 * other.getMass() / (object.getMass() + other.getMass())));
						Vec2 b = other.getPosition().sub(object.getPosition()).mul(
								(other.getVelocity().sub(object.getVelocity()).dot(other.getPosition().sub(object.getPosition())))
								/ Math.pow(other.getPosition().sub(object.getPosition()).mag(), 2)
						);
						Vec2 v2 = other.getVelocity().sub(b.mul(2 * object.getMass() / (object.getMass() + other.getMass())));
						object.setVelocity(v1);
						other.setVelocity(v2);
					}
				}
			}
			object.updatePosition(deltaT);
		}
		this.updateShipPhysics(deltaT);
	}

	private void updateShipPhysics(double deltaT) {
		for (PhysicsObject object : this.model.getObjects()) {
			this.model.getPlayer().getShip().gravitateTowards(object, deltaT);
		}
		this.model.getPlayer().getShip().updateMovement(deltaT);
		this.model.getPlayer().getShip().updatePosition(deltaT);
	}

	private boolean checkCollision(PhysicsObject object, PhysicsObject other) {
		double distance = object.getPosition().hyp(other.getPosition());
		return distance < object.getRadius() + other.getRadius();
	}
}
