import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class EndState extends State {
    private Game game;
    private int level;
    private int score;

    private static final int BIG_FONT = 30;
    private static final int W_WIDTH = 1280;
    private static final int W_HEIGHT = 640;
    private static final int LEVEL_ONE = 1;
    private static final int LEVEL_TWO = 2;
    private static final int LEVEL_THREE = 3;

    public EndState(Game endGame, int finalScore) {
        game = endGame;
        Group root = game.getRoot();
        level = 0;
        score = finalScore;
        root.getChildren().clear();
        Image backgroundImage = new Image(getClass().getResourceAsStream("resources/img/background2.png"));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        root.getChildren().add(backgroundImageView);

        Label firstLine = new Label("YOU LOSE...");
        Label secondLine = new Label("SCORE: " + Integer.toString(score));
        Label thirdLine = new Label("RESTART GAME - ENTER");
        Label fourthLine = new Label("QUIT - Q");

        ArrayList<Label> lines = new ArrayList<Label>();
        lines.add(firstLine);
        lines.add(secondLine);
        lines.add(thirdLine);
        lines.add(fourthLine);

        for (Label line: lines) {
            line.setTextFill(Color.web("#f8faf7"));
            line.setFont(Font.font("Comic Sans MS",BIG_FONT));
        }

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(firstLine, secondLine, thirdLine, fourthLine);
        StackPane pane = new StackPane(vbox);
        pane.setPrefSize(W_WIDTH, W_HEIGHT);

        root.getChildren().add(pane);
        AudioClip audio = new AudioClip(getClass().getResource("resources/sound/fail.wav").toExternalForm());
        audio.play();

    }

    public EndState(Game endGame, int finalScore, int currentLevel){
        game = endGame;
        level = currentLevel;
        Group root = game.getRoot();
        score = finalScore;

        root.getChildren().clear();
        Image backgroundImage = new Image(getClass().getResourceAsStream("resources/img/background2.png"));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        root.getChildren().add(backgroundImageView);

        Label firstLine = (level != 3) ? new Label("YOU WIN THIS LEVEL!") : new Label("CONGRATULATIONS YOU BEAT THE GAME!");
        Label secondLine = new Label("SCORE: " + Integer.toString(score));
        Label thirdLine = (level == 3) ? new Label("RESTART GAME - ENTER") : new Label("NEXT LEVEL - ENTER");
        Label fourthLine = new Label("QUIT - Q");

        ArrayList<Label> lines = new ArrayList<Label>();
        lines.add(firstLine);
        lines.add(secondLine);
        lines.add(thirdLine);
        lines.add(fourthLine);

        for (Label line: lines) {
            line.setTextFill(Color.web("#f8faf7"));
            line.setFont(Font.font("Comic Sans MS",BIG_FONT));
        }

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(firstLine, secondLine, thirdLine, fourthLine);
        StackPane pane = new StackPane(vbox);
        pane.setPrefSize(W_WIDTH, W_HEIGHT);

        root.getChildren().add(pane);
        AudioClip audio = new AudioClip(getClass().getResource("resources/sound/won.mp3").toExternalForm());
        audio.play();
    }

    public void update(){
        InputHandler inputHandler = game.getInputHandler();
        if (inputHandler.isQPressed()) game.getStage().close();
        if (inputHandler.isEnterPressed() && level == 0) game.updateState(new LevelState(game, LEVEL_ONE, 0));
        if (inputHandler.isEnterPressed() && level == 3) game.updateState(new LevelState(game, LEVEL_ONE, 0));
        if (inputHandler.isEnterPressed() && level == 1) game.updateState(new LevelState(game, LEVEL_TWO, score));
        if (inputHandler.isEnterPressed() && level == 2) game.updateState(new LevelState(game, LEVEL_THREE, score));
    }



}
