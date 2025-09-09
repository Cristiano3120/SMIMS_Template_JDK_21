package minigames.animalrun.Worldobject;

import common.ScalablePicture;
import minigames.AbstractGame;

public abstract class Fallen extends WorldObject {
    private static final String PATH_IMAGE = AbstractGame.PATH_TO_RESOURCES +"frogger/goblet.png";

    protected Fallen(double xPos, double yPos, double width, double height, String textur, sas.View view) {
        super(xPos, yPos, width, height, textur, view);
    }
    //public Fallen(int x, int y) {
    //    super(x, y, PATH_IMAGE);
    //}
}
