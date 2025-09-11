package minigames.animalRun;

import controller.AbstractController;
import controller.ArduinoController;
import controller.TastaturController;
import minigames.AbstractGame;
import minigames.animalRun.Worldobject.Platform;
import minigames.animalRun.Worldobject.WorldObject;
import sas.Shapes;
import sas.View;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimalRun extends AbstractGame implements Runnable {

    /* Static Variables */
    private static final int TICK_RATE = 100;

    /* Static Methods */
    // TODO: alles nicht-static machen
    public static double getXKameraVersatz() {
        return World.xKameraVersatz;
    }

    public static Set<WorldObject> getWorldObjects() {
        return World.worldObjects;
    }

    public static void addToWorldObjects(WorldObject o) {
        World.summonNextRoundObjects.add(o);
        //double d = new Platform(view, true).getShapeWidth();
    }

    /* Object Variables */
    private ArduinoController arduinoController;
    private boolean gameRuns = true;
    private int tick = 0;
    private World currentWorld;
    private Monkey[] monkeys;

    public AnimalRun(ArduinoController arduinoController, TastaturController tastaturController, View view) {
        super(tastaturController, view);
    }

    @Override
    protected void initView() {
        view.setName("AnimalRun");

        new Startbildschirm(controller, view);
        currentWorld = new World(controller, view);

        AnimalRun.addToWorldObjects(new Platform(view, true));

        start();
    }

    @Override
    protected void runGame() {
        new Thread(this).start(); // TODO: kein Thread!
    }

    @Override
    public void run() {
        System.out.println("Welcome to AnimalRun");

        monkeys = new Monkey[2];
        monkeys[0] = new Monkey(1000, 200, 200, 200, controller, true, view);
        monkeys[1] = new Monkey(1000, 200, 200, 200, controller, false, view);

        AnimalRun.addToWorldObjects(monkeys[0]);
        AnimalRun.addToWorldObjects( monkeys[1]);

        long milisPerCycle = 1000 / TICK_RATE;
        long timeStamp = System.currentTimeMillis();
        long timeUntilNextCycle;

        int i = 0;
        while (gameRuns) {

            // Setze Zeitstempel (alias "starte Stoppuhr"), lasse alle Objekte ihre Aktionen durchführen und
            // warte anschließend den Tick ab.
            timeStamp = System.currentTimeMillis();
            update();
            checkPlatformCollision();

            timeUntilNextCycle = milisPerCycle - (System.currentTimeMillis() - timeStamp);
            if (timeUntilNextCycle < 0) timeUntilNextCycle = 0;

            view.wait((int) timeUntilNextCycle);

        }
    }

    /**
     * Diese Methode prüft, ob die Affen auf einer Plattform sind, bzw. ob sie gegen eine dotzen.
     */
    private void checkPlatformCollision() {
        for (WorldObject object : World.worldObjects) {
            if (object instanceof Platform platform) {
                for (Monkey monkey : monkeys) {

                    // Wenn der Affe die Plattform von oben berührt ...
                    if (monkey.intersects(platform) && monkey.getShapeY() + monkey.getShapeHeight() >= platform.getShapeY()) {

                        // ... kann der Affe darauf laufen.
                        monkey.onGround = true;
                    }

                    // Der Affe ist offensichtlich in der Luft.
                    monkey.onGround = false;
                }
            }
        }
    }

    private void update() {
        currentWorld.update(tick);
        tick++;
    }

    public Monkey[] getMonkeys() {
        return monkeys;
    }

    public class World {
        public static double xKameraVersatz = 0;
        double xKameraSpeed = view.getWidth() * 0.001;

        //Picture background = new Picture(0,0,WIDTH,HEIGHT,"resources/animalrun/background_game.png");

        public static Set<WorldObject> worldObjects = new HashSet<WorldObject>();
        public static Set<WorldObject> summonNextRoundObjects = new HashSet<>();
        // instanzen der playerklasse

        public World(AbstractController controller, View view) {

        }

        void update(int tick) {
            worldObjects.addAll(summonNextRoundObjects); // TODO: clear all elements? -> sonst wird jede Runde alles neu ge-addet
            shapesToRemove.addAll(summonNextRoundObjects.stream()
                    .filter(o -> o instanceof Shapes)
                    .map(o -> (Shapes) o)
                    .collect(Collectors.toSet()));
            ;
            xKameraVersatz += xKameraSpeed;

            System.out.println("xKameraVersatz = ");

            worldObjects.stream().forEach(w -> {
                w.doThings(tick);
                w.updatePos();
            });

            if (controller.getLinksB()) { // TODO: remove?
                newWorldObjekt(new Platform(view, true));
                view.wait(50);
            }
        }

        void newWorldObjekt(Object o) {
            if (o instanceof WorldObject)
                worldObjects.add((WorldObject) o);
            if (o instanceof Shapes) {
                shapesToRemove.add((Shapes) o);
            }
        }
    }
}