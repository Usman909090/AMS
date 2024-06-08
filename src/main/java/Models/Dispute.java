package Models;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Dispute {
	public static final String PENDING = "Pending";
	public static final String RESOLVED = "Resolved";
	
    private int id;
    private User reporter;
    private String description;
    private String status;
    private List<User> affectedUsers;

    // Parameterized constructor
    public Dispute(int id, User reporter, String description, String status, List<User> affectedUsers) {
        this.id = id;
        this.reporter = reporter;
        this.description = description;
        this.status = status;
        this.affectedUsers = affectedUsers;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getAffectedUsers() {
        return affectedUsers;
    }

    public void setAffectedUsers(List<User> affectedUsers) {
        this.affectedUsers = affectedUsers;
    }

    public void addAffectedUser(User user) {
        this.affectedUsers.add(user);
    }
    
    public void removeAffectedUser(User user) {
        this.affectedUsers.remove(user);
    }

    public StringProperty affectedUsersProperty() {
        StringBuilder ids = new StringBuilder();
        for (User user : affectedUsers) {
            if (ids.length() > 0) {
                ids.append(", ");
            }
            ids.append("[" + user.getId() + "] " + user.getName());
        }
        return new SimpleStringProperty(ids.toString());
    }
    
    // Fetch details
    public String getReport() {
        StringBuilder details = new StringBuilder();
        details.append(String.format("ID: %d\nReporter: %s\nDescription: %s\nStatus: %s\nAffected Users: ",
                getId(), getReporter().getName(), getDescription(), getStatus()));
        for (User user : affectedUsers) {
            details.append(user.getName()).append(" ");
        }
        return details.toString();
    }
}