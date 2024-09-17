package Library_Shiv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryManagerGUI extends  JFrame{
    private LibraryManager libraryManager;

    public LibraryManagerGUI(LibraryManager libraryManager) {
        this.libraryManager = libraryManager;
        initializeGUI();
    }

    private void initializeGUI() {
        JFrame frame = new JFrame("Library Manager");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding for overall layout

        // Top panel with inputs
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // Add spacing between components
        JLabel labelUserId = new JLabel("User ID:");
        JTextField textUserId = new JTextField();
        JLabel labelBookISBN = new JLabel("Book ISBN:");
        JTextField textBookISBN = new JTextField();
        JLabel labelUserRole = new JLabel("User Role (Student/Teacher):");
        JTextField textUserRole = new JTextField();

        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");

        inputPanel.add(labelUserId);
        inputPanel.add(textUserId);
        inputPanel.add(labelBookISBN);
        inputPanel.add(textBookISBN);
        inputPanel.add(labelUserRole);
        inputPanel.add(textUserRole);
        inputPanel.add(borrowButton);
        inputPanel.add(returnButton);

        // Center panel for adding books
        JPanel addBookPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel labelBookTitle = new JLabel("Book Title:");
        JTextField textBookTitle = new JTextField();
        JLabel labelBookAuthor = new JLabel("Book Author:");
        JTextField textBookAuthor = new JTextField();
        JButton addBookButton = new JButton("Add Book");

        addBookPanel.add(labelBookTitle);
        addBookPanel.add(textBookTitle);
        addBookPanel.add(labelBookAuthor);
        addBookPanel.add(textBookAuthor);
        addBookPanel.add(addBookButton);

        // Result area for showing feedback
        JTextArea resultArea = new JTextArea(8, 50);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Action Listeners for buttons
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = Integer.parseInt(textUserId.getText());
                int bookIsbn = Integer.parseInt(textBookISBN.getText());

                boolean result = libraryManager.borrowBook(userId, bookIsbn);
                resultArea.append(result ? "Book borrowed successfully\n" : "Borrowing failed\n");
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = Integer.parseInt(textUserId.getText());
                int bookIsbn = Integer.parseInt(textBookISBN.getText());

                boolean result = libraryManager.returnBook(userId, bookIsbn);
                resultArea.append(result ? "Book returned successfully\n" : "Returning failed\n");
            }
        });

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = textBookTitle.getText();
                String author = textBookAuthor.getText();
                int isbn = libraryManager.getAllBooks().size() + 1; // Generate ISBN automatically

                Book book = new Book(isbn, title, author, true);
                libraryManager.addBook(book);
                resultArea.append("Book added: " + title + "\n");
            }
        });

        // Adding panels to the main frame
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(addBookPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        LibraryManager manager = new LibraryManager(); // Ensure manager is passed correctly
        new LibraryManagerGUI(manager);  // Pass the LibraryManager instance
    }
}
