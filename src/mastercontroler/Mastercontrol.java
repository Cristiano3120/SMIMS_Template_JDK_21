package mastercontroler;

import controller.AbstractController;
import controller.TastaturController;
import minigames.AbstractGame;
import minigames.animalrun.AnimalRun;
import sas.View;

import java.util.ArrayList;

public class Mastercontrol {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    protected AbstractController controller;
    protected View view;
    protected AbstractGame game;

    public Mastercontrol() {
        view = new View(WIDTH,HEIGHT);
        controller =new TastaturController(view);
        game =new AnimalRun(controller,view);
        game.start();

        Monkey monkey = new Monkey(600, 200, 200, controller, true, view);
        Monkey monkey2 = new Monkey(400, 200, 200, controller, false, view);

        monkey.pictures1[0].hashCode();

        loop(monkey, monkey2, view);
    }
}
