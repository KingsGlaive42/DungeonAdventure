package model;

import view.GamePanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); // sizes window to fit preferred size and layouts of subcomponents

        window.setLocationRelativeTo(null); //display at center of screen
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
