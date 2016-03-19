package com.denzyldick.square.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.denzyldick.square.SquareMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Square";
		config.useGL30 = true;
		config.width = 1080;
		config.height = 720;
		new LwjglApplication(new SquareMain(), config);
	}
}
