package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame {
    private JTextField forenameTextField;
    private JTextField surnameTextField;
    private JTextField houseNumberTextField;
    private JTextField roadNameTextField;
    private JTextField cityNameTextField;
    private JTextField postcodeTextField;
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton homeButton;
    private JButton loginButton;
    private JPanel registerPanel;

    public Register() {
        // panel setup
        setContentPane(registerPanel);
        setTitle("Register");
        setSize(400, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrame().setVisible(true);
                setVisible(false);
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login().setVisible(true);
                setVisible(false);
            }
        });
    }

    public JPanel getPanel() {
        return registerPanel;
    }
}
