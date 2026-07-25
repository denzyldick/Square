package com.denzyldick.square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StarManagement {

	private static final float STAR_SIZE = 200;

	private final Texture activeStar;
	private final Texture deactiveStar;
	private final Sprite spriteOne;
	private final Sprite spriteTwo;
	private final Sprite spriteThree;

	public StarManagement() {
		activeStar = new Texture("data/activestar.png");
		deactiveStar = new Texture("data/deactivestar.png");

		float startX = (Gdx.graphics.getWidth() / 3f) - STAR_SIZE;
		float startY = (Gdx.graphics.getHeight() / 2f) - STAR_SIZE;

		spriteOne = new Sprite(activeStar);
		spriteOne.setBounds(startX, startY, STAR_SIZE, STAR_SIZE);

		spriteTwo = new Sprite(activeStar);
		spriteTwo.setBounds(startX + STAR_SIZE, startY, STAR_SIZE, STAR_SIZE);

		spriteThree = new Sprite(activeStar);
		spriteThree.setBounds(startX + STAR_SIZE * 2, startY, STAR_SIZE, STAR_SIZE);
	}

	public void setStarAmount(int starAmount) {
		switch (starAmount) {
			case 0:
				spriteOne.setTexture(deactiveStar);
				spriteTwo.setTexture(deactiveStar);
				spriteThree.setTexture(deactiveStar);
				break;
			case 1:
				spriteOne.setTexture(activeStar);
				spriteTwo.setTexture(deactiveStar);
				spriteThree.setTexture(deactiveStar);
				break;
			case 2:
				spriteOne.setTexture(activeStar);
				spriteTwo.setTexture(activeStar);
				spriteThree.setTexture(deactiveStar);
				break;
			case 3:
				spriteOne.setTexture(activeStar);
				spriteTwo.setTexture(activeStar);
				spriteThree.setTexture(activeStar);
				break;
			default:
				spriteOne.setTexture(deactiveStar);
				spriteTwo.setTexture(deactiveStar);
				spriteThree.setTexture(deactiveStar);
				break;
		}
	}

	public void drawStars(SpriteBatch batch) {
		batch.begin();
		spriteOne.draw(batch);
		spriteTwo.draw(batch);
		spriteThree.draw(batch);
		batch.end();
	}

	public void setWidth(float width) {
		float ratio = width / STAR_SIZE;
		spriteOne.setSize(width, STAR_SIZE * ratio);
		spriteTwo.setSize(width, STAR_SIZE * ratio);
		spriteThree.setSize(width, STAR_SIZE * ratio);
	}

	public void setHeight(float height) {
		spriteOne.setSize(spriteOne.getWidth(), height);
		spriteTwo.setSize(spriteTwo.getWidth(), height);
		spriteThree.setSize(spriteThree.getWidth(), height);
	}

	public void setStartX(float startX) {
		float w = spriteOne.getWidth();
		spriteOne.setX(startX);
		spriteTwo.setX(startX + w);
		spriteThree.setX(startX + w * 2);
	}

	public void setStartY(float startY) {
		spriteOne.setY(startY);
		spriteTwo.setY(startY);
		spriteThree.setY(startY);
	}

	public void dispose() {
		activeStar.dispose();
		deactiveStar.dispose();
	}
}
