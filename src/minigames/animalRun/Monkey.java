package minigames.animalRun;

import common.ScalablePicture;
import controller.AbstractController;
import minigames.animalRun.Worldobject.WorldObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sas.Picture;
import sas.Shapes;

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
    private static final int IMAGE_WIDTH = 250;
    private static final int IMAGE_HEIGHT = 200;

    /* Object Variables */
    private AbstractController controller;
    private final BufferedImage[] MONKEY_IMAGES = new BufferedImage[4];
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

        prepareMonkeyImages();
        setImage(0); // setze das erste Bild als Start
        moveTo(xp, yp);

    }

    public void prepareMonkeyImages() throws IOException {

        int summand = isMonkey1 ? 0 : 4;
        BufferedImage tempImage;
        for (int i = 0; i < MONKEY_IMAGES.length; i++) {

            // Lade das Bild.
            tempImage = ImageIO.read(new File("resources/animalrun/monkey" + (i + 1 + summand) + ".png"));

            // Passe die Größe an.
            BufferedImage sizedImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D) sizedImage.getGraphics();
            Image image = tempImage.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
            g2.drawImage(image, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
            g2.dispose();
            MONKEY_IMAGES[i] = sizedImage;

        }
    }

    /* Object Methods */
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

        // Get the x-position of the correct joystick.
        double joystickVal = isMonkey1 ? controller.getJoystickRechtsX() : controller.getJoystickLinksX();
        flip(joystickVal);
        handleMovement();

        animIndex = animIndex >= MONKEY_IMAGES.length - 1
                ? 0
                : animIndex + 1;

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

    @Override
    protected void setImage(BufferedImage image){
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
