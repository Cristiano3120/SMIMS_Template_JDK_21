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

    public static final double PLATFORM_SPEED = 3.0;

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

        scaleTo(view.getWidth() * 0.3, view.getHeight() * 0.1);

        pos = new Point2D.Double();
        pos.x = view.getWidth();
        pos.y = Math.random() * (view.getHeight() - getHeight() * 3) + getHeight() * 2;
        //pos.y = Math.random() * (view.getHeight() - getHeight() / 2);

        rectTest = new Rectangle(getShapeX(), getShapeY(), getShapeWidth(), getShapeHeight(), Color.red);
        rectTest.setTransparency(0.5F);

        blockedZone = new Rectangle(0, 0, getShapeWidth(), getShapeHeight() * 5, Color.blue);
        blockedZone.setTransparency(0.5F);

        nextPlatformZone = new Rectangle(0, 0, getShapeWidth() + view.getWidth() * 0.3, getShapeHeight() * 5, Color.yellow);
        nextPlatformZone.setTransparency(0F);
        setPosition();

        System.out.println("Platform created");

    }


    @Override
    public void doThings() {
        pos.x -= PLATFORM_SPEED;
        System.out.println("Platform moved" + pos.x);
    }

    public void updatePos() {
        setPosition();
    }

    public boolean isSollit() {
        return false;
    }


    protected void setPosition() {

        moveTo(pos.x, pos.y);
//        System.out.println("Platform: TestTTTTTT" + pos.x + " " + getXPosition());
        //rectTest.moveTo(pos.x - AnimalRun.getXKameraVersatz(), getYPosition());
        rectTest.moveTo(pos.x - PLATFORM_SPEED, getShapeY());
        moveToByCenter(blockedZone, getCenterX(), getCenterY());
        moveToByCenter(nextPlatformZone, getCenterX(), getCenterY());
    }


    public void setImage(BufferedImage bImage) {
        BufferedImage sizedImage = new BufferedImage((int) getShapeWidth(), (int) getShapeHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) sizedImage.getGraphics();
        Image image = bImage.getScaledInstance((int) getShapeWidth(), (int) getShapeHeight(), Image.SCALE_SMOOTH);
        g2.drawImage(bImage, 0, 0, (int) getShapeWidth(), (int) getShapeHeight(), null);
        g2.dispose();
        super.setImage(sizedImage);
    }

    void moveToByCenter(Rectangle r, double x, double y) {
        r.moveTo(x - r.getShapeWidth() / 2, y - r.getShapeHeight() / 2);
    }

    public void deleateMe() {
        view.remove(nextPlatformZone);
        view.remove(blockedZone);
        view.remove(this);
    }
}
