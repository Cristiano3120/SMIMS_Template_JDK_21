package minigames.animalRun.Worldobject;

public interface WorldObject /*extends Shapes*/ {

    //double xKameraVersatz;
    //protected View view;

 //   protected WorldObject(double xPos, double yPos, double width, double height, String textur, View view) {
 //       super(xPos, yPos, width, height, textur);
  //      this.view = view;
 //   }

    public void doThings();

    public void updatePos();

    /* true wenn man darauf laufen kann */
    public boolean isSollit();

}

