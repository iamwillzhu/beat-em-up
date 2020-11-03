import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class StartState extends State{
    private Game game;
    private InputHandler inputHandler;
    private Stage stage;

    private static final int SMALL_FONT = 20;
    private static final int BIG_FONT = 30;
    private static final int W_WIDTH = 1280;
    private static final int W_HEIGHT = 640;
    private static final int LEVEL_ONE = 1;
    private static final int LEVEL_TWO = 2;
    private static final int LEVEL_THREE = 3;
    public StartState(Game newGame) {
        game = newGame;
        inputHandler = game.getInputHandler();
        stage = game.getStage();
        Group root = game.getRoot();
        Image backgroundImage = new Image(getClass().getResourceAsStream("resources/img/background2.png"));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        root.getChildren().add(backgroundImageView);

        Image logoImage = new Image(getClass().getResourceAsStream("resources/img/logo.png"));
        ImageView logoImageView = new ImageView(logoImage);

        Label firstLine = new Label("CONTROLS:");
        Label secondLine = new Label("PRESS \u2190 OR \u2192 TO FIRE");
        Label thirdLine = new Label("START GAME - ENTER");
        Label fourthLine = new Label("QUIT - Q");

        Label lastLine = new Label("Name: Weiyan Zhu\nStudent Number: 20616848");
        lastLine.setTextFill(Color.web("#f8faf7"));
        lastLine.setFont(Font.font("Comic Sans MS",SMALL_FONT));

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
        vbox.getChildren().addAll(logoImageView, firstLine, secondLine, thirdLine, fourthLine);


        StackPane startStackPane = new StackPane(vbox, lastLine);
        startStackPane.setPrefSize(W_WIDTH, W_HEIGHT);

        startStackPane.setAlignment(logoImageView, Pos.TOP_CENTER);
        startStackPane.setAlignment(lastLine, Pos.BOTTOM_LEFT);

        root.getChildren().add(startStackPane);
    }

    public void update() {
        if(inputHandler.isQPressed()) stage.close();
        if(inputHandler.isEnterPressed()) game.updateState(new LevelState(game, LEVEL_ONE, 0));
        if(inputHandler.isOnePressed()) game.updateState(new LevelState(game, LEVEL_ONE, 0));
        if(inputHandler.isTwoPressed()) game.updateState(new LevelState(game, LEVEL_TWO, 0));
        if(inputHandler.isThreePressed()) game.updateState(new LevelState(game, LEVEL_THREE, 0));
    }
}
