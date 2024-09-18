package Library_Shiv;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;

public class BorrowApp {
    private ArrayList<Borrow> borrows = new ArrayList<>();

    public void addBorrow(Borrow borrow) {
        borrows.add(borrow);
    }

    public void saveBorrowsToFile(String filename) {
        JSONArray jsonArray = new JSONArray();
        for (Borrow borrow : borrows) {
            jsonArray.add(borrow.toJSON());
        }
        try (FileWriter file = new FileWriter(filename)) {
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

