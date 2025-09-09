package mastercontroler;

import controller.AbstractController;
import controller.TastaturController;
import minigames.AbstractGame;
import minigames.animalRun.Monkey;
import sas.View;

import java.util.ArrayList;

public class Mastercontrol {
    private static final int WIDTH = 1080;
    private static final int HEIGHT = 1920;

    protected AbstractController controller;
    protected View view;
    protected AbstractGame game;

    public Mastercontrol() {
        view = new View(WIDTH,HEIGHT);
        controller =new TastaturController(view);
        Monkey monkey = new Monkey(0, 200, 200, "C:\\Users\\Admin\\Downloads\\SMIMS_Template_JDK_21\\resources\\animalrun\\monkey1.jpeg", controller, true);
    }
}
