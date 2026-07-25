package com.denzyldick.square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Font {
	private BitmapFont bodyFont;
	private BitmapFont headingFont;

	public Font() {
		FreeTypeFontGenerator bodyGen = new FreeTypeFontGenerator(
				Gdx.files.internal("font/font.ttf"));
		FreeTypeFontParameter bodyParams = new FreeTypeFontParameter();
		bodyParams.size = 28;
		bodyFont = bodyGen.generateFont(bodyParams);
		bodyGen.dispose();

		FreeTypeFontGenerator headingGen = new FreeTypeFontGenerator(
				Gdx.files.internal("font/cartoon.ttf"));
		FreeTypeFontParameter headingParams = new FreeTypeFontParameter();
		headingParams.size = 52;
		headingFont = headingGen.generateFont(headingParams);
		headingGen.dispose();
	}

	public BitmapFont getFont() {
		return bodyFont;
	}

	public BitmapFont getHeadingFont() {
		return headingFont;
	}

	public void dispose() {
		bodyFont.dispose();
		headingFont.dispose();
	}
}
