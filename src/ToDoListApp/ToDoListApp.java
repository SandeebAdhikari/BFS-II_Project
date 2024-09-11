package ToDoListApp;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ToDoListApp {
    public ArrayList<Task> tasks;
    public int taskIdCounter;
    public static final String FILE_PATH = "tasks.json";

    public ToDoListApp() {
        tasks = new ArrayList<>();
        taskIdCounter = 1;
        loadTasksFromFile();
    }

    public void addTask(String description) {
        Task newTask = new Task(taskIdCounter++, description);
        tasks.add(newTask);
    }


    public Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }


    public void loadTasksFromFile() {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(FILE_PATH)) {
            JSONArray taskList = (JSONArray) parser.parse(reader);
            for (Object obj : taskList) {
                JSONObject taskObj = (JSONObject) obj;
                int id = ((Long) taskObj.get("id")).intValue();
                String description = (String) taskObj.get("description");
                boolean isCompleted = (Boolean) taskObj.get("isCompleted");

                Task task = new Task(id, description);
                if (isCompleted) {
                    task.markAsCompleted();
                }
                tasks.add(task);
                taskIdCounter = Math.max(taskIdCounter, id + 1);  // Update counter to prevent duplicate IDs
            }
            System.out.println("Tasks loaded from file.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous tasks found.");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ToDoListApp app = new ToDoListApp();
        SwingUtilities.invokeLater(() -> new ToDoListGUI(app));
    }
}


