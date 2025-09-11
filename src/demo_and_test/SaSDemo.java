package demo_and_test;

import sas.Circle;
import sas.Picture;
import sas.View;

import java.awt.*;

public class SaSDemo {

    /* Static Variables */

    /* Static Methods */

    /* Object Variables */
    private View view;

    /* Constructors */
    public SaSDemo() {
        view = new View(800, 600);
    }

    /* Object Methods */
    public void demo1() {

        view.setName("Demo: Ball");

        Circle ball = new Circle(100, 100, 20, Color.BLACK);

        while (ball.getCenterX() < view.getWidth()) {

            if (view.keyRightPressed()) {
                ball.move(20);
            }

            view.wait(20);

        }
    }

    public void demo2() {

        view.setName("Demo: Winter Wonderland");

        Schneemann schneemann = new Schneemann(view, Color.PINK);
        schneemann.moveTo(100, 100);
        Haus haus = new Haus(view);

        Picture weihnachtsmann = new Picture(20, 500, "resources/demo/weihnachtsmann.png");
        double schrittlaenge = 10;

        while (!weihnachtsmann.intersects(haus.getKlingel())) {

            if (view.keyRightPressed()) {
                weihnachtsmann.move(schrittlaenge);
            }

            if (view.keyLeftPressed()) {
                weihnachtsmann.move(-schrittlaenge);
            }

            if (view.keyUpPressed()) {
                weihnachtsmann.move(0, -schrittlaenge);
            }

            if (view.keyDownPressed()) {
                weihnachtsmann.move(0, schrittlaenge);
            }

            view.wait(20);
        }

        haus.blinke();
        schneemann.tanze();

    }

    /* Getters and Setters */

    /* Inner Classes */

}
