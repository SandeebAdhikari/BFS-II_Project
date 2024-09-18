package Library_Shiv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BorrowGUI extends JFrame {
    private BorrowApp borrowApp;

    public BorrowGUI(BorrowApp borrowApp) {
        this.borrowApp = borrowApp;
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Library Borrowing System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchPanel.add(new JLabel("Search Book:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(outputPanel);

        JButton borrowButton = new JButton("Borrow Selected Book");

        searchButton.addActionListener((ActionEvent e) -> {
            String searchQuery = searchField.getText().trim();
            outputPanel.removeAll();  // Clear previous results

            if (!searchQuery.isEmpty()) {
                List<Book> matchingBooks = borrowApp.searchBooks(searchQuery);
                for (Book book : matchingBooks) {
                    JCheckBox bookCheckbox = new JCheckBox(book.getBookName() + " (ISBN: " + book.getISBN() + ")");
                    bookCheckbox.setName(String.valueOf(book.getId()));
                    outputPanel.add(bookCheckbox);
                }
            } else {
                outputPanel.add(new JLabel("No search query entered."));
            }

            outputPanel.revalidate();
            outputPanel.repaint();
        });

        borrowButton.addActionListener(e -> {
            for (Component comp : outputPanel.getComponents()) {
                if (comp instanceof JCheckBox) {
                    JCheckBox checkbox = (JCheckBox) comp;
                    if (checkbox.isSelected()) {
                        int bookId = Integer.parseInt(checkbox.getName());
                        Book book = borrowApp.fetchBookById(bookId);
                        if (book != null && book.isAvailable()) {
                            showBorrowPopup(book);
                        }
                    }
                }
            }
        });

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(borrowButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void showBorrowPopup(Book book) {
        JDialog borrowDialog = new JDialog(this, "Borrow Book", true);
        borrowDialog.setSize(400, 300);
        borrowDialog.setLayout(new BorderLayout());


        LocalDateTime borrowDate = LocalDateTime.now();
        LocalDateTime returnDate = borrowDate.plusMonths(4);
        JPanel datePanel = new JPanel(new GridLayout(3, 1));
        datePanel.add(new JLabel("Borrow Date: " + borrowDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        datePanel.add(new JLabel("Return Date: " + returnDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));
        datePanel.add(new JLabel("Library Hours:"));
        datePanel.add(new JLabel("MON - FRI: 8 AM - 8 PM"));
        datePanel.add(new JLabel("SAT: 9 AM - 4 PM"));
        datePanel.add(new JLabel("SUN: CLOSED"));

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            User user = borrowApp.fetchUserById(1);  // Dummy user for demo purposes, replace with actual user ID logic
            Borrow borrow = new Borrow(user, book);
            borrowApp.addBorrow(borrow);
            borrowApp.saveBorrowsToFile("borrow.json");
            borrowDialog.dispose();
        });

        borrowDialog.add(datePanel, BorderLayout.CENTER);
        borrowDialog.add(okButton, BorderLayout.SOUTH);

        borrowDialog.setVisible(true);
    }

    public static void main(String[] args) {
        BorrowApp app = new BorrowApp();
        new BorrowGUI(app);
    }
}
