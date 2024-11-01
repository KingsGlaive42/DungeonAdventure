package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CombatPanel extends JFrame {
    private JLabel heroInfo;
    private JLabel enemyInfo;
    private JTextArea actionLog;
    private JButton attackButton;
    private JButton specialSkillButton;
    private JButton defendButton;
    private JButton backButton;

    public CombatPanel() {
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
                performAttack();
            }
        });

        specialSkillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                useSpecialSkill();
            }
        });

        defendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defend();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Closes the combat screen
            }
        });
    }

    // Placeholder methods for combat actions
    private void performAttack() {
        actionLog.append("Hero performs a basic attack!\n");
        // Call the CombatEngine or Controller for attack logic here
    }

    private void useSpecialSkill() {
        actionLog.append("Hero uses a special skill!\n");
        // Call the CombatEngine or Controller for special skill logic
    }

    private void defend() {
        actionLog.append("Hero defends!\n");
        // Call the CombatEngine or Controller for defend logic
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
        SwingUtilities.invokeLater(() -> {
            CombatPanel screen = new CombatPanel();
            screen.setVisible(true);
        });
    }
}
