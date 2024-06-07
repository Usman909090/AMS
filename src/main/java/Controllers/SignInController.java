package Controllers;

import java.sql.SQLException;

import Utility.SceneManager;
import Models.User;
import Repositories.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class SignInController {
	
	UserRepository userRepository;
	
	@FXML
    private TextField email;

    @FXML
    private TextField password;

    public void handleSignIn() {
        String emailT = email.getText().trim();
        String passwordT = password.getText().trim();
        
        // Validate input
        if (emailT.isEmpty() || passwordT.isEmpty()) {
            // Show error dialog
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
            return;
        }

        try {
            User user = UserRepository.authenticateUser(emailT, passwordT);
            
            if (user != null) {
                // Navigate to the main application screen
            	
            	// Change the display according to user type
            	
            	Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("User logged in");
                alert.showAndWait();
                System.out.println(user.getRole());
                
                SessionController.getInstance().setCurrentUser(user);
                
                if("SELLER".equals(user.getRole())) {
                	SceneManager.switchScene("/Views/SellerDashboard.fxml");
                }
                else if("BUYER".equals(user.getRole())) {
                	SceneManager.switchScene("/Views/BuyerDashboard.fxml");
                }
                else if("AUCTIONEER".equals(user.getRole())) {
                	SceneManager.switchScene("/Views/AuctioneerDashboard.fxml");
                }
                
            } else {
                // Show error dialog if authentication fails
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid email or password.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Handle database errors
            System.out.println("Failed to sign in: " + e.getMessage());
            // Optionally, you can show an error message to the user
            // errorMessageLabel.setText("Failed to sign in. Please try again later.");
        }
    }
	
	
	
    public void handleSignUp() {
    	SceneManager.switchScene("/Views/SignUp.fxml"); 	
    }   
}