package minigames.animalRun.Worldobject;

import controller.AbstractController;
import minigames.animalRun.Monkey;
import minigames.animalRun.AnimalRun;
import sas.Picture;
import sas.View;

public abstract class Tumbleweed extends Picture implements WorldObject {

    private Monkey monkeys[];
    private View view;
    private AbstractController controller;

    public Tumbleweed(double xp, double yp, String name, View view, AbstractController controller) {
        super(xp, yp, name);
        this.view = view;
        this.controller = controller;
        moveTo(view.getWidth(), 100);
    }


    @Override
    public void doThings(int tick) {

        monkeys = (Monkey[]) AnimalRun.getWorldObjects().stream().filter(o -> o instanceof Monkey).toArray();

        turn(45);

        // Sind wir aus der map? Dann löschen.
        if (getShapeX() < 0) {
            setHidden(true);
            view.remove(this);
        }

        Monkey deadMonkey;
        for (int i = 0; i < monkeys.length; i++) {
            if (intersects(monkeys[i])) {
                deadMonkey = new Monkey(monkeys[i].getShapeX(), monkeys[i].getShapeY(), controller, true, view);
                monkeys[i].setHidden(true);
                view.remove(monkeys[i]);
                for (int j = 0; j < 40; j++) {
                    deadMonkey.move(1, -1);
                    view.wait(3);
                }
                for (int j = 0; j < view.getHeight() + deadMonkey.getShapeHeight(); j++) {
                    deadMonkey.move(0, 1);
                    view.wait(1);
                }
                deadMonkey.setHidden(true);
                view.remove(deadMonkey);
            }
        }
    }

    @Override
    public void updatePos() {

    }

    @Override
    public boolean isSollit() {
        return false;
    }
}
