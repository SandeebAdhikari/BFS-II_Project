package Library_Sandeeb.User;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class UserApp {
    public ArrayList<User> users;
    public int studentIDCounter;
    public static final String FILE_PATH = "tasks.json";

    public UserApp(){
        users = new ArrayList<>();
        studentIDCounter = 1;

        loadStudentInfoFromFile();
    }
    public void addStudent(String firstName, String lastName, String address, String phoneNumber){
        User newStudent = new User(studentIDCounter++, firstName, lastName, address, phoneNumber);
        users.add(newStudent);
    }


    public void loadStudentInfoFromFile(){
        JSONParser parser = new JSONParser();

        try(FileReader reader = new FileReader(FILE_PATH)){
            JSONArray studentList = (JSONArray) parser.parse(reader);
            for(Object obj : studentList){
                JSONObject studentObj = (JSONObject) obj;
                int id = ((Long) studentObj.get("id")).intValue();
                String firstName = (String) studentObj.get("firstName");
                String lastName = (String) studentObj.get("lastName");
                String address = (String) studentObj.get("address");
                String phoneNumber = (String) studentObj.get("phoneNumber");

                User user = new User(id, firstName, lastName, address, phoneNumber);
            }
            System.out.println("User loaded from file.");
        }catch (FileNotFoundException e) {
            System.out.println("No previous tasks found.");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UserApp app  = new UserApp();
        SwingUtilities.invokeLater(() -> new UserGUI(app));
    }

}
