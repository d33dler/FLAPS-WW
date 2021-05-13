package nl.rug.oop.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import lombok.SneakyThrows;
import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.view.MainFrame;

public class Main {
	@SneakyThrows
	public static void main(String[] args) {
		FlatDarculaLaf.install();
		AppCore model = new AppCore();
	}
}
