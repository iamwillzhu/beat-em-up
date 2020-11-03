import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class Player {

    private Game game;
    private int health;
    private int points;
    private ImageView playerImage;
    private ArrayList<Fireball> fireballs;
    private double fireballSpeed;
    private double fireballReach;

    final static int PLAYER_FACE_LEFT = 1;
    final static int PLAYER_FACE_RIGHT = -1;
    final static int FIREBALL_FACE_RIGHT = 1;
    final static int FIREBALL_FACE_LEFT = -1;
    final static double FIREBALL_SPEED = 2000;
    final static double FIREBALL_REACH = 600;
    static final int FIREBALL_OFFSET = 130;
    static final int FRAMES_PER_SECOND = 30;

    public int getHealth() {
        return health;
    }

    public int getPoints(){
        return points;
    }

    public ImageView getImage(){
        return playerImage;
    }

    public ArrayList<Fireball> getFireballs(){
        return fireballs;
    }

    public void takeHit() {
        health -= 1;
    }

    public void killedEnemy() {
        points += 1;
    }

    public Player(Game currentGame, ImageView image, double level, int currentScore) {
        game = currentGame;
        playerImage = image;
        fireballs = new ArrayList<Fireball>();
        fireballReach = FIREBALL_REACH - level * 150;
        fireballSpeed = FIREBALL_SPEED - FIREBALL_SPEED * (double) level/4;

        health =  3;
        points = currentScore;
    }

    public void moveAndFire(boolean isFacingLeft) {
        Timeline timeline = new Timeline();
        Image fireballImage = new Image(getClass().getResourceAsStream("resources/img/fireball2.gif"));
        ImageView fireballImageView = new ImageView(fireballImage);
        if (isFacingLeft) {
            fireballImageView.setX(playerImage.getX() + 50);
            playerImage.setScaleX(PLAYER_FACE_LEFT);
            fireballImageView.setScaleX(FIREBALL_FACE_LEFT);
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(fireballImageView.translateXProperty(),
                                     0)),
                    new KeyFrame(new Duration(fireballSpeed),
                            new KeyValue(fireballImageView.translateXProperty(),
                                    -fireballReach)));
        } else {
            fireballImageView.setX(playerImage.getX() + FIREBALL_OFFSET);
            playerImage.setScaleX(PLAYER_FACE_RIGHT);
            fireballImageView.setScaleX(FIREBALL_FACE_RIGHT);
            timeline.getKeyFrames().addAll(
                    new KeyFrame(new Duration(fireballSpeed),
                            new KeyValue(fireballImageView.translateXProperty(),
                                    fireballReach)));
        }
        fireballImageView.setY(470);
        Fireball fireball = new Fireball(game, fireballImageView, timeline);
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fireballs.remove(fireball);
                game.getRoot().getChildren().remove(fireballImageView);
                points -= 1;

            }
        });
        fireballs.add(fireball);
        fireball.shoot();

    }
}
