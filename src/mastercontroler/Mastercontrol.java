package mastercontroler;

import controller.AbstractController;
import controller.TastaturController;
import minigames.AbstractGame;
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
    }
}
