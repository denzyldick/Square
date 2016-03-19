package com.denzyldick.square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SoundManager extends Sprite {
	Texture volumeOn, volumeOff;
	public static Sprite volumeSprite;

	Sound buttonClick, failureEffect, succesEffect, objectEffect;
	Music musicLoop;
	Preferences volumeData;

	public void draw(SpriteBatch spritebatch) {
		spritebatch.draw(volumeSprite, 0, 0);

	}
	 protected Preferences getPrefs() {
	      if(volumeData==null){
	         volumeData = Gdx.app.getPreferences("volume");
	      }
	   return volumeData;
	   }

	public float getVolume() {
		return volumeData.getFloat("volume");
	}

	public SoundManager() {
		getPrefs();
		if (volumeData.contains("volume") == false) {
			volumeData.putFloat("volume", 1f);
		}
		volumeOn = new Texture("ui/volumeon.png");
		volumeOff = new Texture("ui/volumeoff.png");

		if (volumeData.getFloat("volume") == 1) {
			volumeSprite = new Sprite(volumeOn);
		} else {
			volumeSprite = new Sprite(volumeOff);
		}

		buttonClick = Gdx.audio.newSound(Gdx.files
				.internal("sounds/menuclick.ogg"));
		musicLoop = Gdx.audio.newMusic(Gdx.files
				.internal("sounds/musicloop1.ogg"));
		failureEffect = Gdx.audio.newSound(Gdx.files
				.internal("sounds/failure.ogg"));
		succesEffect = Gdx.audio.newSound(Gdx.files
				.internal("sounds/succes.ogg"));
		objectEffect = Gdx.audio.newSound(Gdx.files
				.internal("sounds/object.ogg"));
		volumeData.flush();
	}

	public void buttonClick() {
		buttonClick.play(volumeData.getFloat("volume"));

	}

	public void musicLoop() {
	//	musicLoop.play();
		musicLoop.setVolume(volumeData.getFloat("volume"));
		musicLoop.setLooping(true);

	}

	public void musicLoopEnd() {
		musicLoop.stop();
	}

	public void failureSound() {
		failureEffect.play(volumeData.getFloat("volume"));

	}

	public void succeedEffect() {

		succesEffect.play(volumeData.getFloat("volume"));

	}

	public void objectEffect() {
		objectEffect.play(volumeData.getFloat("volume"));
	}

	public void muteVolume() {
		volumeData.putFloat("volume", 0f);
		volumeData.flush();
	}

	public void unmuteVolume() {
		volumeData.putFloat("volume", 1f);
		volumeData.flush();
	}
}
