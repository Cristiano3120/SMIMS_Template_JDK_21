package minigames.animalrun;

import controller.AbstractController;
import minigames.AbstractGame;
import sas.View;

import java.util.ArrayList;

public class AnimalRun extends AbstractGame {
    private  static final int FPS = 60;
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    private boolean gameRuns = true;

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
        long milisPerCycle = 1000 / FPS;
        long timeStamp = System.currentTimeMillis();
        long timeUntilNextCycle;

        while (gameRuns) {
            timeStamp = System.currentTimeMillis();
            update();
            timeUntilNextCycle = milisPerCycle - (System.currentTimeMillis() - timeStamp);
            if (timeUntilNextCycle < 0) timeUntilNextCycle = 0;
            try {
                //Thread.sleep(timeUntilNextCycle);
                view.wait(timeUntilNextCycle);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void update() {

    }
}
