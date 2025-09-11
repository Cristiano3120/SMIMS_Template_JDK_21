package minigames.animalRun;

import common.ScalablePicture;
import controller.AbstractController;
import minigames.animalRun.Worldobject.WorldObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sas.Circle;
import sas.Shapes;
import sas.View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.nio.file.Files.move;

public class Monkey extends Circle implements WorldObject {

    private static final Logger log = LoggerFactory.getLogger(Monkey.class);
    /* Static Variables */
    protected static double MONKEY_MOVEMENT = 5.0;
    protected static int baseLevelX = 0;

    /* Object Variables */
    private AbstractController controller;
    public final ScalablePicture[] pictures1 = new ScalablePicture[4];
    public ScalablePicture jumpPicture;
    private boolean jumpTurnedLeft;
    public int currentIndex1;
    private boolean isMonkey1;
    private boolean turnedLeft = true;
    private View view;

    /* Constructors */
    public Monkey(double xp, double yp, AbstractController controller, boolean isMonkey1, View view) {
        super(xp, yp , 0.1);
        this.controller = controller;
        this.isMonkey1 = isMonkey1;
        this.view = view;
        this.currentIndex1 = 0;
        setHidden(true);

        if (isMonkey1) {
            setupMonkey1();
        } else {
            setupMonkey2();
        }
    }

    public void setupMonkey1() {
        for (int i = 1; i <= 4; i++) {
            ScalablePicture scalablePicture = new ScalablePicture(10, 400, 150, 150, "resources/animalrun/monkey" + (i) + ".png");
            scalablePicture.setHidden(true);

            pictures1[i - 1] = scalablePicture;
            jumpPicture = new ScalablePicture(10, 400, 150, 150, "resources/animalrun/monkey" + 11 + ".png");
            jumpPicture.setHidden(true);
            jumpTurnedLeft = true;

        }
    }

    private void setupMonkey2() {
        for (int i = 1; i <= 4; i++) {
            ScalablePicture scalablePicture = new ScalablePicture(10, 400, 150, 150, "resources/animalrun/monkey" + (i + 4) + ".png");
            scalablePicture.setHidden(true);

            pictures1[i - 1] = scalablePicture;
            jumpPicture = new ScalablePicture(10, 400, 150, 150, "resources/animalrun/monkey" + 12 + ".png");
            jumpPicture.setHidden(true);
            jumpTurnedLeft = true;
        }
    }

    @Override
    public boolean intersects(Shapes shape) {
        return pictures1[currentIndex1].intersects(shape);
    }

    /* Object Methods */
    public void monkeyMove() {
        if (isMonkey1) {
            double joystickVal = controller.getJoystickRechtsX();
            flip(joystickVal);
        } else {
            double joystickVal = controller.getJoystickLinksX();
            flip(joystickVal);
        }
    }

    //MONKEY MUSS BEI SPRINGEN GEFLIPPED WERDEN HIER LOGIK DAFÜR KLAUEN
    private void flip(double joystickVal) {
        if (joystickVal < 0) {
            if (!turnedLeft) {
                for (ScalablePicture scalablePicture : pictures1) {
                    scalablePicture.flipHorizontal();
                }
                turnedLeft = true;
            }
        } else if (joystickVal > 0) {
            if (turnedLeft) {
                for (ScalablePicture scalablePicture : pictures1) {
                    scalablePicture.flipHorizontal();
                }
                turnedLeft = false;
            }
        }

        move(MONKEY_MOVEMENT * joystickVal);
        for (int i = 0; i < 4; i++) {
            pictures1[i].move(MONKEY_MOVEMENT * joystickVal * 6);
        }
    }

    public void monkeyJump() {
        if (isMonkey1) {
            double velocity = 0;
            if (controller.getRechtsA()) {
                jumpPicture.moveTo(pictures1[currentIndex1].getCenterX(), pictures1[currentIndex1].getCenterY() );
                pictures1[currentIndex1].setHidden(true);
                jumpPicture.setHidden(false);

                velocity = 250;
                jumpFlip();
                jumpPicture.move(MONKEY_MOVEMENT * controller.getJoystickRechtsX() * 10, -velocity);

                view.wait(250);

                //double jump
                if (controller.getRechtsA()) {
                    jumpPicture.move(MONKEY_MOVEMENT * controller.getJoystickRechtsX() * 10, -velocity);

                    view.wait(200);

                    jumpPicture.move(MONKEY_MOVEMENT * controller.getJoystickRechtsX() * 10, velocity);

                    view.wait(200);
                }

                    jumpPicture.move(MONKEY_MOVEMENT * controller.getJoystickRechtsX() * 10, velocity);
                    jumpPicture.moveTo(jumpPicture.getCenterX(), jumpPicture.getCenterY());

                jumpPicture.setHidden(true);
                pictures1[currentIndex1].setHidden(false);

                for(int i = 0; i < pictures1.length; i++) {
                    pictures1[i].moveTo(jumpPicture.getCenterX(), jumpPicture.getCenterY() - 147.85);
                }

                System.out.println(pictures1[0].getCenterY());
            }
        } else {
            double velocity = 0;

            if (controller.getLinksA()) {
                jumpPicture.moveTo(pictures1[currentIndex1].getShapeX(), pictures1[currentIndex1].getShapeY());
                pictures1[currentIndex1].setHidden(true);
                jumpPicture.setHidden(false);

                velocity = 250;
                jumpPicture.move(MONKEY_MOVEMENT * controller.getJoystickLinksX() * 10, -velocity);


                view.wait(200);

                //double jump
                if (controller.getRechtsA()) {
                    jumpPicture.move(MONKEY_MOVEMENT * controller.getJoystickRechtsX() * 10, -velocity);

                    view.wait(200);


                    jumpPicture.move(MONKEY_MOVEMENT * controller.getJoystickRechtsX() * 10, velocity);

                    view.wait(200);
                }

                jumpPicture.move(MONKEY_MOVEMENT * controller.getJoystickLinksX() * 10, velocity);

                jumpPicture.setHidden(true);
                pictures1[currentIndex1].setHidden(false);
                for(int i = 0; i < pictures1.length; i++) {
                    pictures1[i].moveTo(jumpPicture.getCenterX(), jumpPicture.getCenterY()-200);
                }
            }

        }
    }

    public void jumpFlip() {
        if (!turnedLeft && jumpTurnedLeft) {
            jumpPicture.flipHorizontal();
            jumpTurnedLeft = false;

        }else if(turnedLeft && !jumpTurnedLeft) {
                jumpPicture.flipHorizontal();
                jumpTurnedLeft = true;
        }
    }
}
