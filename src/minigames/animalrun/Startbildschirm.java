package minigames.animalrun;

import minigames.AbstractGame;
import sas.*;
import common.ScalablePicture;
import controller.AbstractController;

import java.util.ArrayList;


public class Startbildschirm {

    private AbstractController controller;
    private View view;


    public Startbildschirm(AbstractController controller, View view, int viewWidth, int viewHeight) {

        view.setSize(viewWidth, viewHeight);
        view.setName("Startbildschirm Animalrun");

        ScalablePicture background = new ScalablePicture(0, 0, view.getWidth(), view.getHeight(), "resources/animalrun/background.png");
        ScalablePicture pressA_button = new ScalablePicture(210, 300, 375, 250, "resources/animalrun/pressA_button.png");
        ScalablePicture headline = new ScalablePicture(100, 0, 600, 400, "resources/animalrun/headline.png");

        // hier auf button warten
        while(!controller.getLinksA()) {
            view.wait(10);
        }

        background.setHidden(true);
        pressA_button.setHidden(true);
        headline.setHidden(true);

        view.remove(background);
        view.remove(pressA_button);
        view.remove(headline);

    }

    // TODO: Das muss hier weg und ins Game.
    private void moveBackground(double offsetX) {

        ScalablePicture[] backgrounds = new ScalablePicture[4];// TODO: Das hier richtig machen.
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
