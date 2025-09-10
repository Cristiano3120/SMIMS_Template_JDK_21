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
        ScalablePicture pressA_button = new ScalablePicture(0, 0, "resources/animalrun/pressA_button.png");
        ScalablePicture headline = new ScalablePicture(0, 0, "resources/animalrun/headline.png");

        pressA_button.scaleTo(view.getHeight() / 8 * 3);
        headline.scaleTo(view.getHeight() / 8 * 5);

        pressA_button.moveTo(view.getWidth() / 2 - (pressA_button.getShapeWidth() / 2), view.getHeight() / 2);
        headline.moveTo(view.getWidth() / 2 - (headline.getShapeWidth() / 2), 0);

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
}
