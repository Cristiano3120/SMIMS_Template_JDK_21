package minigames.pong;
import controller.AbstractController;
import controller.TastaturController;
import sas.*;
import java.awt.Color;

public class PongNico {

    View view;
    TastaturController controller;
    Rectangle pong1;
    Rectangle pong2;
    Circle ball;
    double n = 1;
    double a = 0;
    double abprallwinkel = 0;
    double treffstelle = 0;

    public PongNico(View view, TastaturController controller) {

        this.view = view;
        this.controller = controller;

        view.setBackgroundColor(Color.gray);

        pong1 = new Rectangle(0, 0, view.getWidth()/100*2, view.getHeight()/12*3, new Color(41,49,51));
        pong2 = new Rectangle(0, 0, view.getWidth()/100*2, view.getHeight()/12*3, new Color(41,49,51));

        pong1.moveTo(view.getWidth()/10*2, view.getHeight()/2-pong1.getShapeHeight()/2);
        pong2.moveTo(view.getWidth()/10*8, view.getHeight()/2-pong2.getShapeHeight()/2);

        ball = new Circle(0, 0, (view.getHeight()/2)/20, new Color(255,255,255));

        ball.moveTo(view.getWidth()/2-ball.getShapeWidth()/2, view.getHeight()/2-ball.getShapeHeight()/2);

    }
    public void doThat() {

        if (controller.getLinksA() && pong1.getShapeY() > 0) {
            pong1.move(0, -8);
        }
        if (controller.getLinksB() && pong1.getShapeY() + pong1.getShapeHeight() < view.getHeight()) {
            pong1.move(0, 8);
        }
        if (controller.getRechtsC() && pong2.getShapeY() > 0) {
            pong2.move(0, -8);
        }
        if (controller.getRechtsD() && pong2.getShapeY() + pong2.getShapeHeight() < view.getHeight()) {
            pong2.move(0, 8);
        }

        ball.move(n, a);

        if (n < 0 && n > 200) {
            n = n - 0.001;
        } else if (n > 0 && n < 200) {
            n = n + 0.001;
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
                Text text = new Text(0, 0, "Spieler 1 hat gewonnen!");
                text.moveTo(view.getWidth()/2-text.getShapeWidth()/2, view.getHeight()/2-text.getShapeHeight()/2);
            }

            if (ball.getShapeX() + ball.getShapeWidth() > view.getWidth()) {
                Text text = new Text(0, 0, "Spieler 2 hat gewonnen!");
                text.moveTo(view.getWidth()/2-text.getShapeWidth()/2, view.getHeight()/2-text.getShapeHeight()/2);
            }

            return false;
        }
        return true;
    }
}
