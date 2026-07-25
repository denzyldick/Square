package com.denzyldick.square.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.denzyldick.square.BackgroundAnimation;
import com.denzyldick.square.Font;
import com.denzyldick.square.GameSkin;
import com.denzyldick.square.SoundManager;
import com.denzyldick.square.SquareMain;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TutorialScreen implements Screen {

	private static final int TOTAL_STEPS = 4;
	private static final float DOT_SIZE = 30f;
	private static final float SQUARE_SIZE = 24f;
	private static final float ARROW_SIZE = 40f;
	private static final float LIFE_SIZE = 20f;

	private SquareMain game;
	private SoundManager sound;
	private Font font;
	private GameSkin gameSkin;
	private Stage stage;
	private BackgroundAnimation backgroundAnimation;
	private ShapeRenderer shapeRenderer;

	private int currentStep = 0;
	private Label heading;
	private Label instructions;
	private Label stepIndicator;
	private TextButton prevButton;
	private TextButton nextButton;
	private Table table;

	public TutorialScreen(SquareMain game, SoundManager sound, Font font, GameSkin gameSkin) {
		this.game = game;
		this.sound = sound;
		this.font = font;
		this.gameSkin = gameSkin;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(GameSkin.BG.r, GameSkin.BG.g, GameSkin.BG.b, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		backgroundAnimation.draw();
		drawStepVisuals();
		stage.act(delta);
		stage.draw();
	}

	private void drawStepVisuals() {
		float cx = Gdx.graphics.getWidth() / 2f;
		float cy = Gdx.graphics.getHeight() / 2f;

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
		shapeRenderer.begin(ShapeType.Filled);

		switch (currentStep) {
			case 0:
				drawCollectDotsVisual(cx, cy);
				break;
			case 1:
				drawAvoidWallsVisual(cx, cy);
				break;
			case 2:
				drawMovementVisual(cx, cy);
				break;
			case 3:
				drawLivesVisual(cx, cy);
				break;
		}

		shapeRenderer.end();
	}

	private void drawCollectDotsVisual(float cx, float cy) {
		float dotY = cy - 60;
		float[] dotX = { cx - 60, cx, cx + 60 };

		for (float x : dotX) {
			shapeRenderer.setColor(GameSkin.GOLD);
			shapeRenderer.circle(x, dotY, DOT_SIZE / 2);
		}

		shapeRenderer.setColor(GameSkin.ACCENT);
		float sqX = cx - 100;
		float sqY = dotY - SQUARE_SIZE / 2;
		shapeRenderer.rect(sqX, sqY, SQUARE_SIZE, SQUARE_SIZE);
	}

	private void drawAvoidWallsVisual(float cx, float cy) {
		float wallY = cy - 80;
		float wallWidth = 200;
		float wallHeight = 8;

		shapeRenderer.setColor(0.5f, 0.5f, 0.55f, 1f);
		shapeRenderer.rect(cx - wallWidth / 2, wallY, wallWidth, wallHeight);
		shapeRenderer.rect(cx - wallWidth / 2, wallY + 40, wallWidth, wallHeight);

		shapeRenderer.setColor(GameSkin.ACCENT);
		shapeRenderer.rect(cx - SQUARE_SIZE / 2, wallY + 12, SQUARE_SIZE, SQUARE_SIZE);

		shapeRenderer.setColor(GameSkin.CORAL);
		float x1 = cx - SQUARE_SIZE / 2 - 2;
		float y1 = wallY + 2;
		shapeRenderer.rect(x1, y1, SQUARE_SIZE + 4, 4);
	}

	private void drawMovementVisual(float cx, float cy) {
		float zoneSize = 50;
		float offset = 70;
		float yTop = cy - 20;
		float yBot = cy - 80;

		shapeRenderer.setColor(GameSkin.ACCENT);
		shapeRenderer.rect(cx - offset - zoneSize / 2, yTop, zoneSize, zoneSize);
		shapeRenderer.rect(cx + offset - zoneSize / 2, yTop, zoneSize, zoneSize);
		shapeRenderer.rect(cx - offset - zoneSize / 2, yBot, zoneSize, zoneSize);
		shapeRenderer.rect(cx + offset - zoneSize / 2, yBot, zoneSize, zoneSize);

		shapeRenderer.setColor(GameSkin.ACCENT);
		float arrowLen = 15;
		float ax = cx - offset;
		float ay = yTop + zoneSize / 2;
		shapeRenderer.rect(ax - arrowLen / 2, ay + zoneSize / 2 + 2, 3, arrowLen);
		shapeRenderer.rect(ax - zoneSize / 2 - arrowLen - 2, ay - 1, arrowLen, 3);

		ax = cx + offset;
		shapeRenderer.rect(ax - arrowLen / 2, ay + zoneSize / 2 + 2, 3, arrowLen);
		shapeRenderer.rect(ax + zoneSize / 2 + 2, ay - 1, arrowLen, 3);

		float bx = cx - offset;
		float by = yBot + zoneSize / 2;
		shapeRenderer.rect(bx - arrowLen / 2, by - arrowLen - 2, 3, arrowLen);
		shapeRenderer.rect(bx - zoneSize / 2 - arrowLen - 2, by - 1, arrowLen, 3);

		float bxx = cx + offset;
		shapeRenderer.rect(bxx - arrowLen / 2, by - arrowLen - 2, 3, arrowLen);
		shapeRenderer.rect(bxx + zoneSize / 2 + 2, by - 1, arrowLen, 3);
	}

	private void drawLivesVisual(float cx, float cy) {
		float y = cy - 50;
		float spacing = LIFE_SIZE + 12;
		float startX = cx - spacing;

		for (int i = 0; i < 3; i++) {
			shapeRenderer.setColor(GameSkin.ACCENT);
			shapeRenderer.rect(startX + i * spacing, y, LIFE_SIZE, LIFE_SIZE);
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		table.invalidateHierarchy();
	}

	@Override
	public void show() {
		currentStep = 0;
		backgroundAnimation = new BackgroundAnimation();
		shapeRenderer = new ShapeRenderer();
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		table = new Table();
		table.setFillParent(true);
		table.center();

		TextButtonStyle btnStyle = gameSkin.getButtonStyle();

		heading = new Label("HOW TO PLAY", gameSkin.getHeadingStyle());
		instructions = new Label("", gameSkin.getBodyStyle());
		stepIndicator = new Label("", gameSkin.getBodyStyle());

		prevButton = new TextButton("PREV", btnStyle);
		prevButton.pad(8, 24, 8, 24);
		prevButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				sound.playButtonClick();
				if (currentStep > 0) {
					currentStep--;
					updateStep();
				}
				return true;
			}
		});

		nextButton = new TextButton("NEXT", btnStyle);
		nextButton.pad(8, 24, 8, 24);
		nextButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				sound.playButtonClick();
				if (currentStep < TOTAL_STEPS - 1) {
					currentStep++;
					updateStep();
				} else {
					game.setScreen(game.gameMenu);
				}
				return true;
			}
		});

		table.add(heading).colspan(2).spaceBottom(16);
		table.row();
		table.add(instructions).colspan(2).spaceBottom(24);
		table.row();
		table.add(prevButton).spaceRight(12);
		table.add(nextButton);
		table.row();
		table.add(stepIndicator).colspan(2).spaceTop(16);

		stage.addActor(table);
		updateStep();
	}

	private void updateStep() {
		switch (currentStep) {
			case 0:
				heading.setText("COLLECT THE DOTS");
				instructions.setText("Move through each level and collect\nall the golden dots to win.");
				nextButton.setText("NEXT");
				break;
			case 1:
				heading.setText("AVOID THE WALLS");
				instructions.setText("Touching the borders ends the game.\nStay inside the safe zones!");
				nextButton.setText("NEXT");
				break;
			case 2:
				heading.setText("HOW TO MOVE");
				instructions.setText("Touch the 4 corners of the screen\nto move in that direction.\n\nKeyboard: Q (up-left)  E (up-right)\n            A (down-left) D (down-right)");
				nextButton.setText("NEXT");
				break;
			case 3:
				heading.setText("3 LIVES");
				instructions.setText("You have 3 lives per level.\nUse them wisely. Good luck!");
				nextButton.setText("GOT IT!");
				break;
		}

		StringBuilder dots = new StringBuilder();
		for (int i = 0; i < TOTAL_STEPS; i++) {
			dots.append(i == currentStep ? "[X] " : "[ ] ");
		}
		stepIndicator.setText(dots.toString());
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
		if (shapeRenderer != null) shapeRenderer.dispose();
		if (stage != null) stage.dispose();
	}
}
