package com.denzyldick.square.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.denzyldick.square.BackgroundAnimation;
import com.denzyldick.square.Font;
import com.denzyldick.square.GameSkin;
import com.denzyldick.square.SoundManager;
import com.denzyldick.square.SquareMain;
import com.denzyldick.square.json.JsonObject;
import com.denzyldick.square.json.JsonObject.Member;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameMenu implements Screen {

	public SquareMain game;
	private Label heading;
	private final float buttonPadding = 8;
	private Stage stage;
	private Table table;
	private GameScreen gameScreen;
	private SoundManager sound;
	private BackgroundAnimation backgroundAnimation;
	private Font font;
	private GameSkin gameSkin;
	private TextButtonStyle textButtonStyle;
	private int iteration = 1;
	private int currentLevelIndex;
	private int maxColumn = 4;
	private FileHandle levelFile = Gdx.files.internal("maps/levels.json");
	Preferences prefs = Gdx.app.getPreferences("levels");
	private JsonObject levelJsonObject;
	private boolean changed = true;
	private ScrollPane scrollpane;

	public GameMenu(SquareMain game, SoundManager sound, Font font, GameSkin gameSkin) {
		this.game = game;
		this.sound = sound;
		this.font = font;
		this.gameSkin = gameSkin;
		gameScreen = new GameScreen(game, sound, font, gameSkin);
		backgroundAnimation = new BackgroundAnimation();

		String jsonString = levelFile.readString();
		levelJsonObject = JsonObject.readFrom(prefs.getString("levels", jsonString));
	}

	private void setTableHeading(String headingText) {
		heading = new Label(headingText, gameSkin.getHeadingStyle());
		table.add(heading).colspan(maxColumn).spaceBottom(16);
		table.row();
	}

	public void saveJson() {
		this.prefs.putString("levels", levelJsonObject.toString());
		this.prefs.flush();
	}

	private void generateStageButtons() {
		for (final Member member : levelJsonObject) {
			if (member.getValue().asBoolean()) {
				textButtonStyle.fontColor = new com.badlogic.gdx.graphics.Color(1f, 1f, 1f, 1f);
				TextButton button = new TextButton(Integer.toString(iteration),
						textButtonStyle);

				button.addListener(new InputListener() {
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						changed = true;
						currentLevelIndex = currentLevelIndex + 1;
						String name = member.getName();
						setStage(Integer.parseInt(name));
						return true;
					}
				});

				button.pad(buttonPadding);
				table.add(button).pad(4).size(64, 64);
			} else {
				textButtonStyle.fontColor = new com.badlogic.gdx.graphics.Color(0.35f, 0.35f, 0.45f, 1f);
				TextButton button = new TextButton(Integer.toString(iteration),
						textButtonStyle);
				button.setDisabled(true);
				button.pad(buttonPadding);
				table.add(button).pad(4).size(64, 64);
			}

			if (iteration % maxColumn == 0)
				table.row();

			iteration++;
		}
		table.row();
	}

	private void createBackButton() {
		TextButton backButton = new TextButton("BACK", gameSkin.getButtonStyle());
		backButton.pad(10, 30, 10, 30);
		backButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				sound.playButtonClick();
				game.setScreen(game.menu);
				return true;
			}
		});
		table.add(backButton).colspan(maxColumn).spaceTop(16);
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
		textButtonStyle = gameSkin.getButtonStyle();

		setTableHeading("LEVELS");
		generateStageButtons();
		createBackButton();

		scrollpane = new ScrollPane(table);
		scrollpane.setFillParent(true);
		stage.addActor(scrollpane);
	}

	public void setStage(Integer level) {
		sound.playButtonClick();
		gameScreen.setLevel(level);
		game.setScreen(gameScreen);
	}

	public void openNewLevel() {
		if (changed) {
			levelJsonObject.set(levelJsonObject.names().get(currentLevelIndex),
					true);
			changed = false;
			saveJson();
		}
	}

	@Override
	public void hide() {
		iteration = 1;
		table.clear();
		if (stage != null) stage.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		table.clear();
		if (stage != null) stage.dispose();
		if (backgroundAnimation != null) backgroundAnimation.dispose();
	}
}
