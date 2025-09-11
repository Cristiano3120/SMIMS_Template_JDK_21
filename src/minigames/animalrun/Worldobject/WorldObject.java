package minigames.animalrun.Worldobject;

import sas.Shapes;
import sas.View;

import java.util.Vector;

public interface WorldObject /*extends Shapes*/ {

    //double xKameraVersatz;
    //protected View view;

 //   protected WorldObject(double xPos, double yPos, double width, double height, String textur, View view) {
 //       super(xPos, yPos, width, height, textur);
  //      this.view = view;
 //   }

    public void doThings(int tick);

    public void updatePos();

    /* true wenn man darauf laufen kann */
    public boolean isSollit();

    public boolean getDeleatMe();
}

