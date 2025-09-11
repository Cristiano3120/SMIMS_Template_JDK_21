package minigames.floLangeweile;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    Rectangle2D bounds;
    public boolean deleateMe = false;
    public Rectangle2D getBounds() {
        return bounds;
    }
    public abstract void doThings(int tick);

    public abstract void drawSelf(Graphics2D g2d);

    protected void drawByRectangles(Graphics2D g2d, Image image){
        g2d.drawImage(image, (int) bounds.getX(), (int) ( bounds.getY() + JumpGame.generalHeight), (int) bounds.getWidth(), (int) bounds.getHeight(),null);
        g2d.drawImage(image, (int) bounds.getX() + 360, (int) ( bounds.getY() + JumpGame.generalHeight), (int) bounds.getWidth(), (int) bounds.getHeight(),null);
    }
}
