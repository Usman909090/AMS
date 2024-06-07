package Controllers;

import java.sql.SQLException;

import Models.Buyer;
import Models.Seller;
import Models.User;
import Models.UserRole;
import Repositories.UserRepository;
import Utility.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class SignUpController {
	UserRepository userRepository = new UserRepository();
	
	
	
	@FXML
	private TextField name;
	
	@FXML
	private TextField cnic;
	
	
	@FXML
	private TextField email;
	
	
	@FXML
	private TextField password;
	
	
	@FXML
    private RadioButton bidderRadioButton;

    @FXML
    private RadioButton sellerRadioButton;
	
    public void handleSignIn() {
    	SceneManager.switchScene("/Views/SignIn.fxml");
    	
    }
    
    
    public void handleBidderRadioButtonSelection()
    {
    	if (bidderRadioButton.isSelected()) {
            sellerRadioButton.setSelected(false);
        }
    }  
    
    public void handleSellerRadioButtonSelection()
    {
    	if (sellerRadioButton.isSelected()) {
            bidderRadioButton.setSelected(false);
        }
    }  
    
    public void handleSignUp() {
        String nameT = name.getText().trim();
        String cnicT = cnic.getText().trim();
        String emailT = email.getText().trim();
        String passwordT = password.getText().trim();
        String roleT = bidderRadioButton.isSelected() ? "Buyer" : "Seller";
        UserRole role = UserRole.getRole(roleT);
        
        
        if (nameT.isEmpty() || cnicT.isEmpty() || emailT.isEmpty() || passwordT.isEmpty()) {
            // Show error dialog
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
            return;
        }

        // Validate CNIC length
        if (cnicT.length() != 13) {
            // Show error dialog
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("CNIC must be 13 digits long.");
            alert.showAndWait();
            return;
        }

        // Validate CNIC format (numeric)
        if (!cnicT.matches("[0-9]+")) {
            // Show error dialog
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("CNIC must contain only numeric digits.");
            alert.showAndWait();
            return;
        }
        
        if (!emailT.matches("[a-zA-Z0-9._%+-]+@gmail\\.com")) {
            // Show error dialog
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email format. Please enter a Gmail address.");
            alert.showAndWait();
            return;
        }
        User user = null;
        try {
        	
            
            if(roleT.equals("Seller")) {
            	user = userRepository.save(new User(nameT, cnicT, emailT, passwordT, UserRole.SELLER));
            }
            else if(roleT.equals("Buyer")) {
            	user = userRepository.save(new User(nameT, cnicT, emailT, passwordT, UserRole.BUYER));
            }
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("User signed up");
            alert.showAndWait();
            
        } catch (SQLException e) {
            // Handle database errors
            System.out.println("Failed to sign up: " + e.getMessage());
            // Optionally, you can show an error message to the user
            // For example, if the user already exists:
            // errorMessageLabel.setText("User with this email already exists.");
        }
    }
}