package minigames.animalRun.Worldobject;

import minigames.animalRun.AnimalRun;
import sas.Picture;
import sas.Rectangle;
import sas.View;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

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

    public Platform(View view, boolean summonNext) {
        //super(220, 100, 200, 200, "resources/animalrun/platforms/testInsel.png");
        super(220, 100, 200, 200, "resources/animalrun/platforms/InselTest2.png");
        this.summonNext = summonNext;
        this.view = view;
        this.id = counter++;

        scaleTo(view.getWidth() * 0.3, view.getHeight() * 0.1);

        pos = new Point2D.Double();
        pos.x = view.getWidth() + AnimalRun.getXKameraVersatz();
        pos.y = Math.random() * (view.getHeight() - getHeight() *3) +  getHeight() *2;
        //pos.y = Math.random() * (view.getHeight() - getHeight() / 2);


//        System.out.println((view.getWidth() * 0.3) + " / " + view.getHeight() * 0.1 + " -> " + getShapeWidth() + " " + getShapeHeight());

        //setImage(ImageIO.read(new File("resources/animalrun/platforms/testInsel.png")));
//        setImage(getImage());

        rectTest = new Rectangle(getShapeX(), getShapeY(), getShapeWidth(), getShapeHeight(), Color.red);
        rectTest.setTransparency(0.5F);

        blockedZone = new Rectangle(0, 0, getShapeWidth(), getShapeHeight() * 5, Color.blue);
        blockedZone.setTransparency(0.5F);

        nextPlatformZone = new Rectangle(0, 0, getShapeWidth() + view.getWidth() * 0.3, getShapeHeight() * 5, Color.yellow);
        nextPlatformZone.setTransparency(0F);
        setPosition();
//        System.out.println("Platform: Test");


    }

    private Platform(double xPos, double yPos, double width, double height, String textur, View view) {
        super(xPos, yPos, width, height, textur);

    }


    public void doThings(int tick) {
//        System.out.println("Platform: doThings " + (getCenterX()));
//        System.out.println("Test------ " + (getShapeX() + getShapeWidth() - AnimalRun.getXKameraVersatz()) + " " + getShapeWidth() + " " + view.getWidth());



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
            p = new Platform(view, true);
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


    protected void setPosition() {

        moveTo(pos.x - AnimalRun.getXKameraVersatz(), pos.y);
//        System.out.println("Platform: TestTTTTTT" + pos.x + " " + getXPosition());
        //rectTest.moveTo(pos.x - AnimalRun.getXKameraVersatz(), getYPosition());
        rectTest.moveTo(pos.x - AnimalRun.getXKameraVersatz(), getShapeY());
        moveToByCenter(blockedZone, getCenterX(), getCenterY());
        moveToByCenter(nextPlatformZone, getCenterX(), getCenterY());
    }


    public void setImage(BufferedImage bImage) {
        BufferedImage sizedImage = new BufferedImage((int) getWidth(), (int) getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) sizedImage.getGraphics();
        Image image = bImage.getScaledInstance((int) getWidth(), (int) getHeight(), Image.SCALE_SMOOTH);
        g2.drawImage(bImage, 0, 0, (int) getWidth(), (int) getHeight(), null);
        g2.dispose();
        super.setImage(sizedImage);
    }

    void moveToByCenter(Rectangle r, double x, double y) {
        r.moveTo(x - r.getShapeWidth() / 2, y - r.getShapeHeight() / 2);
    }

    public void deleateMe(){
        view.remove(nextPlatformZone);
        view.remove(blockedZone);
        view.remove(this);
    }
}
