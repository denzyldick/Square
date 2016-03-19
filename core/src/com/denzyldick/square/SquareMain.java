package com.denzyldick.square;

import screens.GameMenu;
import screens.GameScreen;
import screens.LostScreen;
import screens.MenuScreen;
import screens.OptionsScreen;
import screens.SplashScreen;
import screens.TutorialScreen;
import screens.WonScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;

public class SquareMain extends Game {

	public OptionsScreen optionScreen;
	public SplashScreen splashScreen;
	public MenuScreen menu;
	public AssetManager manager;
	public LostScreen endScreen;
	public WonScreen wonScreen;
	public GameMenu gameMenu;
	public TutorialScreen tutorial;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;

	@Override
	public void create() {
		// TODO Auto-generated method stub
		/*
		 * Loading bar
		 */
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		/*
		 * loading stuff
		 */
		manager = new AssetManager();
		manager.load("data/splashscreenbackground.png",Texture.class);
		manager.load("ui/pauzebutton.png",Texture.class);
		//manager.load("font/font.ttf", FreeTypeFontGenerator.class);
		manager.load("sounds/failure.ogg",Sound.class);
		manager.load("sounds/menuclick.ogg",Sound.class);
		manager.load("sounds/musicloop1.ogg",Music.class);
		manager.load("sounds/object.ogg",Sound.class);
		manager.load("sounds/succes.ogg",Sound.class);
//		manager.load("ui/button.pack",TextureAtlas.class);
		manager.load("data/activestar.png",Texture.class);
		manager.load("data/deactivestar.png",Texture.class);
	
		
		
		Gdx.app.log("Shadow Square:", "Opening");
		optionScreen = new OptionsScreen(this);	
		gameMenu = new GameMenu(this);
		wonScreen = new WonScreen(this);
		endScreen = new LostScreen(this);	
		tutorial = new TutorialScreen(this);		
		splashScreen = new SplashScreen(this);
		setScreen(splashScreen);
		menu = new MenuScreen(this);

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();
		float progress = manager.getProgress();
		if (!manager.update()) {

		batch.begin();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/4,(Gdx.graphics.getWidth()/1.8f)*progress, 30);
		shapeRenderer.end();
		batch.end();
		}
	
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		manager.dispose();
	}
	

}
