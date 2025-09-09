package minigames.animalRun;

import controller.AbstractController;
import sas.Picture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Monkey extends Picture {

    /* Static Variables */
    protected static double MONKEY_MOVEMENT = 5.0;
    protected static int baseLevelX = 0;

    /* Object Variables */
    private AbstractController controller;
    private String[]monkey1Pictures = new String[]{getDynamicFilePath("monkey1.jpeg"), getDynamicFilePath("monkey2.jpeg"), getDynamicFilePath("monkey3.jpeg"), getDynamicFilePath("monkey4.jpeg")};
    private String[]monkey2Pictures = new String[]{getDynamicFilePath("monkey5.jpeg"), getDynamicFilePath("monkey6.jpeg"), getDynamicFilePath("monkey7.jpeg"), getDynamicFilePath("monkey8.jpeg")};
    private boolean isMonkey1;

    /* Constructors */
    public Monkey(double yp, double w, double h, String name, AbstractController controller, boolean isMonkey1) {
        super(baseLevelX, yp, w, h, "C:\\Users\\Admin\\Downloads\\SMIMS_Template_JDK_21\\resources\\animalrun\\monkey1.jpeg");
        this.controller = controller;
        this.isMonkey1 = isMonkey1;

        while (true) {
            monkeyMove();
        }
    }

    private String getDynamicFilePath(String fileName) {
        return "C:\\Users\\Admin\\Downloads\\SMIMS_Template_JDK_21\\resources\\animalrun\\" + fileName;
    }

    /* Object Methods */
    public void monkeyMove(){
        if (isMonkey1){
            String tempPicture = monkey1Pictures[0];
            System.out.println("Joystick: " + controller.getJoystickLinksY());
            move(MONKEY_MOVEMENT * controller.getJoystickRechtsX());
            setImageByString(monkey1Pictures[0]);

            for (int g = 1; g < 3; g++){
                monkey1Pictures[g-1] = monkey1Pictures[g];
            }
            monkey1Pictures[3] = tempPicture;
        } else{
            String tempPicture = monkey2Pictures[0];
            move(MONKEY_MOVEMENT * controller.getJoystickLinksX());
            setImageByString(monkey2Pictures[0]);
            for (int g = 1; g < 4; g++){
                monkey2Pictures[g-1] = monkey2Pictures[g];
            }
            monkey2Pictures[4] = tempPicture;
        }

    }

    public void monkeyJump(boolean isMonkey1){
        if(isMonkey1){
            double velocity = 0;
            if ( controller.getRechtsA()){
                velocity = 5;
                while(velocity > -5){
                    move(MONKEY_MOVEMENT * controller.getJoystickRechtsY(), velocity);
                }

            }
        }else{
            double velocity = 0;
            if ( controller.getRechtsA()){
                velocity = 5;
                while(velocity > -5){
                    move(MONKEY_MOVEMENT * controller.getJoystickLinksY(), velocity);
                }
        }   }

    }

    void setImageByString(String str){
        try {
            Picture picture = new Picture(200, 200, str);
            BufferedImage bufferedImage = ImageIO.read(new File(str));
            //setImage(bufferedImage);
           // setSize(bufferedImage.getWidth(), bufferedImage.getHeight());
            super.loadTexture(str);
            super.setImage(bufferedImage);
            //bImage = ImageIO.read(new File(str));
            //this.setImage(bImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
