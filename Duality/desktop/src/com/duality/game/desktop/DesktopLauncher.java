package com.duality.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.duality.game.Duality;

public class DesktopLauncher {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Duality(), config);
		config.width = WIDTH;
		config.height = HEIGHT;
	}
}
