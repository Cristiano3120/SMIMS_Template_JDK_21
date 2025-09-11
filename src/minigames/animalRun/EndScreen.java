package minigames.animalRun;

import Hilfe.Rechteck_mit_runden_Ecken;
import common.ScalablePicture;
import common.SoundThread;
import controller.AbstractController;
import controller.TastaturController;
import javazoom.jl.decoder.JavaLayerException;
import sas.Rectangle;
import sas.Text;
import sas.View;

import java.awt.*;
import java.io.FileNotFoundException;


public class EndScreen {

    private View view;
    private AbstractController controller;

    private Rectangle recht1;
    private Text text;

    private Rechteck_mit_runden_Ecken r5;

    private ScalablePicture btnRestart;
    private ScalablePicture btnHome;
    private ScalablePicture trophy;
    private ScalablePicture umrandung;
    SoundThread winsound;
    SoundThread klicksound;
    SoundThread klick2;

    public EndScreen(int x, int y, float transparency, View view, boolean player1gewonnen, TastaturController controller) {
        this.view = view;
        this.controller = controller;


        try {
            winsound = new SoundThread("resources/animalRun/music/Winsound.mp3", false, true);
            winsound.start();
        }
        catch (FileNotFoundException | JavaLayerException e) {
            throw new RuntimeException(e);
        }
                recht1 = new Rectangle(x, y, view.getWidth(), view.getHeight(), new Color(81, 72, 72));
        recht1.setTransparency(transparency);
        r5 = new Rechteck_mit_runden_Ecken(view.getWidth() * 8 / 90, view.getHeight() / 12, view.getWidth() * 74 / 90, view.getWidth() * 11 / 90, new Color(43, 43, 43));
        if (player1gewonnen == true) {
            text = new Text(view.getWidth() / 6, view.getHeight() * 4 / 60, "Player 1 Won!", new Color(250, 146, 6));
            text.setFontSerif(true, view.getWidth() * 15 / 90);
        } else {
            text = new Text(view.getWidth() / 6, view.getHeight() * 4 / 60, "Player 2 Won!", new Color(250, 146, 6));
            text.setFontSerif(true, view.getWidth() * 15 / 90);
        }

        btnRestart = new ScalablePicture(view.getWidth() * 2 / 16, view.getHeight() * 5 / 8, "resources/animalrun/REStartBUTTON.png");
        btnRestart.scaleTo(view.getHeight() / 5, view.getHeight() * 2 / 15);
        btnHome = new ScalablePicture(view.getWidth() * 14 / 16 - 217, view.getHeight() * 5 / 8, "resources/animalrun/HOMEBUTTON.png");
        btnHome.scaleTo(view.getHeight() / 5, view.getHeight() * 2 / 15);
        trophy = new ScalablePicture(view.getWidth() * 58 / 100 - 443 - view.getHeight() / 100 * 12, view.getHeight() * 60 / 100 - 552 - view.getHeight() / 100 * 12, "resources/animalrun/Trophäe.png");
        trophy.scaleTo(view.getHeight() * 1 / 4, view.getHeight() * 1 / 4);
        umrandung = new ScalablePicture(view.getWidth() * 178 / 1000 - view.getWidth() / 100 * 5.5 - 94/*149*/, view.getHeight() * 57 / 100, "resources/animalrun/WeißeUmrandung.png");
        umrandung.scaleTo(view.getHeight() * 21 / 100, view.getHeight() * 14 / 100);



    }

    public boolean waitForInput() {

        while (!controller.getLinksA() && controller.getJoystickLinksX() == 0) {
            view.wait(10);
        }
        boolean links = true;
        while (!controller.getLinksA()) {
            if (!links&&controller.getJoystickLinksX() < 0) {
                links = true;
                umrandung.moveTo(btnRestart.getShapeX() - 3, btnRestart.getShapeY() - 3);
                try {
                    klick2 = new SoundThread("resources/animalRun/music/KLick2.mp3", false, true);
                    klick2.start();
                }
                catch (FileNotFoundException | JavaLayerException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (links&&controller.getJoystickLinksX() > 0) {
                links = false;
                umrandung.moveTo(btnHome.getShapeX() - 3, btnHome.getShapeY() - 3);
                try {
                    klick2 = new SoundThread("resources/animalRun/music/KLick2.mp3", false, true);
                    klick2.start();
                }
                catch (FileNotFoundException | JavaLayerException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        try {
            klicksound = new SoundThread("resources/animalRun/music/KLicksound.mp3", false, true);
            klicksound.start();
        }
        catch (FileNotFoundException | JavaLayerException e) {
            throw new RuntimeException(e);
        }

        view.remove(recht1);
        view.remove(text);
        view.remove(btnRestart);
        view.remove(btnHome);
        view.remove(trophy);
        view.remove(r5);
        view.remove(umrandung);
        winsound.stopPlayer();
        return links;
    }

    public static void main(String[] args) {
        View view = new View(900, 700, "bla");
        TastaturController controller = new TastaturController(view);
        EndScreen test = new EndScreen(0, 0, 0.5f, view, true, controller);
        System.out.println(test.waitForInput());

    }
}

