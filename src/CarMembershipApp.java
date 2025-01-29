import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CarMembershipApp {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/car_membership";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "12345";

    public static void main(String[] args) {
        showWelcomePage();
    }

    private static void showWelcomePage() {
        JFrame welcomeFrame = new JFrame("Welcome to Car Membership");
        welcomeFrame.setSize(600, 500);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setLocationRelativeTo(null);

        // Create a custom panel with background image
        JPanel welcomePanel = new JPanel() {
            private Image backgroundImage = new ImageIcon("C:\\Users\\dell\\Downloads/car.jpeg").getImage();  // Change this path to your image location

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        welcomePanel.setLayout(new BorderLayout());

        JLabel lblWelcome = new JLabel("Welcome to the Car Membership System", SwingConstants.CENTER);
        lblWelcome.setForeground(Color.black);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 24));

        JButton btnStart = new JButton("Start");
        styleButton(btnStart, new Color(76, 175, 80));

        welcomePanel.add(lblWelcome, BorderLayout.CENTER);
        welcomePanel.add(btnStart, BorderLayout.SOUTH);

        welcomeFrame.add(welcomePanel);
        welcomeFrame.setVisible(true);

        btnStart.addActionListener(e -> {
            welcomeFrame.setVisible(false);
            showLoginPage();
        });
    }

    private static void showLoginPage() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 300);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setResizable(false);

        loginFrame.getContentPane().setBackground(new Color(38, 50, 56));

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 1, 10, 10));
        loginPanel.setBackground(new Color(38, 50, 56));

        JLabel lblPrompt = new JLabel("Please select your role:", SwingConstants.CENTER);
        lblPrompt.setForeground(Color.WHITE);
        lblPrompt.setFont(new Font("Arial", Font.BOLD, 16));

        JButton btnUser = new JButton("User");
        JButton btnAdmin = new JButton("Admin");

        styleButton(btnUser, new Color(33, 150, 243));
        styleButton(btnAdmin, new Color(255, 87, 34));

        loginPanel.add(lblPrompt);
        loginPanel.add(btnUser);
        loginPanel.add(btnAdmin);

        loginFrame.add(loginPanel, BorderLayout.CENTER);

        btnUser.addActionListener(e -> {
            loginFrame.setVisible(false);
            showUserInterface();
        });

        btnAdmin.addActionListener(e -> {
            loginFrame.setVisible(false);
            showAdminInterface();
        });

        loginFrame.setVisible(true);
    }

    private static void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(color));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorderPainted(true);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(120, 35));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
    }

    private static void showUserInterface() {
        JFrame frame = new JFrame("User - Car Membership System");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.getContentPane().setBackground(new Color(33, 150, 243));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(550, 400));

        JLabel lblSearchType = new JLabel("Search by:");
        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"Name", "Car Model", "License Plate"});
        JLabel lblSearchValue = new JLabel("Enter Value:");

        JTextField txtSearchValue = new JTextField(20);

        JButton btnSearch = new JButton("Search");
        JButton btnBack = new JButton("Back");

        styleButton(btnSearch, new Color(33, 150, 243));
        styleButton(btnBack, new Color(156, 39, 176));

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblSearchType, gbc);
        gbc.gridx = 1;
        formPanel.add(cmbSearchType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblSearchValue, gbc);
        gbc.gridx = 1;
        formPanel.add(txtSearchValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(btnSearch, gbc);

        gbc.gridy = 3;
        formPanel.add(btnBack, gbc);

        customizeTextField(txtSearchValue);

        frame.add(formPanel, BorderLayout.CENTER);

        btnSearch.addActionListener(e -> {
            String searchValue = txtSearchValue.getText();
            if (searchValue.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a value to search.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                searchMember(cmbSearchType.getSelectedItem().toString(), searchValue);
            }
        });

        btnBack.addActionListener(e -> {
            frame.setVisible(false);
            showLoginPage();
        });

        frame.setVisible(true);
    }

    private static void showAdminInterface() {
        JFrame frame = new JFrame("Admin - Car Membership System");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.getContentPane().setBackground(new Color(233, 30, 99));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(550, 500));

        JLabel lblName = new JLabel("Name:");
        JTextField txtName = new JTextField(20);

        JLabel lblCarModel = new JLabel("Car Model:");
        JTextField txtCarModel = new JTextField(20);

        JLabel lblLicense = new JLabel("License Plate:");
        JTextField txtLicense = new JTextField(20);

        JLabel lblMembershipType = new JLabel("Membership Type:");
        String[] membershipTypes = {"Basic", "Premium", "VIP"};
        JComboBox<String> cmbMembershipType = new JComboBox<>(membershipTypes);

        JLabel lblTimePeriod = new JLabel("Time Period:");
        JLabel lblPrice = new JLabel("Price:");

        JTextField txtTimePeriod = new JTextField(10);
        JTextField txtPrice = new JTextField(10);
        txtTimePeriod.setEditable(false);
        txtPrice.setEditable(false);

        JLabel lblContact = new JLabel("Contact:");
        JTextField txtContact = new JTextField(20);

        JLabel lblPaymentMethod = new JLabel("Payment Method:");
        String[] paymentMethods = {"Jazz Cash", "EasyPaisa", "Bank Account"};
        JComboBox<String> cmbPaymentMethod = new JComboBox<>(paymentMethods);

        JLabel lblAccountNumber = new JLabel("Account Number:");
        JTextField txtAccountNumber = new JTextField(20);

        JButton btnAdd = new JButton("Add");
        JButton btnView = new JButton("View All");
        JButton btnSearch = new JButton("Search");
        JButton btnBack = new JButton("Back");

        styleButton(btnAdd, new Color(33, 150, 243));
        styleButton(btnView, new Color(76, 175, 80));
        styleButton(btnSearch, new Color(255, 87, 34));
        styleButton(btnBack, new Color(156, 39, 176));

        // Update the Time Period and Price based on the Membership Type selected
        cmbMembershipType.addActionListener(e -> {
            String selectedType = (String) cmbMembershipType.getSelectedItem();
            switch (selectedType) {
                case "Basic":
                    txtTimePeriod.setText("1 Month");
                    txtPrice.setText("1000");
                    break;
                case "Premium":
                    txtTimePeriod.setText("6 Months");
                    txtPrice.setText("3000");
                    break;
                case "VIP":
                    txtTimePeriod.setText("12 Months");
                    txtPrice.setText("5000");
                    break;
            }
        });

        // Add components to form panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblName, gbc);
        gbc.gridx = 1;
        formPanel.add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblCarModel, gbc);
        gbc.gridx = 1;
        formPanel.add(txtCarModel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lblLicense, gbc);
        gbc.gridx = 1;
        formPanel.add(txtLicense, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(lblMembershipType, gbc);
        gbc.gridx = 1;
        formPanel.add(cmbMembershipType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(lblTimePeriod, gbc);
        gbc.gridx = 1;
        formPanel.add(txtTimePeriod, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(lblPrice, gbc);
        gbc.gridx = 1;
        formPanel.add(txtPrice, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(lblContact, gbc);
        gbc.gridx = 1;
        formPanel.add(txtContact, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(lblPaymentMethod, gbc);
        gbc.gridx = 1;
        formPanel.add(cmbPaymentMethod, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(lblAccountNumber, gbc);
        gbc.gridx = 1;
        formPanel.add(txtAccountNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        formPanel.add(btnAdd, gbc);

        gbc.gridy = 10;
        formPanel.add(btnView, gbc);

        gbc.gridy = 11;
        formPanel.add(btnSearch, gbc);

        gbc.gridy = 12;
        formPanel.add(btnBack, gbc);

        customizeTextField(txtName);
        customizeTextField(txtCarModel);
        customizeTextField(txtLicense);
        customizeTextField(txtContact);
        customizeTextField(txtAccountNumber);

        frame.add(formPanel, BorderLayout.CENTER);

        // Handle adding a new member
        btnAdd.addActionListener(e -> {
            String name = txtName.getText();
            String carModel = txtCarModel.getText();
            String license = txtLicense.getText();
            String membershipType = cmbMembershipType.getSelectedItem().toString();
            String contact = txtContact.getText();
            String accountNumber = txtAccountNumber.getText();

            if (name.isEmpty() || carModel.isEmpty() || license.isEmpty() || contact.isEmpty() || accountNumber.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                addMember(name, carModel, license, membershipType, contact, accountNumber);
            }
        });

        btnView.addActionListener(e -> viewMembers());

        btnSearch.addActionListener(e -> {
            String license = JOptionPane.showInputDialog(frame, "Enter License Plate:");
            if (license != null && !license.isEmpty()) {
                searchMember("License Plate", license); // Searching by License Plate
            }
        });

        btnBack.addActionListener(e -> {
            frame.setVisible(false);
            showLoginPage();
        });

        frame.setVisible(true);
    }

    private static void addMember(String name, String carModel, String license, String membershipType, String contact, String accountNumber) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String query = "INSERT INTO members (name, car_model, license_plate, membership_type, contact, account_number) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, carModel);
                stmt.setString(3, license);
                stmt.setString(4, membershipType);
                stmt.setString(5, contact);
                stmt.setString(6, accountNumber);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Member added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding member to the database.");
        }
    }

    private static void customizeTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(200, 30));
    }

    private static void searchMember(String searchType, String searchValue) {
        // Perform database search based on the selected search type
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String query = "SELECT * FROM members WHERE " + searchType.toLowerCase().replace(" ", "_") + " LIKE ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, "%" + searchValue + "%");
                ResultSet rs = stmt.executeQuery();
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "No members found.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    do {
                        sb.append("Name: ").append(rs.getString("name"))
                                .append(", Car Model: ").append(rs.getString("car_model"))
                                .append(", License Plate: ").append(rs.getString("license_plate"))
                                .append(", Membership Type: ").append(rs.getString("membership_type"))
                                .append(", Contact: ").append(rs.getString("contact"))
                                .append("\n");
                    } while (rs.next());
                    JOptionPane.showMessageDialog(null, sb.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error searching the database.");
        }
    }

    private static void viewMembers() {
        // Display all members in the database
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String query = "SELECT * FROM members";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "No members found.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    do {
                        sb.append("Name: ").append(rs.getString("name"))
                                .append(", Car Model: ").append(rs.getString("car_model"))
                                .append(", License Plate: ").append(rs.getString("license_plate"))
                                .append(", Membership Type: ").append(rs.getString("membership_type"))
                                .append(", Contact: ").append(rs.getString("contact"))
                                .append("\n");
                    } while (rs.next());
                    JOptionPane.showMessageDialog(null, sb.toString(), "All Members", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving members.");
        }
    }
}
