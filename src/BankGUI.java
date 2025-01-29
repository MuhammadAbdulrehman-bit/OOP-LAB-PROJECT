import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LoginBankGUI extends JFrame {
    LoginBankGUI() {
        setVisible(true);
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("LOGIN FORM");

        JPanel p1 = new JPanel(new GridBagLayout());
        p1.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Padding

        // Heading (Centered)
        JLabel heading = new JLabel("LOGIN FORM");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        p1.add(heading, gbc);

        // Add vertical space below the heading
        gbc.gridy = 1;
        gbc.weighty = 0.3;
        p1.add(Box.createVerticalGlue(), gbc);
        gbc.weighty = 0;

        // Pin label and field
        JLabel pin = new JLabel("Pin Number");
        pin.setForeground(Color.WHITE);
        pin.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        p1.add(pin, gbc);

        JPasswordField pinPass = new JPasswordField(20);
        pinPass.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        p1.add(pinPass, gbc);

        // Add vertical space after pin field
        gbc.gridy = 3;
        gbc.weighty = 0.1;
        gbc.gridwidth = 2;
        p1.add(Box.createVerticalGlue(), gbc);
        gbc.weighty = 0;
        gbc.gridwidth = 1;

        // Account Number label and field
        JLabel accountNo = new JLabel("Account Number");
        accountNo.setForeground(Color.WHITE);
        accountNo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        p1.add(accountNo, gbc);

        JTextField accountNotxt = new JTextField(20);
        accountNotxt.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        p1.add(accountNotxt, gbc);

        // Add vertical space after account number field
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weighty = 0.5;
        p1.add(Box.createVerticalGlue(), gbc);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.DARK_GRAY);


        JButton b1 = new JButton("Submit") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                super.paintComponent(g);
            }
        };
        b1.setPreferredSize(new Dimension(100, 40));
        b1.setFont(new Font("Arial", Font.BOLD, 16));
        b1.setForeground(Color.WHITE);
        b1.setBackground(Color.BLUE);
        b1.setFocusPainted(false);
        b1.setBorderPainted(false);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pin = new String(pinPass.getPassword());
                String accountNumber = accountNotxt.getText();

                DatabaseManager db = new DatabaseManager();
                boolean loginSuccess = db.validateLogin(pin, accountNumber);
                if (loginSuccess) {
                    JOptionPane.showMessageDialog(null, "Login Success");
                    BankGUI bank = new BankGUI();
                    bank.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Login");
                }
                db.closeConnection();
            }
        });

        // Admin Login button
        JButton adminButton = new JButton("Admin Login") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                super.paintComponent(g);
            }
        };
        adminButton.setPreferredSize(new Dimension(140, 40));
        adminButton.setFont(new Font("Arial", Font.BOLD, 16));
        adminButton.setForeground(Color.WHITE);
        adminButton.setBackground(Color.RED);
        adminButton.setFocusPainted(false);
        adminButton.setBorderPainted(false);

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminLoginGUI adminLogin = new AdminLoginGUI();
                adminLogin.setVisible(true);
                dispose();
            }
        });

        // Add buttons to the panel
        buttonPanel.add(b1);
        buttonPanel.add(adminButton);

        // Add the button panel to the main panel
        gbc.gridy = 6; // Place it below other fields
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        p1.add(buttonPanel, gbc);

        // Final setup
        add(p1);
        p1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }
}

class BankGUI extends JFrame {
    BankGUI() {
        setVisible(true);
        setTitle("Dash Board");
        setSize(1080, 620);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel welcomePanel = new JPanel() {
            private Image backgroundImage = new ImageIcon("E:\\Programming Profile\\Self work Important Must Read Program\\Java\\AtmProject\\src/BankPic2.jpeg").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        setContentPane(welcomePanel);
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(70,190));
        headerPanel.setOpaque(false);


        JLabel heading = new JLabel("Welcome to Apna Bank!", SwingConstants.CENTER);
        heading.setFont(new Font("Serif", Font.BOLD, 56));
        heading.setForeground(new Color(0, 0, 0, 255));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        headerPanel.add(heading);

        JPanel toLoginPanel = new JPanel();
        toLoginPanel.setLayout(new FlowLayout());
        toLoginPanel.setOpaque(false);

        JButton toLoginBtn = new JButton("To Login");
        toLoginBtn.setFont(new Font("Serif", Font.BOLD, 26));
        toLoginBtn.setPreferredSize(new Dimension(250, 60));
        toLoginBtn.setBackground(new Color(26, 83, 89, 255));
        toLoginBtn.setForeground(Color.WHITE);

        toLoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginBankGUI login = new LoginBankGUI();
                login.setVisible(true);
            }
        });

        toLoginPanel.add(toLoginBtn);
        headerPanel.add(toLoginPanel,BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        JPanel container2 = new JPanel();
        container2.setLayout(new FlowLayout());
        container2.setOpaque(false);

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(1,3,20,20));
        buttonContainer.setOpaque(false);

        JButton deposit = new JButton("Deposit");
        deposit.setPreferredSize(new Dimension(250, 80));
        deposit.setBackground(new Color(36, 83, 89, 255));
        deposit.setForeground(Color.WHITE);
        deposit.setFont(new Font("Arial", Font.BOLD, 26));

        JButton withdraw = new JButton("With Draw");
        withdraw.setPreferredSize(new Dimension(250, 80));
        withdraw.setBackground(new Color(26, 83, 89, 255));
        withdraw.setForeground(Color.WHITE);
        withdraw.setFont(new Font("Arial", Font.BOLD, 26));

        JButton checkBalance = new JButton("Check Balance");
        checkBalance.setPreferredSize(new Dimension(250, 80));
        checkBalance.setFont(new Font("Arial", Font.BOLD, 26));
        checkBalance.setForeground(Color.WHITE);
        checkBalance.setBackground(new Color(26, 83, 89, 255));


        buttonContainer.add(deposit);
        buttonContainer.add(withdraw);
        buttonContainer.add(checkBalance);

        deposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame depositFrame = new JFrame();
                depositFrame.setVisible(true);
                depositFrame.setSize(450,400);
                depositFrame.setLayout(new BorderLayout());
                depositFrame.setTitle("Deposit");

                JPanel depositPanel = new JPanel();
                depositPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20,20));
                depositPanel.setBackground(Color.LIGHT_GRAY);

                JLabel sAccountNumber = new JLabel("Sender Account");
                sAccountNumber.setFont(new Font("Times New Roman", Font.BOLD, 18));
                JTextField saccountTxt = new JTextField();
                saccountTxt.setPreferredSize(new Dimension(400,30));

                JLabel depositAmount = new JLabel("Deposit ");
                depositAmount.setFont(new Font("Times New Roman", Font.BOLD, 18));
                JTextField depositAmountTxt = new JTextField();
                depositAmountTxt.setPreferredSize(new Dimension(400,30));

                JLabel rAccountNumber = new JLabel("Recipent Account ");
                rAccountNumber.setFont(new Font("Times New Roman", Font.BOLD, 18));
                JTextField raccountTxt = new JTextField();
                raccountTxt.setPreferredSize(new Dimension(400,30));

                depositPanel.add(sAccountNumber);
                depositPanel.add(saccountTxt);
                depositPanel.add(rAccountNumber);
                depositPanel.add(raccountTxt);
                depositPanel.add(depositAmount);
                depositPanel.add(depositAmountTxt);

                depositFrame.add(depositPanel, BorderLayout.CENTER);

                JPanel depositButtons = new JPanel();

                JButton confirmButton = new JButton("Confirm");
                confirmButton.setPreferredSize(new Dimension(80,40));

                JButton cancelButton = new JButton("Cancel");
                cancelButton.setPreferredSize(new Dimension(80,40));

                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DatabaseManager myDb = null;
                        try {
                            String sAccountNumber = new String(saccountTxt.getText());
                            String rAccountNumber = new String(raccountTxt.getText());
                            double amount = Double.parseDouble(depositAmountTxt.getText());

                            myDb = new DatabaseManager();
                            if (myDb.validateDepoist(sAccountNumber, rAccountNumber)) {
                                if (amount < 0) {
                                    JOptionPane.showMessageDialog(null, "Deposit amount must be greater than 0");
                                    return;
                                } else {
                                    boolean success = myDb.Deposit(sAccountNumber,rAccountNumber ,amount);
                                    if(success) {
                                        JOptionPane.showMessageDialog(null, "Deposit successful");
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(null, "Deposit was not successfull");
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid Account Number");
                            }
                        }catch(NumberFormatException ex){
                            JOptionPane.showMessageDialog(null,"Error encountered" + ex.getMessage());
                        }
                        finally{
                            if(myDb != null){
                                myDb.closeConnection();
                            }
                        }
                    }
                });
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        depositFrame.dispose();
                    }
                });

                depositPanel.add(confirmButton);
                depositPanel.add(cancelButton);
            }
        });

        withdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame withdrawFrame = new JFrame();
                withdrawFrame.setSize(450,400);
                withdrawFrame.setBackground(Color.LIGHT_GRAY);
                withdrawFrame.setVisible(true);
                withdrawFrame.setLayout(new BorderLayout());
                withdrawFrame.setTitle("WithDraw");

                JPanel withDrawPanel = new JPanel();
                withDrawPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20,20));

                JLabel withPin = new JLabel("Pin Numnber");
                withPin.setFont(new Font("Times New Roman", Font.BOLD, 18));
                JTextField withPinTxt = new JTextField();
                withPinTxt.setPreferredSize(new Dimension(400, 30));

                JLabel withAmount = new JLabel("WithDraw Amount");
                withAmount.setFont(new Font("Times New Roman", Font.BOLD, 18));
                JTextField withAmountTxt = new JTextField();
                withAmountTxt.setPreferredSize(new Dimension(400, 30));

                JButton withSubmit = new JButton("WithDraw");
                JButton withCancel = new JButton("Cancel");

                withSubmit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String pin = withPinTxt.getText().trim();
                        double amount;

                        try {
                            amount = Double.parseDouble(withAmountTxt.getText().trim());
                            if (amount <= 0) {
                                JOptionPane.showMessageDialog(null, "Amount must be greater than 0");
                                return;
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.");
                            return;
                        }

                        DatabaseManager myDb = new DatabaseManager();

                        try {
                            if (myDb.validateWithDraw(pin)) {
                                boolean success = myDb.Withdraw(pin, amount);
                                if (success) {
                                    JOptionPane.showMessageDialog(null, "Withdrawal successful.");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Insufficient balance or invalid PIN.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid PIN.");
                            }
                        } catch (Exception withEx) {
                            JOptionPane.showMessageDialog(null, "Error: " + withEx.getMessage());
                        } finally {
                            myDb.closeConnection();
                        }
                    }
                });

                withCancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        withdrawFrame.dispose();
                    }
                });


                withDrawPanel.add(withPin);
                withDrawPanel.add(withPinTxt);
                withDrawPanel.add(withAmount);
                withDrawPanel.add(withAmountTxt);
                withDrawPanel.add(withSubmit);
                withDrawPanel.add(withCancel);

                withdrawFrame.add(withDrawPanel);
            }
        });

        checkBalance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame checkFrame = new JFrame();
                checkFrame.setSize(950,100);
                checkFrame.setBackground(Color.LIGHT_GRAY);
                checkFrame.setVisible(true);
                checkFrame.setTitle("Check Balance");
                checkFrame.setBackground(Color.LIGHT_GRAY);

                JPanel checkPanel = new JPanel();

                JLabel checkBPin = new JLabel("Pin");
                checkBPin.setFont(new Font("Times New Roman", Font.BOLD, 18));
                JTextField checkBPinTxt = new JTextField();
                checkBPinTxt.setPreferredSize(new Dimension(400, 20));
                JButton checkSubmit = new JButton("Submit");
                JButton checkCancel = new JButton("Cancel");

                checkSubmit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DatabaseManager myDb = new DatabaseManager();
                        try {
                            String pin = checkBPinTxt.getText();
                            double balance = myDb.CheckBalance(pin);

                            if (balance != -1) {
                                JOptionPane.showMessageDialog(null, "Your Balance is: " + balance);
                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid Pin or no Account found");
                            }
                        }catch(NumberFormatException checkEx){
                            JOptionPane.showMessageDialog(null, "Only type in numbers: " +checkEx.getMessage() );
                        }
                        finally{
                            myDb.closeConnection();
                        }
                    }
                });

                checkCancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        checkFrame.dispose();
                    }
                });

                checkPanel.add(checkBPin);
                checkPanel.add(checkBPinTxt);
                checkPanel.add(checkSubmit);
                checkPanel.add(checkCancel);

                checkFrame.add(checkPanel);
            }
        });

        container2.add(buttonContainer, BorderLayout.CENTER);

        welcomePanel.add(headerPanel);
        welcomePanel.add(Box.createVerticalStrut(50));
        welcomePanel.add(container2);

        add(welcomePanel);

    }

    public static void main(String[] args) {
        new BankGUI();
    }
}

class AdminLoginGUI extends JFrame {
    public AdminLoginGUI() {
        setTitle("Admin Login");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel heading = new JLabel("ADMIN LOGIN");
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(heading, gbc);

        JLabel adminIdLabel = new JLabel("Employee ID:");
        adminIdLabel.setFont(new Font("Arial", Font.BOLD, 18));
        adminIdLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(adminIdLabel, gbc);

        JTextField adminIdField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(adminIdField, gbc);

        JLabel deptIdLabel = new JLabel("Department ID:");
        deptIdLabel.setFont(new Font("Arial", Font.BOLD, 18));
        deptIdLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(deptIdLabel, gbc);

        JTextField deptIdField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(deptIdField, gbc);

        JButton loginButton = new JButton("Submit");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(Color.BLUE);
        loginButton.setForeground(Color.WHITE);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String adminId = adminIdField.getText();
                String deptId = deptIdField.getText();

                DatabaseManager db = new DatabaseManager();
                boolean loginSuccess = db.validateAdminLogin(adminId, deptId);
                if (loginSuccess) {
                    JOptionPane.showMessageDialog(null, "Admin Login Successful");
                    dispose();
                    AdminBankGUI admin = new AdminBankGUI();

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Admin Credentials");
                }
                db.closeConnection();
            }
        });


        JButton toLoginButton = new JButton("To Client Login");
        toLoginButton.setFont(new Font("Arial", Font.BOLD, 16));
        toLoginButton.setBackground(Color.RED);
        toLoginButton.setForeground(Color.WHITE);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(toLoginButton, gbc);

        toLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current AdminLoginGUI window
                LoginBankGUI login = new LoginBankGUI();
                login.setVisible(true);
            }
        });

        add(panel);
    }
}
