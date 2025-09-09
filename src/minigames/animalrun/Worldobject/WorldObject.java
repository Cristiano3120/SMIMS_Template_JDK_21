package minigames.animalrun.Worldobject;

import sas.Shapes;

public abstract class WorldObject extends Shapes {


    protected WorldObject(double xPos, double yPos, double width, double height, String textur) {
        super(xPos, yPos, width, height, textur);
    }

    public abstract void doThings(int tick);

    public abstract void updatePos();

    public abstract boolean isSollit();

}

