package mastercontroler;

import controller.AbstractController;
import controller.TastaturController;
import minigames.AbstractGame;
import minigames.animalrun.AnimalRun;
import minigames.animalRun.Monkey;
import sas.View;

import java.util.ArrayList;

import static java.lang.invoke.MethodHandles.loop;

public class Mastercontrol {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    protected AbstractController controller;
    protected View view;
    protected AbstractGame game;

    public Mastercontrol() {
        view = new View(WIDTH,HEIGHT);
        controller =new TastaturController(view);
        game =new AnimalRun(controller,view);
//        System.out.println("test--");
        game.start();
//        System.out.println("test--1");
        Monkey monkey = new Monkey(600, 200, 200, controller, true, view);
        Monkey monkey2 = new Monkey(400, 200, 200, controller, false, view);

        AnimalRun.addToWorldObjects(monkey);
        AnimalRun.addToWorldObjects(monkey2);

//        System.out.println("test0");
        loop(monkey, monkey2, view);
    }

    private void loop(Monkey monkey, Monkey monkey2, View view) {
//        System.out.println("test1");
        monkey.pictures1[0].setHidden(true);
        monkey2.pictures1[0].setHidden(true);

        int i = 0;
        while (true) {
//            System.out.println("test2");
            monkey.monkeyMove();
            monkey2.monkeyMove();
            monkey.monkeyJump();
            monkey2.monkeyJump();
//            System.out.println("test3");
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

//            System.out.println("test");
        }
    }

}
