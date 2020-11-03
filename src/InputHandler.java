import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class InputHandler {
    private boolean qPressed;
    private boolean enterPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean onePressed;
    private boolean twoPressed;
    private boolean threePressed;

    public boolean isQPressed() {
        return qPressed;
    }
    public boolean isEnterPressed() {
        return enterPressed;
    }
    public boolean isLeftPressed() { return leftPressed; }
    public boolean isRightPressed() { return rightPressed; }
    public boolean isOnePressed() { return onePressed; }
    public boolean isTwoPressed() { return twoPressed; }
    public boolean isThreePressed() { return threePressed; }

    public InputHandler () {
        qPressed = false;
        enterPressed = false;
        leftPressed = false;
        rightPressed = false;
        onePressed = false;
        twoPressed = false;
        threePressed = false;

    }

    public void keyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.Q) qPressed = true;
        if (code == KeyCode.ENTER) enterPressed = true;
        if (code == KeyCode.LEFT) leftPressed = true;
        if (code == KeyCode.RIGHT) rightPressed = true;
        if (code == KeyCode.DIGIT1) onePressed = true;
        if (code == KeyCode.DIGIT2) twoPressed = true;
        if (code == KeyCode.DIGIT3) threePressed = true;
    }

    public void keyReleased(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.Q) qPressed = false;
        if (code == KeyCode.ENTER) enterPressed = false;
        if (code == KeyCode.LEFT) leftPressed = false;
        if (code == KeyCode.RIGHT) rightPressed = false;
        if (code == KeyCode.DIGIT1) onePressed = false;
        if (code == KeyCode.DIGIT2) twoPressed = false;
        if (code == KeyCode.DIGIT3) threePressed = false;
    }
}
