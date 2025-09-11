package main;

import common.ScalablePicture;
import sas.View;

public class BLasdatest {

    public static void main(String[] args) {
        View view = new View(800, 800);
        ScalablePicture test = new ScalablePicture(0, 0, "resources/animalrun/monkey1.png");
        test.scaleTo(500);
        test.moveTo(0,0);
        System.out.println(test.getShapeWidth() + ", " +  test.getShapeHeight());
        for (int i = 0; i < 100; i++) {
            test.move(10);
            test.turn(10);
            view.wait(20);

        }
    }

}
