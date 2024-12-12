package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import controller.CombatController;
import model.Combat.AttackResult;
import model.GameConfig;

/**
 * This is a combat panel that hosts all the visual elements
 * for the combat screen.
 *
 * @author Thomas Le
 */
public class CombatPanel extends JPanel {
    private final JLabel heroInfo;
    private final JLabel enemyInfo;
    private final JTextArea actionLog;
    private final JButton attackButton;
    private final JButton specialSkillButton;
    private final JButton defendButton;
    private final JButton usePotionButton;
    private final JButton retreatButton;
    private final JButton returnButton;
    private final JButton heal1Button;
    private final JButton heal2Button;
    private final JButton heal3Button;
    private final CombatController combatController;
    private static JLabel heroImageLabel;
    private static JLabel enemyImageLabel;
    private static ImageIcon heroImageIcon;
    private static ImageIcon enemyImageIcon;
    private Image heroImage;
    private Image enemyImage;
    private JPanel visualPanel;

    /**
     * Combat Panel constructor.
     *
     * @param combatController Controller for connecting view and model.
     */
    public CombatPanel(final CombatController combatController) {
        this.combatController = combatController;

        setSize(new Dimension(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create Hero and Enemy info panels
        heroInfo = new JLabel("Hero Info");
        System.out.println("Constructor: " + heroInfo.getText());
        enemyInfo = new JLabel("Enemy Info");

        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        infoPanel.add(enemyInfo); // Enemy HP
        infoPanel.add(heroInfo); // Hero HP and MP

        // Create Action Log area
        actionLog = new JTextArea(20, 40);
        actionLog.setEditable(false);
        actionLog.setPreferredSize(new Dimension(500, 200));

        JScrollPane actionLogScroll = new JScrollPane(actionLog);

        // Create Combat Buttons
        attackButton = new JButton("Attack");
        specialSkillButton = new JButton("Use Special Skill");
        defendButton = new JButton("Defend");
        usePotionButton = new JButton("Use Potion");
        retreatButton = new JButton("Retreat");
        returnButton = new JButton("Return");

        // Create Heal Range Buttons for Priestess's special skill
        heal1Button = new JButton("Heal I (5MP)");
        heal2Button = new JButton("Heal II (10MP)");
        heal3Button = new JButton("Heal III (15MP)");

        // Set heal buttons to be initially hidden
        heal1Button.setVisible(false);
        heal2Button.setVisible(false);
        heal3Button.setVisible(false);
        returnButton.setVisible(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(attackButton);
        buttonPanel.add(specialSkillButton);
        buttonPanel.add(defendButton);
        buttonPanel.add(usePotionButton);
        buttonPanel.add(retreatButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(heal1Button);
        buttonPanel.add(heal2Button);
        buttonPanel.add(heal3Button);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: infoPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(infoPanel, gbc);

        // Row 2: Visual Panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 0.4;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        // Create a JPanel with a background image
        visualPanel = new JPanel(new BorderLayout()) {
            private Image backgroundImage;

            {
                // Load the background image
                try {
                    backgroundImage = ImageIO.read(new File("src/resources/assets/Terrain/CombatScreen.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    // Draw the image as the background
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Set the preferred size
        visualPanel.setPreferredSize(new Dimension(1000, 500));

        // Load and scale the hero image
        heroImageIcon = combatController.setImage(true);
        heroImage = heroImageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        heroImageIcon = new ImageIcon(heroImage);
        heroImageLabel = new JLabel(heroImageIcon); // Create a JLabel with the scaled image
        heroImageLabel.setHorizontalAlignment(JLabel.CENTER);
        heroImageLabel.setVerticalAlignment(JLabel.CENTER);
        heroImageLabel.setBorder(BorderFactory.createEmptyBorder(100, 20, 0, 50));
        visualPanel.add(heroImageLabel, BorderLayout.EAST);

        // Load and scale the enemy image
        enemyImageIcon = combatController.setImage(false);
        enemyImage = enemyImageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        enemyImageIcon = new ImageIcon(enemyImage);
        enemyImageLabel = new JLabel(enemyImageIcon);
        enemyImageLabel.setHorizontalAlignment(JLabel.CENTER);
        enemyImageLabel.setVerticalAlignment(JLabel.CENTER);
        enemyImageLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20)); // Top, left, bottom, right margins
        visualPanel.add(enemyImageLabel, BorderLayout.WEST);

        // Add the visual panel to the main container
        add(visualPanel, gbc);


        // Row 3: actionLogScroll
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 0.2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(actionLogScroll, gbc);


        // Row 4: buttonPanel
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weighty = 0.03;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, gbc);

        // Add listeners for buttons
        addActionListeners();

        setVisible(true);
    }

    /**
     * This method adds action listeners to buttons
     */
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

        usePotionButton.addActionListener(e -> {
            deactivateButtons();
            combatController.handlePotion();
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

    /**
     * This method updates hero's hp and mp info.
     *
     * @param theInfo Updated hero info.
     */
    public void updateHeroInfo(final String theInfo) {
        heroInfo.setText(theInfo);
        System.out.println("Update: " + heroInfo.getText());
    }

    /**
     * This method updates enemy's hp info
     *
     * @param theInfo Updated enemy info.
     */
    public void updateEnemyInfo(final String theInfo) {
        enemyInfo.setText(theInfo);
    }

    /**
     * This method logs actions in the action log.
     *
     * @param theMessage Message to be logged.
     */
    public void logAction(final String theMessage)
    {
        actionLog.append(theMessage + "\n");
    }

    /**
     * This method displays the game over screen for hero or enemy death.
     *
     * @param theMessage Message displayed on death of hero or enemy.
     */
    public void displayGameOver(final String theMessage) {
        JOptionPane.showMessageDialog(this, theMessage, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        combatController.switchToGamePanel();
    }

    /**
     *  method that resets the panel when it ends.
     */
    public void clearState() {
        ImageIcon newHeroIcon = combatController.setImage(true);
        ImageIcon newEnemyIcon = combatController.setImage(false);
        Image newHeroImage = newHeroIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        Image newEnemyImage = newEnemyIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        newHeroIcon.setImage(newHeroImage);
        newEnemyIcon.setImage(newEnemyImage);
        heroImageLabel.setIcon(newHeroIcon);
        enemyImageLabel.setIcon(newEnemyIcon);
        heroImageIcon = newHeroIcon;
        enemyImageIcon = newEnemyIcon;
        visualPanel.repaint();
        reactivateButtons();
        actionLog.setText("");
    }

    /**
     * This method hides main action buttons and show healing buttons.
     */
    public void showHealOptions() {
        attackButton.setVisible(false);
        specialSkillButton.setVisible(false);
        defendButton.setVisible(false);
        usePotionButton.setVisible(false);
        retreatButton.setVisible(false);
        heal1Button.setVisible(true);
        heal2Button.setVisible(true);
        heal3Button.setVisible(true);
        returnButton.setVisible(true);  // Show the 'Return' button
    }

    /**
     * This method shows main action buttons and hides healing buttons.
     */
    public void showActionButtons() {
        attackButton.setVisible(true);
        specialSkillButton.setVisible(true);
        defendButton.setVisible(true);
        usePotionButton.setVisible(true);
        retreatButton.setVisible(true);
        heal1Button.setVisible(false);
        heal2Button.setVisible(false);
        heal3Button.setVisible(false);
        returnButton.setVisible(false);
    }

    /**
     * This method selects whether hero or enemy sprite is shaked when damaged.
     *
     * @param isHero True if hero is damaged, false if enemy is damaged
     */
    private void shakeImage(final boolean isHero){
        if (isHero) {
            shakeImage(heroImageLabel);
        } else {
            shakeImage(enemyImageLabel);
        }
    }

    /**
     * This method shakes the hero or enemy sprite when damaged.
     *
     * @param theImage The image that is shaken.
     */
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

    /**
     * This method rushes the attacker towards the defender, simulating an attack.
     *
     * @param theHero True if hero is attacking, false if enemy is attack.
     * @param theResult Result of attack to determine if image needs to be shaken.
     */
    public void attackAnimation(final boolean theHero, final AttackResult theResult) {
        final int animationSpeed = 5; // How fast the sprite moves (lower value = faster)
        final int animationStep = 20;  // How far the sprite moves in each step

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
            final int moveDistance = targetX - originalPosition.x; // Calculate the distance to move

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
                    switch(theResult) {
                        case HIT, BONK, HALF_HIT:
                            shakeImage(!theHero);
                            break;
                    }
                    if(!theHero) {
                        reactivateButtons();
                    }
                }
            }
        });

        // Start the animation
        attackTimer.start();
    }

    /**
     * This method slowly changes the hero or enemy
     * sprite to transparent upon death.
     *
     * @param theHero True if hero dies, false if enemy dies.
     */
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

    /**
     * Helper method for turning hero/enemy sprite transparent.
     *
     * @param icon Icon of image to be transparent.
     * @param alpha Transparency number.
     * @return transparent image.
     */
    private static ImageIcon createTransparentIcon(final ImageIcon icon, final float alpha) {
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

    /**
     * Method that deactivates buttons when the attack
     * animation is running.
     */
    private void deactivateButtons() {
        attackButton.setEnabled(false);
        specialSkillButton.setEnabled(false);
        defendButton.setEnabled(false);
        usePotionButton.setEnabled(false);
        retreatButton.setEnabled(false);
    }

    /**
     * Method that reactivates buttons when the attack
     * animation is finished.
     */
    public void reactivateButtons() {
        attackButton.setEnabled(true);
        specialSkillButton.setEnabled(true);
        defendButton.setEnabled(true);
        usePotionButton.setEnabled(true);
        retreatButton.setEnabled(true);
    }
}
