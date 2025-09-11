package controller.arduinoSendData;

public class LedData
{
    public boolean LeftLed;
    public int Red;
    public int Green;
    public int Blue;

    public LedData(int red, int green, int blue, boolean leftLed)
    {
        Red = red;
        Green = green;
        Blue = blue;
        LeftLed = leftLed;
    }
}
