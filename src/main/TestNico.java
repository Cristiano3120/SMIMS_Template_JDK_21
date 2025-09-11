package main;

import controller.AbstractController;
import controller.TastaturController;
import minigames.pong.PongNico;
import sas.Rectangle;
import sas.View;

import java.awt.Color;

public class TestNico {

    private AbstractController controller;
    private View view;
    private Rectangle pong1;
    private  Rectangle pong2;


    public static void main(String[] args)
    {
        View view = new View(800, 600);
        PongNico pongNico = new PongNico(view, new TastaturController(view));
        while (true)
        {
            pongNico.doThat();
            pongNico.pongWin();
            view.wait(10);
        }

        //        view.setName("Startbildschirm Animalrun");
////        Startbildschirm test = new Startbildschirm(controller, view, 700, 500);
//
//        ScalablePicture background = new ScalablePicture(0, 0, view.getWidth(), view.getHeight(), "resources/animalrun/background.png");
//        ScalablePicture pressA_button = new ScalablePicture(0, 0, "resources/animalrun/pressA_button.png");
//        ScalablePicture headline = new ScalablePicture(0, 0, "resources/animalrun/headline.png");
//
//        pressA_button.scaleTo(view.getHeight() / 8 * 3);
//        headline.scaleTo(view.getHeight() / 8 * 5);
//
//        pressA_button.moveTo(view.getWidth() / 2 - (pressA_button.getShapeWidth() / 2), view.getHeight() / 2);
//        headline.moveTo(view.getWidth() / 2 - (headline.getShapeWidth() / 2), 0);
//
//        // hier auf button warten
//        while (!controller.getLinksA()) {
//            view.wait(10);
//        }
//
//
//        background.setHidden(true);
//        pressA_button.setHidden(true);
//        headline.setHidden(true);
//
//        view.remove(background);
//        view.remove(pressA_button);
//        view.remove(headline);

//        Circle loewe = new Circle(50,50,30, new Color(0,0,255));
//        Circle monkey = new Circle(200,100,30, new Color(255,0,0));
//
//
//        while (!controller.getLinksA()) {
//            view.wait(10);
//        }
//
//        if (monkey.getShapeY() > loewe.getShapeY()) {
//            while(monkey.getShapeY() != loewe.getShapeY()) {
//                loewe.move(0, 1);
//                view.wait(4);
//            }
//        } else if (monkey.getShapeY() < loewe.getShapeY()) {
//            while(monkey.getShapeY() != loewe.getShapeY()) {
//                loewe.move(0, -1);
//            }
//        }
//
//        while (!monkey.intersects(loewe)) {
//            monkey.move(-1, 0);
//        }
//
//        while (!controller.getLinksA()) {
//            view.wait(10);
//        }
//
//        if (monkey.intersects(loewe)) {
//            for (int i = 0; i < 40; i++) {
//                monkey.move(1,-1);
//                view.wait(3);
//            }
//            for (int i = 0; i < view.getHeight() + monkey.getShapeHeight(); i++) {
//                monkey.move(0,1);
//                view.wait(1);
//            }
//            monkey.setHidden(true);
//            view.remove(monkey);
//        }
//        Circle monkey = new Circle(200,100,30, new Color(255,0,0));
//        Circle tumbleweed = new Circle(50,100,30, new Color(204,153,51));
//        tumbleweed.moveTo(view.getWidth(), 100);
//        for (int i = 0; i < view.getWidth() + tumbleweed.getShapeWidth(); i++) {
//            tumbleweed.move(-1,0);
//            tumbleweed.turn(45);
//            view.wait(20);
//            if (tumbleweed.intersects(monkey)) {
//                break;
//            }
//        }
//        if (tumbleweed.getShapeX() < 0) {
//            tumbleweed.setHidden(true);
//            view.remove(tumbleweed);
//        }
//
//        if (monkey.intersects(tumbleweed)) {
//            Circle monkey1 = new Circle(0,0,30, new Color(255,0,0));
//            monkey1.moveTo(monkey.getShapeX(), monkey.getShapeY());
//            monkey.setHidden(true);
//            view.remove(monkey);
//            for (int i = 0; i < 40; i++) {
//                monkey1.move(1,-1);
//                view.wait(3);
//            }
//            for (int i = 0; i < view.getHeight() + monkey.getShapeHeight(); i++) {
//                monkey1.move(0,1);
//                view.wait(1);
//            }
//            monkey1.setHidden(true);
//            view.remove(monkey1);
//        }
//
   }

}


