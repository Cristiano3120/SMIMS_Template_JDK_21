package minigames.pong;
import controller.AbstractController;
import controller.ArduinoController;
import controller.TastaturController;
import sas.*;
import java.awt.Color;

public class PongNico {

    View view;
    TastaturController controller;
    ArduinoController  arduinoController;
    Rectangle pong1;
    Rectangle pong2;
    Circle ball;
    double n = 1;
    double a = 0;
    double abprallwinkel = 0;
    double treffstelle = 0;
    Text player1ScoreText;
    Text player2ScoreText;
    int player1Score;
    int player2Score;
    Rectangle player1ScoreRect;
    Rectangle player2ScoreRect;

    public PongNico(View view, TastaturController controller) {

        this.view = view;
        this.controller = controller;
        arduinoController = new ArduinoController();

        view.setBackgroundColor(Color.gray);

        pong1 = new Rectangle(0, 0, view.getWidth()/100*2, view.getHeight()/12*3, new Color(41,49,51));
        pong2 = new Rectangle(0, 0, view.getWidth()/100*2, view.getHeight()/12*3, new Color(41,49,51));



        int offset = 20;
        player1ScoreText = new Text(offset, 0, "0");
        player2ScoreText = new Text(view.getWidth() - offset, 0, "0");

        ball = new Circle(0, 0, (view.getHeight()/2)/20, new Color(255,255,255));

        init();
    }

    private void init(){
        pong1.moveTo(view.getWidth()/10*2, view.getHeight()/2-pong1.getShapeHeight()/2);
        pong2.moveTo(view.getWidth()/10*8, view.getHeight()/2-pong2.getShapeHeight()/2);

        ball.moveTo(view.getWidth()/2-ball.getShapeWidth()/2, view.getHeight()/2-ball.getShapeHeight()/2);
    }

    public void doThat() {

        if (controller.getLinksA() || arduinoController.getJoystickLinksY() < 512 && pong1.getShapeY() > 0) {
            pong1.move(0, -8);
        }

        if (controller.getLinksB() || arduinoController.getJoystickLinksY() > 512 && pong1.getShapeY() + pong1.getShapeHeight() < view.getHeight()) {
            pong1.move(0, 8);
        }
        if (controller.getRechtsC() || arduinoController.getJoystickRechtsX() < 512 && pong2.getShapeY() > 0) {
            pong2.move(0, -8);
        }
        if (controller.getRechtsD() || arduinoController.getJoystickLinksY() > 512 &&  pong2.getShapeY() + pong2.getShapeHeight() < view.getHeight()) {
            pong2.move(0, 8);
        }

        ball.move(n, a);

        if (n < 0 && n > 200) {
            n = n - 0.001;
        } else if (n > 0 && n < 200) {
            n = n + 0.0025;
        }

        if (ball.intersects(pong1) || ball.intersects(pong2)) {
            n = n * -1;
        }

        if (ball.intersects(pong1)) {
            treffstelle = pong1.getCenterY() - ball.getCenterY();
        } else {
            treffstelle = pong2.getCenterY() - ball.getCenterY();
        }

        abprallwinkel = treffstelle / 10 * (100 / 89) * -1;

        if (ball.intersects(pong1) || ball.intersects(pong2)) {
            a = (100 / 45) * abprallwinkel;
        }

        if (ball.getShapeY() < 0 || ball.getShapeY() + ball.getShapeHeight() > view.getHeight()) {
            a = a * -1;
        }
    }
    public boolean pongWin() {
        if (ball.getShapeX() < 0 || ball.getShapeX() + ball.getShapeWidth() > view.getWidth()) {
            pong1.setHidden(true);
            pong2.setHidden(true);
            ball.setHidden(true);

            view.remove(pong1);
            view.remove(pong2);
            view.remove(ball);

            if (ball.getShapeX() < 0) {
                player1ScoreText.setText(String.valueOf(++player1Score));
            }

            if (ball.getShapeX() + ball.getShapeWidth() > view.getWidth()) {
                player2ScoreText.setText(String.valueOf(++player1Score));
            }

            return false;
        }
        return true;
    }
}
