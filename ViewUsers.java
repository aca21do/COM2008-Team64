import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class ViewUsers extends JFrame {
    private JPanel viewUsersPanel;
    private JButton accountButton;
    private JButton staffViewButton;
    private JButton customerViewButton;
    private JTable catalogueTable;
    private JTextField promoteTextField;
    private JTextField demoteTextField;
    private JButton promoteButton;
    private JButton demoteButton;

    public ViewUsers  (Connection connection) {
        // panel setup
        setContentPane(viewUsersPanel);
        setTitle("View Users");
        setSize(800, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        promoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        demoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccountDetails(connection).setVisible(true);
                setVisible(false);
            }
        });
        customerViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CatalogueCustomer(connection).setVisible(true);
                setVisible(false);
            }
        });
        staffViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CatalogueStaff(connection).setVisible(true);
                setVisible(false);
            }
        });
    }
}
