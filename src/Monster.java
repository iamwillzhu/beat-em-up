import javafx.scene.image.ImageView;
import javafx.animation.Timeline;

public class Monster {
    public static int numberMonsters = 0;
    private Game game;
    private ImageView image;
    private Timeline timeline;

    public Monster(Game game, ImageView image, Timeline timeline) {
        this.game = game;
        this.image = image;
        this.timeline = timeline;
        numberMonsters += 1;
    }

    public ImageView getImage() {
        return image;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void spawn() {
        game.getRoot().getChildren().add(image);
        timeline.play();
    }
}
