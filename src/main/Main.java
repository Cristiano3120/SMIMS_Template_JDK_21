package main;

import controller.AbstractController;
import controller.TastaturController;
import demo_and_test.SaSDemo;
import mastercontroler.Mastercontrol;
import minigames.animalrun.AnimalRun;
import sas.View;


public class Main {

    public static void main(String[] args) {

        // eine ganz simple Demo für SaS
     //   SaSDemo demo = new SaSDemo();
   //    demo.demo1();
//        demo.demo2();
        
        // ein einfaches Spiel in SaS
//        View view = new View(800, 600, "SMIMS");
//        AbstractController controller = new TastaturController(view);
//        AbstractGame game = new FroggerGame(controller, view);
//        game.start();
        Mastercontrol mc = new Mastercontrol();
    }
}