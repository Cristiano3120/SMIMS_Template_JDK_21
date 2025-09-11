package minigames.snake;

import controller.AbstractController;
import minigames.AbstractGame;
import sas.*;
import sas.Rectangle;
import sasio.Button;

import java.awt.*;
import java.util.Arrays;

/**
 * @author David Abeln (SMIMS 2024)
 */
public class SnakeGame extends AbstractGame {

    private static final int SQR_SIZE = 30;
    private static final int MAP_SIZE = 18;
    private static final int SNAKE_SIZE = 20;
    private static final int TICKS_TO_MOVE = 25;
    private static final int BLOCK_INPUT_TICKS = 3;
    private static final int SCORE_TO_WIN = 20;
    private static final double THRESHOLD_X_LEFT = -100.0; // To avoid 'stick drift'
    private static final double THRESHOLD_X_RIGHT = 100.0;
    private static final double THRESHOLD_Y_DOWN = 100.0;
    private static final double THRESHOLD_Y_UP = -100.0;

    private static Rectangle newSnakeSegment(double x, double y) {
        return new Rectangle(x, y, SNAKE_SIZE, SNAKE_SIZE, Color.YELLOW);
    }

    private boolean died;
    private boolean won;
    private Circle apple;
    private Rectangle[][] fields;
    private Snake snake;
    private Direction lastDirection;
    private int tickCounter;
    private int blockInputCounter;
    private int score;
    private Rectangle scoutRect;
    private boolean grow;

    public SnakeGame(AbstractController controller, View view) {
        super(controller, view);
    }

    @Override
    protected void initView() {

        this.died = false;
        this.won = false;
        this.fields = new Rectangle[MAP_SIZE][MAP_SIZE];
        this.snake = new Snake(5, 5);
        this.lastDirection = Direction.RIGHT;
        this.tickCounter = 0;
        this.blockInputCounter = 0;
        this.score = 0;
        this.scoutRect = newSnakeSegment(0, 0);
        this.grow = false;

        Rectangle background = new Rectangle(0, 0, view.getWidth(), view.getHeight(), new Color(6, 131, 6));

        int offSetX = view.getWidth() / 2 - MAP_SIZE * SQR_SIZE / 2;
        int offSetY = view.getHeight() / 2 - MAP_SIZE * SQR_SIZE / 2;
        Rectangle border = new Rectangle(offSetX - 5, offSetY - 5, MAP_SIZE * SQR_SIZE + 10, MAP_SIZE * SQR_SIZE + 10, Color.BLACK);
        fields = new Rectangle[MAP_SIZE][MAP_SIZE];
        for (int i = 0; i < MAP_SIZE; i += 2) {
            for (int j = 0; j < MAP_SIZE; j += 2) {
                fields[j][i] = new Rectangle(offSetX + j * SQR_SIZE, offSetY + i * SQR_SIZE, SQR_SIZE, SQR_SIZE, new Color(82, 193, 80));
                fields[j + 1][i] = new Rectangle(offSetX + (j + 1) * SQR_SIZE, offSetY + i * SQR_SIZE, SQR_SIZE, SQR_SIZE, new Color(77, 154, 58));
                fields[j][i + 1] = new Rectangle(offSetX + j * SQR_SIZE, offSetY + (i + 1) * SQR_SIZE, SQR_SIZE, SQR_SIZE, new Color(77, 154, 58));
                fields[j + 1][i + 1] = new Rectangle(offSetX + (j + 1) * SQR_SIZE, offSetY + (i + 1) * SQR_SIZE, SQR_SIZE, SQR_SIZE, new Color(82, 193, 80));
            }
        }


        // initialising Snake
        int targetIndexX = snake.getIndexX();
        int targetIndexY = snake.getIndexY();
        double newX, newY;
        for (int i = 0; i < 3; i++) {
            Rectangle targetField = fields[targetIndexX][targetIndexY];
            newX = (SQR_SIZE - SNAKE_SIZE) / 2 + targetField.getShapeX();
            newY = (SQR_SIZE - SNAKE_SIZE) / 2 + targetField.getShapeY();
            snake.add(newSnakeSegment(newX, newY));
            targetIndexX--;
        }
        snake.getFirst().setColor(Color.ORANGE);
        scoutRect.setHidden(true);
        scoutRect.setTransparency(0.01f);


        // food
        apple = new Circle(0, 0, SQR_SIZE / 2.0, Color.RED);
        placeFood();

        // Make sure all elements created here will be removed later.
        for (int i = 0; i < fields.length; i++) {
            shapesToRemove.addAll(Arrays.asList(fields[i]));
        }
        shapesToRemove.add(apple);
        shapesToRemove.addAll(snake);
        shapesToRemove.add(background);
        shapesToRemove.add(border);
        shapesToRemove.add(scoutRect);

    }

    @Override
    protected void runGame() {

        while (!died && !won) {

            // Did we win?
            if (score >= SCORE_TO_WIN) {
                won = true;
                break;
            }

            // Do we have to move the snake by force?
            if (tickCounter >= TICKS_TO_MOVE) {
                move(lastDirection);
                tickCounter = 0;
            }

            tickCounter++;

            // Do we have to move the snake by input?
            double joyX = controller.getJoystickLinksX();
            double joyY = controller.getJoystickLinksY();

            if (blockInputCounter >= 1) {
                blockInputCounter++;
            }
            if (blockInputCounter > BLOCK_INPUT_TICKS) {
                blockInputCounter = 0;
            }

            // Did we receive any input from the player?
            boolean inputReceived = joyX <= THRESHOLD_X_LEFT || joyX >= THRESHOLD_X_RIGHT
                    || joyY >= THRESHOLD_Y_DOWN || joyY <= THRESHOLD_Y_UP;
            boolean inputValid = false;

            if (blockInputCounter == 0 && inputReceived) {


                if (joyX <= THRESHOLD_X_LEFT && lastDirection != Direction.RIGHT) {
                    lastDirection = Direction.LEFT;
                    inputValid = true;
                }
                if (joyX >= THRESHOLD_X_RIGHT && lastDirection != Direction.LEFT) {
                    lastDirection = Direction.RIGHT;
                    inputValid = true;
                }
                if (joyY >= THRESHOLD_Y_DOWN && lastDirection != Direction.UP) {
                    lastDirection = Direction.DOWN;
                    inputValid = true;
                }
                if (joyY <= THRESHOLD_Y_UP && lastDirection != Direction.DOWN) {
                    lastDirection = Direction.UP;
                    inputValid = true;
                }

                if (inputValid) {
                    move(lastDirection);
                    blockInputCounter = 1;
                    tickCounter = 0;
                }

            }

            view.wait(TICK_RATE);
        }


        // The game is over - one way or another.
        if (died) {
            Text gameOver = new Text(view.getWidth() / 2.0 - 20.0, view.getHeight() / 2.0 - 5.0, "You lost!");
            shapesToRemove.add(gameOver);
        }

        if (won) {
            Text win = new Text(view.getWidth() / 2.0 - 20.0, view.getHeight() / 2.0 - 5.0, "You won!");
            shapesToRemove.add(win);
        }

        // Make sure the player can quit the game so give them a button to click.
        Button okBtn = new Button(view.getWidth() / 2 - 75, view.getHeight() / 2 + 30, 150, 30, "Ok!", Color.WHITE);
        while (!okBtn.clicked()) {
            view.wait(TICK_RATE);
        }
        okBtn.setHidden(true);

    }

    void placeFood() {

        int x, y;
        Rectangle field;

        // Try placing the apple on random fields until we finally find one without a snake segment.
        outer:
        while (true) {

            x = Tools.randomNumber(0, fields.length - 1);
            y = Tools.randomNumber(0, fields[0].length - 1);

            field = fields[x][y];

            apple.moveTo(field.getShapeX(), field.getShapeY());
            for (Rectangle segment : snake) {
                if (apple.intersects(segment)) {
                    continue outer;
                }
            }

            return;

        }

    }

    public void move(Direction direction) {

        int targetIndexX = snake.getIndexX() + direction.getX(); // Compute the new horizontal index of the snake.
        int targetIndexY = snake.getIndexY() + direction.getY(); // Compute the new vertical index of the snake.

        if (!checkOutOfBounds(targetIndexX, targetIndexY)) {
            died = true;
            return;
        }

        // Compute the new coordinates of the target location.
        Rectangle targetField = fields[targetIndexX][targetIndexY];
        Rectangle head = snake.getFirst();
        double newX = (SQR_SIZE - head.getShapeWidth()) / 2 + targetField.getShapeX();
        double newY = (SQR_SIZE - head.getShapeHeight()) / 2 + targetField.getShapeY();

        // Move the 'scout' rectangle into position to check for collisions.
        scoutRect.moveTo(newX, newY);
        scoutRect.setHidden(false);

        // Are we growing the snake? If so, the new segment will spawn where the last segment is right now.
        // If not, we can simply ignore the last segment as it will move out of the way in any case.
        Rectangle temp = grow ? null : snake.getLast();


        // Have we bitten our tail?
        try {
            for (Rectangle segment : snake) {

                // Are we skipping the last segment?
                if (segment == temp) {
                    continue;
                }

                // Game over!
                if (scoutRect.intersects(segment)) {
                    died = true;
                    return;
                }

            }
        } finally {
            scoutRect.setHidden(true);
        }

        // Move each segment to its new location successively.
        double prevX = 0, prevY = 0;

        for (Rectangle segment : snake) {
            prevX = segment.getShapeX();
            prevY = segment.getShapeY();

            segment.moveTo(newX, newY);

            newX = prevX;
            newY = prevY;
        }

        // Are we adding a segment?
        if (grow) {
            Rectangle segment = newSnakeSegment(prevX, prevY);
            snake.add(segment);
            shapesToRemove.add(segment);
            grow = false;
        }

        // Update the snake's index.
        snake.setIndexX(targetIndexX);
        snake.setIndexY(targetIndexY);

        // So we're not game over yet. Have we just eaten some food?
        if (head.intersects(apple)) {
            grow = true;
            score++;
            placeFood();
        }

    }

    private boolean checkOutOfBounds(int x, int y) {

        if (x < 0 || x >= fields.length) {
            return false;
        }
        if (y < 0 || y >= fields[0].length) {
            return false;
        }
        return true;
    }

}