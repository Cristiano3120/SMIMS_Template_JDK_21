package minigames.animalRun;

import common.ScalablePicture;
import controller.AbstractController;
import minigames.animalRun.Worldobject.WorldObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sas.Circle;

import java.io.IOException;


public class Monkey extends Circle implements WorldObject {

    private static final Logger log = LoggerFactory.getLogger(Monkey.class);

    /* Static Variables */
    protected static double MONKEY_MOVEMENT = 5.0;
    private static final int IMAGE_HEIGHT = 250;
    private static final int IMAGE_WIDTH = (int) (IMAGE_HEIGHT * 1.44);

    /* Static Methods */

    /* Object Variables */
    private AbstractController controller;
    private final ScalablePicture[] MONKEY_IMAGES;
    private final ScalablePicture[] MONKEY_IMAGES_JUMP;
    private boolean isMonkey1;
    private boolean turnedLeft;
    private boolean onGround;
    private int indexImageRegular = 0;
    private int indexImageJump = 0;
    private MovementFunction currentMovement;

    /* Constructors */
    public Monkey(double xp, double yp, AbstractController controller, boolean isMonkey1) throws IOException {
        super(xp, yp, 0.1);
        this.MONKEY_IMAGES = new ScalablePicture[4];
        this.MONKEY_IMAGES_JUMP = new ScalablePicture[2];
        this.controller = controller;
        this.isMonkey1 = isMonkey1;
        this.turnedLeft = true;
        this.currentMovement = new FunktionSprung(true);
        setHidden(true);

        prepareMonkeyImages();
        setImageAndMove(0, false); // setze das erste Bild als Start
        moveTo(xp, yp);
        System.out.println(MONKEY_IMAGES[0].getShapeX() + ", " + MONKEY_IMAGES[0].getShapeY());

    }

    public void prepareMonkeyImages() throws IOException {

        // Zuerst laden wir die regulären Bilder.
        int summand = isMonkey1 ? 0 : 4;
        ScalablePicture tempPic;

        for (int i = 0; i < MONKEY_IMAGES.length; i++) {

            tempPic = new ScalablePicture(0, 0, "resources/animalrun/monkey" + (i + 1 + summand) + ".png");
            tempPic.scaleTo(250);
            tempPic.moveTo(getShapeX(), getShapeY());
            tempPic.setHidden(true);

            MONKEY_IMAGES[i] = tempPic;

        }

        // Jetzt laden wir die Sprung-Animationen.
        summand = isMonkey1 ? 0 : 2;
        for (int i = 0; i < MONKEY_IMAGES_JUMP.length; i++) {

            tempPic = new ScalablePicture(0, 0, "resources/animalrun/monkey" + (i + 9 + summand) + ".png");
            tempPic.scaleTo(250);
            tempPic.moveTo(getShapeX(), getShapeY());
            tempPic.setHidden(true);

            MONKEY_IMAGES_JUMP[i] = tempPic;
        }
    }

    /* Object Methods */
    private void flip(double joystickVal) {

        ScalablePicture imageToFlip = currentMovement instanceof FunktionAufBoden
                ? MONKEY_IMAGES[indexImageRegular]
                : MONKEY_IMAGES_JUMP[indexImageJump];

        if (joystickVal < 0 && !turnedLeft) {
            imageToFlip.flipHorizontal();
            turnedLeft = true;
        } else if (joystickVal > 0 && turnedLeft) {
            imageToFlip.flipHorizontal();
            turnedLeft = false;
        }
    }

    private double getJoystickX() {
        return isMonkey1 ? controller.getJoystickLinksX() : controller.getJoystickRechtsX();
    }

    @Override
    public void doThings() {


        // Get the x-position of the correct joystick.
        double joystickVal = isMonkey1 ? controller.getJoystickRechtsX() : controller.getJoystickLinksX();
        flip(joystickVal);

        // Does the player want to jump?
        boolean jumpButtonPressed = isMonkey1
                ? controller.getLinksA()
                : controller.getRechtsA();

        if (jumpButtonPressed && currentMovement instanceof FunktionAufBoden) {
            currentMovement = new FunktionSprung(true);
            System.out.println(currentMovement);
        }

        // Choose the right animation.
        if (currentMovement instanceof FunktionAufBoden) {
            setImageAndMove(indexImageRegular, false);
            indexImageRegular = indexImageRegular >= MONKEY_IMAGES.length - 1
                    ? 0
                    : indexImageRegular + 1;
        } else {
            setImageAndMove(indexImageJump, true);
            indexImageJump = 1;
        }

        Coordinates coords = currentMovement.computeNextVector();
        move(coords.x, coords.y);

    }


    public void signalOnGround(boolean onGround) {
        currentMovement = new FunktionAufBoden();
        this.onGround = onGround;
    }

    @Override
    public void updatePos() {

    }

    /**
     * Sets the desired image and moves it to the current position.
     *
     * @param index the target index.
     * @param jump  indicates whether we are jumping / falling or not.
     */
    public void setImageAndMove(int index, boolean jump) {

        System.out.println("Moving to " + index + ", (" + jump + ")");

        if (jump) {

            if (index != indexImageJump) {
                MONKEY_IMAGES_JUMP[indexImageJump].setHidden(true);
                MONKEY_IMAGES_JUMP[index].setHidden(false);
                MONKEY_IMAGES_JUMP[index].moveTo(getShapeX(), getShapeY());

                indexImageJump = index;
            }
        } else {

            MONKEY_IMAGES[indexImageRegular].setHidden(true);
            MONKEY_IMAGES[index].setHidden(false);
            MONKEY_IMAGES[index].moveTo(getShapeX(), getShapeY());
            indexImageRegular = index;
        }
    }

    public boolean isOnGround() {
        return onGround;
    }

    @Override
    public boolean isSollit() {
        return false;
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

        private FunktionAufBoden() {
            indexImageRegular = 0;
        }

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
            indexImageJump = sturz ? 1 : 0; // Wir springen direkt zum zweiten Bild.
        }

        @Override
        public Coordinates computeNextVector() {
            // -g * t Geschwindigkeit nach Zeit t (Sekunden)
            tickCounter++;
            return new Coordinates(MONKEY_MOVEMENT * getJoystickX(), AnimalRun.GRAVITY * tickCounter);
        }
    }

}
