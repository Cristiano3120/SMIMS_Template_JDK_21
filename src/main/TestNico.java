package main;

import controller.AbstractController;
import controller.TastaturController;
import minigames.animalrun.Startbildschirm;
import sas.View;

public class TestNico {
    public static void main(String[] args) {
        View view = new View(500, 300);
        AbstractController controller = new TastaturController(view);
        new Startbildschirm(controller, view);
    }
}
