package Library_Sandeeb.Book;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class BookGUI {
    public BookApp app;
    public JFrame frame;

    public BookGUI(BookApp app) {
        this.app = app;
        frame = new JFrame("Book List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ISBN label and field at the top
        JLabel bookISBNLabel = new JLabel("ISBN:");
        JTextField bookISBNField = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(bookISBNLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(bookISBNField, gbc);

        JLabel bookNameLabel = new JLabel("Book Name:");
        JTextField bookNameInputField = new JTextField(15);
        JLabel authorNameLabel = new JLabel("Author Name:");
        JTextField authorNameInputField = new JTextField(15);
        JLabel editionLabel = new JLabel("Edition:");
        JTextField editionInputField = new JTextField(15);
        JLabel publishedDateLabel = new JLabel("Published Date:");
        JTextField publishedDateInputField = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(bookNameLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(bookNameInputField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(authorNameLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(authorNameInputField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(editionLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(editionInputField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(publishedDateLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(publishedDateInputField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Book");
        JButton viewBooksButton = new JButton("View Books");
        buttonPanel.add(addButton);
        buttonPanel.add(viewBooksButton);
        inputPanel.add(buttonPanel, gbc);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.setVisible(true);

        addButton.addActionListener(e -> {
            String isbn = bookISBNField.getText();
            String bookName = bookNameInputField.getText();
            String authorName = authorNameInputField.getText();
            String edition = editionInputField.getText();
            String publishedDate = publishedDateInputField.getText();

            if (!(isbn.isEmpty() || bookName.isEmpty() || authorName.isEmpty() || edition.isEmpty() || publishedDate.isEmpty())) {
                app.addBook(Integer.parseInt(isbn), bookName, authorName, edition, Integer.parseInt(publishedDate));
                bookISBNField.setText("");
                bookNameInputField.setText("");
                authorNameInputField.setText("");
                editionInputField.setText("");
                publishedDateInputField.setText("");

                app.saveBooksToFile();
            }
        });

        viewBooksButton.addActionListener(e -> {
            showBookListPopup();
        });
    }

    public void showBookListPopup() {
        JDialog bookListDialog = new JDialog(frame, "Book List", true);
        bookListDialog.setSize(500, 400);

        JPanel dialogPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        dialogPanel.add(searchPanel, BorderLayout.NORTH);

        JPanel bookListPanel = new JPanel();
        bookListPanel.setLayout(new BoxLayout(bookListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(bookListPanel);
        dialogPanel.add(scrollPane, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Delete Selected Books");
        dialogPanel.add(deleteButton, BorderLayout.SOUTH);


        updateBookListInPopup(bookListPanel, "");

        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText();
            updateBookListInPopup(bookListPanel, searchTerm);
        });

        deleteButton.addActionListener(e -> {
            deleteSelectedBooksFromPopup(bookListPanel);
            app.saveBooksToFile();
            updateBookListInPopup(bookListPanel, "");
        });

        bookListDialog.add(dialogPanel);
        bookListDialog.setVisible(true);
    }


    public void updateBookListInPopup(JPanel bookListPanel, String searchTerm) {
        bookListPanel.removeAll();  // Clear previous list

        for (Book book : app.books) {
            JCheckBox bookCheckbox = new JCheckBox(book.toString());
            bookCheckbox.setName(String.valueOf(book.getId()));

            if (book.getBookName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    book.getAuthorName().toLowerCase().contains(searchTerm.toLowerCase())) {
                bookListPanel.add(bookCheckbox);
            }
        }

        bookListPanel.revalidate();
        bookListPanel.repaint();
    }

    public void deleteSelectedBooksFromPopup(JPanel bookListPanel) {
        Component[] components = bookListPanel.getComponents();
        ArrayList<Integer> booksToDelete = new ArrayList<>();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkbox = (JCheckBox) component;
                if (checkbox.isSelected()) {
                    booksToDelete.add(Integer.parseInt(checkbox.getName()));
                }
            }
        }

        Iterator<Book> iterator = app.books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (booksToDelete.contains(book.getId())) {
                iterator.remove();
            }
        }
    }
}
