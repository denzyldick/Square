package screens;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.denzyldick.square.BackgroundAnimation;
import com.denzyldick.square.Font;
import com.denzyldick.square.SoundManager;
import com.denzyldick.square.SquareMain;
import com.denzyldick.square.StarManagement;

public class WonScreen implements Screen {
	
	private SquareMain game;
	private TextButton nextLevelButton, menuButton;
	private Label heading;
	private final float buttonPadding = 5;
	private final float buttonBottomSpacing = 3;
	private SoundManager sound;	
	private SpriteBatch batch;
	private TextureAtlas atlas;
	private Skin skin;
	private Stage stage;
	private Table table;
	private OrthographicCamera camera;
	private BackgroundAnimation backgroundAnimation;
	private int currentLevel;
	private Font font;
	private StarManagement starManagement;
	private int starAmount;
	public WonScreen(SquareMain game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		backgroundAnimation = new BackgroundAnimation();
		sound  = new SoundManager();
		font = new Font();
		starManagement = new StarManagement(game);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glClearColor(1,35,3,1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		backgroundAnimation.draw(batch);
	//	batch.begin();
	//	starManagement.setStarAmount(GameScreen.killtimes);
	//	starManagement.drawStars(batch);
	//	batch.end();
		stage.act(delta);
		stage.draw();
		
	
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		table.invalidateHierarchy();
		table.setSize(width,height);
//		stage.setViewport(width, height, true);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch(); 
			
		atlas = game.manager.get("ui/button.pack",TextureAtlas.class);
		skin = new Skin(atlas);
	
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		table = new Table(skin);
	
		table.setBounds(0, stage.getHeight()/4, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		TextButtonStyle  textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.normal");
		textButtonStyle.down = skin.getDrawable("button.normal");

		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = font.getFont();
		nextLevelButton = new TextButton("next level",textButtonStyle);
		nextLevelButton.pad(buttonPadding);

		nextLevelButton.addListener( new InputListener()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.print(currentLevel+ "Current level.");
				new GameMenu(game).setStage(currentLevel +1);
				return true;
			}

			
		});
		
		menuButton = new TextButton("menu",textButtonStyle);
		menuButton.pad(buttonPadding);

		menuButton.addListener( new InputListener()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				sound.buttonClick();
				game.setScreen(game.gameMenu);
				return true;
			}

			
		});
		LabelStyle headingStyle = new LabelStyle(font.getFont(),Color.RED);
		heading = new Label("awesome!", headingStyle);

		table.add(heading).spaceBottom(buttonBottomSpacing);
		table.row();
		table.add(nextLevelButton).spaceBottom(buttonBottomSpacing);	
		table.row();
		table.add(menuButton).spaceBottom(buttonBottomSpacing);
		stage.addActor(table);
		
	
	}
	public void setStarAmount(int starAmount)
	{
		this.starAmount = starAmount;
	}
	public void setCurrentLevel(int level)
	{
		this.currentLevel = level;
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


}
