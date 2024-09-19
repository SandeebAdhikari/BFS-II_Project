package Library_Sandeeb.Borrow;

import Library_Sandeeb.Book.Book;
import Library_Sandeeb.User.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowApp {
    private List<Borrow> borrows;
    private List<Book> books;
    private List<User> users;

    public BorrowApp() {
        this.borrows = new ArrayList<>();
        this.books = loadBooksFromFile("books.json");  // Load books from books.json
        this.users = loadUsersFromFile("users.json");   // Load users from user.json
    }

    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase();
        for (Book book : books) {
            if (book.getBookName().toLowerCase().contains(lowerCaseQuery) ||
                    String.valueOf(book.getISBN()).contains(lowerCaseQuery)) {
                results.add(book);
            }
        }
        return results;
    }


    private List<Book> loadBooksFromFile(String filename) {
        List<Book> books = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filename)) {
            JSONArray bookList = (JSONArray) parser.parse(reader);

            for (Object obj : bookList) {
                JSONObject bookObj = (JSONObject) obj;

                try {
                    int isbn = ((Long) bookObj.getOrDefault("isbn", 0L)).intValue();
                    int id = ((Long) bookObj.getOrDefault("id", 0L)).intValue();
                    String bookName = (String) bookObj.getOrDefault("bookName", "Unknown");
                    String authorName = (String) bookObj.getOrDefault("authorName", "Unknown");
                    String edition = (String) bookObj.getOrDefault("edition", "Unknown");
                    int publishedDate = ((Long) bookObj.getOrDefault("publishedDate", 0L)).intValue();
                    boolean available = (Boolean) bookObj.getOrDefault("available", Boolean.FALSE);

                    Book book = new Book(isbn, id, bookName, authorName, edition, publishedDate, available);
                    books.add(book);
                } catch (NullPointerException | ClassCastException e) {
                    System.out.println("Skipping incomplete or invalid book entry: " + bookObj);
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Book file not found: " + filename);
        } catch (IOException | ParseException e) {
            System.out.println("Error reading book file: " + e.getMessage());
        }
        return books;
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


    public Book fetchBookById(int id){
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
