package Library_Shiv;

import org.json.simple.JSONObject;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Borrow {
    private User user;
    private Book book;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;

    public Borrow(User user, Book book) {
        this.user = user;
        this.book = book;
        this.borrowDate = LocalDateTime.now();
        this.returnDate = null;
    }


    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }


    public LocalDateTime getReturnDate() {
        return returnDate;
    }


    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public JSONObject toJSON() {
        JSONObject borrowObj = new JSONObject();
        borrowObj.put("userId", user.getId());
        borrowObj.put("userName", user.getFirstName() + " " + user.getLastName());
        borrowObj.put("userRole", user.getRole());
        borrowObj.put("bookId", book.getId());
        borrowObj.put("bookName", book.getBookName());
        borrowObj.put("isbn", book.getISBN());
        borrowObj.put("borrowDate", borrowDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        if (returnDate != null) {
            borrowObj.put("returnDate", returnDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        } else {
            borrowObj.put("returnDate", "Not Returned Yet");
        }
        return borrowObj;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "user=" + user.getFirstName() + " " + user.getLastName() +
                ", book=" + book.getBookName() +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
