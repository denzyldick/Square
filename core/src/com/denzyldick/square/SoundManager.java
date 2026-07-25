package com.denzyldick.square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

	private Sound buttonClickSound;
	private Sound failureSound;
	private Sound successSound;
	private Sound objectSound;
	private Music musicLoop;
	private Preferences preferences;

	public SoundManager() {
		preferences = Gdx.app.getPreferences("volume");
		if (!preferences.contains("volume")) {
			preferences.putFloat("volume", 1f);
			preferences.flush();
		}

		buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/menuclick.ogg"));
		failureSound = Gdx.audio.newSound(Gdx.files.internal("sounds/failure.ogg"));
		successSound = Gdx.audio.newSound(Gdx.files.internal("sounds/succes.ogg"));
		objectSound = Gdx.audio.newSound(Gdx.files.internal("sounds/object.ogg"));
		musicLoop = Gdx.audio.newMusic(Gdx.files.internal("sounds/musicloop1.ogg"));
		musicLoop.setLooping(true);
	}

	public void playButtonClick() {
		buttonClickSound.play(getVolume());
	}

	public void playFailure() {
		failureSound.play(getVolume());
	}

	public void playSuccess() {
		successSound.play(getVolume());
	}

	public void playObject() {
		objectSound.play(getVolume());
	}

	public void startMusic() {
		musicLoop.setVolume(getVolume());
		musicLoop.play();
	}

	public void stopMusic() {
		musicLoop.stop();
	}

	public float getVolume() {
		return preferences.getFloat("volume");
	}

	public void mute() {
		preferences.putFloat("volume", 0f);
		preferences.flush();
	}

	public void unmute() {
		preferences.putFloat("volume", 1f);
		preferences.flush();
	}

	public void dispose() {
		buttonClickSound.dispose();
		failureSound.dispose();
		successSound.dispose();
		objectSound.dispose();
		musicLoop.dispose();
	}
}
