package demo_and_test;

import sas.*;

import java.awt.Color;

/**
 * @author Michel May
 * @version 1.0
 */
public class Haus extends Sprite {

    /* Statische Variablen */

    /* Statische Methoden */

    /* Attribute */
    private View view;
    private Rectangle gebaeude;
    private Rectangle fenster1;
    private Rectangle fenster2;
    private Rectangle tuer;
    private Circle klingel;
    private Polygon dach;

    /* Konstruktoren */
    public Haus(View pView) {

        // zuerst das View-Objekt speichern
        view = pView;

        // anschließend die 'leichteren' Objekte (Rectangles)
        gebaeude = new Rectangle(300, 100, 300, 180, Color.GRAY);
        fenster1 = new Rectangle(350, 150, 40, 40, Color.YELLOW);
        fenster2 = new Rectangle(500, 150, 40, 40, Color.YELLOW);
        klingel = new Circle(415, 240, 5.0, Color.RED);
        tuer = new Rectangle(430, 210, 40, 70, new Color(122, 78, 0));

        // zum Schluss das Polygon, weil es etwas umständlicher ist
        dach = new Polygon(300, 100, Color.BLACK);
        dach.add(300, 0);
        dach.add(250, -50);
        dach.add(50, -50);

        add(gebaeude);
        add(fenster1);
        add(fenster2);
        add(klingel);
        add(tuer);
        add(dach);

    }

    /* Objektmethoden */
    public void blinke() {

        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                fenster1.setColor(Color.PINK);
                fenster2.setColor(Color.PINK);
            } else {
                fenster1.setColor(Color.YELLOW);
                fenster2.setColor(Color.YELLOW);
            }
            view.wait(1000);
        }
    }

    /* Getter und Setter */
    public Circle getKlingel() {
        return klingel;
    }

}