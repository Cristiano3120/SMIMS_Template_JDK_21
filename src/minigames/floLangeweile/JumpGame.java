package minigames.floLangeweile;

import controller.AbstractController;
import minigames.AbstractGame;
import sas.View;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JumpGame extends AbstractGame {

    private  static  final int FRAME = 60;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 700;
    boolean gameRuns = true;
    int tick = 0;

    Set umgebung =  new HashSet();
    Set objekte = new HashSet();

    public JumpGame(AbstractController controller, View view) {
        super(controller, view);
        view.setSize(0,0);
        GamePanel gamePanel = new GamePanel();
    }

    @Override
    protected void initView() {
        view.setSize(WIDTH, HEIGHT);
        view.setName("SMIMS Frogger");
    }

    @Override
    protected void runGame() {

        long milisPerCycle = 1000 / FRAME;
        long timeStamp = System.currentTimeMillis();
        long timeUntilNextCycle;

        while (gameRuns) {
            timeStamp = System.currentTimeMillis();
            update(tick);
            tick++;
            timeUntilNextCycle = milisPerCycle - (System.currentTimeMillis() - timeStamp);
            if (timeUntilNextCycle < 0) timeUntilNextCycle = 0;
            try {
                //Thread.sleep(timeUntilNextCycle);
                view.wait(timeUntilNextCycle);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void update(int tick) {

    }
}
