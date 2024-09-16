package Library_Shiv;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryManagerGUI {
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

        // Panels
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel labelUserId = new JLabel("User ID:");
        JTextField textUserId = new JTextField();
        JLabel labelBookISBN = new JLabel("Book ISBN:");
        JTextField textBookISBN = new JTextField();
        JLabel labelUserRole = new JLabel("User Role (Student/Teacher):");
        JTextField textUserRole = new JTextField();

        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");

        panel.add(labelUserId);
        panel.add(textUserId);
        panel.add(labelBookISBN);
        panel.add(textBookISBN);
        panel.add(labelUserRole);
        panel.add(textUserRole);
        panel.add(borrowButton);
        panel.add(returnButton);

        // Add Book Section
        JPanel addBookPanel = new JPanel();
        addBookPanel.setLayout(new GridLayout(3, 2));

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

        // Result Area
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add panels to the frame
        frame.add(panel, BorderLayout.NORTH);
        frame.add(addBookPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        // Action Listeners
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

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new LibraryManagerGUI();
    }
}
