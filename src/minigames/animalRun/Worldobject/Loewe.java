package minigames.animalRun.Worldobject;

import common.ScalablePicture;
import controller.AbstractController;
import minigames.animalRun.AnimalRun;
import minigames.animalRun.Monkey;
import sas.View;

import java.awt.*;

public class Loewe implements WorldObject {

    private static final int LION_SPAWN_X = 10;

    private Monkey monkeys[];
    private View view;
    private AbstractController controller;
    private ScalablePicture loeweClosed;
    ScalablePicture loeweOpen;

    public Loewe() {

        this.view = view;
        this.controller = controller;

        this.loeweClosed = new ScalablePicture(LION_SPAWN_X, 0, "resources/animalrun/loeweClosed.png");
        this.loeweOpen = new ScalablePicture(LION_SPAWN_X, 0, "resources/animalrun/loeweOpen.png");
        this.monkeys = (Monkey[]) AnimalRun.getWorldObjects().stream().filter(o -> o instanceof Monkey).toArray();

        loeweOpen.setHidden(true);

    }

    @Override
    public void doThings(int tick) {

        Monkey linkerAffe = monkeys[0].getShapeX() < monkeys[1].getShapeX() ? monkeys[0] : monkeys[1];

        double differenceY = linkerAffe.getShapeY() - loeweClosed.getShapeY();
        loeweClosed.move(0, differenceY * 0.2);
        if (linkerAffe.intersects(loeweClosed)) {
            loeweOpen.moveTo(loeweClosed.getShapeX(), loeweClosed.getShapeY());
            loeweOpen.setHidden(false);
            loeweClosed.setHidden(true);
//            linkerAffe.stirb(); // TODO: auskommentieren
        }

    }

    @Override
    public void updatePos() {
    }

    @Override
    public boolean isSollit() {
        return false;
    }

    @Override
    public boolean getDeleatMe() {
        return false;
    }
}
