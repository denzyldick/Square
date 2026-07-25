package com.denzyldick.square.actors;

import com.denzyldick.square.screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Square implements InputProcessor {

	private Vector2 velocity = new Vector2();
	private float speed = 60 * 2f;
	private float centerX = Gdx.graphics.getWidth() / 2;
	private float centerY = Gdx.graphics.getHeight() / 2;
	private TiledMapTileLayer collisionLayer;
	private float degrees = 60 * 0.001f;
	private Pixmap squarePixmap;
	private Texture squareTexture;
	private OrthographicCamera camera;
	public Sprite squareSprite;

	public Square(TiledMapTileLayer tiledMapTileLayer) {
		squarePixmap = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
		squarePixmap.setColor(new Color(0.34f, 0.69f, 0.95f, 1f));
		squarePixmap.fillRectangle(0, 0, 64, 64);
		squareTexture = new Texture(squarePixmap);
		squareSprite = new Sprite(squareTexture);
		collisionLayer = tiledMapTileLayer;
	}

	public void draw(Batch spritebatch) {
		spritebatch.begin();
		squareSprite.draw(spritebatch);
		spritebatch.end();
	}

	public void update(float delta) {
		squareSprite.setX(squareSprite.getX() + velocity.x * delta);
		squareSprite.setY(squareSprite.getY() + velocity.y * delta);

		float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer
				.getTileHeight();
		boolean collision = false;

		if (collisionLayer.getCell((int) (squareSprite.getX() / tileWidth),
				(int) ((squareSprite.getY() + squareSprite.getHeight()) / tileHeight)) != null) {
			if (collisionLayer.getCell((int) (squareSprite.getX() / tileWidth),
					(int) ((squareSprite.getY() + squareSprite.getHeight()) / tileHeight)).getTile() != null) {
				collision = true;
			}
		}
		if (collisionLayer.getCell((int) ((squareSprite.getX() + squareSprite.getWidth()) / tileWidth),
				(int) ((squareSprite.getY() + squareSprite.getHeight()) / tileHeight)) != null) {
			if (collisionLayer.getCell(
					(int) ((squareSprite.getX() + squareSprite.getWidth()) / tileWidth),
					(int) ((squareSprite.getY() + squareSprite.getHeight()) / tileHeight)).getTile() != null) {
				collision = true;
			}
		}
		if (collisionLayer.getCell((int) (squareSprite.getX() / tileWidth),
				(int) (squareSprite.getY() / tileHeight)) != null) {
			if (collisionLayer.getCell((int) (squareSprite.getX() / tileWidth),
					(int) (squareSprite.getY() / tileHeight)).getTile() != null) {
				collision = true;
			}
		}

		if (collisionLayer.getCell((int) ((squareSprite.getX() + squareSprite.getWidth()) / tileWidth),
				(int) (squareSprite.getY() / tileHeight)) != null) {
			if (collisionLayer.getCell(
					(int) ((squareSprite.getX() + squareSprite.getWidth()) / tileWidth),
					(int) (squareSprite.getY() / tileHeight)).getTile() != null) {
				collision = true;
			}
		}
		if (collision) {
			Gdx.app.log("Square collision", "true");
			GameScreen.onPlayerDied();
		}

		degrees = velocity.x * delta;
		squareSprite.rotate(degrees);
	}

	public void goUp() { velocity.y = speed; }
	public void goDown() { velocity.y = -speed; }
	public void goRight() { velocity.x = speed; }
	public void goLeft() { velocity.x = -speed; }

	public void touchRegion(float screenX, float screenY) {
		if (screenX < centerX && screenY < centerY) { goLeft(); goUp(); }
		if (screenX < centerX && screenY > centerY) { goLeft(); goDown(); }
		if (screenY < centerY && screenX > centerX) { goUp(); goRight(); }
		if (screenY > centerY && screenX > centerX) { goDown(); goRight(); }
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touchRegion(screenX, screenY);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.E: goRight(); goUp(); break;
		case Keys.Q: goLeft(); goUp(); break;
		case Keys.A: goDown(); goLeft(); break;
		case Keys.D: goDown(); goRight(); break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) { return false; }

	@Override
	public boolean keyTyped(char character) { return false; }
}
