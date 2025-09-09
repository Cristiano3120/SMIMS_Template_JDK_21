package controller;

import controller.arduinoReceiveData.EmpfangendeDaten;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import com.google.gson.Gson;

public class ArduinoController extends AbstractController implements SerialPortEventListener {

    /* Static Variables */

    /* Static Methods */

    /* Object Variables */
    private String data;
    private SerialPort serialPort;

    /* Constructors */
    public ArduinoController() {
        initSerialPort();
    }

    /* Object Methods */
    @Override
    public void disconnect() {
        try {
            serialPort.closePort();
        } catch (SerialPortException e) {
            System.err.println(e);
        }
    }

    public void serialEvent(SerialPortEvent e) {
        try {
            data = data + serialPort.readString();
            data = data.replaceAll(" ", "");
            data = data.replaceAll("null", "");
            data = data.replaceAll("\n", "");
            data = data.replaceAll("\r", "");

            werteDatenAus();

        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    @Override
    protected void werteDatenAus() {

        System.out.println("data: " + data); // TODO: diese Zeile hier löschen


        EmpfangendeDaten  empfangendeDaten = new EmpfangendeDaten();

        // Sobald wir fertig sind, müssen wir den Inhalt des data-Strings löschen, denn der Controller schreibt
        // hier ununterbrochen neues Input rein. Auf diese Weise würde schnell eine sehr lange Zeichenkette
        // gebildet, in der z.B. alle Analogstick-Bewegungen gespeichert würden, die aber überhaupt nicht mehr
        // relevant sind.
        data = "";
    }

    private void initSerialPort() {
        boolean funktioniert = false;
        for (int i = 21; i > 1 && !funktioniert; i--) {
            funktioniert = true;
            try {
                serialPort = new SerialPort("COM" + i);
                serialPort.openPort();
                serialPort.setParams(9600, 8, 1, 0);
                serialPort.addEventListener(this);
            } catch (SerialPortException ex) {
                funktioniert = false;
            }
        }
    }

    /* Getters and Setters */
    @Override
    public double getJoystickLinksX() {
        return 0;
    }

    @Override
    public double getJoystickLinksY() {
        return 0;
    }

    @Override
    public double getJoystickRechtsX() {
        return 0;
    }

    @Override
    public double getJoystickRechtsY() {
        return 0;
    }

    @Override
    public boolean getLinksA() {
        return false;
    }

    @Override
    public boolean getLinksB() {
        return false;
    }

    @Override
    public boolean getLinksC() {
        return false;
    }

    @Override
    public boolean getLinksD() {
        return false;
    }

    @Override
    public boolean getRechtsA() {
        return false;
    }

    @Override
    public boolean getRechtsB() {
        return false;
    }

    @Override
    public boolean getRechtsC() {
        return false;
    }

    @Override
    public boolean getRechtsD() {
        return false;
    }

    /* Inner Classes */

}
