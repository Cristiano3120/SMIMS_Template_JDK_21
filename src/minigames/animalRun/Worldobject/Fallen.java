package minigames.animalRun.Worldobject;

import common.ScalablePicture;
import minigames.AbstractGame;

public class Fallen extends ScalablePicture {
    private static final String PATH_IMAGE = AbstractGame.PATH_TO_RESOURCES +"tumbleweed.png";
    public Fallen(int x, int y) {
        super(x, y, PATH_IMAGE);
    }
}
