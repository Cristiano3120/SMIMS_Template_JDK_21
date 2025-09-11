package minigames.frogger.obstacles;

import minigames.AbstractGame;
import sas.Tools;

public class Auto extends Obstacle {

    /* Static Variables */
    private static final String PATH_IMAGE = AbstractGame.PATH_TO_RESOURCES + "frogger/car";

    /* Static Methods */

    /* Object Variables */

    /* Constructors */
    public Auto(double xp, double yp) {
        super(xp, yp, PATH_IMAGE + Tools.randomNumber(1, 12) + ".png");
    }

    /* Object Methods */

    /* Getters and Setters */

    /* Inner Classes */

}
