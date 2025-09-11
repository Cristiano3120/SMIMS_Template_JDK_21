package minigames.animalRun;

import common.ScalablePicture;
import controller.AbstractController;
import minigames.animalRun.Worldobject.WorldObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Monkey extends ScalablePicture implements WorldObject {

    private static final Logger log = LoggerFactory.getLogger(Monkey.class);

    /* Static Variables */
    protected static double MONKEY_MOVEMENT = 5.0;
    protected static int baseLevelX = 0;
    private static final int IMAGE_HEIGHT = 250;
    private static final int IMAGE_WIDTH = (int) (IMAGE_HEIGHT * 1.44);

    /* Static Methods */
    private static BufferedImage scaleImage(BufferedImage tempImage) {
        BufferedImage sizedImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) sizedImage.getGraphics();
        Image image = tempImage.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        g2.drawImage(image, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
        g2.dispose();
        return sizedImage;
    }

    /* Object Variables */
    private AbstractController controller;
    private final BufferedImage[] MONKEY_IMAGES = new BufferedImage[4];
    private final BufferedImage[] MONKEY_IMAGES_JUMP = new BufferedImage[2];
    private boolean isMonkey1;
    private boolean turnedLeft;
    private boolean onGround;
    private int indexImageRegular = 0;
    private int indexImageJump = 0;
    private MovementFunction currentMovement;

    /* Constructors */
    public Monkey(double xp, double yp, AbstractController controller, boolean isMonkey1) throws IOException {
        super(xp, yp, "resources/animalrun/monkey1.png");
        this.controller = controller;
        this.isMonkey1 = isMonkey1;
        this.turnedLeft = true;
        this.currentMovement = new FunktionSprung(true);

        prepareMonkeyImages();
        //setImage(1,true); // setze das erste Bild als Start
        moveTo(xp, yp);

    }

    public void prepareMonkeyImages() throws IOException {

        // Zuerst laden wir die regulären Bilder.
        int summand = isMonkey1 ? 0 : 4;
        BufferedImage tempImage;
        for (int i = 0; i < MONKEY_IMAGES.length; i++) {

            // Lade das Bild.
            tempImage = ImageIO.read(new File("resources/animalrun/monkey" + (i + 1 + summand) + ".png"));
            System.out.println("resources/animalrun/monkey" + (i + 1 + summand) + ".png");

            // Passe die Größe an.
            MONKEY_IMAGES[i] = scaleImage(tempImage);

        }

        // Jetzt laden wir die Sprung-Animationen.
        summand = isMonkey1 ? 0 : 2;
        for (int i = 0; i < MONKEY_IMAGES_JUMP.length; i++) {

            // Lade das Bild.
            tempImage = ImageIO.read(new File("resources/animalrun/monkey" + (i + 9 + summand) + ".png"));
            System.out.println("resources/animalrun/monkey" + (i + 9 + summand) + ".png");

            // Passe die Größe an.
            MONKEY_IMAGES_JUMP[i] = scaleImage(tempImage);
        }
    }

    /* Object Methods */
    private void flip(double joystickVal) {
        if (joystickVal < 0 && !turnedLeft) {
            flipHorizontal();
            turnedLeft = true;
        } else if (joystickVal > 0 && turnedLeft) {
            flipHorizontal();
            turnedLeft = false;
        }
    }

    private double getJoystickX() {
        return isMonkey1 ? controller.getJoystickLinksX() : controller.getJoystickRechtsX();
    }

    @Override
    public void doThings() {

        System.out.println(getShapeX()+ ", " + getShapeY());

        // Get the x-position of the correct joystick.
        double joystickVal = isMonkey1 ? controller.getJoystickRechtsX() : controller.getJoystickLinksX();
        flip(joystickVal);
        System.out.println("joystickVal: " + joystickVal); // TODO remove

        // Does the player want to jump?
        boolean jumpButtonPressed = isMonkey1
                ? controller.getLinksA()
                : controller.getRechtsA();

        System.out.println("jumpButton " + jumpButtonPressed);

        if (jumpButtonPressed && currentMovement instanceof FunktionAufBoden) {
            currentMovement = new FunktionSprung(true);
            System.out.println(currentMovement);
        }

        // Choose the right animation.
        if (currentMovement instanceof FunktionAufBoden) {
            setImage(indexImageRegular, false);
            indexImageRegular = indexImageRegular >= MONKEY_IMAGES.length - 1
                    ? 0
                    : indexImageRegular + 1;
        } else {
            setImage(indexImageJump, true);
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

    public boolean isOnGround() {
        return onGround;
    }

    @Override
    public boolean isSollit() {
        return false;
    }

    public void setImage(int index, boolean jump) {
        if (jump) {
            super.setImage(MONKEY_IMAGES_JUMP[index]);
        } else {
            super.setImage(MONKEY_IMAGES[index]);
        }
    }


    @Override
    protected void setImage(BufferedImage image) {
        super.setImage(image);
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
