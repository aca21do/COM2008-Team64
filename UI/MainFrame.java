package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JButton loginButton;
    private JButton registerButton;
    private JPanel mainPanel;
    private JPanel loginPanel;
    private JPanel registerPanel;
    private JTextField emailTextField;
    private JPasswordField passwordField1;
    private JButton homeButton;

    public MainFrame () {
        // panel setup
        setContentPane(mainPanel);
        setTitle("Welcome");
        setSize(400, 250);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login().setVisible(true);
                setVisible(false);
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register().setVisible(true);
                setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
    }

    public JPanel getPanel() {
        return mainPanel;
    }
}
