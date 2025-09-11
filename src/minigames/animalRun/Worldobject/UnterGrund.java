package minigames.animalRun.Worldobject;

import common.ScalablePicture;
import minigames.AbstractGame;

public class UnterGrund extends ScalablePicture {
    private static final String PATH_IMAGE = AbstractGame.PATH_TO_RESOURCES +"frogger/goblet.png";
    public UnterGrund(int x, int y) {
        super(x, y, PATH_IMAGE);
    }

}
