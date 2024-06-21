import javax.swing.*;
import java.awt.*;

public class App {

    public static void main(String[] args) {

        JFrame f = new JFrame("Flappy Bird");

        f.setSize(360, 640);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set exit on close

        FlappyBird flappyBird = new FlappyBird();
        f.add(flappyBird);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        
        // Request focus after setting visibility to true
        flappyBird.requestFocusInWindow();
    }
}
