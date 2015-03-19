package com.duality.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cenario {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private SpriteBatch batch;
	private Pixmap pixmap;
	private Texture texture;
	private Sprite sprite;
	
	public Cenario(){
		batch = new SpriteBatch();
		
		pixmap =  new Pixmap(WIDTH, HEIGHT, Pixmap.Format.RGBA8888);
		
		pixmap.setColor(Color.rgba8888(1.0f, 1.0f, 1.0f, 0.5f));
		pixmap.fillRectangle(100, 0, (WIDTH - 100 * 2), HEIGHT/2);
		
		pixmap.setColor(Color.rgba8888(1.0f, 1.0f, 1.0f, 0.2f));
		pixmap.fillRectangle(100, HEIGHT/2, (WIDTH - 100 * 2), HEIGHT);
		
		texture = new Texture(pixmap);
		sprite = new Sprite(texture);
	}
	
	public Sprite getSprite(){
		return sprite;
	}

}
