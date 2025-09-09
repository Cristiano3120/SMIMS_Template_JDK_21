package minigames.animalrun;

import controller.AbstractController;
import minigames.AbstractGame;
import sas.View;

import java.util.ArrayList;

public class AnimalRun extends AbstractGame {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;

    public AnimalRun(AbstractController controller, View view) {
        super(controller,view);
    }

    @Override
    protected void initView() {
        view.setSize(WIDTH, HEIGHT);
        view.setName("AnimalRun");
    }

    @Override
    protected void runGame() {

    }
}
