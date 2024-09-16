package Library_Shiv;

public class BorrowRecord {
    private int userId;
    private int bookId;
    private String borrowDate;
    private String returnDate;
    private boolean isBorrowed;

    public BorrowRecord(int userId, int bookId, String borrowDate, boolean isBorrowed) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.isBorrowed = isBorrowed;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setReturned(boolean returned) {
        isBorrowed = !returned;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
