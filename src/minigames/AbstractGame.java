package minigames;

import controller.AbstractController;
import sas.Shapes;
import sas.View;

import java.util.ArrayList;

public abstract class AbstractGame {

    /* Static Variables */
    public static final String PATH_TO_RESOURCES = "resources/";
    public static final int TICK_RATE = 20;

    /* Static Methods */

    /* Object Variables */
    protected AbstractController controller;
    protected View view;
    protected ArrayList<Shapes> shapesToRemove;

    /* Constructors */
    public AbstractGame(AbstractController controller, View view) {
        this.controller = controller;
        this.view = view;
        this.shapesToRemove = new ArrayList<>();
        initView();
    }

    /* Object Methods */
    public void start() {

        // Run the game.
        runGame();

        // Clean up afterwards.
        cleanUp();

    }

    protected abstract void initView();

    protected abstract void runGame();

    protected void cleanUp() {
        for (Shapes shapes : shapesToRemove) {
            shapes.setHidden(true);
            view.remove(shapes);
        }
    }

    /* Getters and Setters */

    /* Inner Classes */

}
