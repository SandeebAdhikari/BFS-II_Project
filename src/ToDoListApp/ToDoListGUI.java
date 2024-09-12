package ToDoListApp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ToDoListGUI {
    public ToDoListApp app;
    public JFrame frame;
    public JPanel taskPanel;
    public JPanel completedTaskPanel;

    public ToDoListGUI(ToDoListApp app) {
        this.app = app;
        frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 1500);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(taskPanel);

        completedTaskPanel = new JPanel();
        completedTaskPanel.setLayout(new BoxLayout(completedTaskPanel, BoxLayout.Y_AXIS));
        completedTaskPanel.setBackground(Color.LIGHT_GRAY);
        JScrollPane completedScrollPane = new JScrollPane(completedTaskPanel);


        JPanel taskPanelContainer = new JPanel();
        taskPanelContainer.setLayout(new GridLayout(1, 2));
        taskPanelContainer.add(scrollPane);
        taskPanelContainer.add(completedScrollPane);

        mainPanel.add(taskPanelContainer, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JTextField taskInputField = new JTextField(30);
        JButton addButton = new JButton("Add Task");
        JButton deleteButton = new JButton("Delete Selected Tasks");

        JTextField searchInputField = new JTextField(10);

        JButton searchButton = new JButton("Search Task");
        JButton completeTaskButton = new JButton("Mark as Completed");

        inputPanel.add(taskInputField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);


        inputPanel.add(searchInputField);
        inputPanel.add(searchButton);
        inputPanel.add(completeTaskButton);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String taskDescription = taskInputField.getText();
            if (!taskDescription.isEmpty()) {
                app.addTask(taskDescription);
                taskInputField.setText("");
                updateTaskArea("");
            }
        });

        searchButton.addActionListener(e -> {
            String keyword = searchInputField.getText();
            if (!keyword.isEmpty()) {
                updateTaskArea(keyword);
            } else {
                updateTaskArea("");
            }
        });


        completeTaskButton.addActionListener(e -> {
            markSelectedTasksAsCompleted();
            updateTaskArea("");
        });

        deleteButton.addActionListener(e -> {
            deleteSelectedTasks();
            updateTaskArea("");
        });

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);

        updateTaskArea("");
    }


    public void markSelectedTasksAsCompleted() {
        Component[] components = taskPanel.getComponents();
        ArrayList<Integer> tasksToComplete = new ArrayList<>();

        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkbox = (JCheckBox) component;
                if (checkbox.isSelected()) {
                    tasksToComplete.add(Integer.parseInt(checkbox.getName()));
                }
            }
        }

        for (int taskId : tasksToComplete) {
            Task task = app.findTaskById(taskId);
            if (task != null) {
                task.markAsCompleted();
            }
        }
        updateTaskArea("");
    }

    public void updateTaskArea(String searchTerm) {
        taskPanel.removeAll();
        completedTaskPanel.removeAll();

        for (Task task : app.tasks) {
            JCheckBox taskCheckbox = new JCheckBox(task.toString());
            taskCheckbox.setName(String.valueOf(task.getId()));

            if (task.isCompleted()) {
                completedTaskPanel.add(new JLabel(task.toString()));
            } else if (task.getDescription().toLowerCase().contains(searchTerm.toLowerCase())) {
                taskPanel.add(taskCheckbox);
            }
        }

        taskPanel.revalidate();
        taskPanel.repaint();
        completedTaskPanel.revalidate();
        completedTaskPanel.repaint();
    }

    public void deleteSelectedTasks() {
        Component[] components = taskPanel.getComponents();
        ArrayList<Integer> tasksToDelete = new ArrayList<>();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkbox = (JCheckBox) component;
                if (checkbox.isSelected()) {
                    tasksToDelete.add(Integer.parseInt(checkbox.getName()));
                }
            }
        }

        Iterator<Task> iterator = app.tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (tasksToDelete.contains(task.getId())) {
                iterator.remove();
            }
        }
    }
}
