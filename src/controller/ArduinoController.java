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
    private SerialPort serialPort;
    private final StringBuilder buffer = new StringBuilder();
    private EmpfangendeDaten empfangendeDaten;

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
            String incoming = serialPort.readString();
            if (incoming != null) {
                buffer.append(incoming);

                int newlineIndex;
                while ((newlineIndex = buffer.indexOf("\n")) != -1) {
                    String line = buffer.substring(0, newlineIndex).trim();
                    buffer.delete(0, newlineIndex + 1);

                    if (!line.isEmpty()) {
                        werteDatenAus(line);
                    }
                }
            }
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    @Override
    protected void werteDatenAus(String json) {
        System.out.println("data: " + json);

        try
        {
            Gson gson = new Gson();
            empfangendeDaten = gson.fromJson(json, EmpfangendeDaten.class);
        }
        catch (Exception ex)
        {

        }
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
