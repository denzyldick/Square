package com.denzyldick.square.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.denzyldick.square.SquareMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Square");
		config.setWindowedMode(1080, 720);
		new Lwjgl3Application(new SquareMain(), config);
	}
}
