package minigames.animalrun.Worldobject;

import sas.Shapes;
import sas.View;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Platform extends WorldObject {

    Point2D.Double pos;

    public Platform(View view) {
        super(0, 0, 0, 0, "resources/animalrun/plattforms/testInsel.png", view);
        pos.x = view.getWidth() + xKameraVersatz;
        pos.y = Math.random() * view.getHeight();

        setPosition();
        setSize(200,50);
        System.out.println("Platform: Test");
    }

    public Platform(double xPos, double yPos, double width, double height, String textur,View view) {
        super(xPos, yPos, width, height, textur, view);

    }

    @Override
    public void doThings(int tick) {

    }

    @Override
    public void updatePos() {
        setPosition();
    }

    @Override
    public boolean isSollit() {
        return false;
    }

    @Override
    public Shapes clone() {
        return null;
    }

    protected void setPosition() {
        super.setPosition(pos.x - xKameraVersatz, pos.y);
    }
}
