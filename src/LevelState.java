import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class LevelState extends State {

    private static final double PLAYER_Y_POSITION = 320;
    private static final double PLAYER_X_POSITION = 527.5;
    private static final double MONSTER_Y_POSITION = 360;
    private static final double MONSTER_STARTING_RIGHT_POSITION = 1300;
    private static final double MONSTER_STARTING_LEFT_POSITION = -250;
    private static final double MONSTER_ENDING_RIGHT_POSITION = -600;
    private static final double MONSTER_ENDING_LEFT_POSITION = 620;
    private static final double DEFAULT_MONSTER_SPEED = 6000;
    private static final double KNOCKBACK_DISTANCE = 60;
    private static final double KNOCKBACK_SPEED = 10;
    private static final boolean PLAYER_LEFT = true;
    private static final boolean PLAYER_RIGHT = false;
    private static final int DEFAULT_TOTAL_MONSTERS = 5;
    private static final int SPAWN_INTERVAL = 4000;
    private static final long THRESHOLD = 250_000_000L;
    private static final int W_WIDTH = 1280;
    private static final int W_HEIGHT = 640;
    private static final int BIG_FONT = 30;


    private Game game;
    private InputHandler inputHandler;
    private Group root;
    private int level;
    private Player player;
    private ArrayList<Monster> monsters;
    private double monsterSpeed;
    private Label health;
    private Label points;
    private Label enemiesRemaining;
    private long leftLastMoveNanos;
    private long rightLastMoveNanos;
    private int totalMonsters;
 //   private Timer timer;


    public LevelState(Game currentGame, int currentLevel, int currentScore) {
        game = currentGame;
        level = currentLevel;
        monsters = new ArrayList<Monster>();
//        timer = new Timer();
        inputHandler = game.getInputHandler();
        root = game.getRoot();
        root.getChildren().clear();
        monsterSpeed = DEFAULT_MONSTER_SPEED  - currentLevel * 1500;
        leftLastMoveNanos = 0L;
        rightLastMoveNanos = 0L;
        totalMonsters = DEFAULT_TOTAL_MONSTERS * currentLevel;

        // Create background
        Image backgroundImage = new Image(getClass().getResourceAsStream("resources/img/background2.png"));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        root.getChildren().add(backgroundImageView);

        // Create player
        Image playerImage = new Image(getClass().getResourceAsStream("resources/img/fire.png"));
        ImageView playerImageView = new ImageView(playerImage);
        playerImageView.setX(PLAYER_X_POSITION);
        playerImageView.setY(PLAYER_Y_POSITION);
        root.getChildren().add(playerImageView);
        player = new Player(game, playerImageView, level, currentScore);

        generateMonsters();

        // Indicators
        health = new Label("\u2764".repeat(player.getHealth()));
        points = new Label("Score: " +Integer.toString(player.getPoints()));
        enemiesRemaining = new Label("Enemies Remaining: " + monsters.size());
        health.setFont(Font.font("Comic Sans MS",BIG_FONT));
        points.setFont(Font.font("Comic Sans MS", BIG_FONT));
        enemiesRemaining.setFont(Font.font("Comic Sans MS", BIG_FONT));
        health.setTextFill(Color.web("#f8faf7"));
        points.setTextFill(Color.web("#f8faf7"));
        enemiesRemaining.setTextFill(Color.web("#f8faf7"));
        StackPane pane = new StackPane(health, points, enemiesRemaining);
        root.getChildren().add(pane);
        pane.setPrefSize(W_WIDTH, W_HEIGHT);
        pane.setAlignment(health, Pos.BOTTOM_CENTER);
        pane.setAlignment(points, Pos.BOTTOM_LEFT);
        pane.setAlignment(enemiesRemaining, Pos.BOTTOM_RIGHT);
    }

    @Override
    public void update() {
        long now = System.nanoTime();
        if ((leftLastMoveNanos <= 0L && inputHandler.isLeftPressed()) ||
                (now - leftLastMoveNanos > THRESHOLD && inputHandler.isLeftPressed())){
            player.moveAndFire(PLAYER_LEFT);
            leftLastMoveNanos = now;
        }
        if ((rightLastMoveNanos <= 0L && inputHandler.isRightPressed()) ||
                (now - rightLastMoveNanos > THRESHOLD && inputHandler.isRightPressed())){
            player.moveAndFire(PLAYER_RIGHT);
            rightLastMoveNanos = now;
        }
        collisionFireball();
        // update indicators
        if (player.getHealth() == 0) {

            game.updateState(new EndState(game, player.getPoints()));
        }
        if (monsters.size() == 0) {

            game.updateState(new EndState(game, player.getPoints(), level));
        }
        health.setText("\u2764".repeat(player.getHealth()));
        points.setText("Score: " +Integer.toString(player.getPoints()));
        enemiesRemaining.setText("Enemies Remaining: " + monsters.size());
    }

    public void generateMonsters() {
        Random random = new Random();
        int delayTime = 0;
        int startingPosition;

        for (int i = 0; i < totalMonsters; i++) {
            Timeline timeline = new Timeline();
            Image monsterImage = new Image(getClass().getResourceAsStream("resources/img/enemy.png"));
            ImageView monsterImageView = new ImageView(monsterImage);
            monsterImageView.setY(MONSTER_Y_POSITION);
            startingPosition = random.nextInt(2);
            if (startingPosition == 1) {
                monsterImageView.setX(MONSTER_STARTING_RIGHT_POSITION);
                timeline.getKeyFrames().addAll(
                        new KeyFrame(new Duration(monsterSpeed),
                                new KeyValue(monsterImageView.translateXProperty(),
                                        MONSTER_ENDING_RIGHT_POSITION)));

            } else {
                monsterImageView.setX(MONSTER_STARTING_LEFT_POSITION);
                monsterImageView.setScaleX(-1);
                timeline.getKeyFrames().addAll(
                        new KeyFrame(new Duration(monsterSpeed),
                                new KeyValue(monsterImageView.translateXProperty(),
                                        MONSTER_ENDING_LEFT_POSITION)));
            }
            Monster monster = new Monster(game, monsterImageView, timeline);
            timeline.setDelay(new Duration(delayTime));
            int finalStartingPosition = startingPosition;
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    player.takeHit();
                    timeline.getKeyFrames().clear();
                    timeline.setDelay(Duration.ZERO);
                    if (finalStartingPosition == 1) {
                        timeline.getKeyFrames().addAll(
                                new KeyFrame(new Duration(monsterSpeed / KNOCKBACK_SPEED),
                                        new KeyValue(monsterImageView.translateXProperty(),
                                                MONSTER_ENDING_RIGHT_POSITION + KNOCKBACK_DISTANCE)),
                                new KeyFrame(new Duration(2 * monsterSpeed / KNOCKBACK_SPEED),
                                        new KeyValue(monsterImageView.translateXProperty(),
                                                MONSTER_ENDING_RIGHT_POSITION)));
                    } else {
                        timeline.getKeyFrames().addAll(
                                new KeyFrame(new Duration(monsterSpeed / KNOCKBACK_SPEED),
                                        new KeyValue(monsterImageView.translateXProperty(),
                                                MONSTER_ENDING_LEFT_POSITION - KNOCKBACK_DISTANCE)),
                                new KeyFrame(new Duration(2 * monsterSpeed / KNOCKBACK_SPEED),
                                        new KeyValue(monsterImageView.translateXProperty(),
                                                MONSTER_ENDING_LEFT_POSITION)));
                    }
                    timeline.play();

                }
            });
            monsters.add(monster);
            monster.spawn();
            delayTime = random.nextInt(SPAWN_INTERVAL) + delayTime + 250;
        }
    }
    public void collisionFireball() {
        ArrayList<Fireball> fireballs = player.getFireballs();
        ArrayList<Fireball> fireballsCopy = new ArrayList<Fireball>(fireballs);
        ArrayList<Monster> monstersCopy = new ArrayList<Monster>(monsters);

        for (Fireball fireball: fireballsCopy) {
            ImageView fireballImage = fireball.getImage();
            for (Monster monster: monstersCopy) {
                ImageView monsterImage = monster.getImage();

                if (fireballImage.getBoundsInParent().intersects(monsterImage.getBoundsInParent())) {
                    player.killedEnemy();
                    monster.getTimeline().stop();
                    fireball.getTimeline().stop();
                    root.getChildren().remove(fireballImage);
                    root.getChildren().remove(monsterImage);
                    fireballs.remove(fireball);
                    monsters.remove(monster);
                    AudioClip audio = new AudioClip(getClass().getResource("resources/sound/kill_sound.mp3").toExternalForm());
                    audio.play();
                    break;
                }
            }

        }
    }
}