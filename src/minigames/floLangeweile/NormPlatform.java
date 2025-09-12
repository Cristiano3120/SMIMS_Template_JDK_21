package minigames.floLangeweile;

import minigames.floLangeweile.Platformen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NormPlatform extends Platformen {
    private static BufferedImage image;
    public NormPlatform(int x, int y) {
        super(new Rectangle2D.Double(x,y,WIDTH,HEIGTH));
        if(image == null){
            try {
                image = ImageIO.read(new File("resources/jumpGame/platform.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void doThings(int tick) {
        bounds.setRect(bounds.getX(), bounds.getY() + JumpGame.generalHeight, bounds.getWidth(), bounds.getHeight());
    }

    @Override
    public void drawSelf(Graphics2D g2d) {
        drawByRectangle(g2d, image);
    }
}
