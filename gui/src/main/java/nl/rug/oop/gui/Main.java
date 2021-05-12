package nl.rug.oop.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import lombok.SneakyThrows;
import nl.rug.oop.gui.model.AppCore;
import nl.rug.oop.gui.view.MainFrame;

public class Main {
	@SneakyThrows
	public static void main(String[] args) {
		AppCore model = new AppCore();
		FlatDarculaLaf.install();
		MainFrame gui = new MainFrame(model);
	}
}
