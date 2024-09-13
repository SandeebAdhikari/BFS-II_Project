package Library_Sandeeb.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class UserGUI {
    public UserApp app;
    public JFrame frame;
    public JPanel userPanel;

    public UserGUI(UserApp app) {
        this.app = app;
        frame = new JFrame("User List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);  // Adjust the window size

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dropdown for selecting user role
        JLabel roleLabel = new JLabel("Role:");
        String[] roles = {"Student", "Teacher"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameInputField = new JTextField(15);
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameInputField = new JTextField(15);
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressInputField = new JTextField(15);
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        JTextField phoneNumberInputField = new JTextField(15);

        JButton addButton = new JButton("Add User");
        JButton viewUsersButton = new JButton("View Users");

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(roleLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(roleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(firstNameInputField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(lastNameInputField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(addressLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(addressInputField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(phoneNumberLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(phoneNumberInputField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewUsersButton);
        inputPanel.add(buttonPanel, gbc);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.setVisible(true);

        // Add user to file and clear inputs
        addButton.addActionListener(e -> {
            String firstName = firstNameInputField.getText();
            String lastName = lastNameInputField.getText();
            String address = addressInputField.getText();
            String phoneNumber = phoneNumberInputField.getText();
            String role = roleComboBox.getSelectedItem().toString();

            if (!(firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || phoneNumber.isEmpty())) {
                app.addUser(firstName, lastName, address, phoneNumber, role);
                firstNameInputField.setText("");
                lastNameInputField.setText("");
                addressInputField.setText("");
                phoneNumberInputField.setText("");
            }
        });

        // Button to show user list in a pop-up with search and delete functionality
        viewUsersButton.addActionListener(e -> {
            showUserListPopup();
        });
    }

    // Method to create and show the pop-up window for listing, searching, and deleting users
    public void showUserListPopup() {
        JDialog userListDialog = new JDialog(frame, "User List", true);
        userListDialog.setSize(500, 400);

        JPanel dialogPanel = new JPanel(new BorderLayout());

        // Search bar and search button
        JPanel searchPanel = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        dialogPanel.add(searchPanel, BorderLayout.NORTH);

        // Panel to list the users
        JPanel userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(userListPanel);
        dialogPanel.add(scrollPane, BorderLayout.CENTER);

        // Refresh the user list
        refreshUserList(userListPanel, "");

        // Search button functionality
        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText();
            refreshUserList(userListPanel, searchTerm);
        });

        // Delete selected users button at the bottom of the popup
        JButton deleteButton = new JButton("Delete Selected Users");
        deleteButton.addActionListener(e -> {
            deleteSelectedUsersFromPopup(userListPanel);
            refreshUserList(userListPanel, searchField.getText()); // Refresh after deletion
        });

        dialogPanel.add(deleteButton, BorderLayout.SOUTH);

        userListDialog.add(dialogPanel);
        userListDialog.setVisible(true);
    }

    // Method to refresh the user list in the pop-up based on search term
    public void refreshUserList(JPanel userListPanel, String searchTerm) {
        userListPanel.removeAll();
        for (User user : app.users) {
            if (user.getFirstName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    user.getLastName().toLowerCase().contains(searchTerm.toLowerCase())) {
                JCheckBox userCheckbox = new JCheckBox(user.toString());
                userCheckbox.setName(String.valueOf(user.getId()));
                userListPanel.add(userCheckbox);
            }
        }
        userListPanel.revalidate();
        userListPanel.repaint();
    }

    // Method to delete selected users from the pop-up window
    public void deleteSelectedUsersFromPopup(JPanel userListPanel) {
        Component[] components = userListPanel.getComponents();
        ArrayList<Integer> usersToDelete = new ArrayList<>();

        // Identify selected users for deletion
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkbox = (JCheckBox) component;
                if (checkbox.isSelected()) {
                    usersToDelete.add(Integer.parseInt(checkbox.getName()));
                }
            }
        }

        // Remove selected users from the app's user list
        Iterator<User> iterator = app.users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (usersToDelete.contains(user.getId())) {
                iterator.remove();
            }
        }

        // Save updated list to the file after deletion
        app.saveUsersToFile();
    }
}
