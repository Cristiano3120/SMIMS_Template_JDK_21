package minigames.animalrun.Worldobject;

import common.ScalablePicture;
import minigames.AbstractGame;
import sas.Rectangle;

public abstract class Fallen extends Rectangle {
    private static final String PATH_IMAGE = AbstractGame.PATH_TO_RESOURCES +"frogger/goblet.png";

    protected Fallen(double xPos, double yPos, double width, double height, String textur, sas.View view) {
        super(xPos, yPos, width, height, textur);
    }
    //public Fallen(int x, int y) {
    //    super(x, y, PATH_IMAGE);
    //}
}
