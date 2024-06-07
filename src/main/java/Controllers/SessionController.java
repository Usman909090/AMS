package Controllers;

import Models.User;

public class SessionController {
    private static SessionController instance;
    private User currentUser;

    private SessionController() {}

    public static SessionController getInstance() {
        if (instance == null) {
            instance = new SessionController();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
