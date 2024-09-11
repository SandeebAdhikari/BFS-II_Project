package ToDoListApp;

import org.json.simple.JSONObject;

class Task {
    public int id;
    public String description;
    public boolean isCompleted;

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

    public JSONObject toJSON() {
        JSONObject taskObj = new JSONObject();
        taskObj.put("id", id);
        taskObj.put("description", description);
        taskObj.put("isCompleted", isCompleted);
        return taskObj;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + description + " (" + (isCompleted ? "Completed" : "Not Completed") + ")";
    }
}