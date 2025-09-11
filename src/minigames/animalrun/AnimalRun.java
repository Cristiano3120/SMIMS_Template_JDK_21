package minigames.animalrun;

import controller.AbstractController;
import minigames.AbstractGame;
import minigames.animalrun.Worldobject.Platform;
import minigames.animalrun.Worldobject.WorldObject;
import sas.Shapes;
import sas.View;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimalRun extends AbstractGame implements Runnable {
    private static final int tickRate = 60;
    private static final int WIDTH = 1000 ;
    private static final int HEIGHT = 600 ;
    private boolean gameRuns = true;
    private int tick = 0;

    private World currentWorld;

    public AnimalRun(AbstractController controller, View view) {
        super(controller, view);
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
        new Thread(this).start();;
    }

    @Override
    public void run() {


        System.out.println("Welcome to AnimalRun");

        long milisPerCycle = 1000 / tickRate;
        long timeStamp = System.currentTimeMillis();
        long timeUntilNextCycle;

        while (gameRuns) {
            timeStamp = System.currentTimeMillis();
            update();
            timeUntilNextCycle = milisPerCycle - (System.currentTimeMillis() - timeStamp);
            if (timeUntilNextCycle < 0) timeUntilNextCycle = 0;
            try {
                Thread.sleep(timeUntilNextCycle);
                //view.wait(timeUntilNextCycle);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void update() {
        currentWorld.update(tick);
        tick++;
    }

    public static double getXKameraVersatz() {
        return World.xKameraVersatz;
    }

    public static Set<WorldObject> getWorldObjects() {
        return World.worldObjects;
    }
    public static void addToWorldObjects(WorldObject o) {
        World.summoneNextRoundObjects.add(o);
        //double d = new Platform(view, true).getShapeWidth();
    }




    private class World {
        public static double xKameraVersatz = 0;
        double xKameraSpeed = view.getWidth()* 0.001;

        //Picture background = new Picture(0,0,WIDTH,HEIGHT,"resources/animalrun/background_game.png");

        public static Set<WorldObject> worldObjects = new HashSet<WorldObject>();
        public static Set<WorldObject> summoneNextRoundObjects = new HashSet<>();
        // instanzen der playerklasse

        public World(AbstractController controller, View view) {

        }

        void update(int tick) {
            worldObjects.addAll(summoneNextRoundObjects);
            shapesToRemove.addAll( summoneNextRoundObjects.stream()
                    .filter(o -> o instanceof Shapes)
                    .map(o -> (Shapes) o)
                    .collect(Collectors.toSet()));;
            summoneNextRoundObjects.clear();
            xKameraVersatz += xKameraSpeed;

//            System.out.println("xKameraVersatz = ");

            worldObjects.stream().forEach(w -> {
                w.doThings(tick);
                w.updatePos();
            });

            if (controller.getLinksB()) {
                summoneNextRoundObjects.add(new Platform(view,true));
                view.wait(50);
            }
        }

//        public void newWorldObjekt(Object o) {
//            if (o instanceof WorldObject)
//                worldObjects.add((WorldObject) o);
//            if (o instanceof Shapes) {
//                shapesToRemove.add((Shapes) o);
//            }
//        }
    }
}
