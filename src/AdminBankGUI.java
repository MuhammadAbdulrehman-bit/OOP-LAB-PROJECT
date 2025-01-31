import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


class AdminBankGUI extends JFrame {
    AdminBankGUI() {
        setVisible(true);
        setTitle("Dash Board");
        setSize(1080, 620);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(30, 30));

        JPanel welcomePanel = new JPanel() {
            private Image backgroundImage = new ImageIcon("E:\\Programming Profile\\Self work Important Must Read Program\\Java\\AtmProject\\src/bank.jpeg").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        setContentPane(welcomePanel);


        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 70));
        headerPanel.setBackground(new Color(36, 63, 135));


        JLabel heading = new JLabel("Welcome back Admin", SwingConstants.CENTER);
        heading.setFont(new Font("Serif", Font.BOLD, 36));
        heading.setForeground(Color.WHITE);
        headerPanel.add(heading, BorderLayout.CENTER);

        JPanel toLoginPanel = new JPanel();
        toLoginPanel.setLayout(new FlowLayout());
        toLoginPanel.setBackground(new Color(36, 63, 135));

        JButton toLoginBtn = new JButton("To Login");
        toLoginBtn.setFont(new Font("Serif", Font.BOLD, 26));
        toLoginBtn.setPreferredSize(new Dimension(250, 60));
        toLoginBtn.setBackground(new Color(26, 83, 89, 255));
        toLoginBtn.setForeground(Color.WHITE);
        toLoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminLoginGUI login = new AdminLoginGUI();
                login.setVisible(true);
            }
        });

        toLoginPanel.add(toLoginBtn);
        headerPanel.add(toLoginPanel,BorderLayout.WEST);


        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(1,3, 40,40));


        JButton deleteRow = new JButton("Delete Record");
        deleteRow.setPreferredSize(new Dimension(250, 60));
        deleteRow.setBackground(new Color(36, 63, 135));
        deleteRow.setForeground(Color.WHITE);
        deleteRow.setFont(new Font("Times New Roman", Font.BOLD, 22));

        JButton createRow = new JButton("Create Row");
        createRow.setPreferredSize(new Dimension(250, 60));
        createRow.setBackground(new Color(36, 63, 135));
        createRow.setForeground(Color.WHITE);
        createRow.setFont(new Font("Times New Roman", Font.BOLD, 22));

        deleteRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseManager mydb = new DatabaseManager(); // Initialize database manager

                try {

                    String pin = JOptionPane.showInputDialog("Enter Pin Number:");

                    // Handle if the user cancels the input dialog
                    if (mydb.getConnection() == null) {
                        JOptionPane.showMessageDialog(null, "Database connection could not be established.");
                        return;
                    }

                    if (pin == null) {
                        JOptionPane.showMessageDialog(null, "Operation cancelled.");
                        return; // Exit the method
                    }

                    // Validate that the input is not empty and is numeric
                    if (pin.isEmpty() || !pin.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "Invalid PIN. Please enter a valid numeric PIN.");
                        return; // Exit the method
                    }

                    // Perform the delete operation
                    boolean success = mydb.deleteRowByPin(pin);

                    // Show appropriate message based on success
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Row deleted successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Row was not deleted. The PIN may not exist.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage());
                } finally {
                    mydb.closeConnection();
                }
            }
        });

        createRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame createFrame = new JFrame();
                createFrame.setVisible(true);
                createFrame.setSize(450,600);
                createFrame.setLayout(new BorderLayout());
                createFrame.setTitle("Create Row");

                JPanel createPanel = new JPanel();
                createPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20,20));
                createPanel.setBackground(Color.LIGHT_GRAY);

                JLabel crPin = new JLabel("Pin");
                crPin.setFont(new Font("Times New Roman", Font.BOLD, 18));
                JTextField crPinTxt = new JTextField();
                crPinTxt.setPreferredSize(new Dimension(400,30));

                JLabel craccountNumber = new JLabel("Account Number");
                craccountNumber.setFont(new Font("Times New Roman", Font.BOLD, 18));
                JTextField craccountNumberTxt = new JTextField();
                craccountNumberTxt.setPreferredSize(new Dimension(400,30));

                JLabel crbalance = new JLabel("Balance");
                crbalance.setFont(new Font("Times New Roman", Font.BOLD, 18));
                JTextField crbalanceTxt = new JTextField();
                crbalanceTxt.setPreferredSize(new Dimension(400,30));

                JLabel crName = new JLabel("Name");
                crName.setFont(new Font("Times New Roman", Font.BOLD, 18));
                JTextField crNameTxt = new JTextField();
                crNameTxt.setPreferredSize(new Dimension(400,30));


                createPanel.add(crPin);
                createPanel.add(crPinTxt);
                createPanel.add(craccountNumber);
                createPanel.add(craccountNumberTxt);
                createPanel.add(crbalance);
                createPanel.add(crbalanceTxt);
                createPanel.add(crName);
                createPanel.add(crNameTxt);

                createFrame.add(createPanel, BorderLayout.CENTER);


                JButton confirmButton = new JButton("Confirm");
                confirmButton.setPreferredSize(new Dimension(80,40));

                JButton cancelButton = new JButton("Cancel");
                cancelButton.setPreferredSize(new Dimension(80,40));

                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DatabaseManager myDb = null;
                        try {

                            String pin = crPinTxt.getText();
                            int accountNumber = Integer.parseInt(craccountNumberTxt.getText());
                            double balance = Double.parseDouble(crbalanceTxt.getText());
                            String name = crNameTxt.getText();

                            myDb = new DatabaseManager();


                            if (crPinTxt.getText().isEmpty() || craccountNumberTxt.getText().isEmpty() ||
                                    crbalanceTxt.getText().isEmpty() || crNameTxt.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "All fields must be filled!");
                                return;
                            }

                            if (myDb.createRow(pin, accountNumber, balance, name)) {
                                JOptionPane.showMessageDialog(null, "Rows inserted success");
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Rows not inserted due to Error");
                            }

                        }

                        catch(Exception ex){
                            System.out.println(ex.getMessage());
                        }
                    }
                });

                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        createFrame.dispose();
                    }
                });

                createPanel.add(confirmButton);
                createPanel.add(cancelButton);

            }
        });

        buttonContainer.add(deleteRow);
        buttonContainer.add(createRow);
        buttonContainer.setOpaque(false);

        welcomePanel.add(headerPanel);
        welcomePanel.add(buttonContainer);

    }

    public static void main(String[] args){
        new AdminBankGUI();
    }
}