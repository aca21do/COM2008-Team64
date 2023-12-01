import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class ViewProduct extends JFrame {
    private JTextField productCodeTextField;
    private JTextField brandNameTextField;
    private JTextField productNameTextField;
    private JTextField priceTextField;
    private JTextField quantityTextField;
    private JTextField gaugeCodeTextField;
    private JTextField eraCodeTextField;
    private JButton updateInfoButton;
    private JLabel updateMessageLabel;
    private JPanel viewProductPanel;
    private JTextField dccCodeTextField;
    private JButton backButton;
    private JButton deleteProductButton;

    public ViewProduct (Connection connection, String productCode) {
        // panel setup
        setContentPane(viewProductPanel);
        setTitle("Product");
        setSize(400, 550);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        productCodeTextField.setText(productCode);
        // TODO: setText in the rest of the Fields using productCode
        updateInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CatalogueStaff(connection).setVisible(true);
                setVisible(false);
            }
        });
    }
}
