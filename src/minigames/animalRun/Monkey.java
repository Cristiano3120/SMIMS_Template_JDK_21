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
    protected static double MONKEY_MOVEMENT = 10.0;
    private static final int IMAGE_HEIGHT = 250;

    /* Static Methods */

    /* Object Variables */
    private AbstractController controller;
    private final ScalablePicture[] MONKEY_IMAGES;
    private final ScalablePicture[] MONKEY_IMAGES_JUMP;
    private boolean isMonkey1;
    private boolean turnedLeft;
    private int indexImageRegular;
    private MovementFunction currentMovement;
    private boolean nextTickSetFallImage;

    /* Constructors */
    public Monkey(double xp, double yp, AbstractController controller, boolean isMonkey1) throws IOException {
        super(xp, yp, 0.1);
        this.MONKEY_IMAGES = new ScalablePicture[4];
        this.MONKEY_IMAGES_JUMP = new ScalablePicture[2];
        this.controller = controller;
        this.isMonkey1 = isMonkey1;
        this.turnedLeft = true;
        this.indexImageRegular = 0;
        this.currentMovement = new FunktionAufBoden(); // TODO
        this.nextTickSetFallImage = false;
        setHidden(true);

        prepareMonkeyImages();
        moveTo(xp, yp);
        MONKEY_IMAGES[0].setHidden(false);
        MONKEY_IMAGES[0].moveTo(xp, yp);

    }

    public void prepareMonkeyImages() {

        // Zuerst laden wir die regulären Bilder.
        int summand = isMonkey1 ? 0 : 4;
        ScalablePicture tempPic;

        for (int i = 0; i < MONKEY_IMAGES.length; i++) {

            tempPic = new ScalablePicture(0, 0, "resources/animalrun/monkey" + (i + 1 + summand) + ".png");
            tempPic.scaleTo(IMAGE_HEIGHT);
            tempPic.moveTo(getShapeX(), getShapeY());
            tempPic.setHidden(true);

            MONKEY_IMAGES[i] = tempPic;

        }

        // Jetzt laden wir die Sprung-Animationen.
        summand = isMonkey1 ? 0 : 2;
        for (int i = 0; i < MONKEY_IMAGES_JUMP.length; i++) {

            tempPic = new ScalablePicture(0, 0, "resources/animalrun/monkey" + (i + 9 + summand) + ".png");
            tempPic.scaleTo(IMAGE_HEIGHT);
            tempPic.moveTo(getShapeX(), getShapeY());
            tempPic.setHidden(true);

            MONKEY_IMAGES_JUMP[i] = tempPic;
        }
    }

    /* Object Methods */
    private void flip(double joystickVal) {

        if (joystickVal < 0 && !turnedLeft) {
            flipAll();
            turnedLeft = true;
        } else if (joystickVal > 0 && turnedLeft) {
            flipAll();
            turnedLeft = false;
        }

    }

    private void flipAll() {
        for (ScalablePicture pic : MONKEY_IMAGES) {
            pic.flipHorizontal();
        }
        for (ScalablePicture pic : MONKEY_IMAGES_JUMP) {
            pic.flipHorizontal();
        }
    }

    private double getJoystickX() {
        return isMonkey1 ? controller.getJoystickLinksX() : controller.getJoystickRechtsX();
    }

    @Override
    public void doThings() {

        // Get the x-position of the correct joystick.
        double joystickVal = isMonkey1 ? controller.getJoystickLinksX() : controller.getJoystickRechtsX();
        flip(joystickVal);

        // Does the player want to jump?
        boolean jumpButtonPressed = isMonkey1
                ? controller.getLinksA()
                : controller.getRechtsA();

        if (jumpButtonPressed && currentMovement instanceof FunktionAufBoden /* TODO BODEN VERLOREN */) {
            currentMovement = new FunktionSprung(false);
            for (ScalablePicture pic : MONKEY_IMAGES) {
                pic.setHidden(true);
            }
            for (ScalablePicture pic : MONKEY_IMAGES_JUMP) {
                pic.setHidden(true);
            }
            MONKEY_IMAGES_JUMP[0].setHidden(false);
            nextTickSetFallImage = true;
        }

        // Berechne die Zielkoordinaten.
        Vector vector = currentMovement.computeNextVector();
        move(vector.x, vector.y);

        // Falls sich die X-Koordinate nicht ändert, fallen wir entweder senkrecht oder stehen auf der Stelle. In beiden
        // Fällen brauchen wir das Bild nicht zu ändern, selbst wenn die Y-Koordinate sich unterscheidet.
        if (vector.x == 0) {
            for (ScalablePicture pic : MONKEY_IMAGES) {
                pic.moveTo(getShapeX(), getShapeY());
            }
            for (ScalablePicture pic : MONKEY_IMAGES_JUMP) {
                pic.moveTo(getShapeX(), getShapeY());
            }
            if (nextTickSetFallImage) {
                MONKEY_IMAGES_JUMP[0].setHidden(true);
                MONKEY_IMAGES_JUMP[1].setHidden(false);
                nextTickSetFallImage = false;
            }
            return;
        }

        nextTickSetFallImage = false;

        // Wir müssen also das Bild ändern und den Affen bewegen. Entweder fallen wir gerade oder laufen. Fallen zuerst:
        if (currentMovement instanceof FunktionSprung) {
            for (ScalablePicture pic : MONKEY_IMAGES) {
                pic.setHidden(true);
            }
            MONKEY_IMAGES_JUMP[0].setHidden(true);
            MONKEY_IMAGES_JUMP[1].setHidden(false);
            MONKEY_IMAGES_JUMP[1].moveTo(getShapeX(), getShapeY());
            return;
        }

        // An diesem Punkt können wir nur noch regulär gehen.
        indexImageRegular = indexImageRegular >= MONKEY_IMAGES.length - 1
                ? 0
                : indexImageRegular + 1;
        for (int i = 0; i < MONKEY_IMAGES.length; i++) {
            if (i == indexImageRegular) {
                MONKEY_IMAGES[i].setHidden(false);
                MONKEY_IMAGES[i].moveTo(getShapeX(), getShapeY());
            } else {
                MONKEY_IMAGES[i].setHidden(true);
            }
        }
        for (ScalablePicture pic : MONKEY_IMAGES_JUMP) {
            pic.setHidden(true);
        }
    }


    public void signalOnGround(boolean onGround) {
        currentMovement = new FunktionAufBoden();
    }

    public void signaleCollision() {
        currentMovement = new FunktionSprung(true);
    }

    @Override
    public void updatePos() {

    }

    @Override
    public boolean isSollit() {
        return false;
    }

    /* Inner classes */
    private static class Vector {
        private double x;
        private double y;

        private Vector(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private interface MovementFunction {
        public Vector computeNextVector();
    }

    private class FunktionAufBoden implements MovementFunction {

        private FunktionAufBoden() {
            indexImageRegular = 0;
        }

        @Override
        public Vector computeNextVector() {
            return new Vector(MONKEY_MOVEMENT * getJoystickX(), 0);
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
            this.tickCounter = sturz ? -1 : -15;
        }

        @Override
        public Vector computeNextVector() {
            // -g * t Geschwindigkeit nach Zeit t (Sekunden)
            tickCounter++;
            return new Vector(MONKEY_MOVEMENT * getJoystickX(), AnimalRun.GRAVITY * tickCounter);
        }
    }

}
