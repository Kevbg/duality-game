package com.duality.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class TelaJogo implements Screen{	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final int PAUSED = 0;
	private static final int RUNNING = 1;
	private static final int UP= 0;
	private static final int DOWN = 1;
	
	Duality jogo;
	
	private String title = "Duality";
	private SpriteBatch batch;
	private Sprite spritePersonagem;
	private Sprite spriteCenario;
	private World world;
	private Body circleBody;
	private Body groundBody;
	private BodyDef circleBodyDef;
	private BodyDef groundBodyDef;
	private Vector2 gravity;
	private Personagem personagem = new Personagem();
	private Cenario cenario = new Cenario();
	private OrthographicCamera camera;
	private int circleRadius = 15;
	private int state;
	private int orientation;
	
	public TelaJogo (Duality jogo){
		this.jogo = jogo;
	}

	@Override
	public void show() {
		state = RUNNING;
		orientation = UP;
		
		System.out.println("Controles:\n" + "Setas - Movimentação do personagem\n" + "Espaço - Pular\n" + 
						   "CTRL (Esquerdo) - Reseta o personagem\n" + "ESC - Pausa o jogo");
		
		batch = new SpriteBatch();
		gravity = new Vector2(0, -100f);
		world = new World(gravity, true);
		camera = new OrthographicCamera(WIDTH, HEIGHT);
		
		spritePersonagem = personagem.getSprite();
		spriteCenario = cenario.getSprite();	
		
		camera.position.x = WIDTH/2;
		camera.position.y = HEIGHT/2;
		
		//Cria o corpo do chão
		groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyDef.BodyType.StaticBody;
		groundBodyDef.position.set(0, -circleRadius);	
		groundBody = world.createBody(groundBodyDef);	
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(WIDTH/2, 1);
		groundBody.createFixture(groundBox, 0.0f);
		
		//Cria o corpo do personagem
		circleBodyDef = new BodyDef();
		circleBodyDef.type = BodyDef.BodyType.DynamicBody;
		circleBodyDef.position.set(0, 0);
		circleBody = world.createBody(circleBodyDef);
		CircleShape circle = new CircleShape();
		circle.setRadius(circleRadius);
		
		//Cria a fixture do personagem (p/ colisão, gravidade, etc)
		FixtureDef fixtureDef = new FixtureDef();  
        fixtureDef.shape = circle;  
        fixtureDef.density = 1.0f;  
        fixtureDef.friction = 0.0f;  
        fixtureDef.restitution = 0.15f;  
        circleBody.createFixture(fixtureDef);
		
        //A shape circle já não é mais usada e pode ser descartada
		circle.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		spriteCenario.draw(batch);
		spritePersonagem.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
		if(state == RUNNING){
			state = PAUSED;
			Gdx.graphics.setTitle(title + " - PAUSED");
		}
	}

	@Override
	public void resume() {
		if(state == PAUSED){
			state = RUNNING;
		}
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {	
	}
	
	public String getTitle(){
		return title;
	}
	
	public void checkInput(){
		//Verifica a entrada de comandos do teclado
		if (state == RUNNING){	
			if (Gdx.input.isKeyPressed(Keys.LEFT)){
				circleBody.setTransform(circleBody.getPosition().x -3f,circleBody.getPosition().y, 0f);
			}
			
			if (Gdx.input.isKeyPressed(Keys.RIGHT)){
				circleBody.setTransform(circleBody.getPosition().x +3f, circleBody.getPosition().y, 0f);	
			}
			
			//Só é permitido mudar a orientação do personagem quando ele está a 2 PIXELS do chão, para evitar bugs e
			//comportamentos indesejados
			if (Gdx.input.isKeyJustPressed(Keys.DOWN) && orientation == UP && 
			   (circleBody.getPosition().y <= 2 && circleBody.getPosition().y >= 0)){
					
				circleBody.setTransform(circleBody.getPosition().x, -circleRadius * 2, 0f);
					world.setGravity(new Vector2(0, 100f));
					switchOrientation();
			}
			
			if (Gdx.input.isKeyJustPressed(Keys.UP) && orientation == DOWN && 
			   (circleBody.getPosition().y >= -circleRadius * 2 - 2 && 
			   circleBody.getPosition().y <= 0 -circleRadius*2)){
				
					circleBody.setTransform(circleBody.getPosition().x, 0, 0f);
					world.setGravity(new Vector2(0, -100f));
					switchOrientation();
			}
			
			//Só é permitido que o personagem pule quando ele está a 2 PIXELS do chão, para evitar bugs e
			//comportamentos indesejados
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)){
					if (orientation == UP && (circleBody.getPosition().y > 0 && 
					   circleBody.getPosition().y < 2)){
						circleBody.setLinearVelocity(0f, 300f);
					}
					
					if (orientation == DOWN && (circleBody.getPosition().y < -circleRadius * 2 && 
					   circleBody.getPosition().y > -circleRadius * 2 - 2)){
						circleBody.setLinearVelocity(0f, -300f);
					}
			}
	
			if (Gdx.input.isKeyJustPressed(Keys.CONTROL_LEFT)){
				world.setGravity(new Vector2(0, -100f));
				
				circleBody.setAngularVelocity(0f);
				circleBody.setLinearVelocity(0f, 0f);
				circleBody.setTransform(0f, 0f, 0f);
				circleBodyDef.position.set(0f, 0f);
				orientation = UP;
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			switch(state){
			case RUNNING:
				pause();
				break;
			case PAUSED:
				resume();
				break;
			}
		}
	}
	
	public void update(){
		//Atualiza a lógica do jogo
		checkInput();
		
		if(state == RUNNING){
			world.step(Gdx.graphics.getDeltaTime(), 6, 2);
			spritePersonagem.setPosition(circleBody.getPosition().x, circleBody.getPosition().y);
			
			camera.position.x = WIDTH/2 + spritePersonagem.getX();
			camera.position.y = HEIGHT/2 + spritePersonagem.getY();
			camera.update();
			
			Gdx.graphics.setTitle(title + " - FPS: " + Gdx.graphics.getFramesPerSecond());
		}
	}
	
	public void switchOrientation(){
		switch(orientation){
		case UP:
			orientation = DOWN;
			break;
		case DOWN:
			orientation = UP;
			break;
		}
	}
	
}
