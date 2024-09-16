package Library_Shiv;

import java.util.ArrayList;

import java.text.SimpleDateFormat;
import java.util.Date;


public class LibraryManager {
    private ArrayList<Book> books;
    private ArrayList<User> users;
    private ArrayList<BorrowRecord> borrowRecords;
    private final int MAX_BORROW_LIMIT_STUDENT = 3;
    private final int MAX_BORROW_LIMIT_TEACHER = 10;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public LibraryManager() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        borrowRecords = new ArrayList<>();
    }

    // Add a new book to the library
    public void addBook(Book book) {
        books.add(book);
    }

    // Remove a book from the library
    public void removeBook(int isbn) {
        books.removeIf(book -> book.getId() == isbn);
    }

    // Register a new user
    public void registerUser(User user) {
        users.add(user);
    }

    // Borrow a book for a user
    public boolean borrowBook(int userId, int bookId) {
        User user = findUserById(userId);
        Book book = findBookById(bookId);

        if (user == null || book == null || !book.isAvailable()) {
            System.out.println("User or Book not found, or Book is not available.");
            return false;
        }

        int borrowLimit = user.getRole().equalsIgnoreCase("Teacher") ? MAX_BORROW_LIMIT_TEACHER : MAX_BORROW_LIMIT_STUDENT;

        if (user.getBorrowedBooks().size() >= borrowLimit) {
            System.out.println("Borrow limit reached for " + user.getRole() + ".");
            return false;
        }

        user.borrowBook(book);
        borrowRecords.add(new BorrowRecord(userId, bookId, getCurrentDate(), true));
        return true;
    }

    // Return a book from a user
    public boolean returnBook(int userId, int bookId) {
        User user = findUserById(userId);
        Book book = findBookById(bookId);

        if (user == null || book == null) {
            System.out.println("User or Book not found.");
            return false;
        }

        if (!user.getBorrowedBooks().contains(book)) {
            System.out.println("This user has not borrowed this book.");
            return false;
        }

        user.returnBook(book);
        BorrowRecord record = findBorrowRecord(userId, bookId);
        if (record != null) {
            record.setReturned(true);
            record.setReturnDate(getCurrentDate());
        }
        return true;
    }

    // Find a book by its ID
    private Book findBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst().orElse(null);
    }

    // Find a user by their ID
    private User findUserById(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    // Find a borrow record by user ID and book ID
    private BorrowRecord findBorrowRecord(int userId, int bookId) {
        return borrowRecords.stream()
                .filter(record -> record.getUserId() == userId && record.getBookId() == bookId && record.isBorrowed())
                .findFirst()
                .orElse(null);
    }

    // Get all books in the library
    public ArrayList<Book> getAllBooks() {
        return books;
    }

    // Get all users in the library
    public ArrayList<User> getAllUsers() {
        return users;
    }

    // Get the current date in a readable format
    public String getCurrentDate() {
        return dateFormat.format(new Date());
    }
}
