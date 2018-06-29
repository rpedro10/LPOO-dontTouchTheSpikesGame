package com.projeto2.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.StringBuilder;
import com.projeto2.game.screen.MenuScreen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DontTouchTheSpikes extends Game {
	public static final int V_WIDTH=480;
	public static final int V_HEIGHT=800;
	public static final float PPM =100;
	public SpriteBatch batch;

	public static final short CANDY_BIT=5;
	public static final short BIRD_BIT=2;
	public static final short TOP_BOT_BIT=4;
	public static final short WALL_BIT=8;
	public static final short LEFT_SPIKE_BIT=16;
	public static final short RIGHT_SPIKE_BIT=32;

	public static String FILE_NAME="InfoGame.txt";

	public static boolean SOUND=true;

	public static AssetManager manager;
	public static Preferences prefs;

	//hud variables
	private int bestScore;
	private int score;
	private int numCandy;

	public DontTouchTheSpikes(){
		score = 0;
		readFile();

		System.out.println(bestScore);
		System.out.println(numCandy);
	}

	public void resetScore(){
		score = 0;
	}

	public int getBestScore(){
		return bestScore;
	}

	public int getScore(){
		return score;
	}

	public int getNumCandy(){
		return numCandy;
	}

	public void updateScore(){
		score++;
	}

	public void updateBestScore(){
		if (this.score >= this.bestScore)
			this.bestScore = this.score;
	}

	public void updateCandyNumber(){
		numCandy++;
	}


	/**
	 * Reads best score and number of candies from text file
	 */
	public void readFile(){
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(FILE_NAME));
			bestScore = Integer.parseInt(br.readLine());
			numCandy = Integer.parseInt(br.readLine());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Saves best score and number of candies in the game
	 */
	public void saveGame() {

		StringBuilder sb = new StringBuilder();
		sb.append("");
		sb.append(bestScore);

		StringBuilder sb2 = new StringBuilder();
		sb2.append("");
		sb2.append(numCandy);

		BufferedWriter writer = null;

		try {
			File file = new File(FILE_NAME);
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(sb.toString());
			writer.newLine();
			writer.write(sb2.toString());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Saves game and exits app
	 */
	public void exitGame(){
		saveGame();
		Gdx.app.exit();
	}

	public AssetManager getManager(){
		return manager;
	}

	@Override
	public void create () {

		prefs=Gdx.app.getPreferences("PreferencesName");
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("game_music.mp3", Music.class);
		manager.finishLoading();
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
