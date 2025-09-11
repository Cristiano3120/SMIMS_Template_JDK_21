package minigames.animalRun;

import common.Util;
import controller.AbstractController;
import controller.ArduinoController;
import minigames.AbstractGame;
import minigames.animalRun.Worldobject.Platform;
import minigames.animalRun.Worldobject.WorldObject;
import sas.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimalRun extends AbstractGame {

    /* Static Variables */
    public static final double GRAVITY = 4.0;
    private static final int TICK_RATE = 100;

    /* Static Methods */

    /* Object Variables */
    private ArduinoController arduinoController;
    private boolean gameRuns = true;
    private Monkey[] monkeys;
    private Set<WorldObject> worldObjects;
    private Set<WorldObject> summonNextRoundObjects;
    private int tick;

    public AnimalRun(AbstractController controller, View view) {
        super(controller, view);
        this.worldObjects = new HashSet<>();
        this.summonNextRoundObjects = new HashSet<>();
    }

    @Override
    protected void initView() {
        view.setName("AnimalRun");
    }

    @Override
    protected void runGame() {


        monkeys = new Monkey[2];
        try {
            monkeys[0] = new Monkey(50, 100, controller, true);
            monkeys[1] = new Monkey(150, 200, controller, false);

            summonNextRoundObjects.add(monkeys[0]);
            summonNextRoundObjects.add(monkeys[1]);
        } catch (IOException e) {
            Tools.confirmDialog("Das Spiel kann nicht starten, weil Bilder nicht geladen werden konnten!");
            throw new RuntimeException(e);
        }

        long milisPerCycle = 1000 / TICK_RATE;
        long timeStamp = System.currentTimeMillis();
        long timeUntilNextCycle;

        //summonNextRoundObjects.add(new Platform(view,true));

        while (gameRuns) {

            // Setze Zeitstempel (alias "starte Stoppuhr"), lasse alle Objekte ihre Aktionen durchführen und
            // warte anschließend den Tick ab.
            timeStamp = System.currentTimeMillis();
            //MonkeyCollision
            for (Monkey monkey : monkeys) {
                boolean collision =
                    worldObjects.stream()
                        .filter(o -> o instanceof Platform)
                        .map(p -> (Platform) p)
                        .filter(p -> Util.getRectengle2DFrom(p).intersects(Util.getRectengle2DFrom(monkey)))
                        .anyMatch(p -> p.getShapeY() > monkey.getShapeY()+monkey.getShapeHeight());
                if(collision) monkey.signalCollision();
            }

            // Füge eventuelle Objekte, die diesen Tick im set stehen, der Welt hinzu.
            worldObjects.addAll(summonNextRoundObjects);
            shapesToRemove.addAll(summonNextRoundObjects.stream()
                    .filter(o -> o instanceof Shapes)
                    .map(o -> (Shapes) o)
                    .collect(Collectors.toSet()));
            summonNextRoundObjects.clear();

            worldObjects.stream().forEach(w -> {
                w.doThings();
                w.updatePos();
            });

            if(tick % 200 == 0)
                summonNextRoundObjects.add(new Platform(view,false));

            checkPlatformCollision();

            timeUntilNextCycle = milisPerCycle - (System.currentTimeMillis() - timeStamp);
            if (timeUntilNextCycle < 0) timeUntilNextCycle = 0;

            view.wait((int) timeUntilNextCycle);
            tick++;
        }
    }

    /**
     * Diese Methode prüft, ob die Affen auf einer Plattform sind, bzw. ob sie gegen eine dotzen.
     */
    private void checkPlatformCollision() {
        for (WorldObject object : worldObjects) {
            if (object instanceof Platform platform) {
                for (Monkey monkey : monkeys) {

                    // Wenn der Affe die Plattform von oben berührt ...
                    if (monkey.intersects(platform) && monkey.getShapeY() + monkey.getShapeHeight() >= platform.getShapeY()) {

                        // ... kann der Affe darauf laufen.
                        monkey.signalOnGround(true);
                    }

                    // Der Affe ist offensichtlich in der Luft.
                    monkey.signalOnGround(false);
                }
            }
        }
    }

    public Monkey[] getMonkeys() {
        return monkeys;
    }

}