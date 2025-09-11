package main;

import common.ScalablePicture;
import controller.AbstractController;
import controller.TastaturController;
import minigames.animalRun.Monkey;
import sas.View;

import java.io.IOException;

public class BLasdatest {

    public static void main(String[] args) throws IOException {
        View view = new View(800, 800);
        AbstractController controller = new TastaturController(view);

        Monkey monkey1 = new Monkey(50, 200, view, controller, true);
        Monkey monkey2 = new Monkey(50, 200, view, controller, false);
        while (true) {

            monkey1.doThings();
            monkey2.doThings();
            view.wait(50);
        }

    }

}
