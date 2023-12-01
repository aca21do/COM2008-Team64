import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewUsers extends JFrame {
    private JPanel viewUsersPanel;
    private JButton accountButton;
    private JButton staffViewButton;
    private JButton customerViewButton;
    private JTable userTable;
    private JTextField promoteTextField;
    private JTextField demoteTextField;
    private JButton promoteButton;
    private JButton demoteButton;

    public void populateStaffTable(Connection connection) {
        String[] columnNames = {"UserID", "Email", "Forename", "Surname"};

        DefaultTableModel dataModel = new DefaultTableModel(columnNames, 0);

        Object[] data;

        try {
            String sql = "SELECT * FROM Users WHERE isStaff = true";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                data = new Object[] {resultSet.getString("UserID"),
                                        resultSet.getString("Email"),
                                        resultSet.getString("Forename"),
                                        resultSet.getString("Surname")};
                dataModel.addRow(data);
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        userTable.setModel(dataModel);
    }

    public ViewUsers  (Connection connection) {
        // panel setup
        setContentPane(viewUsersPanel);
        setTitle("View Users");
        setSize(800, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        populateStaffTable(connection);
        promoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = "UPDATE Users SET isStaff = false WHERE Email = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, promoteTextField.getText());
                    int rowsAffected = preparedStatement.executeUpdate();
                    System.out.println(rowsAffected + " row(s) updated successfully");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                populateStaffTable(connection);
            }
        });
        demoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String sql = "UPDATE Users SET isStaff = false WHERE Email = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, promoteTextField.getText());
                    int rowsAffected = preparedStatement.executeUpdate();
                    System.out.println(rowsAffected + " row(s) updated successfully");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                populateStaffTable(connection);
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
