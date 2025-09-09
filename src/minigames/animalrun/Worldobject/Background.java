package minigames.animalrun.Worldobject;

import common.ScalablePicture;
import minigames.AbstractGame;

public class Background extends ScalablePicture {
    private static final String PATH_IMAGE = AbstractGame.PATH_TO_RESOURCES +"background_game.png";
    public Background(int x, int y) {
        super(x, y, PATH_IMAGE);
    }
}
