package Library_Shiv;

import org.json.simple.JSONObject;

public class Book {
    private int id;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(int id, String title, String author, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public JSONObject toJSON() {
        JSONObject bookObj = new JSONObject();
        bookObj.put("id", id);
        bookObj.put("title", title);
        bookObj.put("author", author);
        bookObj.put("isAvailable", isAvailable);
        return bookObj;
    }

    @Override
    public String toString() {
        return "<html>Title: " + title + "<br>Author: " + author +
                "<br>Status: " + (isAvailable ? "Available" : "Borrowed") + "</html>";
    }
}
