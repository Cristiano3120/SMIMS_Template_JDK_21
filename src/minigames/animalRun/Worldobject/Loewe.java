package minigames.animalRun.Worldobject;

import common.ScalablePicture;
import minigames.animalRun.Monkey;
import sas.Shapes;

public class Loewe implements WorldObject {

    private static final int LION_SPAWN_X = 10;
    private static final int LION_HEIGHT = 150;

    private Monkey monkeys[];
    private ScalablePicture loeweClosed;
    ScalablePicture loeweOpen;

    public Loewe(Monkey[] monkeys) {

        this.loeweClosed = new ScalablePicture(LION_SPAWN_X, 0, "resources/animalrun/loeweClosed.png");
        this.loeweOpen = new ScalablePicture(LION_SPAWN_X, 0, "resources/animalrun/loeweOpen.png");
        this.monkeys = monkeys;

        loeweClosed.scaleTo(LION_HEIGHT);
        loeweOpen.scaleTo(LION_HEIGHT);

        loeweClosed.moveTo(LION_SPAWN_X, 0);
        loeweOpen.moveTo(LION_SPAWN_X, 0);

        loeweOpen.setHidden(true);

    }

    @Override
    public void doThings() {


        Monkey linkerAffe = monkeys[0].getShapeX() < monkeys[1].getShapeX() ? monkeys[0] : monkeys[1];

        double differenceY = linkerAffe.getShapeY() - loeweClosed.getShapeY();
        loeweClosed.move(0, differenceY * 0.2);
        if (linkerAffe.getHitbox().intersects(loeweClosed)) {
            loeweOpen.moveTo(loeweClosed.getShapeX(), loeweClosed.getShapeY());
            loeweOpen.setHidden(false);
            loeweClosed.setHidden(true);
        }

    }

    public boolean intersects(Shapes shape) {
        if (loeweClosed.getHidden()) {
            return loeweOpen.intersects(shape);
        } else {
            return loeweClosed.intersects(shape);
        }
    }

    @Override
    public void updatePos() {
    }

    @Override
    public boolean isSollit() {
        return false;
    }

    public boolean getDeleatMe() {
        return false;
    }
}
