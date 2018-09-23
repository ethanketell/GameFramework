package game;

import framework.*;

public class Player extends Entity {
	
	public Player() {
		super();
		this.sprite = Images.getImage("dino");
	}

	@Override
	public void tick(long elapsedMillis) {
		if(Game.keyIsDown("down")) {
			y += 5;
		}
		if(Game.keyIsDown("up")) {
			y -= 5;
		}
		if(Game.keyIsDown("right")) {
			x += 5;
		}
		if(Game.keyIsDown("left")) {
			x -= 5;
		}
		this.direction = this.getDirectionToward(Input.getMousePosition());
	}

}
