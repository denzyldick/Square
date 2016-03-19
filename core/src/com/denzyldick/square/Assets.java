package com.denzyldick.square;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

	private static AssetManager manager;
	
	private Assets()
	{
		
	}
	
	public static void loadAssets()
	{
		/*
		 * loading stuff
		 */
		manager = new AssetManager();
		manager.load("data/splashscreenbackground.png",Texture.class);
		manager.load("data/ball.png",Texture.class);
		manager.load("ui/pauzebutton.png",Texture.class);
		//manager.load("font/font.ttf", FreeTypeFontGenerator.class);
		manager.load("sounds/failure.ogg",Sound.class);
		manager.load("sounds/menuclick.ogg",Sound.class);
		manager.load("sounds/musicloop1.ogg",Music.class);
		manager.load("sounds/musicloop2.ogg",Music.class);
		manager.load("sounds/object.ogg",Sound.class);
		manager.load("sounds/succes.ogg",Sound.class);
		manager.load("ui/button.pack",TextureAtlas.class);
	}
	public static FileHandle getAsset(String filename, Class type)
	{
		
		return manager.get(filename);
	}
	public static void disposeAssets()
	{
		manager.dispose();
	}
	
	public static float assetsLoadProgress()
	{
		return manager.getProgress();
	}

	public boolean isUpdating() {
		// TODO Auto-generated method stub
		return manager.update();
	}
}
