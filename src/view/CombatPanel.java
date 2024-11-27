package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import controller.CombatController;
import model.DungeonCharacters.*;

public class CombatPanel extends JFrame {
    private final JLabel heroInfo;
    private final JLabel enemyInfo;
    private final JTextArea actionLog;
    private final JButton attackButton;
    private final JButton specialSkillButton;
    private final JButton defendButton;
    private final JButton retreatButton;
    private final JButton returnButton;  // Changed to 'returnButton'
    private final JButton heal1Button;
    private final JButton heal2Button;
    private final JButton heal3Button;
    private final CombatController combatController;
    private static JLabel heroImageLabel;
    private static JLabel enemyImageLabel;
    private static ImageIcon heroImageIcon;
    private static ImageIcon enemyImageIcon;

    public CombatPanel(final CombatController combatController) {
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
        buttonPanel.add(returnButton);
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

        // Load and scale the hero image
        heroImageIcon = new ImageIcon("src/resources/assets/player/Terra.png");
        Image heroImage = heroImageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Adjust width and height as needed
        heroImageIcon = new ImageIcon(heroImage);
        heroImageLabel = new JLabel(heroImageIcon); // Create a JLabel with the scaled image
        heroImageLabel.setHorizontalAlignment(JLabel.CENTER);
        heroImageLabel.setVerticalAlignment(JLabel.CENTER);
        // Add margins to the hero image label
        heroImageLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // Top, left, bottom, right margins
        visualPanel.add(heroImageLabel, BorderLayout.EAST);

        // Load and scale the enemy image
        enemyImageIcon = new ImageIcon("src/resources/assets/player/Behemoth.png");
        Image enemyImage = enemyImageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Adjust width and height as needed
        enemyImageIcon = new ImageIcon(enemyImage);
        enemyImageLabel = new JLabel(enemyImageIcon); // Create a JLabel with the scaled image
        enemyImageLabel.setHorizontalAlignment(JLabel.CENTER);
        enemyImageLabel.setVerticalAlignment(JLabel.CENTER);
        // Add margins to the enemy image label
        enemyImageLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // Top, left, bottom, right margins
        visualPanel.add(enemyImageLabel, BorderLayout.WEST);

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
        attackButton.addActionListener(e -> {
            deactivateButtons();
            combatController.handleAttack();
        });

        specialSkillButton.addActionListener(e -> {
            deactivateButtons();
            combatController.handleSpecialSkill();
        });

        defendButton.addActionListener(e -> {
            deactivateButtons();
            combatController.handleDefend();
        });

        retreatButton.addActionListener(e -> {
            deactivateButtons();
            combatController.handleRetreat();
        });

        returnButton.addActionListener(e -> {
            reactivateButtons();
            showActionButtons();  // Show the default action buttons again
        });

        // Add heal button listeners for each heal range
        heal1Button.addActionListener(e -> combatController.handleHeal(1));

        heal2Button.addActionListener(e -> combatController.handleHeal(2));

        heal3Button.addActionListener(e -> combatController.handleHeal(3));
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
    public void displayGameOver(final String theMessage) {
        JOptionPane.showMessageDialog(this, theMessage, "Game Over", JOptionPane.INFORMATION_MESSAGE);
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

    public void shakeHero(){
        shakeImage(heroImageLabel);
    }

    public void shakeEnemy(){
        shakeImage(enemyImageLabel);
    }

    // Add this method to handle the shake effect
    private void shakeImage(final JLabel theImage) {
        final int shakeDistance = 10; // How far the image moves left or right
        final int shakeCount = 10; // Number of shakes
        final int shakeSpeed = 50; // Speed of shake (in milliseconds)

        // Store the original position of the image
        Point originalPosition = theImage.getLocation();

        // Create a timer to animate the shake
        Timer shakeTimer = new Timer(shakeSpeed, new ActionListener() {
            int shakeCounter = 0;
            boolean moveRight = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (shakeCounter < shakeCount) {
                    // Move the image left and right
                    int xOffset = moveRight ? shakeDistance : -shakeDistance;
                    theImage.setLocation(originalPosition.x + xOffset, originalPosition.y);

                    // Toggle direction for the shake
                    moveRight = !moveRight;
                    shakeCounter++;
                } else {
                    // Stop the shake and reset the image position
                    theImage.setLocation(originalPosition);
                    ((Timer) e.getSource()).stop(); // Stop the timer
                }
            }
        });

        // Start the shake animation
        shakeTimer.start();
    }

    // Add this method to handle the attack animation
    public void attackAnimation(final boolean theHero) {
        final int animationSpeed = 10; // How fast the sprite moves (lower value = faster)
        final int animationStep = 50;  // How far the sprite moves in each step

        // Store the original position of the image
        Point originalPosition, targetPosition;
        if (theHero) {
            originalPosition = heroImageLabel.getLocation();
            targetPosition = enemyImageLabel.getLocation();
        } else {
            originalPosition = enemyImageLabel.getLocation();
            targetPosition = heroImageLabel.getLocation();
        }
        int targetX = targetPosition.x; // Calculate the target X position

        Timer attackTimer = new Timer(animationSpeed, new ActionListener() {
            int currentX = originalPosition.x; // Track the current X position
            int moveDistance = targetX - originalPosition.x; // Calculate the distance to move

            @Override
            public void actionPerformed(final ActionEvent e) {
                // Move the sprite step by step towards the target position
                if ((moveDistance < 0 && currentX > targetX) || (moveDistance > 0 && currentX < targetX)) {
                    currentX += (int) (animationStep * Math.signum(moveDistance)); // Adjust X position
                    if (theHero) {
                        heroImageLabel.setLocation(currentX, originalPosition.y); // Update position
                    } else {
                        enemyImageLabel.setLocation(currentX, originalPosition.y); // Update position
                    }
                } else {
                    // Stop the animation and reset the sprite to the original position
                    if (theHero) {
                        heroImageLabel.setLocation(originalPosition);
                    } else {
                        enemyImageLabel.setLocation(originalPosition);
                    }
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        // Start the animation
        attackTimer.start();

        if (theHero) { //Need to change so that doesn't shake when blocked
            shakeEnemy();
        } else {
            shakeHero();
        }
        reactivateButtons();
    }

    public static void deathAnimation(final boolean theHero) {
        JLabel label;
        ImageIcon originalIcon;
        if (theHero) {
            label = heroImageLabel;
            originalIcon = heroImageIcon;
        } else {
            label = enemyImageLabel;
            originalIcon = enemyImageIcon;
        }
        final int frames = 100; // Number of animation frames
        final float alphaStep = 1.0f / frames; // Step size for transparency
        final int delay = 1500 / frames; // Time per frame in milliseconds

        Timer timer = new Timer(delay, null);
        final float[] alpha = {1.0f}; // Start fully visible

        timer.addActionListener(e -> {
            // Reduce alpha for each frame
            alpha[0] -= alphaStep;

            // Clamp alpha to a minimum of 0.0f
            if (alpha[0] < 0.0f) {
                alpha[0] = 0.0f;
                timer.stop(); // Stop the timer when fully transparent
            }

            // Update the JLabel with the new transparent icon
            label.setIcon(createTransparentIcon(originalIcon, alpha[0]));
        });

        timer.start(); // Start the animation
    }

    public static ImageIcon createTransparentIcon(final ImageIcon icon, final float alpha) {
        // Get the original image from the ImageIcon
        Image originalImage = icon.getImage();

        // Create a BufferedImage with transparency
        BufferedImage transparentImage = new BufferedImage(
                originalImage.getWidth(null),
                originalImage.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );

        // Draw the original image onto the BufferedImage with the specified transparency
        Graphics2D g2d = transparentImage.createGraphics();
        g2d.setComposite(AlphaComposite.SrcOver.derive(alpha)); // Set the alpha transparency
        g2d.drawImage(originalImage, 0, 0, null); // Draw the image
        g2d.dispose();

        // Return a new ImageIcon with the transparent image
        return new ImageIcon(transparentImage);
    }

    private void deactivateButtons() {
        attackButton.setEnabled(false);
        specialSkillButton.setEnabled(false);
        defendButton.setEnabled(false);
        retreatButton.setEnabled(false);
    }

    private void reactivateButtons() {
        attackButton.setEnabled(true);
        specialSkillButton.setEnabled(true);
        defendButton.setEnabled(true);
        retreatButton.setEnabled(true);
    }

    //For testing
    public static void main(String[] args) {
        Hero theHero = new Berserker("Terra");
        DungeonCharacter enemy = new Ogre(100, 15, 25, 5, 0.6,
                0.4, 10, 20); //temp

        CombatController combatController = new CombatController(theHero, enemy);

        SwingUtilities.invokeLater(() -> {
            CombatPanel screen = combatController.getCombatPanel();
            screen.setVisible(true);
        });
    }
}
