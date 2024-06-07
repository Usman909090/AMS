package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import Models.Item;
import Models.User;
import Repositories.ItemRepository;


import java.util.List;

public class SellerDashboardController {
    
    @FXML
    private GridPane productsGrid;
    @FXML
    private VBox sidebar;
    @FXML
    private Label welcomeLabel;

    public void initialize() {
    	 User currentUser = SessionController.getInstance().getCurrentUser();
         welcomeLabel.setText("Welcome, " + currentUser.getName());
         loadProducts(currentUser.getId());
    }

    private void loadProducts(int id) {
        List<Item> items = ItemRepository.getItemsBySeller(id); 
        int column = 0;
        int row = 1;
        for (Item item : items) {
            productsGrid.add(createItemCard(item), column, row);
            column++;
            if (column == 2) {
                column = 0;
                row++;
            }
        }
    }

    public VBox createItemCard(Item item) {
        VBox card = new VBox(10); // Create a VBox with spacing of 10 pixels
        card.setStyle("-fx-padding: 10; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-radius: 5; -fx-border-color: blue;");

        // Create and configure the image view
        ImageView imageView = new ImageView();
        imageView.setFitHeight(100); // Set the height of the image
        imageView.setFitWidth(100); // Set the width of the image
        
        String defaultImagePath = getClass().getResource("/Images/DefaultItem.jpg").toExternalForm();
        imageView.setImage(new Image(defaultImagePath));
     

        // Create labels for the name, description, and price
        Label nameLabel = new Label(item.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label descriptionLabel = new Label(item.getDescription());
        descriptionLabel.setWrapText(true); // Enable text wrapping within the label

        Label priceLabel = new Label("Starting Price: $" + item.getStartingPrice());
        priceLabel.setStyle("-fx-text-fill: green;");

        // Add all components to the VBox
        card.getChildren().addAll(imageView, nameLabel, descriptionLabel, priceLabel);

        return card;
    }

    @FXML
    private void handleCreateAuction() {
        // Code to create a new auction
    }

    @FXML
    private void handleScheduleAuction() {
        // Code to schedule an auction
    }

    @FXML
    private void handleSetReservePrice() {
        // Code to set a reserve price
    }

    @FXML
    private void handleMonitorAuction() {
        // Code to monitor auction progress
    }
}
