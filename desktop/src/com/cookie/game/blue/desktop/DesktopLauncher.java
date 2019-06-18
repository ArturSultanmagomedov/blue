package com.cookie.game.blue.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cookie.game.blue.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name", "Public");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "blue";
		config.useGL30 = true;
		config.height = 9 * 40;
		config.width = 16 * 40;
		new LwjglApplication(new Game(), config);
	}
}
