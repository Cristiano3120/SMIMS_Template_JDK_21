package common;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SoundThread extends Thread {

    private FileInputStream inputStream;
    private String file;
    private Player player;
    private boolean loop;

    private SoundThread(String file, boolean loop) throws FileNotFoundException, JavaLayerException {
        this.file = file;
        this.inputStream = new FileInputStream(file);
        this.player = new Player(inputStream);
        this.loop = loop;
    }

    public void run() {
        try {
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } finally {
            closeStream();
        }
    }

    public void stopPlayer() {
        player.close();
        closeStream();
    }

    private void closeStream() {

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Do we have to start a new player thread?
        if (loop) {
            try {
                new SoundThread(file, loop).start();
            } catch (FileNotFoundException | JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }
}