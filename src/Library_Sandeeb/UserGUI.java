package Library_Sandeeb;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class UserGUI {
    public UserApp app;
    public JFrame frame;
    public JPanel studentPanel;

    public UserGUI(UserApp app) {
        this.app = app;
        frame = new JFrame("Student List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);  // Adjust the window size

        // Use GridBagLayout for precise control
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Center the inputPanel in both X and Y axis
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels and Input fields (First name and First name input field on the same line)
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameInputField = new JTextField(15);
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameInputField = new JTextField(15);
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressInputField = new JTextField(15);
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        JTextField phoneNumberInputField = new JTextField(15);

        // Search Field and Button
        JLabel searchLabel = new JLabel("Search:");
        JTextField searchStudentInputField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        // Add and Delete Buttons
        JButton addButton = new JButton("Add Student");
        JButton deleteButton = new JButton("Delete Student");

        // First Name Row
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(firstNameInputField, gbc);

        // Last Name Row
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(lastNameInputField, gbc);

        // Address Row
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(addressLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(addressInputField, gbc);

        // Phone Number Row
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(phoneNumberLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(phoneNumberInputField, gbc);

        // Search Row
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(searchLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(searchStudentInputField, gbc);
        gbc.gridx = 2;
        inputPanel.add(searchButton, gbc);

        // Add and Delete Buttons on Same Line
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        inputPanel.add(buttonPanel, gbc);

        // Main Panel
        studentPanel = new JPanel();
        studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(studentPanel);
        scrollPane.setPreferredSize(new Dimension(200, 200));

        // Centering the input panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        frame.add(mainPanel);

        // Action listeners
        addButton.addActionListener(e -> {
            String studentFirstName = firstNameInputField.getText();
            String studentLastName = lastNameInputField.getText();
            String studentAddress = addressInputField.getText();
            String studentPhoneNumber = phoneNumberInputField.getText();

            if (!(studentFirstName.isEmpty() || studentLastName.isEmpty() || studentAddress.isEmpty() || studentPhoneNumber.isEmpty())) {
                app.addStudent(studentFirstName, studentLastName, studentAddress, studentPhoneNumber);
                firstNameInputField.setText("");
                lastNameInputField.setText("");
                addressInputField.setText("");
                phoneNumberInputField.setText("");
                updateStudentArea("");
            }
        });

        searchButton.addActionListener(e -> {
            String keyword = searchStudentInputField.getText();
            if (!keyword.isEmpty()) {
                updateStudentArea(keyword);
            } else {
                updateStudentArea("");
            }
        });

        deleteButton.addActionListener(e -> {
            deleteSelectedStudents();
            updateStudentArea("");
        });

        frame.setVisible(true);
        updateStudentArea("");
    }

    public void updateStudentArea(String searchTerm) {
        studentPanel.removeAll();

        for (User student : app.users) {
            JCheckBox studentCheckbox = new JCheckBox(student.toString());
            studentCheckbox.setName(String.valueOf(student.getId()));

            if (student.getFirstName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    student.getLastName().toLowerCase().contains(searchTerm.toLowerCase())) {
                studentPanel.add(studentCheckbox);
            }
        }

        studentPanel.revalidate();
        studentPanel.repaint();
    }

    public void deleteSelectedStudents() {
        Component[] components = studentPanel.getComponents();
        ArrayList<Integer> studentsToDelete = new ArrayList<>();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkbox = (JCheckBox) component;
                if (checkbox.isSelected()) {
                    studentsToDelete.add(Integer.parseInt(checkbox.getName()));
                }
            }
        }

        Iterator<User> iterator = app.users.iterator();
        while (iterator.hasNext()) {
            User student = iterator.next();
            if (studentsToDelete.contains(student.getId())) {
                iterator.remove();
            }
        }
    }
}
