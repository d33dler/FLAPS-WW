package nl.andrewlalis.model;

import java.util.HashSet;
import java.util.Set;

public class GameModel {
	private final Player player;
	private final Set<PhysicsObject> objects;
	private PhysicsObject focusedObject;

	public GameModel(Player player) {
		this.player = player;
		this.objects = new HashSet<>();
		this.objects.add(player.getShip());
		this.focusedObject = this.player.getShip();
	}

	public Set<PhysicsObject> getObjects() {
		return objects;
	}

	public void add(PhysicsObject object) {
		this.objects.add(object);
	}

	public PhysicsObject getFocusedObject() {
		return focusedObject;
	}

	public void setFocusedObject(PhysicsObject object) {
		this.focusedObject = object;
	}

	public Player getPlayer() {
		return player;
	}
}
