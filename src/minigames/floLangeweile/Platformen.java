package minigames.floLangeweile;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Platformen {
    Rectangle2D bounds;
    public boolean deletMe = false;
    public Platformen(Rectangle2D bounds) {
        this.bounds = bounds;
    }

    public abstract void drawSelf(Graphics2D g2d);

    protected void drawByRectangle(Graphics2D g2d, Image image){
        g2d.drawImage(image, (int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight(),null);
    }
}
