package main;

import controller.ArduinoController;
import controller.arduinoSendData.LedData;

public class Main {

    public static void main(String[] args) {
        ArduinoController arduinoController = new ArduinoController();
        while (true) {
            arduinoController.sendeDaten(new LedData(20, 20, 20, true));
        }
    }
}