import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game {
    private Stage stage;
    private Scene scene;
    private Group root;
    private State state;
    private InputHandler inputHandler;

    private static final String TITLE = "Monster vs Monsters - Beat'em up";

    public Game (Stage stage){
        this.stage = stage;
    }

    public String getTitle () {
        return TITLE;
    }

    public Group getRoot() {
        return root;
    }


    public Stage getStage(){
        return stage;
    }

    public InputHandler getInputHandler(){
        return inputHandler;
    }

    public Scene init (int width, int height) {
        root = new Group();
        scene = new Scene(root, width, height);
        inputHandler = new InputHandler();

        scene.setOnKeyPressed(keyEvent -> inputHandler.keyPressed(keyEvent));
        scene.setOnKeyReleased(keyEvent -> inputHandler.keyReleased(keyEvent));
        state = new StartState(this);
        return scene;
    }

    public void updateState (State newState) {
        state = newState;
    }

    public void step () {
        state.update();
    }


}
