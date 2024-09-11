import java.util.ArrayList;
import java.util.Scanner;

class Task {
    private int id;
    private String description;
    private boolean isCompleted;

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.isCompleted = false;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + description + " (" + (isCompleted ? "Completed" : "Not Completed") + ")";
    }
}

public class ToDoList {
    private ArrayList<Task> tasks;
    private Scanner scanner;
    private int taskIdCounter;

    public ToDoList() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
        taskIdCounter = 1;
    }

    public void start() {
        while (true) {
            showMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    markTaskAsCompleted();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    System.out.println("Exiting application.");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n1. Add Task");
        System.out.println("2. View Tasks");
        System.out.println("3. Mark Task as Completed");
        System.out.println("4. Delete Task");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void addTask() {
        scanner.nextLine(); // consume newline
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        Task newTask = new Task(taskIdCounter++, description);
        tasks.add(newTask);
        System.out.println("Task added successfully.");
    }

    private void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            System.out.println("Tasks:");
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    private void markTaskAsCompleted() {
        System.out.print("Enter task ID to mark as completed: ");
        int id = getUserChoice();
        Task task = findTaskById(id);
        if (task != null) {
            task.markAsCompleted();
            System.out.println("Task marked as completed.");
        } else {
            System.out.println("Task with ID " + id + " not found.");
        }
    }

    private void deleteTask() {
        System.out.print("Enter task ID to delete: ");
        int id = getUserChoice();
        Task task = findTaskById(id);
        if (task != null) {
            tasks.remove(task);
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("Task with ID " + id + " not found.");
        }
    }

    private Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ToDoList app = new ToDoList();
        app.start();
    }
}
