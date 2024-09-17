package Library_Shiv;

public class Main {

    public static void main(String[] args) {
        // Initialize the LibraryManager
        LibraryManager libraryManager = new LibraryManager();

        // Add some sample books for testing
        libraryManager.addBook(new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", true));
        libraryManager.addBook(new Book(2, "1984", "George Orwell", true));
        libraryManager.addBook(new Book(3, "To Kill a Mockingbird", "Harper Lee", true));

        // Create some sample users
        User user1 = new User(101, "John", "Doe", "123 Main St", "555-1234", "Student");
        User user2 = new User(102, "Jane", "Smith", "456 Elm St", "555-5678", "Teacher");

        // Register the users
        libraryManager.registerUser(user1);
        libraryManager.registerUser(user2);

        // Initialize the LibraryManagerGUI and pass the LibraryManager object to it
        LibraryManagerGUI libraryManagerGUI = new LibraryManagerGUI(libraryManager);

        // Launch the GUI
        libraryManagerGUI.setVisible(true);  // Show the GUI
    }
}
