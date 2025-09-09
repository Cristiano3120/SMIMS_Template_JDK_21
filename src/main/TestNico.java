package main;

import controller.AbstractController;
import controller.TastaturController;
import minigames.animalrun.Startbildschirm;
import sas.Shapes;
import sas.View;

import java.util.ArrayList;

public class TestNico {



    public static void main(String[] args) {
        View view = new View(500, 300);
        AbstractController controller = new TastaturController(view);
        Startbildschirm test = new Startbildschirm(controller, view, 700, 500);

    }
}

