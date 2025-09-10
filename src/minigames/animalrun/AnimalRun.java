package minigames.animalrun;

import controller.AbstractController;
import minigames.AbstractGame;
import minigames.animalrun.Worldobject.WorldObject;
import sas.Picture;
import sas.View;

import java.util.HashSet;
import java.util.Set;

public class AnimalRun extends AbstractGame {
    private  static final int tickRate = 60;
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    private boolean gameRuns = true;

    private World currentWorld;

    public AnimalRun(AbstractController controller, View view) {
        super(controller,view);
    }

    @Override
    protected void initView() {
        view.setSize(WIDTH, HEIGHT);
        view.setName("AnimalRun");

        new Startbildschirm(controller, view, WIDTH, HEIGHT);

        currentWorld = new World(controller, view);
    }

    @Override
    protected void runGame() {
        long milisPerCycle = 1000 / tickRate;
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

    private class World {
        int xKameraVersatz = 0;
        Picture background = new Picture(0,0,900,700,"resources/animalrun/background_game.png");

        Set<WorldObject> wordObjects = new HashSet<WorldObject>();
        // instanzen der playerklasse

        public World(AbstractController controller, View view){

        }

        void update(int tick) {

            wordObjects.stream().forEach(w ->{
                w.doThings(tick);
                w.updatePos();
            });
        }
    }
}
