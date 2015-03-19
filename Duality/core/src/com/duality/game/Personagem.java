package com.duality.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Personagem{
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private SpriteBatch batch;
	private Pixmap pixmap;
	private Texture texture;
	private Sprite sprite;
	
	Personagem(){
		int circleRadius = 15;
		
		batch = new SpriteBatch();
		pixmap = new Pixmap(WIDTH, HEIGHT, Pixmap.Format.RGBA8888);
		
		pixmap.setColor(Color.RED);
		pixmap.fillCircle(WIDTH/2, (HEIGHT/2 - 1) - circleRadius , circleRadius); // -1 offset;
		
		texture = new Texture(pixmap);
		sprite = new Sprite(texture);
	}
	
	public Sprite getSprite(){
		return sprite;
	}
}
