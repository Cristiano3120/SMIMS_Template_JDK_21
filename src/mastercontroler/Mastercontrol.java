package mastercontroler;

import controller.AbstractController;
import controller.TastaturController;
import minigames.AbstractGame;
import minigames.animalRun.AnimalRun;
import minigames.animalRun.Monkey;
import sas.View;

import java.util.ArrayList;

public class Mastercontrol {
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    protected AbstractController controller;
    protected View view;
    protected AbstractGame game;

    public Mastercontrol() {
        view = new View(WIDTH, HEIGHT);
        controller = new TastaturController(view);
        game = new AnimalRun(controller, view);
//        System.out.println("test--");
        game.start();
//        System.out.println("test--1");
        Monkey monkey = new Monkey(100, 600, controller, true, view);
        Monkey monkey2 = new Monkey(100, 400, controller, false, view);

        AnimalRun.addToWorldObjects(monkey);
        AnimalRun.addToWorldObjects(monkey2);

        loop(monkey, monkey2, view);

    }

    private void loop(Monkey monkey, Monkey monkey2, View view) {

        monkey.pictures1[0].setHidden(true);
        monkey2.pictures1[0].setHidden(true);

        int i = 0;
        while (true) {
            monkey.monkeyMove();
            monkey2.monkeyMove();
            monkey.monkeyJump();
            monkey2.monkeyJump();

            monkey.pictures1[i].setHidden(true);
            monkey2.pictures1[i].setHidden(true);

            i = i >= 4 - 1
                    ? 0
                    : i + 1;

            monkey.pictures1[i].setHidden(false);
            monkey.currentIndex1 = i;

            monkey2.pictures1[i].setHidden(false);
            monkey2.currentIndex1 = i;

            view.wait(180);
        }
    }
}
