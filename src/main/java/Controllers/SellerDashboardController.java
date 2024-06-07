package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import Models.Auction;
import Models.Item;
import Models.User;
import Repositories.AuctionRepository;
import Repositories.ItemRepository;
import Utility.SceneManager;
import javafx.event.ActionEvent;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SellerDashboardController {
	private boolean isManageInventoryActive = false; 
	
    @FXML
    private GridPane productsGrid;
    @FXML
    private VBox sidebar;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label contentLabel;
    @FXML
    private VBox contentArea;

    public void initialize() {
        User currentUser = SessionController.getInstance().getCurrentUser();
        welcomeLabel.setText("Welcome, " + currentUser.getName());
        loadProducts(currentUser.getId());
    }

    private void loadProducts(int id) {
        List<Item> items = ItemRepository.getItemsBySeller(id);
        productsGrid.getChildren().clear();  // Clear existing items
        int column = 0;
        int row = 1;
        for (Item item : items) {
            productsGrid.add(createItemCard(item, isManageInventoryActive), column, row); // Use flag to add checkboxes
            column++;
            if (column == 2) {
                column = 0;
                row++;
            }
        }
    }

    
    
    public void handleHome() {
    	SceneManager.switchScene("/Views/SellerDashboard.fxml");
    }
    
    

    public VBox createItemCard(Item item, boolean showCheckbox) {
        VBox card = new VBox(10);
        card.setStyle("-fx-padding: 10; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-radius: 5; -fx-border-color: blue;");

        ImageView imageView = new ImageView();
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        String defaultImagePath = getClass().getResource("/Images/DefaultItem.jpg").toExternalForm();
        imageView.setImage(new Image(defaultImagePath));

        Label nameLabel = new Label(item.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label descriptionLabel = new Label(item.getDescription());
        descriptionLabel.setWrapText(true);

        Label priceLabel = new Label("Starting Price: $" + item.getStartingPrice());
        priceLabel.setStyle("-fx-text-fill: green;");

        card.getChildren().addAll(imageView, nameLabel, descriptionLabel, priceLabel);
        
        if (showCheckbox) {
            CheckBox selectCheckBox = new CheckBox();
            selectCheckBox.setId("chk" + item.getId());
            card.getChildren().add(0, selectCheckBox);
        }

        return card;
    }

    @FXML
    private void handleCreateAuction() {
        List<Auction> auctions = AuctionRepository.getAuctions();
        contentArea.getChildren().clear();
        Label auctionLabel = new Label("Auction Listings");

        // Set font size and weight
        auctionLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Alternatively, using the Font class for more control:
        auctionLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        contentArea.getChildren().add(auctionLabel);
        for (Auction auction : auctions) {
        	contentArea.getChildren().add(createAuctionCard(auction));
        }
        Button createAuctionButton = new Button("Create Auction Listing");
        createAuctionButton.setOnAction(e -> showAvailableItemsForAuction());
        contentArea.getChildren().add(createAuctionButton);
    }

    private VBox createAuctionCard(Auction auction) {
        VBox card = new VBox(10);
        card.setStyle("-fx-padding: 10; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-radius: 5; -fx-border-color: black;");

        Label itemLabel = new Label("Item: " + auction.getItem().getName());
        Label priceLabel = new Label("Start Price: $" + auction.getStartPrice());
        Label startTimeLabel = new Label("Starts: " + auction.getStartTime().toString());
        Label endTimeLabel = new Label("Ends: " + auction.getEndTime().toString());

        card.getChildren().addAll(itemLabel, priceLabel, startTimeLabel, endTimeLabel);
        return card;
    }
    
    
    private void showAvailableItemsForAuction() {
        List<Item> availableItems = ItemRepository.getAvailableItemsForAuction();
        Dialog<List<Item>> dialog = new Dialog<>();
        dialog.setTitle("Select Items for Auction");
        dialog.setHeaderText("Select one or more items to auction:");

        VBox container = new VBox(10);
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);

        for (Item item : availableItems) {
            CheckBox checkBox = new CheckBox(item.getName() + " - Starting Price: $" + item.getStartingPrice());
            checkBox.setId("item" + item.getId());
            container.getChildren().add(checkBox);
        }

        dialog.getDialogPane().setContent(scrollPane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.NEXT, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.NEXT) {
                List<Item> selectedItems = container.getChildren().stream()
                    .filter(node -> node instanceof CheckBox && ((CheckBox) node).isSelected())
                    .map(node -> ItemRepository.findItemById(Integer.parseInt(node.getId().substring(4))))
                    .collect(Collectors.toList());
                return selectedItems;
            }
            return null;
        });

        Optional<List<Item>> result = dialog.showAndWait();
        result.ifPresent(this::askAuctionDetails);
    }
    	
    
    private void askAuctionDetails(List<Item> items) {
        if (items.isEmpty()) {
            showAlert("No items selected", "You must select at least one item to create an auction.", Alert.AlertType.ERROR);
            return;
        }

        Dialog<Auction> detailsDialog = new Dialog<>();
        detailsDialog.setTitle("Auction Details");
        detailsDialog.setHeaderText("Enter details for the auction:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField startPriceField = new TextField();
        TextField startTimeField = new TextField();
        TextField endTimeField = new TextField();

        grid.add(new Label("Start Price:"), 0, 0);
        grid.add(startPriceField, 1, 0);
        grid.add(new Label("Start Time (dd/MM/yyyy HH:mm):"), 0, 1);
        grid.add(startTimeField, 1, 1);
        grid.add(new Label("End Time (dd/MM/yyyy HH:mm):"), 0, 2);
        grid.add(endTimeField, 1, 2);

        detailsDialog.getDialogPane().setContent(grid);
        detailsDialog.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);

        detailsDialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.FINISH) {
                try {
                    double startPrice = Double.parseDouble(startPriceField.getText());
                    Date startTime = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(startTimeField.getText());
                    Date endTime = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(endTimeField.getText());
                    // Assume auctioneerId is 2
                    return new Auction(0, items.get(0), SessionController.getInstance().getCurrentUser(), startTime, endTime, startPrice, startPrice, 2);
                } catch (ParseException | NumberFormatException e) {
                    showAlert("Invalid input", "Please check your inputs and try again.", Alert.AlertType.ERROR);
                    return null;
                }
            }
            return null;
        });

        Optional<Auction> auctionResult = detailsDialog.showAndWait();
        auctionResult.ifPresent(auction -> {
            AuctionRepository.addAuctionToDatabase(auction);
            handleCreateAuction();  // Refresh the auction listings
        });
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


 
    
    @FXML
    public void handleManageInventoryClicked(ActionEvent event) {
    	isManageInventoryActive = true;
    	loadProducts(SessionController.getInstance().getCurrentUser().getId());  // Reload products with checkboxes
    	contentLabel.setText("Manage Inventory");

        HBox buttonContainer = new HBox(10);
        //buttonContainer.setPadding(new Insets(10, 10, 10, 10)); // Correct Insets

        Button addButton = new Button("Add New Item");
        addButton.setOnAction(e -> openAddNewItemPage());
        Button removeButton = new Button("Remove Item");
        removeButton.setOnAction(e -> openRemoveItemPage());
        Button updateButton = new Button("Update Item");
        updateButton.setOnAction(e -> openUpdateItemPage());

        buttonContainer.getChildren().addAll(addButton, removeButton, updateButton);

        if (!contentArea.getChildren().contains(buttonContainer)) {
            contentArea.getChildren().add(buttonContainer);
        }
    }

    private void openAddNewItemPage() {
        Dialog<Item> dialog = new Dialog<>();
        dialog.setTitle("Add New Item");

        // Set the button types.
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField();
        TextField priceField = new TextField();
        TextField descField = new TextField();

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Starting Price:"), 0, 1);
        grid.add(priceField, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(descField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a new Item instance when the save button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Item(0, nameField.getText(), Double.parseDouble(priceField.getText()), descField.getText(), SessionController.getInstance().getCurrentUser().getId());
            }
            return null;
        });

        Optional<Item> result = dialog.showAndWait();

        result.ifPresent(item -> {
            ItemRepository.addItemToDatabase(item);
            loadProducts(SessionController.getInstance().getCurrentUser().getId()); // Reload products to update the view
        });
    }

    private void openRemoveItemPage() {
        List<Integer> itemsToRemove = new ArrayList<>();
        for (Node node : productsGrid.getChildren()) {
            if (node instanceof VBox && ((VBox) node).getChildren().get(0) instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) ((VBox) node).getChildren().get(0);
                if (checkBox.isSelected()) {
                    int itemId = Integer.parseInt(checkBox.getId().substring(3)); // Extract item ID
                    itemsToRemove.add(itemId);
                }
            }
        }
        itemsToRemove.forEach(ItemRepository::removeItemFromDatabase);  // Remove each selected item from the database
        loadProducts(SessionController.getInstance().getCurrentUser().getId());  // Reload products to update the view
    }


    private void openUpdateItemPage() {
        List<Item> selectedItems = getSelectedItems();

        if (selectedItems.size() == 1) {
            Item selectedItem = selectedItems.get(0);
            Dialog<Item> dialog = createItemUpdateDialog(selectedItem);
            Optional<Item> result = dialog.showAndWait();

            result.ifPresent(item -> {
                ItemRepository.updateItemInDatabase(item);
                loadProducts(SessionController.getInstance().getCurrentUser().getId()); // Reload products to update the view
            });
        } else {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select exactly one item to update it");
            alert.showAndWait();
            // Optionally, show a user-friendly notification in the UI instead of just printing to the console.
        }
    }

    private List<Item> getSelectedItems() {
        List<Item> selectedItems = new ArrayList<>();
        for (Node node : productsGrid.getChildren()) {
            if (node instanceof VBox && ((VBox) node).getChildren().get(0) instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) ((VBox) node).getChildren().get(0);
                if (checkBox.isSelected()) {
                    int itemId = Integer.parseInt(checkBox.getId().substring(3));
                    selectedItems.add(ItemRepository.findItemById(itemId)); // Assuming there's a method to find items by ID
                }
            }
        }
        return selectedItems;
    }

    private Dialog<Item> createItemUpdateDialog(Item item) {
        Dialog<Item> dialog = new Dialog<>();
        dialog.setTitle("Update Item");

        // Set the button types.
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField(item.getName());
        TextField priceField = new TextField(String.valueOf(item.getStartingPrice()));
        TextField descField = new TextField(item.getDescription());

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Starting Price:"), 0, 1);
        grid.add(priceField, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(descField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a new Item instance when the save button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Item(item.getId(), nameField.getText(), Double.parseDouble(priceField.getText()), descField.getText(), item.getSellerId());
            }
            return null;
        });

        return dialog;
    }

    @FXML
    private void handleSetReservePrice() {
    	isManageInventoryActive = false;
        // Code to set a reserve price
    }

    @FXML
    private void handleMonitorAuction() {
    	isManageInventoryActive = false;
        // Code to monitor auction progress
    }
}
