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
import java.time.LocalTime;
// import javax.swing.text.AttributeSet.ColorAttribute;
import java.time.ZoneId;

import media.AudioPlayer;
import java.awt.*;
import java.awt.event.*;

public class Intro extends JPanel {
    private JPanel parent;

    private int WIDTH = 700;
    private int LEFT = GameView.WIDTH / 2 - WIDTH / 2;
    private int RIGHT = GameView.WIDTH / 2 + WIDTH / 2;
    private Color START_COLOR = Color.white;
    JLabel title, start;
    JButton quitButton;
    int startButtonBlinkTime = 500;

    // remember, you must start the timer!
    Timer timer = new Timer(startButtonBlinkTime, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int time = LocalTime.now(ZoneId.of("Asia/Taipei")).toSecondOfDay();
            start.setForeground(new Color(255, 255, 255, 255 * (time % 3) / 2));
        }
    });

    public Intro(JPanel cards) {
        this.parent = cards;
        super.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // set intro panel
        setSize(GameView.WIDTH, GameView.HEIGHT);
        setBackground(Color.black);
        setFocusable(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent evt) {
                // stop();
            }

            @Override
            public void componentShown(ComponentEvent evt) {
                requestFocus();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.VK_SPACE == keyEvent.getKeyCode()) {
                    AudioPlayer.playSounds("click JButton");
                    ((CardLayout) parent.getLayout()).next(parent);
                }
            }
        });
        // set title
        title = new JLabel("LA Street Fight!!!"); // title
        title.setForeground(Color.red);
        title.setFont(new Font("Times New Roman", Font.BOLD, 72));

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 2;
        c.anchor = GridBagConstraints.CENTER;

        add(title, c); // add to titlePanel

        start = new JLabel("press SPACE to start"); // start
        start.setForeground(Color.blue);
        start.setFont(new Font("Times New Roman", Font.PLAIN, 48));

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.weighty = 1;
        add(start, c);
        // set quit button
        quitButton = new JButton("QUIT"); // quit button
        quitButton.setBackground(Color.white);
        quitButton.setForeground(Color.blue);
        quitButton.setFont(new Font("Times New Roman", Font.PLAIN, 48));

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.insets = new Insets(10, 0, 0, 0); // top padding
        c.anchor = GridBagConstraints.PAGE_END;
        add(quitButton, c);

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.playSounds("click JButton");
                System.exit(0);
            }
        });

        // start the timer!!!
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage image;
        image = read_image("assets/background/intro/1.jpg");
        g.drawImage(image, 0, 0, 1300, 800, null);
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
