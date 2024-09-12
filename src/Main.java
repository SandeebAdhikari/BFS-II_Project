import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ToDoListApp.ToDoListGUI;
import ToDoListApp.ToDoListApp;
import Notepad.NotePad;

public class Main {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Main GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(2, 1));


        JButton toDoListButton = new JButton("Open To-Do List");
        toDoListButton.setFont(new Font("Arial", Font.PLAIN, 20));
        toDoListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    ToDoListApp app = new ToDoListApp();
                    new ToDoListGUI(app);
                });
            }
        });

        JButton notepadButton = new JButton("Open Notepad");
        notepadButton.setFont(new Font("Arial", Font.PLAIN, 20));
        notepadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new NotePad());
            }
        });

        // Add the buttons to the frame
        frame.add(toDoListButton);
        frame.add(notepadButton);

        // Make the frame visible
        frame.setVisible(true);
    }
}
