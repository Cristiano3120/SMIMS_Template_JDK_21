package minigames.floLangeweile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {

    public static double yPos;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2d = ((Graphics2D) g );
        if(JumpGame.oneDied) {

            g2d.setColor(Color.darkGray);
            g2d.fillRect((int) (getWidth()/6), getHeight()/3, (int) (getWidth()/1.5), getHeight()/4);
            g2d.setColor(Color.gray);
            g2d.drawString("Erneut Spielen", (int) (getWidth()/4 -10), (int) (getHeight()/2 -30));
            return;
        }
        BufferedImage canva = new BufferedImage(900, 900, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) canva.getGraphics();

        /// //////////////////////


        JumpGame.objekte.forEach(objekte -> objekte.drawSelf(g2d));
        JumpGame.umgebung.forEach(objekte -> objekte.drawSelf(g2d));
//        try {
//            BufferedImage i = ImageIO.read(new File("resources/jumpGame/player/player0.png"));
//            //g2d.drawImage(i, 50,50,100,100,null);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


        g2d.setBackground(Color.blue);
        g2d.setColor(Color.RED);
        //g2d.fillRect(0,0,getWidth(),100);
        //g2d.fillRect(000,200,getWidth(),100);
        g2d.dispose();


        /// //////////////////////


        //g2.drawImage(canva, getWidth(), getWidth(), null);

        //g2.setColor(Color.blue);
        //g2.fillRect(10,10,50,50);
    }

}
