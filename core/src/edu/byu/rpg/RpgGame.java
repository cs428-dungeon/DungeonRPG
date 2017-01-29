package edu.byu.rpg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RpgGame extends ApplicationAdapter {
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
