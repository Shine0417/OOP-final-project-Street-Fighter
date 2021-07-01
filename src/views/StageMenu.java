package views;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;
import media.AudioPlayer;

public class StageMenu extends JPanel {
    private JPanel parent;
    private Canvas canvas;
    private Image image;

    public StageMenu(JPanel cards, Canvas canvas) {
        this.canvas = canvas;
        this.parent = cards;
        setSize(GameView.WIDTH, GameView.HEIGHT);
        setBackground(Color.blue);

        super.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;

        JLabel title = new JLabel("Select Stage"); // title
        title.setForeground(Color.black);
        title.setFont(new Font("Times New Roman", Font.BOLD, 42));

        add(title, c);

        JButton background1 = getBackgroundIcon("1");
        JButton background2 = getBackgroundIcon("2");
        JButton background3 = getBackgroundIcon("3");
        JButton background4 = getBackgroundIcon("4");
        JButton background5 = getBackgroundIcon("5");
        JButton background6 = getBackgroundIcon("6");

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;
        add(background1, c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;
        add(background2, c);

        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;
        add(background3, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;
        add(background4, c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;
        add(background5, c);

        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;
        add(background6, c);

        JButton start = new JButton("start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.playSounds("click JButton");
                canvas.startGame(0);
                ((CardLayout) parent.getLayout()).show(parent, "Card with GamePlay panel");
            }
        });
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;
        add(start, c);
    }

    private JButton getBackgroundIcon(String filename) {
        try {
            String iconPath = "assets/background/gamescene/" + filename + ".png";
            Image img = ImageIO.read(new File(iconPath));
            Image dimg = img.getScaledInstance(260, 160, Image.SCALE_SMOOTH);
            JButton label = new JButton(new ImageIcon(dimg));
            label.setSize(100, 200);
            label.setContentAreaFilled(false);
            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    canvas.setBackgroundImage(iconPath);
                    setBackgroundImage(iconPath);
                }
            });
            return label;
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    protected void setBackgroundImage(String path) {
        try {

            image = ImageIO.read(new File(path));
            repaint();
        } catch (IOException ex) {
            System.out.println("read image error");
        }
    }

    @Override
    protected void paintComponent(Graphics g /* paintbrush */) {
        super.paintComponent(g);
        if (image != null)
            g.drawImage(image, 0, 0, GameView.WIDTH, GameView.HEIGHT, this);
    }

}
