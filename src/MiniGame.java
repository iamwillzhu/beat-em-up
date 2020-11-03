import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;  
import javafx.animation.Timeline;
import javafx.util.Duration;

public class MiniGame extends Application {

    static final int W_WIDTH = 1280;
    static final int W_HEIGHT = 640;
    public static final int FRAMES_PER_SECOND = 30;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

    // We don't need a main method, since JavaFX runs from start() below.

    @Override
    public void start(Stage stage) {
        Game game = new Game(stage);
        Scene scene = game.init(W_WIDTH, W_HEIGHT);
        stage.setTitle(game.getTitle());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        // sets the game's loop
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> game.step());
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

    }
}
