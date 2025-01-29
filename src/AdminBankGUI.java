import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;





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

        JButton rollBack = new JButton("Reload");
        rollBack.setPreferredSize(new Dimension(250, 60));
        rollBack.setBackground(new Color(36, 63, 135));

        JButton commit = new JButton("Commit");
        rollBack.setPreferredSize(new Dimension(250, 60));
        rollBack.setBackground(new Color(36, 63, 135));

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

        rollBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseManager myDb = new DatabaseManager();
                boolean success = myDb.rollbackTransaction();
                    if(success){
                        JOptionPane.showMessageDialog(null, "Rolled Back Successs");
                    }
                else{
                    JOptionPane.showMessageDialog(null, "Error in roll back");
                }
            }
        });

        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseManager myDb = new DatabaseManager();
                boolean success = myDb.commitTransaction();
                if(success){
                    JOptionPane.showMessageDialog(null, "Commited Back Successs");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Error in commit");
                }
            }
        });


        buttonContainer.add(deleteRow);
        buttonContainer.add(commit);
        buttonContainer.add(rollBack);


        welcomePanel.add(headerPanel);
        welcomePanel.add(buttonContainer);
        add(welcomePanel);

    }

    public static void main(String[] args){
        new AdminBankGUI();
    }
}