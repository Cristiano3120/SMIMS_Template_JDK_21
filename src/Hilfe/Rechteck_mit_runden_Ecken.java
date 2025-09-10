package Hilfe;

import sas.Circle;
import sas.Rectangle;
import sas.Sprite;

import java.awt.*;

public class Rechteck_mit_runden_Ecken extends Sprite {
    Rectangle r1;
    Rectangle r2;
    Circle c1;
    Circle c2;
    Circle c3;
    Circle c4;

    public Rechteck_mit_runden_Ecken(int X, int Y, int Breite, int Höhe, Color c5) {
        r1=new Rectangle(X,Y+30,Breite,Höhe-60,c5);
        r2=new Rectangle(X+30,Y,Breite-60,Höhe,c5);
        c1=new Circle(X ,Y, 30,c5);
        c2=new Circle(X,Y+Höhe-60, 30,c5);
        c3=new Circle(X+Breite-60,Y, 30,c5);
        c4=new Circle(X+Breite-60,Y+Höhe-60, 30,c5);

        add(r1);
        add(r2);
        add(c1);
        add(c2);
        add(c3);
        add(c4);

    }
}
