package Library_Sandeeb.Borrow;

import Library_Sandeeb.Book.Book;
import Library_Sandeeb.User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BorrowGUI extends JFrame {
    private User loggedInUser;
    private BorrowApp borrowApp;
    //private BookApp bookApp;

    public JTextField bookIdField;
    private JTextArea textArea;

    public BorrowGUI(BorrowApp borrowApp,User loggedInUser) {
        this.borrowApp = borrowApp;
        this.loggedInUser = loggedInUser;
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Borrow a Book");
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

        bookIdField = new JTextField(5);

        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);

        JButton borrowButton = new JButton("Borrow Selected Book");

        searchButton.addActionListener((ActionEvent e) -> {
            String searchQuery = searchField.getText().trim();
            outputPanel.removeAll();

            if (!searchQuery.isEmpty()) {
                List<Book> matchingBooks = borrowApp.searchBooks(searchQuery);
                if (matchingBooks.isEmpty()) {
                    outputPanel.add(new JLabel("No matching books found."));
                } else {
                    for (Book book : matchingBooks) {
                        JCheckBox bookCheckbox = new JCheckBox(book.getBookName() + " (ISBN: " + book.getISBN() + ")");
                        bookCheckbox.setName(String.valueOf(book.getId()));
                        outputPanel.add(bookCheckbox);
                    }
                }
            } else {
                outputPanel.add(new JLabel("Please enter a search term."));
            }

            outputPanel.revalidate();
            outputPanel.repaint();
        });


        borrowButton.addActionListener(e -> {
            for (Component component : outputPanel.getComponents()) {
                if (component instanceof JCheckBox) {
                    JCheckBox bookCheckbox = (JCheckBox) component;
                    if (bookCheckbox.isSelected()) {
                        int bookId = Integer.parseInt(bookCheckbox.getName());
                        Book book = borrowApp.fetchBookById(bookId);

                        if (book != null && book.isAvailable()) {
                            Borrow borrow = new Borrow(loggedInUser, book);
                            borrowApp.addBorrow(borrow);
                            book.setAvailable(false);

                            borrowApp.saveBorrowsToFile("borrows.json");
                            showBorrowPopup(book);
                        } else {
                            JOptionPane.showMessageDialog(this, "Book is not available for borrowing.");
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


        public void showBorrowPopup(Book book) {
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
        okButton.addActionListener(e -> borrowDialog.dispose());

        borrowDialog.add(datePanel, BorderLayout.CENTER);
        borrowDialog.add(okButton, BorderLayout.SOUTH);

        borrowDialog.setVisible(true);
    }


    public static void main(String[] args) {
        BorrowApp app = new BorrowApp();
        User loggedInUser = null;
        new BorrowGUI(app, loggedInUser);
    }
}

