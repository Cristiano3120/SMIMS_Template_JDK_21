package common;

import sas.Shapes;

import java.awt.geom.Rectangle2D;

public class Util {
    public static Rectangle2D getRectengle2DFrom(Shapes s){
        return new Rectangle2D.Double(s.getShapeX(),s.getShapeY(),s.getShapeWidth(),s.getShapeHeight());

    }
}
