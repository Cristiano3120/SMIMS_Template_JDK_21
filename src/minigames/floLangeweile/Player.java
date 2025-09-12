package minigames.floLangeweile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {

    double lastHeight = 0;
    private double speedX = 0;
    private double speedY = 0;
    private boolean rechterPlayer;


    private static BufferedImage[] images;

    public Player(int x, int y, boolean rechts) {
        bounds = new Rectangle2D.Double(x, y, 50, 50);
        rechterPlayer = rechts;

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
        var panelWidth = JumpGame.getGamePanel().getWidth();
        if(bounds.getX() < -panelWidth/2 ) bounds.setRect(panelWidth/2, bounds.getY(), bounds.getWidth(), bounds.getHeight());
        if(bounds.getX() > panelWidth/2) bounds.setRect( -panelWidth/2, bounds.getY(), bounds.getWidth(), bounds.getHeight());


        // Steuerung
        steuerung();

        // erster spieler
        if(bounds.getY() + JumpGame.generalHeight < JumpGame.getGamePanel().getHeight()*0.3)
            JumpGame.generalHeight++;

        //ende
        System.out.println(bounds.getY() + JumpGame.generalHeight > JumpGame.getGamePanel().getHeight());
        if(bounds.getY() + JumpGame.generalHeight > JumpGame.getGamePanel().getHeight() )
            JumpGame.oneDied = true;

        //movment
        boolean inAir = tryToMove();
        if(inAir){
             speedY += 0.1;
        }else {
            speedY = -6;
        }

    }
    private void steuerung(){

        if(rechterPlayer){
            speedX = (speedX*19 + JumpGame.controller.getJoystickRechtsX()*10)/20;

        }
        else{
            speedX = (speedX*19 + JumpGame.controller.getJoystickLinksX()*10)/20;
        }
        if(speedX > 15) speedX = 15;
        if(speedX < -15) speedX = -15;
    }



    @Override
    public void drawSelf(Graphics2D g2d) {
        drawByRectangles(g2d,images[0]);
    }

    // true wenn funktionirt
    private boolean tryToMove(){
        if(speedY < 0){
            bounds.setFrame(bounds.getMinX() + speedX, bounds.getMinY() +speedY, bounds.getWidth(), bounds.getHeight());

            return true;
        }
        Rectangle2D.Double r = new Rectangle2D.Double(bounds.getX() + speedX ,bounds.getY() + bounds.getHeight() -1 + speedY, bounds.getWidth(), 1);
        Rectangle2D.Double r2 = new Rectangle2D.Double(bounds.getX() + speedX + JumpGame.getGamePanel().getWidth() ,bounds.getY() + bounds.getHeight() -1 + speedY, bounds.getWidth(), 1);
        boolean collide = JumpGame.umgebung.stream()
                .anyMatch(u -> u.bounds.intersects(r));
        if(!collide)
            collide = JumpGame.umgebung.stream()
                    .anyMatch(u -> u.bounds.intersects(r2));

        if(collide){
            return false;
        }
        else{
            bounds.setFrame(bounds.getMinX() + speedX, bounds.getMinY() +speedY, bounds.getWidth(), bounds.getHeight());

            return true;
        }

    }
}
