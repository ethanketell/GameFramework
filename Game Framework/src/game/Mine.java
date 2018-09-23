package game;

import framework.Entity;
import framework.Images;
import framework.Input;

public class Mine extends Entity {
	
	public Mine() {
		this.sprite = Images.getImage("mine");
	}

	@Override
	public void tick(long elapsedMillis) {
		if(Input.keyWasPressed("right")) {
			this.x += 100;
		}
	}

}
