package nl.andrewlalis.model;

import nl.andrewlalis.view.view_model.SimpleBodyViewModel;
import nl.andrewlalis.view.view_model.ViewModel;

import java.awt.*;

public class SimpleBody extends PhysicsObject implements ViewModelled {
	private Color color;

	private final SimpleBodyViewModel viewModel;

	public SimpleBody(double mass, double radius, Color color) {
		super(mass, radius);
		this.radius = radius;
		this.color = color;
		this.viewModel = new SimpleBodyViewModel(this);
	}

	public double getRadius() {
		return radius;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public ViewModel getViewModel() {
		return this.viewModel;
	}
}
