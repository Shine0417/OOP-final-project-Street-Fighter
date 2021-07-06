package views;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.awt.AlphaComposite;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import characters.alita.Alita;
import characters.emily.Emily;
import characters.gray.Gray;
import characters.knight.Knight;
import characters.nazi.Nazi;
import model.Direction;
import media.AudioPlayer;

import java.awt.event.*;

public class CharacterMenu extends JPanel {
    private JPanel parent;
    private List<Knight> team1;
    private List<Knight> team2;
    private JButton next;

    public CharacterMenu(JPanel cards, List<Knight> team1, List<Knight> team2) {
        this.parent = cards;
        this.team1 = team1;
        this.team2 = team2;
        super.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 9;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;

        setSize(GameView.WIDTH, GameView.HEIGHT);
        setBackground(Color.blue);

        JLabel title = new JLabel("SELECT PLAY"); // title
        title.setForeground(Color.black);
        title.setFont(new Font("Times New Roman", Font.BOLD, 52));

        add(title, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.NONE;

        JButton alita1 = getKnightIcon("alita", team1, 1);
        add(alita1, c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.NONE;
        JButton gray1 = getKnightIcon("gray", team1, 1);
        add(gray1, c);

        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.NONE;
        JButton emily1 = getKnightIcon("emily", team1, 1);
        add(emily1, c);

        c.gridx = 3;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.NONE;
        JButton nazi1 = getKnightIcon("nazi", team1, 1);
        add(nazi1, c);

        c.gridx = 5;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.NONE;

        JButton nazi2 = getKnightIcon("nazi", team2, 2);
        add(nazi2, c);

        c.gridx = 6;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.NONE;
        JButton emily2 = getKnightIcon("emily", team2, 2);
        add(emily2, c);

        c.gridx = 7;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.NONE;
        JButton gray2 = getKnightIcon("gray", team2, 2);
        add(gray2, c);

        c.gridx = 8;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.NONE;
        JButton alita2 = getKnightIcon("alita", team2, 2);
        add(alita2, c);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent evt) {
                // stop();
            }

            @Override
            public void componentShown(ComponentEvent evt) {
                requestFocusInWindow();
                team1.clear();
                team2.clear();
                emily1.setEnabled(false);
                gray1.setEnabled(false);
                alita1.setEnabled(false);
                nazi1.setEnabled(false);

                emily2.setEnabled(false);
                gray2.setEnabled(false);
                nazi2.setEnabled(false);
                alita2.setEnabled(false);
                reRender();
            }
        });

        c.gridx = 4;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 2;
        c.fill = GridBagConstraints.NONE;
        next = new JButton("Fight");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.playSounds("click JButton");
                ((CardLayout) parent.getLayout()).next(parent);
            }
        });
        next.setEnabled(false);
        next.setBounds(60, 400, 70, 40);
        add(next, c);

    }

    private JButton getKnightIcon(String filepath, List<Knight> team, Integer teamNum) {
        String iconPath = "assets/character/" + filepath + "/icon/" + teamNum.toString() + ".png";
        String disabledIconPath = "assets/character/" + filepath + "/icon/" + teamNum.toString() + "-disabled.png";

        JButton label = new JButton(new ImageIcon(getProfileImage(iconPath, 70, 80)));
        label.setDisabledIcon(new ImageIcon(getProfileImage(disabledIconPath, 70, 80)));
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                handleClickKnight(e.getComponent(), team, teamNum, filepath);
            }
        });
        label.setEnabled(false);
        // label.setBorder(BorderFactory.createEmptyBorder());
        label.setContentAreaFilled(false);
        label.setPreferredSize(new Dimension(80, 80));
        label.setFocusPainted(false);
        
        // add(label);
        return label;

    }

    protected void handleClickKnight(Component component, List<Knight> team, Integer teamNum, String filepath) {
        if (!team.removeIf(k -> k.toString().toLowerCase().equals(filepath))) {
            if (team.size() > 1)
                return;
            Point knightLocation = (teamNum == 1) ? new Point(300, 250) : new Point(700, 250);
            Direction direction = (teamNum == 1) ? Direction.RIGHT : Direction.LEFT;
            Knight knight;

            if (filepath.equals("emily"))
                knight = new Emily(knightLocation, direction);
            else if (filepath.equals("gray"))
                knight = new Gray(knightLocation, direction);
            else if (filepath.equals("alita"))
                knight = new Alita(knightLocation, direction);
            else if (filepath.equals("nazi"))
                knight = new Nazi(knightLocation, direction);
            else {
                component.setEnabled(false);
                return;
            }
            team.add(knight);
            knight.setTeam(teamNum);
            component.setEnabled(true);
        } else {
            component.setEnabled(false);
        }
        reRender();
    }

    public Image getProfileImage(String path, int width, int height) {
        try {
            return ImageIO.read(new File(path)).getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void reRender() {
        if (team1.size() == 2 && team2.size() == 2)
            next.setEnabled(true);
        else
            next.setEnabled(false);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (team1 == null || team2 == null || team1.isEmpty() && team2.isEmpty())
            return;
        g.setFont(new Font("Verdana", Font.BOLD + Font.ITALIC, 35));
        g.setColor(Color.black);
        if (team1.size() > 0) {
            drawBorder(30, 100, 300, 400, g);
            String path = "assets/character/" + team1.get(0).toString().toLowerCase() + "/icon/1.png";
            g.drawImage(getProfileImage(path, 260, 160), 30, 100, 300, 400, this);
            g.drawString(team1.get(0).toString(), 120, 130);
        }

        if (team1.size() > 1) {
            drawBorder(230, 300, 300, 400, g);
            String path = "assets/character/" + team1.get(1).toString().toLowerCase() + "/icon/1.png";
            g.drawImage(getProfileImage(path, 260, 160), 230, 300, 300, 400, this);
            g.drawString(team1.get(1).toString(), 320, 330);
        }

        if (team2.size() > 0) {
            drawBorder(1280, 100, -300, 400, g);
            String path = "assets/character/" + team2.get(0).toString().toLowerCase() + "/icon/1.png";
            g.drawImage(getProfileImage(path, 260, 160), 1280, 100, -300, 400, this);
            g.drawString(team2.get(0).toString(), 1070, 130);
        }

        if (team2.size() > 1) {
            drawBorder(1080, 300, -300, 400, g);
            String path = "assets/character/" + team2.get(1).toString().toLowerCase() + "/icon/1.png";
            g.drawImage(getProfileImage(path, 260, 160), 1080, 300, -300, 400, this);
            g.drawString(team2.get(1).toString(), 870, 330);
        }

    }

    public void drawBorder(int x, int y, int width, int height, Graphics g) {
        g.drawLine(x, y, x + width, y);
        g.drawLine(x, y, x, y + height);
        g.drawLine(x + width, y + height, x + width, y);
        g.drawLine(x + width, y + height, x, y + height);
    }
}
