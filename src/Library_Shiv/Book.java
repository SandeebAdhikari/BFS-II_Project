package Library_Shiv;

import org.json.simple.JSONObject;

public class Book {
    public int isbn;
    public int id;
    public String bookName;
    public String authorName;
    public String edition;
    public int publishedDate;
    private boolean available; // Added attribute to track availability

    public Book(int isbn, int id, String bookName, String authorName, String edition, int publishedDate) {
        this.isbn = isbn;
        this.id = id;
        this.bookName = bookName;
        this.authorName = authorName;
        this.edition = edition;
        this.publishedDate = publishedDate;
        this.available = true; // Set default availability to true
    }

    // Getter and Setter for ISBN
    public int getISBN() {
        return isbn;
    }

    public void setISBN(int isbn) {
        if (isbn > 0) {
            this.isbn = isbn;
        } else {
            System.out.println("ISBN Error!!");
        }
    }

    // Getter and Setter for Book ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        } else {
            System.out.println("ID Error!!");
        }
    }

    // Getter and Setter for Book Name
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    // Getter and Setter for Author Name
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    // Getter and Setter for Edition
    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    // Getter and Setter for Published Date
    public int getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(int publishedDate) {
        if (publishedDate > 0) {
            this.publishedDate = publishedDate;
        } else {
            System.out.println("Publish Date Error!!");
        }
    }

    // Getter and Setter for Availability
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Convert Book to JSON
    public JSONObject toJSON() {
        JSONObject bookObj = new JSONObject();
        bookObj.put("isbn", isbn);
        bookObj.put("bookName", bookName);
        bookObj.put("authorName", authorName);
        bookObj.put("edition", edition);
        bookObj.put("publishedDate", publishedDate);
        bookObj.put("available", available); // Include availability status
        return bookObj;
    }

    // toString method for display purposes
    @Override
    public String toString() {
        return "<html>ISBN: " + isbn + "<br>Book Name: " + bookName +
                "<br>Author Name: " + authorName + "<br>Edition: " + edition +
                "<br>Published Year: " + publishedDate + "<br>Available: " + available + "</html>";
    }
}
