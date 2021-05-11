package nl.rug.oop.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import nl.rug.oop.gui.view.GuiMain;

public class Main {
	public static void main(String[] args) {

		FlatDarculaLaf.install();
		GuiMain gui = new GuiMain();
	}
}
