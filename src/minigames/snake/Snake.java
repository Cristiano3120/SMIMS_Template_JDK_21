package minigames.snake;

import sas.Rectangle;

import java.util.LinkedList;

/**
 * @author David Abeln (SMIMS 2024)
 */
public class Snake extends LinkedList<Rectangle> {

    /**
     * This variable indicates the x-position inside the game array.
     */
    private int indexX;
    private int indexY;

    public Snake(int indexX, int indexY) {
        this.indexX = indexX;
        this.indexY = indexY;
    }

    public int getIndexX() {
        return indexX;
    }


    public int getIndexY() {
        return indexY;
    }

    public void setIndexX(int indexX) {
        this.indexX = indexX;
    }

    public void setIndexY(int indexY) {
        this.indexY = indexY;
    }
}
