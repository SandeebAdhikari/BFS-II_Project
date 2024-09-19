package Library_Sandeeb.User;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class UserApp {
    public ArrayList<User> users;
    public int userIdCounter;
    public static final String FILE_PATH = "users.json";

    public UserApp() {
        users = new ArrayList<>();
        userIdCounter = 1;
        loadUsersFromFile();
    }

    public void addUser(String firstName, String lastName, String address, String phoneNumber, String role) {
        User newUser = new User(userIdCounter++, firstName, lastName, address, phoneNumber, role);
        users.add(newUser);
        saveUsersToFile();
    }

    public void loadUsersFromFile() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            JSONArray userList = (JSONArray) parser.parse(reader);
            for (Object obj : userList) {
                JSONObject userObj = (JSONObject) obj;
                int id = ((Long) userObj.get("id")).intValue();
                String firstName = (String) userObj.get("firstName");
                String lastName = (String) userObj.get("lastName");
                String address = (String) userObj.get("address");
                String phoneNumber = (String) userObj.get("phoneNumber");
                String role = (String) userObj.get("role");

                User user = new User(id, firstName, lastName, address, phoneNumber, role);
                users.add(user);
                userIdCounter = Math.max(userIdCounter, id + 1);  // Ensure unique user IDs
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous users found.");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void saveUsersToFile() {
        JSONArray userList = new JSONArray();
        for (User user : users) {
            userList.add(user.toJSON());
        }

        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(userList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean validateLogin(String firstName, String phoneNumber) {
        for (User user : users) {
            if (user.getFirstName().equalsIgnoreCase(firstName) && user.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public User fetchUserByCredentials(String firstName, String phoneNumber) {
        for (User user : users) {
            if (user.getFirstName().equalsIgnoreCase(firstName) && user.getPhoneNumber().equals(phoneNumber)) {
                return user;  // Return the matching user
            }
        }
        return null;  // Return null if no match found
    }


    public static void main(String[] args) {
        UserApp app = new UserApp();
        SwingUtilities.invokeLater(() -> new UserGUI(app));
    }
}
