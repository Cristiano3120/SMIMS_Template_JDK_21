package controller;

import controller.arduinoReceiveData.EmpfangendeDaten;
import controller.arduinoSendData.LedData;
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
    private String data;
    private EmpfangendeDaten empfangendeDaten = new EmpfangendeDaten();

    boolean ersterLauf = true;
    double offSetLinksX = 0;
    double offSetLinksY = 0;
    double offSetRechtsX = 0;
    double offSetRechtsY = 0;

    /* Constructors */
    public ArduinoController() {
        this.data = "";
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

            werteDatenAus(null);

        } catch (SerialPortException ex) {
            System.out.println(ex);
        }


//        try {
//            String incoming = serialPort.readString();
//
//            if (incoming != null) {
//                buffer.append(incoming);
//
//                int newLineIndex;
//                while ((newLineIndex = buffer.indexOf("\n")) != -1) {
//                    System.out.println("> " + newLineIndex);
//                    String line = buffer.substring(0, newLineIndex).trim();
//                    buffer.delete(0, newLineIndex +1);
//                    System.out.println(">>"+line);
//                    if (!line.isEmpty())
//                    {
//                        werteDatenAus(line);
//                    }
//                }
//            }
//        } catch (SerialPortException ex) {
//            ex.printStackTrace();
//        }

    }

    @Override
    public void sendeDaten(LedData ledData) {
        Gson gson = new Gson();
        String json = gson.toJson(ledData);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            serialPort.writeString(json + "\n");
        } catch (SerialPortException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void werteDatenAus(String json) {

        json = data;

        if (data == null) {
            return;
        }

//        System.out.println(data);

        if (!data.contains("}}")) {
            return;
        }

        try {
            json = data.substring(data.indexOf('{'), data.indexOf("}}")+2);
            Gson gson = new Gson();
            empfangendeDaten = gson.fromJson(json, EmpfangendeDaten.class);
            readJoystickOffset();

        } catch (Exception ex) {
            System.out.println("ERROR: " + json);
        } finally {
            data = "";
        }

    }

//        try {
//
//            int newLineIndex;
//            while ((newLineIndex = buffer.indexOf("\n")) != -1) {
//                String line = buffer.substring(0, newLineIndex).trim();
//                buffer.delete(0, newLineIndex + 1);
//                if (!line.isEmpty()) {
//                    werteDatenAus(line);
//                }
//
//            }
//        } catch (SerialPortException ex) {
//            ex.printStackTrace();
//        }
//
//        try {
//            Gson gson = new Gson();
//            empfangendeDaten = gson.fromJson(json, EmpfangendeDaten.class);
//            readJoystickOffset();
//        } catch (Exception ex) {
//            System.out.println("ERROR: " + json);
//        }
//    }

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

    private void readJoystickOffset() {
        if (ersterLauf) {
            ersterLauf = false;

            offSetLinksX = empfangendeDaten.getLinks().X - 512;
            offSetLinksY = empfangendeDaten.getLinks().Y - 512;
            offSetRechtsX = empfangendeDaten.getRechts().X - 512;
            offSetRechtsY = empfangendeDaten.getRechts().Y - 512;
        }
    }

    /* Getters and Setters */
    @Override
    public double getJoystickLinksX() {
        double value = (empfangendeDaten.getLinks().X - 512 - offSetLinksX) / 512;
        if (Math.abs(value) > 1) value = value / Math.abs(value);
        return -value;
    }

    @Override
    public double getJoystickLinksY() {
        double value = (empfangendeDaten.getLinks().Y - 512 - offSetLinksY) / 512;
        if (Math.abs(value) > 1) value = value / Math.abs(value);
        return value;
    }

    @Override
    public double getJoystickRechtsX() {
        double value = (empfangendeDaten.getRechts().X - 512 - offSetRechtsX) / 512;
        if (Math.abs(value) > 1) value = value / Math.abs(value);
        return -value;
    }

    @Override
    public double getJoystickRechtsY() {
        double value = (empfangendeDaten.getRechts().Y - 512 - offSetRechtsY) / 512;
        if (Math.abs(value) > 1) value = value / Math.abs(value);
        return value;
    }

    @Override
    public boolean getLinksA() {
        return empfangendeDaten.getLinks().A == 1;
    }

    @Override
    public boolean getLinksB() {
        return empfangendeDaten.getLinks().A == 1;
    }

    @Override
    public boolean getLinksC() {
        return empfangendeDaten.getLinks().C == 1;
    }

    @Override
    public boolean getLinksD() {
        return empfangendeDaten.getLinks().D == 1;
    }

    @Override
    public boolean getRechtsA() {
        return empfangendeDaten.getRechts().A == 1;
    }

    @Override
    public boolean getRechtsB() {
        return empfangendeDaten.getRechts().B == 1;
    }

    @Override
    public boolean getRechtsC() {
        return empfangendeDaten.getRechts().C == 1;
    }

    @Override
    public boolean getRechtsD() {
        return empfangendeDaten.getRechts().D == 1;
    }
    /* Inner Classes */

}
