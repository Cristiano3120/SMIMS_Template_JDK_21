package minigames.animalrun.Worldobject;

import minigames.animalrun.AnimalRun;
import sas.Picture;
import sas.Rectangle;
import sas.View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Platform extends Picture implements WorldObject {

    private static int counter = 0; // TODO: Remove

    //Test
    private boolean summonNext;
    private Point2D.Double pos;
    private Rectangle rectTest;
    private Rectangle blockedZone;
    private Rectangle nextPlatformZone;
    private double xKameraVrsatz = 0;
    private View view;
    private int id;
    private int size;
    public boolean deleateMe = false;


    public Platform(View view, boolean summonNext, boolean space) {
        //super(220, 100, 200, 200, "resources/animalrun/platforms/testInsel.png");
        super(view.getWidth(), 100, 200, 200, "resources/animalrun/platforms/InselTest2.png");
        this.summonNext = summonNext;
        this.view = view;
        this.id = counter++;
        size = (int) (Math.random()* 2);
        BufferedImage image = new BufferedImage(1021+1077+1795*size, 1223, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();

        try {
            BufferedImage front = ImageIO.read(new File("resources/animalrun/platforms/platformLinks.png"));
            BufferedImage back = ImageIO.read(new File("resources/animalrun/platforms/platformRechts.png"));
            BufferedImage center = ImageIO.read(new File("resources/animalrun/platforms/platformMitte.png"));
            g2.drawImage(front,0,100,null);
            for(int i = 0; i < size; i++){
                g2.drawImage(center,1021 + i*1795,0,null);
            }
            g2.drawImage(back,1021 + size*1795,0,null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setImage(image);

        scaleTo(image.getWidth() * (size+1)*0.1, image.getHeight() * 0.08);

        pos = new Point2D.Double();
        space = true;
        pos.x = (space)? view.getWidth() + AnimalRun.getXKameraVersatz() + view.getWidth()*0.1 : view.getWidth() + AnimalRun.getXKameraVersatz();
        pos.y = Math.random() * (view.getHeight() - getHeight() *3) +  getHeight() *2;
        pos.y = ((int) (Math.random() * (view.getHeight() - getHeight() *3) +  getHeight() *2) / 10)*10;
        //pos.y = Math.random() * (view.getHeight() - getHeight() / 2);


//        System.out.println((view.getWidth() * 0.3) + " / " + view.getHeight() * 0.1 + " -> " + getShapeWidth() + " " + getShapeHeight());

        //setImage(ImageIO.read(new File("resources/animalrun/platforms/testInsel.png")));
//        setImage(getImage());

        rectTest = new Rectangle(getShapeX(), getShapeY(), getShapeWidth(), getShapeHeight(), Color.red);
        rectTest.setTransparency(0.0F);

        blockedZone = new Rectangle(0, 0, getShapeWidth(), getShapeHeight() * 5, Color.blue);
        blockedZone.setTransparency(0.00F);

        nextPlatformZone = new Rectangle(0, 0, getShapeWidth() + view.getWidth() * 0.3, getShapeHeight() * 4, Color.yellow);
        nextPlatformZone.setTransparency(0.00F);
        setPosition();
//        System.out.println("Platform: Test");


    }

    private Platform(double xPos, double yPos, double width, double height, String textur, View view) {
        super(xPos, yPos, width, height, textur);

    }


    public void doThings(int tick) {
        if(getShapeX() + getShapeWidth() < 0 )
            deleateMe();

        if (summonNext && getShapeX() + getShapeWidth() <= view.getWidth()) {
            System.out.println(id + " spawnt neu");
            summonNext();
        }
    }

    private void summonNext() {
        summonNext = false;
        Platform p = null;
        do {

            //  view.getWidth() + AnimalRun.getXKameraVersatz(),
            //            Math.random() * view.getHeight(),
            //              view.getWidth() * 0.3,
            //                view.getHeight() * 0.1);
            if (p != null){
                p.deleateMe();
                p = null;
            }
            //System.out.println(id + " neue Plattform");
            p = (Math.random() < 0.2)?  new Platform(view, true,true): new Platform(view, true,false);
//            p.setHidden(true);
            //view.remove(p);

        } while (!p.intersects(nextPlatformZone));

        AnimalRun.addToWorldObjects(p);
    }

    public void updatePos() {
        setPosition();
    }

    public boolean isSollit() {
        return false;
    }

    public Point2D.Double getPos(){
        return pos;
    }

    public double getWidth(){
        return getShapeWidth();
    }

    public double getHeight(){
        return getShapeHeight();
    }

    protected void setPosition() {

        moveTo(pos.x - AnimalRun.getXKameraVersatz(), pos.y);
//        System.out.println("Platform: TestTTTTTT" + pos.x + " " + getXPosition());
        //rectTest.moveTo(pos.x - AnimalRun.getXKameraVersatz(), getYPosition());
        rectTest.moveTo(pos.x - AnimalRun.getXKameraVersatz(), getShapeY());
        moveToByCenter(blockedZone, getCenterX(), getCenterY());
        moveToByCenter(nextPlatformZone, getCenterX(), getCenterY());
    }


    public void setImage(BufferedImage bImage) {
        BufferedImage sizedImage = new BufferedImage((int) (getShapeWidth()*1.5), (int) (getShapeHeight()*1.5), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) sizedImage.getGraphics();
       // Image image = bImage.getScaledInstance((int) (getShapeWidth()*1.5), (int) (getShapeHeight()*1.5), Image.SCALE_SMOOTH);
        Image image = bImage.getScaledInstance((int) (getShapeWidth()*1.5), (int) (getShapeHeight()*1.5), Image.SCALE_DEFAULT);
        g2.drawImage(bImage, 0, 0, (int) (getShapeWidth()*1.5), (int) (getShapeHeight()*1.5), null);
        g2.dispose();
        super.setImage(sizedImage);
    }

    void moveToByCenter(Rectangle r, double x, double y) {
        r.moveTo(x - r.getShapeWidth() / 2, y - r.getShapeHeight() / 2);
    }

    public void deleateMe(){
        view.remove(rectTest);
        rectTest.setHidden(true);
        view.remove(nextPlatformZone);
        nextPlatformZone.setHidden(true);
        view.remove(blockedZone);
        blockedZone.setHidden(true);
        view.remove(this);
        this.setHidden(true);
        deleateMe = true;
    }
    public boolean getDeleatMe(){
        return deleateMe;
    }
}
