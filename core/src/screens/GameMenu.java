package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.denzyldick.square.BackgroundAnimation;
import com.denzyldick.square.Font;
import com.denzyldick.square.SoundManager;
import com.denzyldick.square.SquareMain;
import com.denzyldick.square.StarManagement;
import com.denzyldick.square.json.JsonObject;
import com.denzyldick.square.json.JsonObject.Member;


public class GameMenu implements Screen {

	public SquareMain game;
	private TextButton backButton;
	private Label heading;
	private final float buttonPadding = 5;
	private SpriteBatch batch;
	private TextureAtlas atlas;
	private Skin skin;
	private Stage stage;
	private Table table;
	private GameScreen gameScreen;
	private SoundManager sound;
	private BackgroundAnimation backgroundAnimation;
	private OrthographicCamera camera;
	private Font font;
	private TextButtonStyle textButtonStyle = new TextButtonStyle();
	private int iteration = 1;
	private int currentLevelIndex;
	private int maxColumn = 4;
	private FileHandle levelFile = Gdx.files.internal("maps/levels.json"), starFile = Gdx.files.internal("maps/levelstar.json");
	Preferences prefs = Gdx.app.getPreferences("levels");
	StarManagement starManagement = new StarManagement(game);
	private JsonObject levelJsonObject, starsJsonObject;
	private boolean changed = true;
	private ScrollPane scrollpane;

	public GameMenu(SquareMain game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		gameScreen = new GameScreen(game);
		backgroundAnimation = new BackgroundAnimation();
		sound = new SoundManager();
		font = new Font();
		table = new Table(skin);
		table.setZIndex(0);
		
		/*
		 * Read Json file.
		 */
		String jsonString = levelFile.readString();
		String starsfilejson = starFile.readString();

		levelJsonObject = JsonObject.readFrom(prefs.getString("levels",jsonString));
		starsJsonObject = JsonObject.readFrom(starsfilejson);
	}

	private void setTableHeading(String headingText) {
		LabelStyle headingStyle = new LabelStyle(font.getFont(), Color.RED);
		heading = new Label(headingText, headingStyle);
		table.add(heading).colspan(maxColumn);
		table.row();
	}
	public void saveJson()
	{
		this.prefs.putString("levels",levelJsonObject.toString());
		this.prefs.flush();
//		levelFile.writeString(levelJsonObject.toString(),false);
	}

	private void generateStageButtons() {

		for (final Member member : levelJsonObject) {



			if (member.getValue().asBoolean() == true) {

				textButtonStyle.fontColor = Color.RED;
				TextButton button = new TextButton(Integer.toString(iteration),
						textButtonStyle);

				button.addListener(new InputListener() {

					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						changed = true;
						currentLevelIndex = currentLevelIndex + 1;
						System.out.println(currentLevelIndex);
						String name = member.getName();
						setStage(Integer.parseInt(name));
						
						return true;
					}

				});

				button.pad(buttonPadding);
				table.add(button).pad(5);
				
			
			} else {
				textButtonStyle.fontColor = Color.DARK_GRAY;
				TextButton button = new TextButton(Integer.toString(iteration),
						textButtonStyle);
				button.isChecked();
				button.setDisabled(true);
				button.pad(buttonPadding);
				table.add(button).pad(5);
			}

			if (iteration % maxColumn == 0)
				table.row();

			iteration++;


		}
		table.row();
	}

	private void createBackButton() {
		backButton = new TextButton("back", textButtonStyle);
		backButton.pad(buttonPadding);
		backButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				game.setScreen(game.menu);
				return true;
			}
		});
		table.add(backButton).colspan(maxColumn);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glClearColor(1, 35, 3, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.end();
		backgroundAnimation.draw(batch);

		stage.act(delta);
		stage.draw();
		/*
		 * To slow on my computer. Find a different  solution.
		 */
		
		/**for(int i = 0; i < table.getCells().size()-1; i++)
		{	
				
			
			if(i>=1){
				TextButton button = (TextButton) table.getCells().get(i).getWidget();
				if(!button.isDisabled())
				{
					
					starManagement.setStartX(table.getCells().get(i).getWidgetX());
					starManagement.setStartY(table.getCells().get(i).getWidgetY()-10);
					int amount = starsJsonObject.get("stage"+Integer.toString(i)).asInt();
					starManagement.setStarAmount(amount);
					
					starManagement.setWidth(table.getCells().get(i).getWidgetWidth()/3);
					starManagement.setHeight(30);
					starManagement.drawStars(batch);
				}
			}
				
		
		
		}**/
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

		table.setSize(width, height);
		scrollpane.setSize(width, height);
//		stage.setViewport(width, height, true);
	

	}

	private void createTextButtonStyle() {
//		textButtonStyle.up = skin.getDrawable("button.normal");
//		textButtonStyle.down = skin.getDrawable("button.normal");

		textButtonStyle.pressedOffsetX = 3;
		textButtonStyle.pressedOffsetY = -3;
		textButtonStyle.checkedFontColor = Color.RED;
		textButtonStyle.font = font.getFont();
		textButtonStyle.fontColor = Color.RED;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(1, h / w);
		batch = new SpriteBatch();
//		atlas = game.manager.get("ui/button.pack", TextureAtlas.class);
//		skin = new Skin(atlas);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		/* The methods needs to be called in the same order all the time */
		setTableHeading("LEVELS");
		createTextButtonStyle();
		generateStageButtons();
		createBackButton();
		scrollpane = new ScrollPane(table);
		stage.addActor(scrollpane);
	}

	/*
	 * Set the current stage that need to be played.
	 */
	public void setStage(Integer level) {
		sound.buttonClick();
		gameScreen.setlevel(level);
		game.setScreen(gameScreen);
	}

	/*
	 * Open a level after completed the previous one.
	 */
	public void openNewLevel() {
		if (changed) {
			levelJsonObject.set(levelJsonObject.names().get(currentLevelIndex),
					true);
			changed = false;
			saveJson();
		}
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		iteration = 1; // It doesn't stop counting. That is why, it is being set
						// to it's initial value.
		table.clear();
		stage.dispose();
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
		table.clear();
		stage.dispose();
	}

}
