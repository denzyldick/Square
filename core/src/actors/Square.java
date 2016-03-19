package actors;

import screens.GameScreen;

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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public class Square  implements InputProcessor {

	private Vector2 velocity = new Vector2();
	private float speed = 60 * 2f;
	private float centerX = Gdx.graphics.getWidth() / 2;
	private float centerY = Gdx.graphics.getHeight() / 2;
	private TiledMapTileLayer collisionLayer;
	private float  degrees = 60 * 0.001f; 
	private Pixmap squarePixmap;
	private Texture squareTexture;
	private OrthographicCamera  camera;
	public Sprite squareSprite;
	
	public Square(TiledMapTileLayer tiledMapTileLayer) {
		squarePixmap = new Pixmap(32,32,Pixmap.Format.RGB565);
		squarePixmap.fillRectangle(0, 0, 60, 60);
		squareTexture = new Texture(squarePixmap);
		squareSprite =  new Sprite(squareTexture);
		squareSprite.setColor(Color.RED);
		collisionLayer = tiledMapTileLayer;

	}

	public void draw(Batch spritebatch) {
		spritebatch.begin();
		squareSprite.draw(spritebatch);
		spritebatch.end();
	}

	public void update(float delta) {
		// TODO Auto-generated method stub
	
		
		squareSprite.setX(squareSprite.getX() + velocity.x *delta);
		squareSprite.setY(squareSprite.getY() +velocity.y * delta);
		
		
		float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer
				.getTileHeight();
		boolean collision = false;

		// top left
		if (collisionLayer.getCell((int) (squareSprite.getX() / tileWidth),
				(int) ((squareSprite.getY() + squareSprite.getHeight()) / tileHeight)) != null) {
			if (collisionLayer.getCell((int) (squareSprite.getX() / tileWidth),
					(int) ((squareSprite.getY() + squareSprite.getHeight()) / tileHeight)).getTile() != null) {
				collision = true;
			}
		}
		// top right
		if (collisionLayer.getCell((int) ((squareSprite.getX() + squareSprite.getWidth()) / tileWidth),
				(int) ((squareSprite.getY() + squareSprite.getHeight()) / tileHeight)) != null) {
			if (collisionLayer.getCell(
					(int) ((squareSprite.getX() + squareSprite.getWidth()) / tileWidth),
					(int) ((squareSprite.getY() + squareSprite.getHeight()) / tileHeight)).getTile() != null) {
				collision = true;
			}
		}
		// bottom left
		if (collisionLayer.getCell((int) (squareSprite.getX() / tileWidth),
				(int) (squareSprite.getY() / tileHeight)) != null) {
			if (collisionLayer.getCell((int) (squareSprite.getX() / tileWidth),
					(int) (squareSprite.getY() / tileHeight)).getTile() != null) {
				collision = true;
			}
		}
		// bottom right

		if (collisionLayer.getCell((int) ((squareSprite.getX() + squareSprite.getWidth()) / tileWidth),
				(int) (squareSprite.getY()/ tileHeight)) != null) {
			if (collisionLayer.getCell(
					(int) ((squareSprite.getX() + squareSprite.getWidth()) / tileWidth),
					(int) (squareSprite.getY() / tileHeight)).getTile() != null) {
				collision= true;
			}
		}
		if (collision) {
			Gdx.app.log("Square collision","true");
			GameScreen.endGame();
		}
		
		//Rotate the square
		degrees = velocity.x * delta;
		squareSprite.rotate(degrees);
	
	}

	public void goUp() {
		velocity.y = speed;
	}

	public void goDown() {
		velocity.y = -speed;
	}

	public void goRight() {
		velocity.x = speed;
	}

	public void goLeft() {
		velocity.x = -speed;
	}

	public void touchRegion(float screenX, float screenY) {
		// Go top Left
		if (screenX < centerX && screenY < centerY) {
			goLeft();
			goUp();

		}
		// Go bottom Left
		if (screenX < centerX && screenY > centerY) {
			goLeft();
			goDown();
		}
		// Go top right
		if (screenY < centerY && screenX > centerX) {
			goUp();
			goRight();
		}
		if (screenY > centerY && screenX > centerX) {
			goDown();
			goRight();
		}
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		touchRegion(screenX, screenY);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub

		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setCamera(OrthographicCamera camera) {
		// TODO Auto-generated method stub
		this.camera = camera;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		switch(keycode)
		{
		case Keys.E:
			goRight();
			goUp();
			break;
		case Keys.Q:
			goLeft();
			goUp();
			break;
		case Keys.A:
			goDown();
			goLeft();
			break;
		case Keys.D:
			goDown();
			goRight();
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

}
