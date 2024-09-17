package Library_Shiv;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.time.LocalTime;


public class LibraryManager {
    private ArrayList<Book> books;
    private ArrayList<User> users;
    private ArrayList<BorrowRecord> borrowRecords;
    private final int MAX_BORROW_LIMIT_STUDENT = 3;
    private final int MAX_BORROW_LIMIT_TEACHER = 10;
    private final LocalDateTime dateFormat = new LocalDateTime.now();

    public LibraryManager() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        borrowRecords = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }


    public void removeBook(int isbn) {
        books.removeIf(book -> book.getId() == isbn);
    }

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


    private Book findBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst().orElse(null);
    }


    private User findUserById(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }


    private BorrowRecord findBorrowRecord(int userId, int bookId) {
        return borrowRecords.stream()
                .filter(record -> record.getUserId() == userId && record.getBookId() == bookId && record.isBorrowed())
                .findFirst()
                .orElse(null);
    }


    public ArrayList<Book> getAllBooks() {
        return books;
    }


    public ArrayList<User> getAllUsers() {
        return users;
    }


    public String getCurrentDate() {
        return dateFormat.format(new Date());
    }
}
