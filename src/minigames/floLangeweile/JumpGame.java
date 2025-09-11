package minigames.floLangeweile;

import controller.AbstractController;
import controller.ArduinoController;
import controller.TastaturController;
import minigames.AbstractGame;
import sas.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JumpGame extends AbstractGame {

    JFrame frame;
    GamePanel gamePanel;
    public static AbstractController controller;

    private  static  final int FRAME = 60;
    private static final int WIDTH = 9*40; // 360
    private static final int HEIGHT = 16*40; //640
    boolean gameRuns = true;
    int tick = 0;

    public static Set<Platformen> umgebung =  new HashSet<>();
    public static Set<Platformen> umgebungNew =  new HashSet<>();

    public static Set<Entity> objekte = new HashSet<>();
    public static Set<Entity> objekteNew = new HashSet<>();

    public static double generalHeight = 0;

    public static void main(String[] args) {
        var v = new View(WIDTH, HEIGHT);
        var j = new JumpGame(new TastaturController(v), v);
        j.runGame();
    }

    public JumpGame(AbstractController controller, View view) {
        super(controller, view);
        view.setSize(0,0);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH,HEIGHT);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        gamePanel = new GamePanel();
        //gamePanel.setVisible(true);
        //gamePanel.setSize(WIDTH,HEIGHT);
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.setVisible(true);

        JumpGame.controller = controller;
    }

    @Override
    protected void initView() {
        view.setSize(WIDTH, HEIGHT);
        view.setName("SMIMS Frogger");
    }

    @Override
    protected void runGame() {

        objekte.add(new Player(120,300,false));
        objekte.add(new Player(240,300, true));

        long milisPerCycle = 1000 / FRAME;
        long timeStamp = System.currentTimeMillis();
        long timeUntilNextCycle;

        while (gameRuns) {
            timeStamp = System.currentTimeMillis();
            update(tick);
            tick++;
            if(tick > 2147483640) tick = 0;
            timeUntilNextCycle = milisPerCycle - (System.currentTimeMillis() - timeStamp);
            if (timeUntilNextCycle < 0) timeUntilNextCycle = 0;
            try {
                //Thread.sleep(timeUntilNextCycle);
                Thread.sleep(timeUntilNextCycle);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void update(int tick) {
        umgebung.addAll(umgebungNew);
        umgebungNew.clear();
        objekte.addAll(objekteNew);
        objekteNew.clear();
        ///  ////

        objekte.forEach(objekt -> objekt.doThings(tick));


        /// ////
        gamePanel.repaint();
    }
}
