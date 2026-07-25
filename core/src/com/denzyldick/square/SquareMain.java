package com.denzyldick.square;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.denzyldick.square.screens.GameMenu;
import com.denzyldick.square.screens.GameScreen;
import com.denzyldick.square.screens.LostScreen;
import com.denzyldick.square.screens.MenuScreen;
import com.denzyldick.square.screens.OptionsScreen;
import com.denzyldick.square.screens.SplashScreen;
import com.denzyldick.square.screens.TutorialScreen;
import com.denzyldick.square.screens.WonScreen;

public class SquareMain extends Game {

	private static final float BAR_WIDTH_RATIO = 0.4f;
	private static final float BAR_HEIGHT = 6f;

	public SplashScreen splashScreen;
	public MenuScreen menu;
	public GameMenu gameMenu;
	public OptionsScreen optionScreen;
	public LostScreen endScreen;
	public WonScreen wonScreen;
	public TutorialScreen tutorial;

	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private AssetManager manager;
	private SoundManager soundManager;
	private Font font;
	private GameSkin gameSkin;

	@Override
	public void create() {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		soundManager = new SoundManager();
		font = new Font();
		gameSkin = new GameSkin(font);

		manager = new AssetManager();
		manager.load("data/splashscreenbackground.png", Texture.class);
		manager.load("ui/pauzebutton.png", Texture.class);
		manager.load("sounds/failure.ogg", Sound.class);
		manager.load("sounds/menuclick.ogg", Sound.class);
		manager.load("sounds/musicloop1.ogg", Music.class);
		manager.load("sounds/object.ogg", Sound.class);
		manager.load("sounds/succes.ogg", Sound.class);
		manager.load("data/activestar.png", Texture.class);
		manager.load("data/deactivestar.png", Texture.class);

		Gdx.app.log("Shadow Square:", "Opening");
		splashScreen = new SplashScreen(this);
		menu = new MenuScreen(this, soundManager, font, gameSkin);
		gameMenu = new GameMenu(this, soundManager, font, gameSkin);
		optionScreen = new OptionsScreen(this, soundManager, font, gameSkin);
		endScreen = new LostScreen(this, soundManager, font, gameSkin);
		wonScreen = new WonScreen(this, soundManager, font, gameSkin);
		tutorial = new TutorialScreen(this, soundManager, font, gameSkin);
		setScreen(splashScreen);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render() {
		super.render();
		float progress = manager.getProgress();
		if (!manager.update()) {
			float barWidth = Gdx.graphics.getWidth() * BAR_WIDTH_RATIO;
			float barX = (Gdx.graphics.getWidth() - barWidth) / 2;
			float barY = (Gdx.graphics.getHeight() - BAR_HEIGHT) / 2;

			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(0.2f, 0.2f, 0.3f, 1f);
			shapeRenderer.rect(barX, barY, barWidth, BAR_HEIGHT);
			shapeRenderer.setColor(GameSkin.ACCENT);
			shapeRenderer.rect(barX, barY, barWidth * progress, BAR_HEIGHT);
			shapeRenderer.end();
		}
	}

	@Override
	public void dispose() {
		manager.dispose();
		batch.dispose();
		shapeRenderer.dispose();
		soundManager.dispose();
		font.dispose();
		gameSkin.dispose();
	}

	public AssetManager getAssetManager() {
		return manager;
	}

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public Font getFont() {
		return font;
	}

	public GameSkin getGameSkin() {
		return gameSkin;
	}
}
