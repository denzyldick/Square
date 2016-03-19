package screens;

import actors.Square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    private int redDots, redDotsRemaind;
    private final float redDotsDiameter = 22;
    public static boolean gameRunning = false;
    private TextButton button;
    private Font font;
    private Stage buttonStage;
    public static int killtimes = 3;

    public GameScreen(SquareMain game) {
        this.game = game;
        sound = new SoundManager();
        font = new Font();

    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        camera.position.set(square.squareSprite.getX(),
                square.squareSprite.getY(), 0);
        camera.update();

        renderer.setView(camera);
        square.update(delta);

        renderer.render();
        renderer.getBatch().begin();


        font.getFont().draw(renderer.getBatch(),
                Integer.toString(redDotsRemaind) + "/"
                        + Integer.toString(redDots),
                renderer.getViewBounds().x + 10,
                renderer.getViewBounds().y + renderer.getViewBounds().height
                        - 20);


        renderer.getBatch().end();
        square.draw(renderer.getBatch());

        // Render objects
        shape.setProjectionMatrix(camera.combined);
        for (MapObject object : map.getLayers().get("points").getObjects()) {
            redDots = map.getLayers().get("points").getObjects().getCount();
            if (object instanceof EllipseMapObject) {
                Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
                shape.setColor(Color.RED);
                shape.begin(ShapeType.Filled);
                shape.ellipse(ellipse.x, ellipse.y, this.redDotsDiameter,
                        this.redDotsDiameter);
                shape.end();
                objectCollision(ellipse, object);
                // Check if the user has won the game
                if (redDotsRemaind == redDots && gameRunning == true) {
                    gameRunning = false;
                    /**
                     * Transition to new level
                     */
                    redDotsRemaind = 0;
                    this.dispose();
                    currentLevel++;
                    this.wonStage();
                    this.show();
                }

            }
        }

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        camera = new OrthographicCamera();
        camera.viewportWidth = width/2;
        camera.viewportHeight = height/2;
        square.setCamera(camera);
        camera.update();

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        sound.musicLoop();
        gameRunning = true;
        map = new TmxMapLoader().load("maps/" + currentLevel + ".tmx");

        renderer = new OrthogonalTiledMapRenderer(map);

        square = new Square((TiledMapTileLayer) map.getLayers().get(0));
        square.squareSprite.setPosition(100, 100);
        Gdx.input.setInputProcessor(square);

        // Shape renderer
        shape = new ShapeRenderer();

        // Load pause button
        TextButtonStyle style = new TextButtonStyle();
        style.font = font.getFont();
        style.fontColor = Color.RED;
        button = new TextButton("||", style);
        button.setPosition(
                renderer.getViewBounds().x + renderer.getViewBounds().width
                        - button.getWidth() - 2,
                renderer.getViewBounds().y
                        + renderer.getViewBounds().getHeight()
                        - button.getHeight() - 2);

        button.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                sound.buttonClick();
                game.setScreen(game.gameMenu);
                return true;
            }
        });

        buttonStage = new Stage();
        buttonStage.addActor(button);
    }

    private void objectCollision(Ellipse ellipse, MapObject object) {
        float squareX = square.squareSprite.getX(), squareY = square.squareSprite
                .getY(), squareWidth = square.squareSprite.getWidth(), squareHeight = square.squareSprite
                .getHeight();
        if (squareX > ellipse.x && squareX < ellipse.x + this.redDotsDiameter
                && squareY > ellipse.y && squareY < ellipse.y + redDotsDiameter

                || squareX + squareWidth < ellipse.x + this.redDotsDiameter
                && squareX + squareWidth > ellipse.x && squareY > ellipse.y
                && squareY < ellipse.y + redDotsDiameter

                ||

                squareX + squareWidth < ellipse.x + this.redDotsDiameter
                        && squareX + squareWidth > ellipse.x
                        && squareY + squareHeight < ellipse.y + this.redDotsDiameter
                        && squareY + squareHeight > ellipse.y

                ||

                squareX > ellipse.x
                        && squareX < ellipse.x + this.redDotsDiameter
                        && squareY + squareHeight < ellipse.y + this.redDotsDiameter
                        && squareY + squareHeight > ellipse.y

                ) {
            ellipse.set(10000, 1000, 1, 1);
            object.setColor(Color.WHITE);
            object.setVisible(false);
            object.setOpacity(1f);
            redDotsRemaind++;
            sound.objectEffect();

        }

    }

    public static void endGame() {
        Gdx.app.log("Game status", "Lost, ending game.");
        --killtimes;
        sound.musicLoopEnd();
        sound.failureSound();
        game.endScreen.setCurrentLevel(currentLevel);

        game.setScreen(game.endScreen);
    }

    public static void wonStage() {

        Gdx.app.log("Game status", "Won, ending game.");
        killtimes = 3;
        sound.musicLoopEnd();
        sound.succeedEffect();
        game.gameMenu.openNewLevel();
//		game.wonScreen.setCurrentLevel(currentLevel.substring(currentLevel.length() - 1));
//        game.setScreen(game.wonScreen);

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub


    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    public void setlevel(Integer level) {
        // TODO Auto-generated method stub
        currentLevel = level;


    }

    public void restart() {
        this.redDotsRemaind = 0;
    }

}
