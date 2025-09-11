package common;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * <p>Dies ist die mir einfachste Klasse, um Sounds bzw. Musik ins Spiel zu bekommen. Es handelt sich um einen Hintergrund-
 * thread, damit das Spiel durch die Wiedergabe nicht blockiert wird. Verwendung:</p>
 * <ul>
 *     <li><code>SoundThread</code>-Objekt erstellen</li>
 *     <li>wenn <code>loop = true</code>, dann wird die Datei automatisch wiederholt (z.B. für Hintergrundmusik)</li>
 *     <li>{@link #start()}-Methode ausführen</li>
 *     <li>ggf. mit {@link #stopPlayer()} frühzeitig beenden</li>
 * </ul>
 */
public class SoundThread extends Thread {

    private FileInputStream inputStream;
    private String file;
    private Player player;
    private boolean loop;

    /**
     * Dieser Konstruktor erstellt ein neues Objekt vom Typ <code>SoundThread</code>.
     *
     * @param file   das zu verwendende sound file
     * @param loop   gibt an, ob der Sound unendlich oft wiederholt werden soll.
     * @param daemon gibt an, ob der Player als daemon thread behandelt werden soll. Das bedeutet: Ist der Wert auf
     *               <code>false</code> gesetzt, dann wird das gesamte Programm erst dann beendet, wenn der Track vollständig
     *               durchgelaufen ist. Setzt man hingegen <code>true</code>, dann wird der Thread automatisch 'gekillt',
     *               wenn das Programm beendet wird.
     * @throws FileNotFoundException falls das sound file nicht gefunden werden kann.
     * @throws JavaLayerException    falls das {@link Player}-Objekt nicht erstellt werden kann.
     */
    private SoundThread(String file, boolean loop, boolean daemon) throws FileNotFoundException, JavaLayerException {
        this.file = file;
        this.inputStream = new FileInputStream(file);
        this.player = new Player(inputStream);
        this.loop = loop;
        setDaemon(daemon);
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

    public boolean isFinished() {
        return player.isComplete();
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
                new SoundThread(file, loop, isDaemon()).start();
            } catch (FileNotFoundException | JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }
}