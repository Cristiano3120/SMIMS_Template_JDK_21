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
        view.wait(1000);

        monkey.move(0,0);
        view.wait(1000);

        for (int i = 0; i < 12; i++) {

            monkey.setImage(i % 4, false);
            System.out.println(i);

            view.wait(500);

        }

//        monkey.setImage(2, false);




    }

}
