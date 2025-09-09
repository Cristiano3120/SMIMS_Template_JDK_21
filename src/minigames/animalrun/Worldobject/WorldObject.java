package minigames.animalrun.Worldobject;

import sas.Shapes;
import sas.View;

import java.util.Vector;

public abstract class WorldObject extends Shapes {

    protected double xKameraVersatz;
    protected View view;

    protected WorldObject(double xPos, double yPos, double width, double height, String textur, View view) {
        super(xPos, yPos, width, height, textur);
        this.view = view;
    }

    public abstract void doThings(int tick);

    public abstract void updatePos();

    /* true wenn man darauf laufen kann */
    public abstract boolean isSollit();

    public void setXKameraVersatz(double versatz){
        xKameraVersatz = versatz;
    }
}

