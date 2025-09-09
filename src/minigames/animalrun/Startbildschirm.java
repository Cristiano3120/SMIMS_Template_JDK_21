package minigames.animalrun;
import minigames.AbstractGame;
import sas.Text;
import sas.Tools;
import sas.View;
import common.ScalablePicture;
import controller.AbstractController;


public class Startbildschirm extends AbstractGame{

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public Startbildschirm(AbstractController controller, View view) {
        super(controller, view);
        ScalablePicture background = new ScalablePicture(0,0,view.getWidth(), view.getHeight(), "resources/animalrun/background.png");
        ScalablePicture pressA_button = new ScalablePicture(200,250, 375, 250, "resources/animalrun/pressA_button.png");

    }

    @Override
    protected void initView() {
        view.setSize(WIDTH, HEIGHT);
        view.setName("Startbildschirm Animalrun");

    }

    @Override
    protected void runGame() {

    }
}
