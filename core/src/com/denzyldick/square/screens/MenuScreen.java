package com.denzyldick.square.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.denzyldick.square.BackgroundAnimation;
import com.denzyldick.square.Font;
import com.denzyldick.square.GameSkin;
import com.denzyldick.square.SoundManager;
import com.denzyldick.square.SquareMain;

public class MenuScreen implements Screen {

	private SquareMain game;
	private TextButton buttonExit, buttonStart, buttonOption;
	private Label heading;
	private SoundManager sound;
	private Stage stage;
	private Table table;
	private BackgroundAnimation backgroundAnimation;
	private Font font;
	private GameSkin gameSkin;

	public MenuScreen(SquareMain game, SoundManager sound, Font font, GameSkin gameSkin) {
		this.game = game;
		this.sound = sound;
		this.font = font;
		this.gameSkin = gameSkin;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(GameSkin.BG.r, GameSkin.BG.g, GameSkin.BG.b, 1f);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

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

		heading = new Label("SQUARE", gameSkin.getHeadingStyle());

		buttonStart = new TextButton("START", btnStyle);
		buttonStart.pad(10, 40, 10, 40);
		buttonStart.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				sound.playButtonClick();
				game.setScreen(game.gameMenu);
			}
		});

		buttonOption = new TextButton("OPTIONS", btnStyle);
		buttonOption.pad(10, 40, 10, 40);
		buttonOption.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				sound.playButtonClick();
				game.setScreen(game.optionScreen);
			}
		});

		buttonExit = new TextButton("EXIT", btnStyle);
		buttonExit.pad(10, 40, 10, 40);
		buttonExit.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				sound.playButtonClick();
				Gdx.app.exit();
			}
		});

		table.add(heading).spaceBottom(30);
		table.row();
		table.add(buttonStart).spaceBottom(12);
		table.row();
		table.add(buttonOption).spaceBottom(12);
		table.row();
		table.add(buttonExit);

		stage.addActor(table);
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
