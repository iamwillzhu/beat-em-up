import javafx.scene.image.ImageView;
import javafx.animation.Timeline;
import javafx.scene.media.AudioClip;

public class Fireball {
    private Game game;
    private ImageView image;
    private Timeline timeline;

    public ImageView getImage(){
        return image;
    }

    public Fireball(Game game, ImageView image, Timeline timeline) {
        this.game = game;
        this.image = image;
        this.timeline = timeline;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void shoot() {
        AudioClip audio = new AudioClip(getClass().getResource("resources/sound/firecast.mp3").toExternalForm());
        game.getRoot().getChildren().add(image);
        timeline.play();
        audio.play();
    }



}
