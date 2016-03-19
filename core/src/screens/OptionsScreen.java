package screens;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.denzyldick.square.BackgroundAnimation;
import com.denzyldick.square.Font;
import com.denzyldick.square.SoundManager;
import com.denzyldick.square.SquareMain;


public class OptionsScreen implements Screen {
	
	private SquareMain game;
	private TextButton volumeButton,backButton;
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
	public OptionsScreen(SquareMain game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		backgroundAnimation = new BackgroundAnimation();		
		font = new Font();
		sound  =new SoundManager();

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glClearColor(1,35,3,1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
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
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch(); 			
//		atlas = new TextureAtlas("ui/button.pack");
//		skin = new Skin(atlas);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);		
		table = new Table();
		table.setBounds(0, stage.getHeight()/4, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		TextButtonStyle  textButtonStyle = new TextButtonStyle();
//		textButtonStyle.up = skin.getDrawable("button.normal");
//		textButtonStyle.down = skin.getDrawable("button.normal");
		textButtonStyle.fontColor = Color.RED;
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = font.getFont();
		
		
		final String volumeText;
		if(sound.getVolume() == 1)
		{
		volumeText = "off";
		}else
		{
			volumeText = "on";
		}
		volumeButton =new TextButton("volume "+volumeText, textButtonStyle);
		volumeButton.pad(buttonPadding);
		volumeButton.addListener( new InputListener()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				sound.buttonClick();
				if(volumeText == "off")
				{
				sound.muteVolume();
				}else
				{
					sound.unmuteVolume();
				}
				game.setScreen(game.optionScreen);
				return true;
			}

		
		});
		backButton = new TextButton("back",textButtonStyle);
		backButton.pad(buttonPadding);
	
		backButton.addListener( new InputListener()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

				sound.buttonClick();
				game.setScreen(game.menu);
				return true;
			}

			
		});
		
		LabelStyle headingStyle = new LabelStyle(font.getFont(),Color.RED);

		heading = new Label("option", headingStyle);

		table.add(heading).spaceBottom(buttonBottomSpacing);
		table.row();
		
		table.add(volumeButton).spaceBottom(buttonBottomSpacing);
	

		
		table.row();
		table.add(backButton).spaceBottom(buttonBottomSpacing);
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
