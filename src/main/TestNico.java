package main;

import controller.AbstractController;
import controller.TastaturController;
import minigames.animalRun.Startbildschirm;
import sas.View;

public class TestNico {



    public static void main(String[] args) {
        View view = new View(1920, 1080);
        AbstractController controller = new TastaturController(view);
        //Startbildschirm test = new Startbildschirm(controller, view, 1920, 1080);

    }
}

