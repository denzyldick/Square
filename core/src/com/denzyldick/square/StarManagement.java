package com.denzyldick.square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
/*
 * draw stars at the end of the game.
 */
public class StarManagement extends Sprite {
	
	private  float starWidth = 200;
	private  float starHeight = 200;
	private Texture activeStar, deactiveStar;
	private float startX, startY;
	private int starAmount;
	Sprite spriteOne,spriteTwo,spriteThree;
	
	public StarManagement(SquareMain game)
	{
		activeStar = new Texture("data/activestar.png");
		deactiveStar = new Texture("data/deactivestar.png");
		startX = (Gdx.graphics.getWidth()/3)-(starWidth);
		startY = (Gdx.graphics.getHeight()/2)-(starHeight);

	

	}
	
	public void drawStars(SpriteBatch spritebatch)
	{
		spriteOne = new Sprite(activeStar);
		spriteOne.setBounds(startX, startY, starWidth, starHeight);
		spriteTwo = new Sprite(activeStar);
		spriteTwo.setBounds(startX + starWidth, startY, starWidth, starHeight);
		spriteThree = new Sprite(activeStar);
		spriteThree.setBounds(startX + starWidth+starWidth, startY, starWidth, starHeight);
		
		
switch(starAmount)
	{
	case 0:
		spriteOne.setTexture(deactiveStar);
		spriteTwo.setTexture(deactiveStar);
		spriteThree.setTexture(deactiveStar);
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
			
		
			
		System.out.println("Drawing stars.");

		spritebatch.begin();
		spriteOne.draw(spritebatch);
		spriteTwo.draw(spritebatch);
		spriteThree.draw(spritebatch);
		spritebatch.end();
	}
	
	public void setWidth(float width)
	{
		this.starWidth = width;
	}
	
	public void setHeight(float height)
	{
		this.starHeight =  height;
	}
	
	public void setStartX(float startX)
	{
		this.startX = startX;
	}
	public void setStartY(float startY)
	{
		this.startY =  startY;
	}
	public void setStarAmount(int starAmount)
	{
		this.starAmount = starAmount;
	}
}
