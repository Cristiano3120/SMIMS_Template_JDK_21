package minigames.animalRun.Worldobject;

import common.ScalablePicture;
import minigames.AbstractGame;
import sas.Picture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background extends Picture {
    String s = "resources/animalrun/animalRunBackground.png";
    private static double SPEED = 3;
    int width;
    int height;
    //private static final String PATH_IMAGE = AbstractGame.PATH_TO_RESOURCES +"background_game.png";
    BufferedImage hintergrundImage;
    public Background(int x, int y,int width,int height) {
        super(0, 0,width,height, "resources/animalrun/animalRunBackgroundSchmal.png");
        //super(x, y,width,height, path);
        this.width = width;
        this.height = height;
        try {
            hintergrundImage = ImageIO.read(new File("resources/animalrun/animalRunBackgroundSchmal.png"));
            BufferedImage bImage = new BufferedImage((int) (width*2*1.5), (int) (height*1.5), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = (Graphics2D) bImage.getGraphics();
            g2.drawImage( hintergrundImage, (int) 0, 0, (int) (getShapeWidth()*1.5), (int) (getShapeHeight()*1.5),  null);
            g2.drawImage(hintergrundImage, (int) (width*1.5),0, (int) (getShapeWidth()*1.5), (int) (getShapeHeight()*1.5), null);
            hintergrundImage = bImage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //setImage(hintergrundImage);
        System.out.println("Background: Test" + getShapeX() + " " + getShapeY() + " " + getShapeWidth() + " " + getShapeHeight());
    }
    public void moveBackground(int tick){
        moveTo(-tick % (getShapeWidth() ), 0);
        super.setImage(hintergrundImage);
        System.out.println("Background: " + tick +  " " + (-(tick*SPEED) % width*1.5));

    }

    public void setImage(BufferedImage bImage) {
        BufferedImage sizedImage = new BufferedImage((int) (width*1.5), (int) (height*1.5), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) sizedImage.getGraphics();
        //Image image = bImage.getScaledInstance((int) (getShapeWidth()*1.5), (int) (getShapeHeight()*1.5), Image.SCALE_SMOOTH);
        Image image = bImage.getScaledInstance((int) (getShapeWidth()), (int) (getShapeHeight()), Image.SCALE_DEFAULT);
        g2.drawImage(image, 0, 0, (int) (getShapeWidth()), (int) (getShapeHeight()), null);
        g2.dispose();
        super.setImage(sizedImage);
    }
}
