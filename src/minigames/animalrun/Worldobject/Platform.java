package minigames.animalrun.Worldobject;

import minigames.animalrun.AnimalRun;
import sas.Picture;
import sas.Rectangle;
import sas.View;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Platform extends Picture implements WorldObject {

    //Test
    private boolean summonNext;
    Point2D.Double pos;
    Rectangle rectTest;
    Rectangle blockedZone;
    Rectangle nextPlatformZone;
    double xKameraVrsatz = 0;
    View view;

    public Platform(View view, boolean summonNext) {
        //super(220, 100, 200, 200, "resources/animalrun/platforms/testInsel.png");
        super(220, 100, 200, 200, "resources/animalrun/platforms/InselTest2.png");
        this.summonNext = summonNext;
        this.view = view;

        pos = new Point2D.Double();
        pos.x = view.getWidth() + AnimalRun.getXKameraVersatz();
        pos.y = Math.random() * view.getHeight();

        scaleTo(view.getWidth() * 0.3, view.getHeight() * 0.1);
        System.out.println((view.getWidth() * 0.3) + " / " + view.getHeight() * 0.1 + " -> " + getShapeWidth() + " " + getShapeHeight());

        //setImage(ImageIO.read(new File("resources/animalrun/platforms/testInsel.png")));
//        setImage(getImage());

        rectTest = new Rectangle(getShapeX(), getShapeY(), getShapeWidth(), getShapeHeight(), Color.red);
        rectTest.setTransparency(0.5F);

        blockedZone = new Rectangle(0, 0, getShapeWidth(), getShapeHeight()*5, Color.blue);
        blockedZone.setTransparency(0.5F);

        nextPlatformZone = new Rectangle(0, 0, getShapeWidth() + view.getWidth()*0.3, getShapeHeight()*5, Color.yellow);
        nextPlatformZone.setTransparency(0.5F);
        setPosition();
        System.out.println("Platform: Test");


    }

    private Platform(double xPos, double yPos, double width, double height, String textur, View view) {
        super(xPos, yPos, width, height, textur);

    }


    public void doThings(int tick) {
        System.out.println("Platform: doThings " + (getCenterX()));
        if(summonNext && getShapeX()+getShapeWidth() - AnimalRun.getXKameraVersatz() < view.getWidth()) summonNext();
    }

    void summonNext() {
        //AnimalRun.
    }

    public void updatePos() {
        setPosition();
    }

    public boolean isSollit() {
        return false;
    }


    protected void setPosition() {

        moveTo(pos.x - AnimalRun.getXKameraVersatz(), pos.y);
        System.out.println("Platform: TestTTTTTT" + pos.x + " " + getXPosition());
        //rectTest.moveTo(pos.x - AnimalRun.getXKameraVersatz(), getYPosition());
        rectTest.moveTo(pos.x - AnimalRun.getXKameraVersatz(), getShapeY());
        moveToByCenter (blockedZone, getCenterX(), getCenterY());
        moveToByCenter (nextPlatformZone, getCenterX(), getCenterY());
    }

    @Override
    public void setView(View view) {

    }

    @Override
    public void setXKameraVersatz(double versatz) {

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
        r.moveTo(x - r.getShapeWidth()/2, y - r.getShapeHeight()/2);
    }
}
