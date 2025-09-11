package mastercontroler;

import controller.AbstractController;
import controller.ArduinoController;
import controller.TastaturController;
import minigames.AbstractGame;
import minigames.animalRun.AnimalRun;
import sas.View;

public class Mastercontrol {
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    protected AbstractController controller;
    protected View view;
    protected AbstractGame game;

    public Mastercontrol() {
        view = new View(WIDTH,HEIGHT);
        TastaturController controller = new TastaturController(view);
        ArduinoController arduinoController = new ArduinoController();

        game = new AnimalRun(arduinoController, controller, view);
    }
}
