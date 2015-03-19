package com.duality.game;

import com.badlogic.gdx.Game;

public class Duality extends Game {
	
	TelaJogo telaJogo;
	
	@Override
	public void create () {
		telaJogo = new TelaJogo(this);
		setScreen(telaJogo);
	}
}
