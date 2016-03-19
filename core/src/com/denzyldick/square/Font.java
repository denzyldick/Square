package com.denzyldick.square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Font {
	private BitmapFont font;
	private FreeTypeFontGenerator fontGenerator;
	private int fontSize = 20;
	private Color fontColor =  Color.RED;
	
	public Font()
	{
		fontGenerator = new FreeTypeFontGenerator(
				Gdx.files.internal("font/font.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameters.size = 20;
		font = fontGenerator.generateFont(parameters);
		fontGenerator.scaleForPixelHeight(Gdx.graphics.getHeight());
		fontGenerator.dispose();
		font.setColor(fontColor);
	}
	
	public BitmapFont getFont()
	{
		return font;
	}
	public void setColor(Color color)
	{
		this.fontColor = color;
	}
	public void dispose()
	{
		font.dispose();
	}
}
