package minigames.floLangeweile;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Platformen {

    public static int WIDTH = 50;
    public static int HEIGTH = WIDTH/16*5;
    Rectangle2D bounds;
    public boolean deletMe = false;
    public Platformen(Rectangle2D bounds) {
        this.bounds = bounds;
    }

    public abstract void doThings(int tick);

    public abstract void drawSelf(Graphics2D g2d);

    protected void drawByRectangle(Graphics2D g2d, Image image){
        g2d.drawImage(image, (int) (bounds.getX() ), (int) (bounds.getY() + JumpGame.generalHeight), (int) bounds.getWidth(), (int) bounds.getHeight(),null);
    }
}
