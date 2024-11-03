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
    private JButton backButton;
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
        backButton = new JButton("Retreat");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(attackButton);
        buttonPanel.add(specialSkillButton);
        buttonPanel.add(defendButton);
        buttonPanel.add(backButton);

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

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Closes the combat screen
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

    // Launch the Combat Screen (for testing)
    public static void main(String[] args) {
        // Dummy data for testing purposes
        Hero theHero = new Warrior("Hero Name");
        DungeonCharacter enemy = new Ogre();
        CombatController combatController = new CombatController(theHero, enemy);

        SwingUtilities.invokeLater(() -> {
            CombatPanel screen = combatController.getCombatPanel();
            screen.setVisible(true);
        });
    }
}
