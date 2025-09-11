package minigames.floLangeweile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {

    double speedX = 0;
    double speedY = 0;

    private static BufferedImage[] images;

    public Player(int x, int y, boolean rechts) {
        bounds = new Rectangle2D.Double(x, y, 50, 50);

        if(images == null){
            images = new BufferedImage[4];
            for(int i = 0; i < images.length; i++){
                try {
                    images[i] = ImageIO.read(new File("resources/jumpGame/player/player0.png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        }
    }

    @Override
    public void doThings(int tick) {
        //keine ränder
        if(bounds.getX() < -180) bounds.setRect(bounds.getX() + 180, bounds.getY(), bounds.getWidth(), bounds.getHeight());
        if(bounds.getX() > 180) bounds.setRect(bounds.getX() - 180, bounds.getY(), bounds.getWidth(), bounds.getHeight());


        // Steuerung
        speedX += JumpGame.controller.

    }

    @Override
    public void drawSelf(Graphics2D g2d) {
        drawByRectangles(g2d,images[0]);
    }

    // true wenn funktionirt
    private boolean tryToMove(){
        if(speedY < 0){
            bounds.setFrame(bounds.getMinX() + speedX, bounds.getMinY() +speedY, bounds.getWidth(), bounds.getHeight());
            if(bounds.getX() > JumpGame.generalHeight){
                JumpGame.generalHeight = bounds.getX();
            }
            return true;
        }
        Rectangle2D.Double r = new Rectangle2D.Double(bounds.getX() + speedX ,bounds.getY() + speedY, bounds.getWidth(), 1);
        boolean collide = JumpGame.umgebung.stream()
                .anyMatch(u -> u.bounds.intersects(r));

        if(collide){
            return false;
        }
        else{
            bounds.setFrame(bounds.getMinX() + speedX, bounds.getMinY() +speedY, bounds.getWidth(), bounds.getHeight());
            if(bounds.getX() > JumpGame.generalHeight){
                JumpGame.generalHeight = bounds.getX();
            }
            return true;
        }

    }
}
