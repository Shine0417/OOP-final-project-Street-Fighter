package views;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.*;
import java.awt.event.*;
public class Intro extends JPanel {
    private JPanel parent;

    private int WIDTH = 700;
    private int LEFT = GameView.WIDTH / 2 - WIDTH / 2;
    private int RIGHT = GameView.WIDTH / 2 + WIDTH / 2;

    public Intro(JPanel cards) {
        this.parent = cards;

        // set intro panel
        setSize(GameView.WIDTH, GameView.HEIGHT);
        setBackground(Color.black);
        setLayout(null);

        // settitle
        JPanel titlePanel = new JPanel(); // title panel
        // titlePanel.setLayout(null); // layout be default works
        titlePanel.setBackground(Color.black);
        titlePanel.setBounds(LEFT, 200, WIDTH, 200);
        JLabel title = new JLabel("La Street Fight!!!"); // title
        title.setForeground(Color.red);
        title.setFont(new Font("Times New Roman", Font.BOLD, 72));
        titlePanel.add(title); // add to titlePanel
        
        // set button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.setBackground(Color.black);
        buttonPanel.setBounds(LEFT, 500, WIDTH, 200);
        JButton startButton = new JButton("START"); // start button
        startButton.setBackground(Color.white);
        startButton.setForeground(Color.blue);
        startButton.setFont(new Font("Times New Roman", Font.PLAIN, 36));
        JButton quitButton = new JButton("QUIT"); // quit button
        quitButton.setBackground(Color.white);
        quitButton.setForeground(Color.blue);
        quitButton.setFont(new Font("Times New Roman", Font.PLAIN, 36));
        buttonPanel.add(startButton); // add to buttonPanel
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

        this.add(titlePanel);
        this.add(buttonPanel);
    }
}
