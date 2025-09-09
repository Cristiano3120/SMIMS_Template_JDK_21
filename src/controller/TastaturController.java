package controller;

import sas.View;

/**
 * Die Klasse <code>{@link TastaturController}</code> dient lediglich als Platzhalter während der Programmierphase,
 * solange Joystick, Gamepad, ... noch nicht fertiggestellt sind. Damit die anderen Teams programmieren und ihre Spiele
 * auch steuern können, wird übergangsweise die Tastatur zur Eingabe verwendet. Später kann man dann einfach zur
 * Joystick-/Gamepad-/... Steuerung wechseln, indem man schlicht die verwendete Klasse ändert.
 */
public class TastaturController extends AbstractController {

    /* Static Variables */

    /* Static Methods */

    /* Object Variables */
    private View view;

    /* Constructors */
    public TastaturController(View view) {
        this.view = view;
    }

    /* Object Methods */
    @Override
    protected void werteDatenAus(String json) {
        // Hier muss nichts getan werden.
    }

    @Override
    public void disconnect() {
        // Hier muss nichts getan werden.
    }

    /* Getters and Setters */

    /**
     * In dieser Beispielmethode wird gezeigt, wie man mithilfe der Tastatureingabe (Pfeiltaste links und / oder
     * Pfeiltaste rechts) das Joystick- bzw. Gamepad-Input simulieren könnte.
     *
     * @return
     */
    @Override
    public double getJoystickLinksX() {

        double x = 0.0;

        if (view.keyPressed('A')) {
            x += -1d;
        }

        if (view.keyPressed('D')) {
            x += 1d;
        }

        return x;
    }

    @Override
    public double getJoystickLinksY() {
        double y = 0.0;

        if (view.keyPressed('W')) {
            y += -1d;
        }

        if (view.keyDownPressed()) {
            y += 1d;
        }

        return y;
    }

    public double getJoystickRechtsX() {

        double x = 0.0;

        if (view.keyLeftPressed()) {
            x += -1d;
        }

        if (view.keyRightPressed()) {
            x += 1d;
        }

        return x;
    }

    @Override
    public double getJoystickRechtsY() {
        double y = 0.0;

        if (view.keyUpPressed()) {
            y += -1d;
        }

        if (view.keyDownPressed()) {
            y += 1d;
        }

        return y;
    }

    @Override
    public boolean getLinksA() {
        return view.keyPressed('1');
    }

    @Override
    public boolean getLinksB() {
        return view.keyPressed('1');
    }

    @Override
    public boolean getLinksC() {
        return view.keyPressed('3');
    }
    @Override
    public boolean getLinksD() {
        return view.keyPressed('4');
    }

    @Override
    public boolean getRechtsA() {
        return view.keyPressed('7');
    }

    @Override
    public boolean getRechtsB() {
        return view.keyPressed('8');
    }

    @Override
    public boolean getRechtsC() {
        return view.keyPressed('9');
    }

    @Override
    public boolean getRechtsD() {
        return view.keyPressed('0');
    }
    /* Inner Classes */

}
