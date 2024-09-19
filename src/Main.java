package Library_Sandeeb.Main;

import Library_Sandeeb.Borrow.BorrowGUI;
import Library_Sandeeb.Borrow.BorrowApp;
import Library_Sandeeb.User.User;
import Library_Sandeeb.User.UserGUI;
import Library_Sandeeb.User.UserApp;
import Library_Sandeeb.Book.BookApp;
import Library_Sandeeb.Book.BookGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main extends JFrame {
    private static BookApp bookApp;
    private static UserApp userApp;
    private static BorrowApp borrowApp;


    public Main() {
       userApp = new UserApp();
        borrowApp = new BorrowApp();
        this.bookApp = new BookApp();
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Library System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");

        loginButton.setFont(new Font("Arial", Font.PLAIN, 24));
        signUpButton.setFont(new Font("Arial", Font.PLAIN, 24));

        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createVerticalStrut(100));
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(signUpButton);

        add(buttonPanel, BorderLayout.CENTER);

        JButton adminButton = new JButton("Admin");
        adminButton.setFont(new Font("Arial", Font.PLAIN, 24));

        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.add(adminButton);

        add(topRightPanel, BorderLayout.NORTH);

        signUpButton.addActionListener((ActionEvent e) -> {
            new UserGUI(userApp);
        });

        loginButton.addActionListener((ActionEvent e) -> {
            showLoginPopup();
        });

        adminButton.addActionListener((ActionEvent e) -> {
            showAdminPopup();
        });

        setVisible(true);
    }

    private void showLoginPopup() {
        JDialog loginDialog = new JDialog(this, "Log In", true);
        loginDialog.setSize(600, 400);
        loginDialog.setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));


        JLabel nameLabel = new JLabel("User Name");
        JTextField nameField = new JTextField();
        JLabel phoneLabel = new JLabel("Password");
        JTextField phoneField = new JTextField();


        nameLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        nameField.setFont(new Font("Arial", Font.PLAIN, 24));
        phoneField.setFont(new Font("Arial", Font.PLAIN, 24));


        nameField.setMaximumSize(new Dimension(300, 40));
        phoneField.setMaximumSize(new Dimension(300, 40));


        JButton loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 24));


        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        phoneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        phoneField.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        loginPanel.add(Box.createVerticalStrut(50));
        loginPanel.add(nameLabel);
        loginPanel.add(nameField);
        loginPanel.add(Box.createVerticalStrut(20));
        loginPanel.add(phoneLabel);
        loginPanel.add(phoneField);
        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(loginButton);


        loginDialog.add(loginPanel, BorderLayout.CENTER);


        loginButton.addActionListener(e -> {
            String firstName = nameField.getText().trim();
            String phoneNumber = phoneField.getText().trim();

            // Validate login credentials
            boolean loginSuccess = userApp.validateLogin(firstName, phoneNumber);

            if (loginSuccess) {
                // Fetch the logged-in user
                User loggedInUser = userApp.fetchUserByCredentials(firstName, phoneNumber);

                if (loggedInUser != null) {
                    loginDialog.dispose();  // Close login pop-up
                    new BorrowGUI(borrowApp, loggedInUser);  // Pass the logged-in user to BorrowGUI
                }
            } else {
                JOptionPane.showMessageDialog(loginDialog, "Invalid credentials. Try again.");
            }
        });


        loginDialog.setLocationRelativeTo(null);
        loginDialog.setVisible(true);
    }

    private void showAdminPopup() {
        JDialog adminDialog = new JDialog(this, "Admin Login", true);
        adminDialog.setSize(600, 400);
        adminDialog.setLayout(new BorderLayout());

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));

        JLabel userNameLabel = new JLabel("Username:");
        JTextField userNameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Admin Login");

        userNameLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        userNameField.setFont(new Font("Arial", Font.PLAIN, 24));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 24));
        loginButton.setFont(new Font("Arial", Font.PLAIN, 24));

        userNameField.setMaximumSize(new Dimension(300, 40));
        passwordField.setMaximumSize(new Dimension(300, 40));

        userNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        adminPanel.add(Box.createVerticalStrut(50));
        adminPanel.add(userNameLabel);
        adminPanel.add(userNameField);
        adminPanel.add(Box.createVerticalStrut(20));
        adminPanel.add(passwordLabel);
        adminPanel.add(passwordField);
        adminPanel.add(Box.createVerticalStrut(30));
        adminPanel.add(loginButton);

        adminDialog.add(adminPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> {
            String username = userNameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.equals("admin") && password.equals("admin")) {
                adminDialog.dispose();
                new BookGUI(bookApp);
            } else {
                JOptionPane.showMessageDialog(adminDialog, "Invalid admin credentials. Try again.");
            }
        });

        adminDialog.setLocationRelativeTo(null);
        adminDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
