package com.duality.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class TelaJogo implements Screen{	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private Duality jogo;
	private SpriteBatch batch;
	private Sprite spritePersonagem;
	private Sprite spriteCenario;
	private World world;
	private Body body;
	private BodyDef circleBodyDef;
	private BodyDef groundBodyDef;
	private Vector2 vector2d;
	private float bodyPosX;
	private float bodyPosY;
	
	Personagem personagem = new Personagem();
	Cenario cenario = new Cenario();
	
	public TelaJogo (Duality jogo){
		this.jogo = jogo;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		
		spritePersonagem = personagem.getSprite();
		spriteCenario = cenario.getSprite();
		
		vector2d = new Vector2(0, -100f);
		world = new World(vector2d, true);
		
		groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyDef.BodyType.StaticBody;
		groundBodyDef.position.set(0, -15);
		
		Body groundBody = world.createBody(groundBodyDef);
		
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(WIDTH, 1);
		groundBody.createFixture(groundBox, 0.0f);
		
		circleBodyDef = new BodyDef();
		circleBodyDef.type = BodyDef.BodyType.DynamicBody;
		circleBodyDef.position.set(0, 0);

		body = world.createBody(circleBodyDef);
		
		CircleShape circle = new CircleShape();
		circle.setRadius(15);
		
		FixtureDef fixtureDef = new FixtureDef();  
        fixtureDef.shape = circle;  
        fixtureDef.density = 1.0f;  
        fixtureDef.friction = 0.0f;  
        fixtureDef.restitution = 1;  
        body.createFixture(fixtureDef);  
		
		bodyPosX = groundBodyDef.position.x;
		bodyPosY = groundBodyDef.position.y;
		
		circle.dispose();
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		
		spritePersonagem.setPosition(body.getPosition().x, body.getPosition().y);
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)){
			spritePersonagem.translateX(-5);
			circleBodyDef.position.set(bodyPosX - 5, bodyPosY);
		}
			
		if (Gdx.input.isKeyPressed(Keys.RIGHT)){
			spritePersonagem.translateX(5);
			circleBodyDef.position.set(bodyPosX + 5, bodyPosY);
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.DOWN)){
			world.setGravity(new Vector2(0, 100f));
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.UP)){
			world.setGravity(new Vector2(0, -100f));
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)){
			spritePersonagem.setPosition(0, 0);
			circleBodyDef.position.set(0, 0);
			body = world.createBody(circleBodyDef);
			world.setGravity(new Vector2(0, -100f));
		}
		
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
		
		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void hide() {
		
		
	}

	@Override
	public void dispose() {
		
		
	}
	
}
