package minigames.animalrun.Worldobject;

import common.ScalablePicture;
import minigames.AbstractGame;

public class Fallen extends ScalablePicture {
    private static final String PATH_IMAGE = AbstractGame.PATH_TO_RESOURCES +"frogger/goblet.png";
    public Fallen(int x, int y) {
        super(x, y, PATH_IMAGE);
    }
}
