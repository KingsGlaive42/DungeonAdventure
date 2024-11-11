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

    public CombatPanel(CombatController combatController) {
        this.combatController = combatController;

        setTitle("Combat Screen");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create Hero and Enemy info panels
        heroInfo = new JLabel("Hero Info");
        enemyInfo = new JLabel("Enemy Info");

        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        infoPanel.add(heroInfo);
        infoPanel.add(enemyInfo);

        // Create Action Log area
        actionLog = new JTextArea(10, 30);
        actionLog.setEditable(false);
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

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(attackButton);
        buttonPanel.add(specialSkillButton);
        buttonPanel.add(defendButton);
        buttonPanel.add(retreatButton);
        buttonPanel.add(returnButton);  // Add the return button
        buttonPanel.add(heal1Button);
        buttonPanel.add(heal2Button);
        buttonPanel.add(heal3Button);

        // Add components to the frame
        add(infoPanel, BorderLayout.NORTH);
        add(actionLogScroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add listeners for buttons
        addActionListeners();
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

    // Show heal buttons only if the hero is a Priestess
    public void showHealButtons(boolean visible) {
        heal1Button.setVisible(visible);
        heal2Button.setVisible(visible);
        heal3Button.setVisible(visible);
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

    public static void main(String[] args) {
        Hero theHero = new Berserker("Clive");
        DungeonCharacter enemy = new Ogre();

        CombatController combatController = new CombatController(theHero, enemy);

        SwingUtilities.invokeLater(() -> {
            CombatPanel screen = combatController.getCombatPanel();
            screen.setVisible(true);
        });
    }
}
