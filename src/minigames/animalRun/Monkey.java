package minigames.animalRun;

import common.ScalablePicture;
import controller.AbstractController;
import minigames.animalRun.Worldobject.WorldObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sas.Circle;
import sas.Shapes;
import sas.View;

import java.awt.*;


public class Monkey extends Circle implements WorldObject {

    private static final Logger log = LoggerFactory.getLogger(Monkey.class);
    /* Static Variables */
    protected static double MONKEY_MOVEMENT = 5.0;
    protected static int baseLevelX = 0;

    /* Object Variables */
    private AbstractController controller;
    private final ScalablePicture[] pictures1 = new ScalablePicture[4];
    private int currentIndex1;
    private boolean isMonkey1;
    private boolean turnedLeft = true;
    private boolean onGround;
    private int animIndex = 0;

    /* Constructors */
    public Monkey(double xp, double yp, AbstractController controller, boolean isMonkey1) {
        super(xp, yp, 0.1);
        this.controller = controller;
        this.isMonkey1 = isMonkey1;
        this.currentIndex1 = 0;
        setHidden(true);

        if (isMonkey1) {
            setupMonkey1(xp, yp);
        } else {
            setupMonkey2(xp, yp);
        }
    }

    public void setupMonkey1(double xp, double yp) {
        for (int i = 1; i <= 4; i++) {
            ScalablePicture scalablePicture = new ScalablePicture(xp, yp, 150, 150, "resources/animalrun/monkey" + (i) + ".png");
            scalablePicture.setHidden(true);

            pictures1[i - 1] = scalablePicture;
        }
    }

    private void setupMonkey2(double xp, double yp) {
        for (int i = 1; i <= 4; i++) {
            ScalablePicture scalablePicture = new ScalablePicture(xp, yp, 150, 150, "resources/animalrun/monkey" + (i + 4) + ".png");
            scalablePicture.setHidden(true);

            pictures1[i - 1] = scalablePicture;
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

        /*
         * alle möglichen Fälle
         * - Bewegung nach rechts
         * - Bewegung nach links
         * - Absprung
         * - im Flug / Fallen
         */

    }

    public void gravity() {
        if (!onGround) {
            for (ScalablePicture scalablePicture : pictures1) {
                scalablePicture.move(0, 1);
            }
        }
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

//    public void monkeyJump() {
//        if (isMonkey1) {
//            double velocity = 0;
//            if (controller.getRechtsA()) {
//                velocity = 200;
//                for (int i = 0; i < pictures1.length; i++) {
//                    pictures1[i].move(MONKEY_MOVEMENT * controller.getJoystickRechtsX() * 20, -velocity);
//                }
//                view.wait(200);
//                for (int i = 0; i < pictures1.length; i++) {
//                    pictures1[i].move(MONKEY_MOVEMENT * controller.getJoystickRechtsX() * 20, velocity);
//                }
//            }
//        } else {
//            double velocity = 0;
//        if (controller.getLinksA()) {
//            velocity = 200;
//            for (int i = 0; i < pictures1.length; i++) {
//                pictures1[i].move(MONKEY_MOVEMENT * controller.getJoystickLinksX() * 20, -velocity);
//            }
//            view.wait(200);
//            for (int i = 0; i < pictures1.length; i++) {
//                pictures1[i].move(MONKEY_MOVEMENT * controller.getJoystickLinksX() * 20, velocity);
//            }
//        }
//        }
//
//    }


    @Override
    public void doThings(int tick) {
        monkeyMove();
//        monkeyJump(); TODO
        gravity();

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
