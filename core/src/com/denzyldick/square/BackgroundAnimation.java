package com.denzyldick.square;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundAnimation {

	private static final int SHAPE_COUNT = 8;
	private FloatingShape[] shapes;
	private SpriteBatch batch;
	private Random random = new Random();
	private float time;

	private static class FloatingShape {
		float x, y;
		float size;
		float vx, vy;
		float rotation;
		float rotationSpeed;
		Color color;
		Sprite sprite;
		float baseAlpha;
		float pulseSpeed;
	}

	public BackgroundAnimation() {
		batch = new SpriteBatch();
		shapes = new FloatingShape[SHAPE_COUNT];
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		Color[] palette = {
			new Color(0.34f, 0.69f, 0.95f, 1f),
			new Color(0.95f, 0.35f, 0.45f, 1f),
			new Color(0.38f, 0.82f, 0.67f, 1f),
			new Color(0.50f, 0.75f, 1.0f, 1f),
			new Color(1.0f, 0.50f, 0.60f, 1f),
			new Color(0.20f, 0.80f, 0.70f, 1f),
			new Color(0.60f, 0.45f, 0.80f, 1f),
			new Color(1.0f, 0.80f, 0.35f, 1f)
		};

		float[] sizes = { 200, 150, 100, 130, 170, 90, 110, 80 };
		float[] alphas = { 0.10f, 0.08f, 0.12f, 0.07f, 0.09f, 0.11f, 0.06f, 0.10f };

		for (int i = 0; i < SHAPE_COUNT; i++) {
			shapes[i] = new FloatingShape();
			shapes[i].x = random.nextFloat() * w;
			shapes[i].y = random.nextFloat() * h;
			shapes[i].size = sizes[i];
			shapes[i].vx = (random.nextFloat() - 0.5f) * 20;
			shapes[i].vy = (random.nextFloat() - 0.5f) * 15;
			shapes[i].rotation = random.nextFloat() * 360;
			shapes[i].rotationSpeed = (random.nextFloat() - 0.5f) * 30;
			shapes[i].baseAlpha = alphas[i];
			shapes[i].pulseSpeed = 0.3f + random.nextFloat() * 0.5f;

			Color c = palette[i];
			shapes[i].color = new Color(c.r, c.g, c.b, shapes[i].baseAlpha);

			boolean isCircle = (i % 2 == 0);
			shapes[i].sprite = new Sprite(createShape(isCircle));
		}
	}

	private Texture createShape(boolean circle) {
		int texSize = 64;
		Pixmap pixmap = new Pixmap(texSize, texSize, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		if (circle) {
			pixmap.fillCircle(texSize / 2, texSize / 2, texSize / 2);
		} else {
			pixmap.fillRectangle(0, 0, texSize, texSize);
		}
		Texture tex = new Texture(pixmap);
		pixmap.dispose();
		return tex;
	}

	public void draw() {
		float dt = Gdx.graphics.getDeltaTime();
		time += dt;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		batch.enableBlending();
		batch.begin();

		for (FloatingShape fs : shapes) {
			fs.x += fs.vx * dt;
			fs.y += fs.vy * dt;
			fs.rotation += fs.rotationSpeed * dt;

			if (fs.x < -fs.size) fs.x = w + fs.size;
			if (fs.x > w + fs.size) fs.x = -fs.size;
			if (fs.y < -fs.size) fs.y = h + fs.size;
			if (fs.y > h + fs.size) fs.y = -fs.size;

			float alpha = fs.baseAlpha + (float) Math.sin(time * fs.pulseSpeed) * 0.03f;
			fs.color.a = Math.max(0.02f, Math.min(0.2f, alpha));
			fs.sprite.setColor(fs.color);
			fs.sprite.setOriginCenter();
			fs.sprite.setRotation(fs.rotation);
			fs.sprite.setBounds(
				fs.x - fs.size / 2,
				fs.y - fs.size / 2,
				fs.size,
				fs.size
			);
			fs.sprite.draw(batch);
		}

		batch.end();
	}

	public void dispose() {
		for (FloatingShape fs : shapes) {
			fs.sprite.getTexture().dispose();
		}
		batch.dispose();
	}
}
