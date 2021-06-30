package views;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import characters.emily.Emily;
import characters.gray.Gray;
import characters.knight.Knight;
import model.Direction;

import java.awt.event.*;

public class GameOver extends JPanel {
    private JPanel parent;

    public GameOver(JPanel cards) {
        this.parent = cards;
        setSize(GameView.WIDTH, GameView.HEIGHT);
        setBackground(Color.black);

        super.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        JLabel title = new JLabel("GAME OVER"); // title
        title.setForeground(Color.red);
        title.setFont(new Font("Times New Roman", Font.BOLD, 62));

        add(title, c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        JLabel restart = new JLabel("continue?"); // title
        restart.setForeground(Color.gray);
        restart.setFont(new Font("Times New Roman", Font.BOLD, 42));
        add(restart, c);

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

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        JButton next = new JButton("Yes");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) parent.getLayout()).show(cards, "Card with Character Selection panel");
            }
        });
        add(next, c);

        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        JButton exit = new JButton("No");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) parent.getLayout()).show(cards, "Card with Intro panel");
            }
        });
        add(exit, c);
    }

    private JLabel getKnightIcon(String filepath, List<Knight> team, Integer teamNum) {
        try {
            String iconPath = "assets/character/" + filepath + "/icon/" + teamNum.toString() + ".png";
            String disalbledIconPath = "assets/character/" + filepath + "/icon/" + teamNum.toString() + "-disabled.png";

            JLabel label = new JLabel(new ImageIcon(new File(iconPath).toURI().toURL()));
            label.setDisabledIcon(new ImageIcon(new File(disalbledIconPath).toURI().toURL()));
            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    Point knightLocation = (teamNum == 1) ? new Point(300, 300) : new Point(700, 300);
                    Direction direction = (teamNum == 1) ? Direction.RIGHT : Direction.LEFT;

                    System.out.println("Team " + teamNum + " selects: " + filepath);
                    if (filepath.equals("emily")
                            && team.stream().noneMatch(knight -> knight.toString().equals("Emily"))) {
                        Knight emily = new Emily(100, knightLocation, direction);
                        team.add(emily);
                        emily.setTeam(teamNum);
                        e.getComponent().setEnabled(false);
                    } else if (filepath.equals("gray")
                            && team.stream().noneMatch(knight -> knight.toString().equals("Gray"))) {
                        Knight gray = new Gray(100, knightLocation, direction);
                        team.add(gray);
                        gray.setTeam(teamNum);
                        e.getComponent().setEnabled(false);
                    }
                }
            });
            return label;
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        return null;

    }

}
