package views;

import javax.swing.*;
import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class GameView extends JFrame {
    public static final int HEIGHT = 800;
    public static final int WIDTH = 1300;


    public GameView() throws HeadlessException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setTitle("Street Fighter !!!!");
        setResizable(false);
        setVisible(true);
    }    
}
