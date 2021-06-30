package views;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
// import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.*;
import java.awt.event.*;
public class Intro extends JPanel {
    private JPanel parent;

    private int WIDTH = 700;
    private int LEFT = GameView.WIDTH / 2 - WIDTH / 2;
    private int RIGHT = GameView.WIDTH / 2 + WIDTH / 2;

    JPanel titlePanel, buttonPanel;
    JLabel title;
    JButton startButton, quitButton;
    int startButtonBlinkTime = 500;

    // remember, you must start the timer!
    Timer timer = new Timer(startButtonBlinkTime, new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            if(startButton.getForeground().equals(Color.blue)){
                startButton.setForeground(Color.white);
            }
            else{
                startButton.setForeground(Color.blue);
            }
        }
    });

    public Intro(JPanel cards) {
        this.parent = cards;

        // set intro panel
        setSize(GameView.WIDTH, GameView.HEIGHT);
        setBackground(Color.black);
        setLayout(null);

        // set title
        titlePanel = new JPanel(); // title panel, layout should be default works
        titlePanel.setBackground(Color.black);
        titlePanel.setBounds(LEFT, 100, WIDTH, 100);
        title = new JLabel("LA Street Fight!!!"); // title
        title.setForeground(Color.red);
        title.setFont(new Font("Times New Roman", Font.BOLD, 72));
        titlePanel.add(title); // add to titlePanel
        
        // set start button
        buttonPanel = new JPanel(); // start button panel
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.setBackground(Color.black);
        buttonPanel.setBounds(LEFT+125, 400, WIDTH-300, 200);
        startButton = new JButton("START"); // start button
        startButton.setBackground(Color.white);
        startButton.setForeground(Color.blue);
        startButton.setFont(new Font("Times New Roman", Font.PLAIN, 48));
        // set quit button
        quitButton = new JButton("QUIT"); // quit button
        quitButton.setBackground(Color.white);
        quitButton.setForeground(Color.blue);
        quitButton.setFont(new Font("Times New Roman", Font.PLAIN, 48));
        // add buttons to buttonPanel
        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) parent.getLayout()).next(parent);
            }
        });

        quitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            } 
        });

        // add all panels to menu itself
        this.add(titlePanel);
        this.add(buttonPanel);

        // start the timer!!!
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage image;
        image = read_image("assets/background/intro/1.jpg");
        g.drawImage(image, 0, 0,1300,800, null);
    }

    private static BufferedImage read_image(String background) {
        try {
            return ImageIO.read(new File(background));
        } catch (IOException ex) {
            System.out.println("read image error");
        }
        return null;
    }


}
