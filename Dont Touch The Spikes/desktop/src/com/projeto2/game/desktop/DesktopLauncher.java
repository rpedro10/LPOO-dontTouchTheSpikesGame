package com.projeto2.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.projeto2.game.DontTouchTheSpikes;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=DontTouchTheSpikes.V_WIDTH;
		config.height=DontTouchTheSpikes.V_HEIGHT;
		new LwjglApplication(new DontTouchTheSpikes(), config);
	}
}
