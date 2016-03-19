package com.denzyldick.square;



import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class BackgroundAnimation extends Sprite {
	
	private ShapeRenderer shapeRenderer;
	private final float square1Width = 400;
	private final float square1Height = 400;
	private final float square2Width = 300;
	private final float square2Height = 300;
	private float square1X, square1Y;
	private final float angleSpeed = 60 * 0.2f;	
	private Vector2 square1Vector = new Vector2(0,0);
	private Pixmap pixmap;
	private Sprite squareSprite;
	private Sprite squareSprite2;
	private Timer timer;
	private float delaySeconds;
	private float intervalSeconds;
	float red,green,blue;

	public BackgroundAnimation()
	{
		pixmap = new Pixmap(32, 32, Pixmap.Format.RGB565);
		pixmap.setColor(Color.CYAN);
		squareSprite = new Sprite(new Texture(pixmap));
		squareSprite2 = new Sprite(new Texture(pixmap));
		square1Vector.x = Gdx.graphics.getWidth()/2-square1Width;
		square1Vector.y = Gdx.graphics.getHeight()/3;
		squareSprite.setOrigin(square1Vector.x -(square1Width/2), square1Vector.y - (square1Height/2));
		squareSprite2.setOrigin(square1Width/2, square1Height/2);
	
	}

	public void draw(SpriteBatch spriteBatch) {

		spriteBatch.begin();
		
		squareSprite.setBounds(square1Vector.x, square1Vector.y, square1Width, square1Height);
		squareSprite.draw(spriteBatch);
	
		squareSprite2.setBounds(square1Vector.x+square1Width+100, square1Vector.y, square1Width, square1Height);
		squareSprite2.draw(spriteBatch);
		spriteBatch.end();
	
		update();
	}
	private void update()
	{
		squareSprite.rotate(angleSpeed * Gdx.graphics.getDeltaTime());
		squareSprite2.rotate(-angleSpeed * Gdx.graphics.getDeltaTime());
	}
	
	
	

}
