package Library_Shiv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;

public class BorrowGUI extends JFrame {
    private BorrowApp borrowApp;

    public BorrowGUI(BorrowApp borrowApp) {
        this.borrowApp = borrowApp;
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Library Borrowing System");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JPanel panel = new JPanel();
        JTextField userIdField = new JTextField(5);
        JTextField bookIdField = new JTextField(5);
        JButton borrowButton = new JButton("Borrow");

        panel.add(new JLabel("User ID:"));
        panel.add(userIdField);
        panel.add(new JLabel("Book ID:"));
        panel.add(bookIdField);
        panel.add(borrowButton);

        borrowButton.addActionListener((ActionEvent e) -> {
            int userId = Integer.parseInt(userIdField.getText());
            int bookId = Integer.parseInt(bookIdField.getText());
            // Assuming you have a method to fetch user and book by their IDs
            User user = fetchUserById(userId);
            Book book = fetchBookById(bookId);

            if (book.isAvailable()) {
                Borrow borrow = new Borrow(user, book);
                borrowApp.addBorrow(borrow);
                borrowApp.saveBorrowsToFile("borrows.json");
                textArea.setText("Book borrowed successfully on " +
                        borrow.getBorrowDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            } else {
                textArea.setText("Book is not available.");
            }

        });

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        setVisible(true);
    }

    // Mock methods to demonstrate functionality
    private User fetchUserById(int id) {
        return new User(id, "John", "Doe", "123 Main St", "1234567890", "Student");
    }

    private Book fetchBookById(int id) {
        return new Book(123, 1, "Effective Java", "Joshua Bloch", "3rd", 2018);
    }

    public static void main(String[] args) {
        BorrowApp app = new BorrowApp();
        new BorrowGUI(app);
    }
}

