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


        viewUsersButton.addActionListener(e -> {
            showUserListPopup();
        });
    }


    public void showUserListPopup() {
        JDialog userListDialog = new JDialog(frame, "User List", true);
        userListDialog.setSize(500, 400);

        JPanel dialogPanel = new JPanel(new BorderLayout());


        JPanel searchPanel = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        dialogPanel.add(searchPanel, BorderLayout.NORTH);


        JPanel userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(userListPanel);
        dialogPanel.add(scrollPane, BorderLayout.CENTER);


        refreshUserList(userListPanel, "");


        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText();
            refreshUserList(userListPanel, searchTerm);
        });


        JButton deleteButton = new JButton("Delete Selected Users");
        deleteButton.addActionListener(e -> {
            deleteSelectedUsersFromPopup(userListPanel);
            refreshUserList(userListPanel, searchField.getText()); // Refresh after deletion
        });

        dialogPanel.add(deleteButton, BorderLayout.SOUTH);

        userListDialog.add(dialogPanel);
        userListDialog.setVisible(true);
    }


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


    public void deleteSelectedUsersFromPopup(JPanel userListPanel) {
        Component[] components = userListPanel.getComponents();
        ArrayList<Integer> usersToDelete = new ArrayList<>();


        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkbox = (JCheckBox) component;
                if (checkbox.isSelected()) {
                    usersToDelete.add(Integer.parseInt(checkbox.getName()));
                }
            }
        }


        Iterator<User> iterator = app.users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (usersToDelete.contains(user.getId())) {
                iterator.remove();
            }
        }


        app.saveUsersToFile();
    }
}
