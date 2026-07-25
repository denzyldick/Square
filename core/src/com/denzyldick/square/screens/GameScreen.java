package com.denzyldick.square.screens;

import com.denzyldick.square.actors.Square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.denzyldick.square.Font;
import com.denzyldick.square.GameSkin;
import com.denzyldick.square.SoundManager;
import com.denzyldick.square.SquareMain;

public class GameScreen implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private static SquareMain game;
    private Square square;
    public static Integer currentLevel;
    private static SoundManager sound;
    private ShapeRenderer shape;
    private int redDots, redDotsRemaining;
    private final float redDotsDiameter = 22;
    public static boolean gameRunning = false;
    private TextButton button;
    private Font font;
    private GameSkin gameSkin;
    private Stage buttonStage;
    public static int killTimes = 3;
    private BitmapFont hudFont;

    public GameScreen(SquareMain game, SoundManager sound, Font font, GameSkin gameSkin) {
        this.game = game;
        this.sound = sound;
        this.font = font;
        this.gameSkin = gameSkin;
        hudFont = font.getFont();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.06f, 0.06f, 0.10f, 1f);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        camera.position.set(square.squareSprite.getX(),
                square.squareSprite.getY(), 0);
        camera.update();

        renderer.setView(camera);
        square.update(delta);

        renderer.render();
        renderer.getBatch().begin();

        String hudText = Integer.toString(redDotsRemaining) + "/"
                + Integer.toString(redDots);
        hudFont.setColor(Color.WHITE);
        hudFont.draw(renderer.getBatch(), hudText,
                renderer.getViewBounds().x + 10,
                renderer.getViewBounds().y + renderer.getViewBounds().height - 20);

        renderer.getBatch().end();
        square.draw(renderer.getBatch());

        shape.setProjectionMatrix(camera.combined);
        for (MapObject object : map.getLayers().get("points").getObjects()) {
            redDots = map.getLayers().get("points").getObjects().getCount();
            if (object instanceof EllipseMapObject) {
                Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
                shape.setColor(GameSkin.GOLD);
                shape.begin(ShapeType.Filled);
                shape.ellipse(ellipse.x, ellipse.y, this.redDotsDiameter,
                        this.redDotsDiameter);
                shape.end();
                objectCollision(ellipse, object);
                if (redDotsRemaining == redDots && gameRunning == true) {
                    gameRunning = false;
                    redDotsRemaining = 0;
                    this.dispose();
                    currentLevel++;
                    this.onLevelWon();
                    this.show();
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        camera = new OrthographicCamera();
        camera.viewportWidth = width / 2;
        camera.viewportHeight = height / 2;
        square.setCamera(camera);
        camera.update();
    }

    @Override
    public void show() {
        sound.startMusic();
        gameRunning = true;
        map = new TmxMapLoader().load("maps/" + currentLevel + ".tmx");

        renderer = new OrthogonalTiledMapRenderer(map);

        square = new Square((TiledMapTileLayer) map.getLayers().get(0));
        square.squareSprite.setPosition(100, 100);
        Gdx.input.setInputProcessor(square);

        shape = new ShapeRenderer();

        TextButtonStyle style = gameSkin.getButtonStyle();
        button = new TextButton("||", style);
        button.pad(6, 12, 6, 12);
        button.setPosition(
                renderer.getViewBounds().x + renderer.getViewBounds().width
                        - button.getWidth() - 10,
                renderer.getViewBounds().y
                        + renderer.getViewBounds().getHeight()
                        - button.getHeight() - 10);

        button.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                sound.playButtonClick();
                game.setScreen(game.gameMenu);
                return true;
            }
        });

        buttonStage = new Stage();
        buttonStage.addActor(button);
    }

    private void objectCollision(Ellipse ellipse, MapObject object) {
        float sqX = square.squareSprite.getX();
        float sqY = square.squareSprite.getY();
        float sqW = square.squareSprite.getWidth();
        float sqH = square.squareSprite.getHeight();

        float dotX = ellipse.x;
        float dotY = ellipse.y;
        float dotW = this.redDotsDiameter;
        float dotH = this.redDotsDiameter;

        float margin = 8;

        boolean overlap = (sqX - margin < dotX + dotW)
                && (sqX + sqW + margin > dotX)
                && (sqY - margin < dotY + dotH)
                && (sqY + sqH + margin > dotY);

        if (overlap) {
            ellipse.set(10000, 1000, 1, 1);
            object.setColor(Color.WHITE);
            object.setVisible(false);
            object.setOpacity(1f);
            redDotsRemaining++;
            sound.playObject();
        }
    }

    public static void onPlayerDied() {
        Gdx.app.log("Game status", "Lost, ending game.");
        --killTimes;
        sound.stopMusic();
        sound.playFailure();
        game.endScreen.setCurrentLevel(currentLevel);
        game.setScreen(game.endScreen);
    }

    public static void onLevelWon() {
        Gdx.app.log("Game status", "Won, ending game.");
        killTimes = 3;
        sound.stopMusic();
        sound.playSuccess();
        game.gameMenu.openNewLevel();
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
        if (map != null) map.dispose();
        if (renderer != null) renderer.dispose();
        if (shape != null) shape.dispose();
        if (buttonStage != null) buttonStage.dispose();
    }

    public void setLevel(Integer level) {
        currentLevel = level;
    }

    public void restart() {
        this.redDotsRemaining = 0;
    }
}
