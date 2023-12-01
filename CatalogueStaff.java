import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;

public class CatalogueStaff extends JFrame {
    private JComboBox categoryComboBox;
    private JButton ordersButton;
    private JButton accountButton;
    private JButton customerViewButton;
    private JButton managerViewButton;
    private JTable catalogueTable;
    private JButton viewProductButton;
    private JPanel catalogueStaffPanel;
    private JButton createNewProductButton;

    public CatalogueStaff (Connection connection) {
        // panel setup
        setContentPane(catalogueStaffPanel);
        setTitle("Catalogue (Staff)");
        setSize(800, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // make manager button invisible by default
        managerViewButton.setVisible(false);

        // TODO: if manager
        managerViewButton.setVisible(true);
        managerViewButton.setEnabled(true);

        // placeholder data
        String[] columnNames = {"First Name",
                "Last Name"};

        Object[][] data = {
                {"Kathy", "Smith"},
                {"John", "Doe"},
                {"Sue", "Black"},
                {"Jane", "White"},
                {"Joe", "Brown"}
        };

        DefaultTableModel dataModel = new DefaultTableModel(data, columnNames){
            // make table not able to be selected
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        catalogueTable.setModel(dataModel);

        // select first row automatically
        catalogueTable.setRowSelectionInterval(0, 0);

        categoryComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            }
        });
        ordersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrdersStaff(connection).setVisible(true);
                setVisible(false);
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
        managerViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewUsers(connection).setVisible(true);
                setVisible(false);
            }
        });
        viewProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get data in first column of selected row
                int column = 0;
                int row = catalogueTable.getSelectedRow();
                String productCode = catalogueTable.getModel().getValueAt(row, column).toString();

                new ViewProduct(connection, productCode).setVisible(true);
                setVisible(false);
            }
        });
        createNewProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateProduct(connection).setVisible(true);
                setVisible(false);
            }
        });
    }
}
