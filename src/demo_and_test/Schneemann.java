package demo_and_test;

import sas.*;

import java.awt.Color;

/**
 * @author Michel May
 * @version 1.0
 */
public class Schneemann extends Sprite {

    /* Statische Variablen */

    /* Statische Methoden */

    /* Objektvariablen / Attribute */
    private View view;
    private Color farbe;
    private Circle kopf;
    private Circle bauch;
    private Circle beine;
    private Rectangle hut;

    /* Konstruktor(en) */
    public Schneemann(View pView, Color pFarbe) {
        view = pView;
        farbe = pFarbe;
        kopf = new Circle(10.0, 20.0, 20.0, farbe);
        bauch = new Circle(5.0, 50.0, 25.0, farbe);
        beine = new Circle(0.0, 85.0, 30.0, farbe);
        hut = new Rectangle(5.0, 0.0, 50.0, 25, Color.BLACK);

        add(kopf);
        add(bauch);
        add(beine);
        add(hut);
    }

    /* Objektmethoden */
    public void wechsleFarbe(Color pFarbe) {
        farbe = pFarbe;
        kopf.setColor(farbe);
        bauch.setColor(farbe);
        beine.setColor(farbe);
    }

    public void tanze() {
        bauch.move(10.0);
        beine.move(-10.0);
        for (int i = 0; i < 4; i++) {
            view.wait(500);
            bauch.move(-20.0);
            beine.move(20.0);
            view.wait(500);
            bauch.move(20.0);
            beine.move(-20.0);
        }
        view.wait(500);
        bauch.move(-10.0);
        beine.move(10.0);

    }

    /* Getter & Setter */
    public Color getFarbe() {
        return farbe;
    }

}
