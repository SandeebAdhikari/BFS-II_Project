package Library_Shiv;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BorrowApp {
    private List<Borrow> borrows;
    private List<Book> books;
    private List<User> users;

    public BorrowApp() {
        this.borrows = new ArrayList<>();
        this.books = loadBooksFromFile("books.json");  // Load books from books.json
        this.users = loadUsersFromFile("user.json");   // Load users from user.json
    }

    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getBookName().toLowerCase().contains(query.toLowerCase()) ||
                    String.valueOf(book.getISBN()).contains(query)) {
                results.add(book);
            }
        }
        return results;
    }

    private List<Book> loadBooksFromFile(String fileName) {
        List<Book> bookList = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(fileName)) {
            JSONArray bookArray = (JSONArray) jsonParser.parse(reader);
            for (Object obj : bookArray) {
                JSONObject bookObj = (JSONObject) obj;
                int isbn = ((Long) bookObj.get("isbn")).intValue();
                int id = ((Long) bookObj.get("id")).intValue();
                String bookName = (String) bookObj.get("bookName");
                String authorName = (String) bookObj.get("authorName");
                String edition = (String) bookObj.get("edition");
                int publishedDate = ((Long) bookObj.get("publishedDate")).intValue();

                Book book = new Book(isbn, id, bookName, authorName, edition, publishedDate);
                bookList.add(book);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return bookList;
    }

    private List<User> loadUsersFromFile(String fileName) {
        List<User> userList = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(fileName)) {
            JSONArray userArray = (JSONArray) jsonParser.parse(reader);
            for (Object obj : userArray) {
                JSONObject userObj = (JSONObject) obj;
                int id = ((Long) userObj.get("id")).intValue();
                String firstName = (String) userObj.get("firstName");
                String lastName = (String) userObj.get("lastName");
                String address = (String) userObj.get("address");
                String phoneNumber = (String) userObj.get("phoneNumber");
                String role = (String) userObj.get("role");

                User user = new User(id, firstName, lastName, address, phoneNumber, role);
                userList.add(user);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public void addBorrow(Borrow borrow) {
        borrows.add(borrow);
    }

    public void saveBorrowsToFile(String fileName) {
        JSONArray borrowList = new JSONArray();
        for (Borrow borrow : borrows) {
            borrowList.add(borrow.toJSON());
        }

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(borrowList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Book fetchBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public User fetchUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}
