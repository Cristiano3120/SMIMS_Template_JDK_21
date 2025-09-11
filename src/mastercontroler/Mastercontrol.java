package mastercontroler;

import controller.AbstractController;
import controller.ArduinoController;
import controller.TastaturController;
import minigames.AbstractGame;
import minigames.animalRun.AnimalRun;
import sas.View;

public class Mastercontrol {
    private static final int WIDTH = 1850;
    private static final int HEIGHT = 630;

    protected AbstractController controller;
    protected View view;
    protected AbstractGame game;

    public Mastercontrol() {
        view = new View(WIDTH, HEIGHT);
        AbstractController controller = new TastaturController(view);
        game = new AnimalRun(controller, view);
        game.start();
    }
}
