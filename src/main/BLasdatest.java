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


        Monkey monkey = new Monkey(50, 200, controller, true);
        for (int i = 0; i < 110; i++) {
            int index = i % 4;
            monkey.setImageAndMove(index, false);
            view.wait(500);
        }


//        monkey.setImage(2, false);




    }

}
