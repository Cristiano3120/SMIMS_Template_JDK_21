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
    private  EmpfangendeDaten empfangendeDaten;


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

                int newLineIndex;
                while ((newLineIndex = buffer.indexOf("\n")) != -1) {
                    String line = buffer.substring(0, newLineIndex).trim();
                    buffer.delete(0, newLineIndex +1);

                    if (!line.isEmpty())
                    {
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

        Gson gson = new Gson();
        empfangendeDaten =  gson.fromJson(json, EmpfangendeDaten.class);
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
        return (empfangendeDaten.links.X - 512) / 512 ;
    }

    @Override
    public double getJoystickLinksY() {
        return (empfangendeDaten.links.Y - 512) / 512 ;
    }
    @Override
    public double getJoystickRechtsX() {
        return (empfangendeDaten.rechts.X - 512) / 512 ;
    }

    @Override
    public double getJoystickRechtsY() {
        return (empfangendeDaten.rechts.Y - 512) / 512 ;
    }

    @Override
    public boolean getLinksA() {
        return empfangendeDaten.links.A == 1;
    }

    @Override
    public boolean getLinksB() {
        return empfangendeDaten.links.B == 1;
    }

    @Override
    public boolean getLinksC() {
        return empfangendeDaten.links.C == 1;
    }

    @Override
    public boolean getLinksD() {
        return empfangendeDaten.links.D == 1;
    }

    @Override
    public boolean getRechtsA() {
        return empfangendeDaten.rechts.A == 1;
    }

    @Override
    public boolean getRechtsB() {
        return empfangendeDaten.rechts.B == 1;
    }

    @Override
    public boolean getRechtsC() {
        return empfangendeDaten.rechts.C == 1;
    }

    @Override
    public boolean getRechtsD() {
        return empfangendeDaten.rechts.D == 1;
    }
    /* Inner Classes */

}
