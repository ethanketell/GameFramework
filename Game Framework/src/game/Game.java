package game;


import framework.*;

public class Game extends AbstractGame {

	@Override
	public void setup() {
		World test = new World(640,480);
		this.setActiveWorld(test);
		
		test.add(new Player(), 320,240);
		test.add(new Mine(),300,300);
	}

	@Override
	public void stop() {
		
	}

}
