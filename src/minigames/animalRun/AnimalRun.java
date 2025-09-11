package minigames.animalRun;

import controller.AbstractController;
import minigames.AbstractGame;
import minigames.animalRun.Worldobject.Background;
import minigames.animalRun.Worldobject.Platform;
import minigames.animalRun.Worldobject.WorldObject;
import sas.Picture;
import sas.Shapes;
import sas.View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimalRun extends AbstractGame implements Runnable {
    private static final int tickRate = 100;
    private static final int WIDTH = 1000 ;
    private static final int HEIGHT = 600  ;
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
            if (timeUntilNextCycle < 0){
                System.out.println("AnimalRun: " + timeUntilNextCycle);
                timeUntilNextCycle = 0;
            }
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
        double xKameraSpeed = view.getWidth()* 0.003;

        Background background = new Background(0,0,WIDTH,HEIGHT);

        BufferedImage hintergrundImage;
        public static Set<WorldObject> worldObjects = new HashSet<WorldObject>();
        public static Set<WorldObject> summoneNextRoundObjects = new HashSet<>();
        // instanzen der playerklasse

        public World(AbstractController controller, View view) {
            //new Picture(0,-50,view.getWidth(),view.getHeight(),"resources/animalrun/animalRunBackgroundSchmal.png");
        }





        void update(int tick) {
            background.moveBackground(tick);
            worldObjects.stream()
                    .filter(o -> o.getDeleatMe())
                    .collect(Collectors.toCollection(HashSet::new))
                    .stream()
                    .forEach(o -> {
                        worldObjects.remove(o);
                        shapesToRemove.remove(o);
                    });
            worldObjects.addAll(summoneNextRoundObjects);
            shapesToRemove.addAll( summoneNextRoundObjects.stream()
                    .filter(o -> o instanceof Shapes)
                    .map(o -> (Shapes) o)
                    .collect(Collectors.toSet()));;
            summoneNextRoundObjects.clear();
            xKameraVersatz += xKameraSpeed;

//            System.out.println("xKameraVersatz = ");

            worldObjects.stream().forEach(w -> {
                //w.doThings(tick);
                w.updatePos();
            });

            if (controller.getLinksB()) {
                summoneNextRoundObjects.add(new Platform(view,true,false));
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

    public void fallen(){

    }
}
