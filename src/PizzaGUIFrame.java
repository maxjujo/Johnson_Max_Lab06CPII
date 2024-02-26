import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class PizzaGUIFrame extends JFrame {
    private JRadioButton thinCrustRadioButton, regularCrustRadioButton, deepDishCrustRadioButton;
    private JComboBox<String> sizeComboBox;
    private JCheckBox pepperoniCheckBox, mushroomsCheckBox, onionsCheckBox, sausageCheckBox, baconCheckBox, extraCheeseCheckBox;
    private JTextArea orderTextArea;
    private JButton orderButton, clearButton, quitButton;

    private final double TAX_RATE = 0.07;
    private final double BASE_SMALL_PRICE = 8.00;
    private final double BASE_MEDIUM_PRICE = 12.00;
    private final double BASE_LARGE_PRICE = 16.00;
    private final double BASE_SUPER_PRICE = 20.00;
    private double totalPrice = 0.0;

    public PizzaGUIFrame() {
        setTitle("Pizza Order GUI Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crust Panel
        JPanel crustPanel = new JPanel(new GridLayout(3, 1));
        crustPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Crust Type",
                TitledBorder.CENTER, TitledBorder.TOP));
        thinCrustRadioButton = new JRadioButton("Thin");
        regularCrustRadioButton = new JRadioButton("Regular");
        deepDishCrustRadioButton = new JRadioButton("Deep-dish");
        ButtonGroup crustButtonGroup = new ButtonGroup();
        crustButtonGroup.add(thinCrustRadioButton);
        crustButtonGroup.add(regularCrustRadioButton);
        crustButtonGroup.add(deepDishCrustRadioButton);
        crustPanel.add(thinCrustRadioButton);
        crustPanel.add(regularCrustRadioButton);
        crustPanel.add(deepDishCrustRadioButton);

        // Size Panel
        JPanel sizePanel = new JPanel(new GridLayout(1, 2));
        sizePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Size",
                TitledBorder.CENTER, TitledBorder.TOP));
        String[] sizes = {"Small", "Medium", "Large", "Super"};
        sizeComboBox = new JComboBox<>(sizes);
        sizePanel.add(sizeComboBox);

        // Toppings Panel
        JPanel toppingsPanel = new JPanel(new GridLayout(6, 1));
        toppingsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Toppings",
                TitledBorder.CENTER, TitledBorder.TOP));
        pepperoniCheckBox = new JCheckBox("Pepperoni");
        mushroomsCheckBox = new JCheckBox("Mushrooms");
        onionsCheckBox = new JCheckBox("Onions");
        sausageCheckBox = new JCheckBox("Sausage");
        baconCheckBox = new JCheckBox("Bacon");
        extraCheeseCheckBox = new JCheckBox("Extra Cheese");
        toppingsPanel.add(pepperoniCheckBox);
        toppingsPanel.add(mushroomsCheckBox);
        toppingsPanel.add(onionsCheckBox);
        toppingsPanel.add(sausageCheckBox);
        toppingsPanel.add(baconCheckBox);
        toppingsPanel.add(extraCheeseCheckBox);

        // Order Text Area Panel
        JPanel orderTextPanel = new JPanel(new BorderLayout());
        orderTextArea = new JTextArea(10, 20);
        orderTextArea.setEditable(false);
        orderTextPanel.add(new JScrollPane(orderTextArea), BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");
        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        // Add components to the frame
        add(crustPanel, BorderLayout.NORTH);
        add(sizePanel, BorderLayout.WEST);
        add(toppingsPanel, BorderLayout.EAST); // Check this line
        add(orderTextPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event listeners
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayOrder();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        pack();
        setLocationRelativeTo(null); // Center the frame
    }

    private void displayOrder() {
        DecimalFormat df = new DecimalFormat("0.00");
        totalPrice = 0.0;
        StringBuilder orderDetails = new StringBuilder();

        // Calculate price based on size
        String size = (String) sizeComboBox.getSelectedItem();
        switch (size) {
            case "Small":
                totalPrice += BASE_SMALL_PRICE;
                break;
            case "Medium":
                totalPrice += BASE_MEDIUM_PRICE;
                break;
            case "Large":
                totalPrice += BASE_LARGE_PRICE;
                break;
            case "Super":
                totalPrice += BASE_SUPER_PRICE;
                break;
        }

        // Calculate price for toppings
        totalPrice += (pepperoniCheckBox.isSelected()) ? 1.00 : 0.00;
        totalPrice += (mushroomsCheckBox.isSelected()) ? 1.00 : 0.00;
        totalPrice += (onionsCheckBox.isSelected()) ? 1.00 : 0.00;
        totalPrice += (sausageCheckBox.isSelected()) ? 1.00 : 0.00;
        totalPrice += (baconCheckBox.isSelected()) ? 1.00 : 0.00;
        totalPrice += (extraCheeseCheckBox.isSelected()) ? 1.00 : 0.00;

        // Calculate tax
        double taxAmount = totalPrice * TAX_RATE;
        totalPrice += taxAmount;

        // Prepare order details
        orderDetails.append("Type of Crust: ");
        if (thinCrustRadioButton.isSelected()) {
            orderDetails.append("Thin");
        } else if (regularCrustRadioButton.isSelected()) {
            orderDetails.append("Regular");
        } else if (deepDishCrustRadioButton.isSelected()) {
            orderDetails.append("Deep-dish");
        }
        orderDetails.append("\nSize: ").append(size);
        orderDetails.append("\nToppings: ");
        if (pepperoniCheckBox.isSelected()) {
            orderDetails.append("Pepperoni, ");
        }
        if (mushroomsCheckBox.isSelected()) {
            orderDetails.append("Mushrooms, ");
        }
        if (onionsCheckBox.isSelected()) {
            orderDetails.append("Onions, ");
        }
        if (sausageCheckBox.isSelected()) {
            orderDetails.append("Sausage, ");
        }
        if (baconCheckBox.isSelected()) {
            orderDetails.append("Bacon, ");
        }
        if (extraCheeseCheckBox.isSelected()) {
            orderDetails.append("Extra Cheese, ");
        }

        // Format order details
        orderDetails.append("\nSub-total: $").append(df.format(totalPrice - taxAmount));
        orderDetails.append("\nTax: $").append(df.format(taxAmount));
        orderDetails.append("\n--------------------------------------");
        orderDetails.append("\nTotal: $").append(df.format(totalPrice));

        // Update text area with order details
        orderTextArea.setText(orderDetails.toString());
    }

    private void clearForm() {
        thinCrustRadioButton.setSelected(false);
        regularCrustRadioButton.setSelected(false);
        deepDishCrustRadioButton.setSelected(false);
        sizeComboBox.setSelectedIndex(0);
        pepperoniCheckBox.setSelected(false);
        mushroomsCheckBox.setSelected(false);
        onionsCheckBox.setSelected(false);
        sausageCheckBox.setSelected(false);
        baconCheckBox.setSelected(false);
        extraCheeseCheckBox.setSelected(false);
        orderTextArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PizzaGUIFrame().setVisible(true);
            }
        });
    }
}
