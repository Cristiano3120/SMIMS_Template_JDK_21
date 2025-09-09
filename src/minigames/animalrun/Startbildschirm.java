package minigames.animalrun;

import minigames.AbstractGame;
import sas.*;
import common.ScalablePicture;
import controller.AbstractController;

import java.util.ArrayList;


public class Startbildschirm {

    protected AbstractController controller;
    protected View view;
    protected ArrayList<Shapes> shapesToRemove;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    ScalablePicture[] backgrounds;

    public Startbildschirm(AbstractController controller, View view) {
        this.controller = controller;
        this.view = view;
        this.shapesToRemove = new ArrayList<>();
        initView();
    }


    protected void initView() {
        view.setSize(WIDTH, HEIGHT);
        view.setName("Startbildschirm Animalrun");

        ScalablePicture background = new ScalablePicture(0, 0, view.getWidth(), view.getHeight(), "resources/animalrun/background.png");
        ScalablePicture pressA_button = new ScalablePicture(210, 300, 375, 250, "resources/animalrun/pressA_button.png");
        ScalablePicture headline = new ScalablePicture(100, 0, 600, 400, "resources/animalrun/headline.png");

        shapesToRemove.add(background);
        shapesToRemove.add(pressA_button);
        shapesToRemove.add(headline);

        // hier auf button warten
        while(!controller.getLinksA()) {
            view.wait(10);
        }


    }


    protected void runGame() {

//        cleanUp();

        // Generiere Backgrounds.
        backgrounds = new ScalablePicture[4];
        for (int i = 0; i < backgrounds.length; i++) {
            backgrounds[i] = new ScalablePicture(i * 375.0, view.getHeight() - 100d, 375.0, 100, "resources/animalrun/background_1.png");
            shapesToRemove.add(backgrounds[i]);
        }

    }




    private void moveBackground(double offsetX) {
        for (int j = 0; j < backgrounds.length; j++) { // Gehe durch alle Bodenelemente durch ...
            backgrounds[j].move(-offsetX, 0); // ... und bewege das jeweils aktuelle Element nach links.
            if (backgrounds[j].getShapeX() + backgrounds[j].getShapeWidth() < 0) { // Sind wir links über den Rand hinaus?
                System.out.println("moving " + j);
                int prevIndex = j <= 0 ? backgrounds.length - 1 : j - 1; // bestimme den Index des vorherigen Bodenelements
                backgrounds[j].moveTo(backgrounds[prevIndex].getShapeX() + backgrounds[prevIndex].getShapeWidth(), backgrounds[j].getShapeY()); // "teleportiere" das ELement hinter seinen Vorgänger, sprich: ganz nach rechts
            }
        }
    }
}
