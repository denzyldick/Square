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

public class OptionsScreen implements Screen {

	private SquareMain game;
	private TextButton volumeButton, backButton;
	private Label heading;
	private SoundManager sound;
	private Stage stage;
	private Table table;
	private BackgroundAnimation backgroundAnimation;
	private Font font;
	private GameSkin gameSkin;

	public OptionsScreen(SquareMain game, SoundManager sound, Font font, GameSkin gameSkin) {
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

		final String volumeText;
		if (sound.getVolume() == 1) {
			volumeText = "off";
		} else {
			volumeText = "on";
		}
		volumeButton = new TextButton("VOLUME " + volumeText.toUpperCase(), btnStyle);
		volumeButton.pad(10, 40, 10, 40);
		volumeButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				sound.playButtonClick();
				if ("off".equals(volumeText)) {
					sound.mute();
				} else {
					sound.unmute();
				}
				game.setScreen(game.optionScreen);
				return true;
			}
		});

		backButton = new TextButton("BACK", btnStyle);
		backButton.pad(10, 40, 10, 40);
		backButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				sound.playButtonClick();
				game.setScreen(game.menu);
				return true;
			}
		});

		heading = new Label("OPTIONS", gameSkin.getHeadingStyle());

		table.add(heading).spaceBottom(30);
		table.row();
		table.add(volumeButton).spaceBottom(12);
		table.row();
		table.add(backButton);

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
