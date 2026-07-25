package com.denzyldick.square.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.denzyldick.square.BackgroundAnimation;
import com.denzyldick.square.Font;
import com.denzyldick.square.GameSkin;
import com.denzyldick.square.SoundManager;
import com.denzyldick.square.SquareMain;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class WonScreen implements Screen {

	private SquareMain game;
	private TextButton nextLevelButton, menuButton;
	private Label heading;
	private SoundManager sound;
	private Stage stage;
	private Table table;
	private BackgroundAnimation backgroundAnimation;
	private int currentLevel;
	private Font font;
	private GameSkin gameSkin;

	public WonScreen(SquareMain game, SoundManager sound, Font font, GameSkin gameSkin) {
		this.game = game;
		this.sound = sound;
		this.font = font;
		this.gameSkin = gameSkin;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(GameSkin.BG.r, GameSkin.BG.g, GameSkin.BG.b, 1f);
		Gdx.gl.glClear(com.badlogic.gdx.graphics.GL30.GL_COLOR_BUFFER_BIT);

		backgroundAnimation.draw();
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		table.invalidateHierarchy();
	}

	@Override
	public void show() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		backgroundAnimation = new BackgroundAnimation();
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		table = new Table();
		table.setFillParent(true);
		table.center();

		TextButtonStyle btnStyle = gameSkin.getButtonStyle();

		nextLevelButton = new TextButton("NEXT LEVEL", btnStyle);
		nextLevelButton.pad(10, 40, 10, 40);
		nextLevelButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.gameMenu.setStage(currentLevel + 1);
				return true;
			}
		});

		menuButton = new TextButton("MENU", btnStyle);
		menuButton.pad(10, 40, 10, 40);
		menuButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				sound.playButtonClick();
				game.setScreen(game.gameMenu);
				return true;
			}
		});

		heading = new Label("LEVEL COMPLETE!", gameSkin.getHeadingStyle());

		table.add(heading).spaceBottom(30);
		table.row();
		table.add(nextLevelButton).spaceBottom(12);
		table.row();
		table.add(menuButton);

		stage.addActor(table);
	}

	public void setCurrentLevel(int level) {
		this.currentLevel = level;
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		if (backgroundAnimation != null) backgroundAnimation.dispose();
		if (stage != null) stage.dispose();
	}
}
