package controller;

import controller.arduinoSendData.LedData;

public abstract class AbstractController {

    /* Static Variables */

    /* Static Methods */

    /* Object Variables */

    /* Constructors */
    public AbstractController() {
       super();
    }

    /* Object Methods */
    protected abstract void werteDatenAus(String json);

    protected abstract void sendeDaten(LedData ledData);

    public abstract void disconnect();

    /* Getters and Setters */
    /**
     * A getter for the x-axis of the joystick's analog stick.
     *
     * @return values ranging from <code>-512</code> (all the way left) to, <code>0</code> (neutral position) to
     * <code>511</code> (all the way right).
     */
    public abstract double getJoystickLinksX();

    /**
     * A getter for the y-axis of the joystick's analog stick.
     *
     * @return values ranging from <code>-512</code> (all the way up) to, <code>0</code> (neutral position) to
     * <code>511</code> (all the way down).
     */
    public abstract double getJoystickLinksY();

    public abstract double getJoystickRechtsX();

    public abstract double getJoystickRechtsY();

    public abstract boolean getLinksA();

    public abstract boolean getLinksB();

    public abstract boolean getLinksC();

    public abstract boolean getLinksD();

    public abstract boolean getRechtsA();

    public abstract boolean getRechtsB();

    public abstract boolean getRechtsC();

    public abstract boolean getRechtsD();
    // TODO: Mehr Methoden hinzufügen, z.B. für Buttons o.ä.

    /* Inner Classes */
}