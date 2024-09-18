package Library_Sandeeb.Book;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class BookApp {
    public ArrayList<Book> books;
    public int bookIDCounter;
    public static final String FILE_PATH = "books.json";


    public BookApp() {
        books = new ArrayList<>();
        bookIDCounter = 1;
        loadBookInfoFromFile();
    }


    public void addBook(int isbn, String bookName, String authorName, String edition, int publishedDate) {
        Book newBook = new Book(isbn, bookIDCounter++, bookName, authorName, edition, publishedDate);
        books.add(newBook);
        saveBooksToFile();
    }

    public void loadBookInfoFromFile() {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(FILE_PATH)) {
            JSONArray bookList = (JSONArray) parser.parse(reader);
            for (Object obj : bookList) {
                JSONObject bookObj = (JSONObject) obj;

                Long isbnLong = (Long) bookObj.get("isbn");
                int isbn = (isbnLong != null) ? isbnLong.intValue() : 0;

                Long idLong = (Long) bookObj.get("id");
                int id = (idLong != null) ? idLong.intValue() : 0;

                String bookName = (String) bookObj.get("bookName");
                if (bookName == null) bookName = "Unknown";

                String authorName = (String) bookObj.get("authorName");
                if (authorName == null) authorName = "Unknown";

                String edition = (String) bookObj.get("edition");
                if (edition == null) edition = "Unknown";

                Long publishedDateLong = (Long) bookObj.get("publishedDate");
                int publishedDate = (publishedDateLong != null) ? publishedDateLong.intValue() : 0;

                Book book = new Book(isbn, id, bookName, authorName, edition, publishedDate);
                books.add(book);
            }
            System.out.println("Books loaded from file.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous books found.");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }


    public void saveBooksToFile() {
        JSONArray bookList = new JSONArray();
        for (Book book : books) {
            JSONObject bookObj = book.toJSON();
            bookList.add(bookObj);
        }

        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(bookList.toJSONString());
            file.flush();
            System.out.println("Books saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BookApp app = new BookApp();
        SwingUtilities.invokeLater(() -> new BookGUI(app));
    }
}
