package minigames.animalRun;

import sas.*;
import common.ScalablePicture;
import controller.AbstractController;

public class Startbildschirm {
    public Startbildschirm(AbstractController controller, View view) {
        int viewHeight = view.getHeight();
        int viewWidth = view.getWidth();

        ScalablePicture background = new ScalablePicture(0, 0, viewWidth, viewHeight, "resources/animalrun/background.png");
        ScalablePicture pressA_button = new ScalablePicture(0, 0, "resources/animalrun/pressA_button.png");
        ScalablePicture headline = new ScalablePicture(0, 0, "resources/animalrun/headline.png");

        pressA_button.scaleTo((double) viewHeight / 8 * 3);
        headline.scaleTo((double) viewHeight / 8 * 5);

        pressA_button.moveTo((double) viewWidth / 2 - (pressA_button.getShapeWidth() / 2), (double) viewHeight / 2);
        headline.moveTo((double) viewWidth / 2 - (headline.getShapeWidth() / 2), 0);


        while(!controller.getLinksA()) {
            view.wait(10);
        }

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
