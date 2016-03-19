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


public class MenuScreen implements Screen {
	
	private SquareMain game;
	private TextButton buttonExit, buttonStart, buttonOption;
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
	private Font font;
	
	public MenuScreen(SquareMain game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		font = new Font();
		sound  =new SoundManager();

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glClearColor(1,35,3,1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		//batch.setTransformMatrix(camera.combined);
		batch.begin();
		batch.end();
		backgroundAnimation.draw(batch);
		
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		table.invalidateHierarchy();
		table.setSize(width, height);
//		stage.setViewport(width, height, true);
		camera.viewportHeight = height /2;
		camera.viewportWidth = width /2;
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		backgroundAnimation = new BackgroundAnimation();
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch(); 
			
//		atlas = game.manager.get("ui/button.pack",TextureAtlas.class);
//		skin = new Skin(atlas);
	
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		table = new Table(skin);
		table.setDebug(false);
	
		table.setBounds(0, stage.getHeight()/4, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		TextButtonStyle  textButtonStyle = new TextButtonStyle();
//		textButtonStyle.up = skin.getDrawable("button.normal");
///		textButtonStyle.down = skin.getDrawable("button.normal");
		textButtonStyle.fontColor = Color.RED;

		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = font.getFont();
		
		buttonExit = new TextButton("EXIT",textButtonStyle);
		buttonExit.pad(buttonPadding);
		buttonExit.addListener(new ClickListener()
		{
			public void clicked(InputEvent event, float x, float y)
			{
				
				Gdx.app.exit();
			}
		});
		buttonExit.addListener( new InputListener()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				sound.buttonClick();
				Gdx.app.exit();
				return true;
			}

			
		});
		buttonStart =new TextButton("START", textButtonStyle);
		buttonStart.pad(buttonPadding);
		buttonStart.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y)
			{
				sound.buttonClick();
				game.setScreen(game.gameMenu);
			}
		});
		buttonStart.addListener( new InputListener()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				sound.buttonClick();
				game.setScreen(game.gameMenu);
				return true;
			}

			
		});
		buttonOption = new TextButton("OPTIONS",textButtonStyle);
		buttonOption.pad(buttonPadding);
		buttonOption.addListener(new ClickListener()
		{
			public void clicked(InputEvent event, float x, float y)
			{
				sound.buttonClick();
				game.setScreen(game.optionScreen);
			}
		});
		buttonOption.addListener( new InputListener()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				sound.buttonClick();
				game.setScreen(game.optionScreen);
				return true;
			}

			
		});
		
		LabelStyle headingStyle = new LabelStyle(font.getFont(),Color.DARK_GRAY);

		heading = new Label("SQUARE", headingStyle);

		table.add(heading).spaceBottom(buttonBottomSpacing);
		table.row();
		table.add(buttonStart).spaceBottom(buttonBottomSpacing);
	
		
		table.row();
		table.add(buttonOption).spaceBottom(buttonBottomSpacing);
		table.bottom();
		table.row();
		table.add(buttonExit).spaceBottom(buttonBottomSpacing);
		table.row();
//		table.debug();
		stage.addActor(table);
		
	
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
