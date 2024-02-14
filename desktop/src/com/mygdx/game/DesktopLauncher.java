package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("My GDX Game");

		// Get the screen size
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth() / 4 * 3;
		int screenHeight = (int) screenSize.getHeight() / 4 * 3;

		config.setWindowedMode(screenWidth, screenHeight);
		config.setResizable(false);
		new Lwjgl3Application(new Game(), config);
	}
}
