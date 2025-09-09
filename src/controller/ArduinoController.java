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
    private  EmpfangendeDaten empfangendeDaten = new EmpfangendeDaten();
    boolean ersterLauf = true;
    double offSetLinksX = 0;
    double offSetLinksY = 0;
    double offSetRechtsX = 0;
    double offSetRechtsY = 0;

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

        try
        {
            Gson gson = new Gson();
            empfangendeDaten =  gson.fromJson(json, EmpfangendeDaten.class);
        }
        catch (Exception ex) {System.out.println("FEHLER: " + json);}
        if(ersterLauf){
            offSetLinksX = empfangendeDaten.links.X - 512;
            offSetLinksY = empfangendeDaten.links.Y - 512;
            offSetRechtsX = empfangendeDaten.links.X - 512;
            offSetRechtsY = empfangendeDaten.links.Y - 512;
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
        double value = (empfangendeDaten.links.X - 512 - offSetLinksX) / 512 ;
        if(Math.abs(value) > 1) value = value / Math.abs(value);
        return value;
    }

    @Override
    public double getJoystickLinksY() {
        double value = (empfangendeDaten.links.Y - 512 - offSetLinksY) / 512 ;
        if(Math.abs(value) > 1) value = value / Math.abs(value);
        return value;
    }
    @Override
    public double getJoystickRechtsX() {
        double value = (empfangendeDaten.rechts.X - 512 - offSetRechtsX) / 512 ;
        if(Math.abs(value) > 1) value = value / Math.abs(value);
        return value;
    }

    @Override
    public double getJoystickRechtsY() {
        double value = (empfangendeDaten.rechts.Y - 512 - offSetRechtsY) / 512 ;
        if(Math.abs(value) > 1) value = value / Math.abs(value);
        return value;
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
