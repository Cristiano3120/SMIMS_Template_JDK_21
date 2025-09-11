package main;

import controller.ArduinoController;
import controller.arduinoSendData.LedData;

public class CristianoTest {

    public static void main(String[] args) {
        ArduinoController arduinoController = new ArduinoController();

        LedData ledData1 = new LedData(255, 198, 207, true);
        LedData ledData2 = new LedData(0, 0, 255, false);

        arduinoController.sendeDaten(ledData1);
        arduinoController.sendeDaten(ledData2);
    }

}
