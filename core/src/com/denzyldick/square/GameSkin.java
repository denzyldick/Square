package com.denzyldick.square;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;

public class GameSkin {
	public static final Color BG = new Color(0.098f, 0.098f, 0.18f, 1f);
	public static final Color ACCENT = new Color(0.34f, 0.69f, 0.95f, 1f);
	public static final Color ACCENT_DARK = new Color(0.22f, 0.50f, 0.78f, 1f);
	public static final Color CORAL = new Color(0.95f, 0.35f, 0.45f, 1f);
	public static final Color MINT = new Color(0.38f, 0.82f, 0.67f, 1f);
	public static final Color GOLD = new Color(1.0f, 0.84f, 0.2f, 1f);

	private Skin skin;
	private Array<Texture> textures = new Array<>();

	public GameSkin(Font font) {
		skin = new Skin();

		Texture btnNormal = new Texture(createRoundedRect(300, 64, 12, ACCENT));
		Texture btnPressed = new Texture(createRoundedRect(300, 64, 12, ACCENT_DARK));
		textures.add(btnNormal);
		textures.add(btnPressed);

		NinePatch normalPatch = new NinePatch(btnNormal, 14, 14, 14, 14);
		NinePatch pressedPatch = new NinePatch(btnPressed, 14, 14, 14, 14);

		TextButtonStyle btnStyle = new TextButtonStyle();
		btnStyle.up = new NinePatchDrawable(normalPatch);
		btnStyle.down = new NinePatchDrawable(pressedPatch);
		btnStyle.font = font.getFont();
		btnStyle.fontColor = Color.WHITE;
		btnStyle.pressedOffsetX = 1;
		btnStyle.pressedOffsetY = -1;
		skin.add("default", btnStyle);

		LabelStyle headingStyle = new LabelStyle(font.getHeadingFont(), Color.WHITE);
		skin.add("heading", headingStyle);

		LabelStyle bodyStyle = new LabelStyle(font.getFont(), new Color(0.8f, 0.8f, 0.85f, 1f));
		skin.add("body", bodyStyle);
	}

	public TextButtonStyle getButtonStyle() {
		return skin.get("default", TextButtonStyle.class);
	}

	public LabelStyle getHeadingStyle() {
		return skin.get("heading", LabelStyle.class);
	}

	public LabelStyle getBodyStyle() {
		return skin.get("body", LabelStyle.class);
	}

	public Skin getSkin() {
		return skin;
	}

	public void dispose() {
		for (Texture t : textures) {
			t.dispose();
		}
		skin.dispose();
	}

	private static Pixmap createRoundedRect(int w, int h, int r, Color c) {
		Pixmap p = new Pixmap(w, h, Pixmap.Format.RGBA8888);
		p.setColor(c);
		p.fillRectangle(r, 0, w - 2 * r, h);
		p.fillRectangle(0, r, w, h - 2 * r);
		p.fillCircle(r, r, r);
		p.fillCircle(w - 1 - r, r, r);
		p.fillCircle(r, h - 1 - r, r);
		p.fillCircle(w - 1 - r, h - 1 - r, r);
		return p;
	}
}
