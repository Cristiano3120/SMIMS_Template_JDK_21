package minigames.animalRun;

import common.ScalablePicture;
import controller.AbstractController;
import minigames.animalRun.Worldobject.WorldObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sas.Shapes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Monkey extends ScalablePicture implements WorldObject {

    private static final Logger log = LoggerFactory.getLogger(Monkey.class);

    /* Static Variables */
    protected static double MONKEY_MOVEMENT = 5.0;
    protected static int baseLevelX = 0;
    private static final int IMAGE_WIDTH = 40;
    private static final int IMAGE_HEIGHT= 100;

    /* Object Variables */
    private AbstractController controller;
    private final ScalablePicture[] pictures1 = new ScalablePicture[4];
    private final BufferedImage[] MONKEY_IMAGES = new BufferedImage[4];
    private int currentIndex1;
    private boolean isMonkey1;
    private boolean turnedLeft;
    private boolean onGround;
    private int animIndex = 0;

    /* Constructors */
    public Monkey(double xp, double yp, AbstractController controller, boolean isMonkey1) throws IOException {
        super(xp, yp, "resources/animalrun/monkey1.png");
        this.controller = controller;
        this.isMonkey1 = isMonkey1;
        this.turnedLeft = true;
        this.currentIndex1 = 0;

        prepareMonkeyImages();
        setImage(0); // setze das erste Bild als Start

    }

    public void prepareMonkeyImages() throws IOException {
        int summand = isMonkey1 ? 0 : 4;
        for (int i = 1; i <= 4; i++) {
            MONKEY_IMAGES[i - 1] = ImageIO.read(new File("resources/animalrun/monkey" + (i + summand) + ".png"));
        }
    }

    @Override
    public boolean intersects(Shapes shape) {
        return pictures1[currentIndex1].intersects(shape);
    }

    /* Object Methods */
    public void monkeyMove() {
        double joystickVal = isMonkey1 ? controller.getJoystickRechtsX() : controller.getJoystickLinksX();
        flip(joystickVal);
        handleMovement();
    }

    private void handleMovement() {

        // TODO

        /*
         * alle möglichen Fälle
         * - Bewegung nach rechts
         * - Bewegung nach links
         * - Absprung
         * - im Flug / Fallen
         */

    }

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
    }

    private double getJoystickX() {
        return isMonkey1 ? controller.getJoystickLinksX() : controller.getJoystickRechtsX();
    }


    @Override
    public void doThings(int tick) {
        monkeyMove();

        final int animDelay = 8;
        if (tick % animDelay == 0) {
            pictures1[animIndex].setHidden(true);

            animIndex = animIndex >= 4 - 1
                    ? 0
                    : animIndex + 1;

            pictures1[animIndex].setHidden(false);
            currentIndex1 = animIndex;
        }
    }

    @Override
    public void updatePos() {

    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    @Override
    public boolean isSollit() {
        return false;
    }

    private void setImage(int index) {
        super.setImage(MONKEY_IMAGES[index]);
    }


    /* Inner classes */
    private static class Coordinates {
        private double x;
        private double y;

        private Coordinates(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private interface MovementFunction {
        public Coordinates computeNextVector();
    }

    private class FunktionAufBoden implements MovementFunction {
        @Override
        public Coordinates computeNextVector() {
            return new Coordinates(MONKEY_MOVEMENT * getJoystickX(), 0);
        }
    }

    private class FunktionSprung implements MovementFunction {

        private int tickCounter;

        /**
         *
         * @param sturz Dieser Parameter muss <code>true</code> sein, wenn der Affe z.B. gegen eine Plattform gesprungen
         *              ist und nun senkrecht nach unten fallen soll. Bei einem Sprung muss hier <code>true</code> gewählt
         *              werden.
         */
        private FunktionSprung(boolean sturz) {
            this.tickCounter = sturz ? -1 : -100;
        }

        @Override
        public Coordinates computeNextVector() {
            // -g * t Geschwindigkeit nach Zeit t (Sekunden)
            tickCounter++;
            return new Coordinates(MONKEY_MOVEMENT * getJoystickX(), AnimalRun.GRAVITY * tickCounter);
        }
    }


}
