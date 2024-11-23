package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.CombatController;
import model.DungeonCharacters.*;

public class CombatPanel extends JFrame {
    private JLabel heroInfo;
    private JLabel enemyInfo;
    private JTextArea actionLog;
    private JButton attackButton;
    private JButton specialSkillButton;
    private JButton defendButton;
    private JButton retreatButton;
    private JButton returnButton;  // Changed to 'returnButton'
    private JButton heal1Button;
    private JButton heal2Button;
    private JButton heal3Button;
    private CombatController combatController;
    private JLabel heroImageLabel;
    private JLabel enemyImageLabel;

    public CombatPanel(CombatController combatController) {
        this.combatController = combatController;

        setTitle("Combat Screen");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Use GridBagLayout for more flexible sizing
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create Hero and Enemy info panels
        heroInfo = new JLabel("Hero Info");
        enemyInfo = new JLabel("Enemy Info");

        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        infoPanel.add(heroInfo);
        infoPanel.add(enemyInfo);

        // Create Action Log area with a larger JTextArea
        actionLog = new JTextArea(20, 40);  // Increase the number of rows and columns to make it larger
        actionLog.setEditable(false);

        // Set the preferred size of the JTextArea (make it larger)
        actionLog.setPreferredSize(new Dimension(500, 200));  // Adjust width and height as needed

        JScrollPane actionLogScroll = new JScrollPane(actionLog);

        // Create Combat Buttons
        attackButton = new JButton("Attack");
        specialSkillButton = new JButton("Use Special Skill");
        defendButton = new JButton("Defend");
        retreatButton = new JButton("Retreat");
        returnButton = new JButton("Return");  // Added 'Return' button

        // Create Heal Range Buttons for Priestess's special skill
        heal1Button = new JButton("Heal I (25 HP, 5 MP)");
        heal2Button = new JButton("Heal II (50 HP, 10 MP)");
        heal3Button = new JButton("Heal III (75 HP, 15 MP)");

        // Set heal buttons to be initially hidden
        heal1Button.setVisible(false);
        heal2Button.setVisible(false);
        heal3Button.setVisible(false);
        returnButton.setVisible(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(attackButton);
        buttonPanel.add(specialSkillButton);
        buttonPanel.add(defendButton);
        buttonPanel.add(retreatButton);
        buttonPanel.add(returnButton);  // Add the return button
        buttonPanel.add(heal1Button);
        buttonPanel.add(heal2Button);
        buttonPanel.add(heal3Button);

        // Add components to the frame using GridBagLayout
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: infoPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(infoPanel, gbc);

        // Row 2: Visual Panel (empty space for now)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 0.5;  // Give visual panel more space
        gbc.weightx = 1.0;  // Make visual panel resize horizontally
        gbc.fill = GridBagConstraints.BOTH;  // Allow the panel to expand both ways
        JPanel visualPanel = new JPanel(new BorderLayout());  // This is the blank visual panel
        visualPanel.setPreferredSize(new Dimension(1000,500)); // Set a preferred size
        visualPanel.setBackground(Color.GREEN);  // Set background color to differentiate the panel

        // Create and add the image label
        ImageIcon heroImageIcon = new ImageIcon("src/resources/assets/player/Terra.png"); // Load the image
        heroImageLabel = new JLabel(heroImageIcon); // Create a JLabel with the image
        heroImageLabel.setHorizontalAlignment(JLabel.CENTER); // Center the image horizontally
        heroImageLabel.setVerticalAlignment(JLabel.CENTER);   // Center the image vertically
        visualPanel.add(heroImageLabel, BorderLayout.EAST); // Add the image label to the panel

        // Create and add the image label
        ImageIcon enemyImageIcon = new ImageIcon("src/resources/assets/player/Behemoth.png"); // Load the image
        enemyImageLabel = new JLabel(enemyImageIcon); // Create a JLabel with the image
        enemyImageLabel.setHorizontalAlignment(JLabel.CENTER); // Center the image horizontally
        enemyImageLabel.setVerticalAlignment(JLabel.CENTER);   // Center the image vertically
        visualPanel.add(enemyImageLabel, BorderLayout.WEST); // Add the image label to the panel

        add(visualPanel, gbc);

        // Row 3: actionLogScroll
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1; // Smaller weight for action log
        gbc.weightx = 1.0; // Make it resize horizontally
        gbc.fill = GridBagConstraints.BOTH;  // Make it expand in both directions
        add(actionLogScroll, gbc);


        // Row 4: buttonPanel
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weighty = 0.03; // Smaller weight for button panel
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, gbc);

        // Add listeners for buttons
        addActionListeners();

        // Center the CombatPanel on the screen
        setLocationRelativeTo(null);
    }

    private void addActionListeners() {
        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                combatController.handleAttack();
            }
        });

        specialSkillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                combatController.handleSpecialSkill();
            }
        });

        defendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                combatController.handleDefend();
            }
        });

        retreatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                combatController.handleRetreat();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showActionButtons();  // Show the default action buttons again
            }
        });

        // Add heal button listeners for each heal range
        heal1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                combatController.handleHeal(1);
            }
        });

        heal2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                combatController.handleHeal(2);
            }
        });

        heal3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                combatController.handleHeal(3);
            }
        });
    }

    public void updateHeroInfo(String info) {
        heroInfo.setText(info);
    }

    public void updateEnemyInfo(String info) {
        enemyInfo.setText(info);
    }

    public void logAction(String message) {
        actionLog.append(message + "\n");
    }

    // Called when the battle is over, transition to the GamePanel
    public void displayGameOver(String message) {
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        combatController.switchToGamePanel();  // Notify the controller to switch to GamePanel
    }

    // Show heal options and hide main buttons
    public void showHealOptions() {
        attackButton.setVisible(false);
        specialSkillButton.setVisible(false);
        defendButton.setVisible(false);
        retreatButton.setVisible(false);
        heal1Button.setVisible(true);
        heal2Button.setVisible(true);
        heal3Button.setVisible(true);
        returnButton.setVisible(true);  // Show the 'Return' button
    }

    // Show main action buttons and hide heal options
    public void showActionButtons() {
        attackButton.setVisible(true);
        specialSkillButton.setVisible(true);
        defendButton.setVisible(true);
        retreatButton.setVisible(true);
        heal1Button.setVisible(false);
        heal2Button.setVisible(false);
        heal3Button.setVisible(false);
        returnButton.setVisible(false);  // Hide the 'Return' button when in action mode
    }

    // Add this method to handle the shake effect
    public void shakeImage() {
        final int shakeDistance = 10; // How far the image moves left or right
        final int shakeCount = 10; // Number of shakes
        final int shakeSpeed = 50; // Speed of shake (in milliseconds)

        // Store the original position of the image
        Point originalPosition = heroImageLabel.getLocation();

        // Create a timer to animate the shake
        Timer shakeTimer = new Timer(shakeSpeed, new ActionListener() {
            int shakeCounter = 0;
            boolean moveRight = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (shakeCounter < shakeCount) {
                    // Move the image left and right
                    int xOffset = moveRight ? shakeDistance : -shakeDistance;
                    heroImageLabel.setLocation(originalPosition.x + xOffset, originalPosition.y);

                    // Toggle direction for the shake
                    moveRight = !moveRight;
                    shakeCounter++;
                } else {
                    // Stop the shake and reset the image position
                    heroImageLabel.setLocation(originalPosition);
                    ((Timer) e.getSource()).stop(); // Stop the timer
                }
            }
        });

        // Start the shake animation
        shakeTimer.start();
    }


    /*
    public static void main(String[] args) {
        Hero theHero = new Priestess("Tera");
        DungeonCharacter enemy = new Ogre();

        CombatController combatController = new CombatController(theHero, enemy);

        SwingUtilities.invokeLater(() -> {
            CombatPanel screen = combatController.getCombatPanel();
            screen.setVisible(true);
        });
    }

     */
}
