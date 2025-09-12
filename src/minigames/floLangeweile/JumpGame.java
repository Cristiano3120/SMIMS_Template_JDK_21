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
    static GamePanel gamePanel;
    public static AbstractController controller;
    public static boolean oneDied = false;

    private  static  final int FRAME = 100;
    private static final int WIDTH = 9*40; // 360
    private static final int HEIGHT = 16*40; //640

    private static final int LAYER_DISTENCE = 50;

    public static double generalHeight = 0;
    boolean gameRuns = true;
    int tick = 0;

    public static Set<Platformen> umgebung =  new HashSet<>();
    public static Set<Platformen> umgebungNew =  new HashSet<>();

    public static Set<Entity> objekte = new HashSet<>();
    public static Set<Entity> objekteNew = new HashSet<>();

    //public static double generalHeight = 0;
    //private static int pletformsUntil = 0;

    public static void main(String[] args) {
        var v = new View((int) (WIDTH), HEIGHT);
        var j = new JumpGame(new TastaturController(v), v);
        j.runGame();
    }

    public JumpGame(AbstractController controller, View view) {
        super(controller, view);
        view.setSize(0,0);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH,HEIGHT);
        frame.setResizable(false);
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
        while (gameRuns) {
            umgebungNew.add(new NormPlatform(200, 300));

            for (int i = 0; i < gamePanel.getHeight() / LAYER_DISTENCE; i++) {

                if (Math.random() < 0.5) {
                    summonPlatform(i);
                }
                summonPlatform(i);
            }

            objekte.add(new Player(120, (int) (200), false));
            objekte.add(new Player(240, (int) (200), true));

            long milisPerCycle = 1000 / FRAME;
            long timeStamp = System.currentTimeMillis();
            long timeUntilNextCycle;

            while (!oneDied ) {
                timeStamp = System.currentTimeMillis();
                update(tick);
                tick++;
                if (tick > 2147483640) tick = 0;
                timeUntilNextCycle = milisPerCycle - (System.currentTimeMillis() - timeStamp);
                if (timeUntilNextCycle < 0) timeUntilNextCycle = 0;
                try {
                    //Thread.sleep(timeUntilNextCycle);
                    Thread.sleep(timeUntilNextCycle);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            umgebung.forEach( e -> e = null);
            umgebung.clear();
            objekte.forEach( e -> e = null);
            objekte.clear();

            oneDied = false;
            tick = 0;
            System.out.println("Test");
            while (!controller.getLinksA()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("TestTTTTTTT");

        }

    }
    private void summonPlatform( int height){
        int x = (int) (Math.random() * gamePanel.getWidth() /Platformen.WIDTH);
        umgebungNew.add(new NormPlatform(x*Platformen.WIDTH, (int) (height*LAYER_DISTENCE - generalHeight)));
        System.out.println("Summon Platform" + (height*LAYER_DISTENCE - generalHeight) + " " + (height*LAYER_DISTENCE ));
    }

    private void update(int tick) {
        umgebung.addAll(umgebungNew);
        umgebungNew.clear();
        objekte.addAll(objekteNew);
        objekteNew.clear();
        ///  ////
        if(tick % 2 == 0){
            JumpGame.generalHeight++;
        }
        System.out.println("Test: " + !umgebung.stream().anyMatch(u -> u.bounds.getY() + generalHeight < 100));
        if(!umgebung.stream().anyMatch(u -> u.bounds.getY() + generalHeight < LAYER_DISTENCE)) {
            summonPlatform(0);
            System.out.println("TEstEEEEEEEEEEEEEEEe");
        }

        objekte.forEach(objekt -> objekt.doThings(tick));

        /// ////
        gamePanel.repaint();
    }

//    private void summonPlatforms(){
//
//    }

    public static GamePanel getGamePanel() {
        return gamePanel;
    }
}
