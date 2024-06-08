package Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

import Models.Dispute;
import Models.User;
import Models.UserRole;
import Repositories.DisputeRepository;

public class DisputeController {
	private DisputeRepository disputeRepository = new DisputeRepository();
	
    @FXML
    private TableView<Dispute> disputeTable;

    @FXML
    private TableColumn<Dispute, Integer> idColumn;

    @FXML
    private TableColumn<Dispute, String> reporterColumn;

    @FXML
    private TableColumn<Dispute, String> descriptionColumn;

    @FXML
    private TableColumn<Dispute, String> statusColumn;

    @FXML
    private TableColumn<Dispute, String> affectedUsersColumn;
    
    @FXML
    private TableColumn<Dispute, String> resolutionActionColumn;
    
    private ObservableList<Dispute> disputeData = FXCollections.observableArrayList();

    @FXML
    private void initialize() throws SQLException {
        // Initialize the dispute table columns.
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        reporterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReporter().getName()));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        affectedUsersColumn.setCellValueFactory(cellData -> cellData.getValue().affectedUsersProperty());
        resolutionActionColumn.setCellValueFactory(cellData -> new SimpleStringProperty("Toggle"));
        
        resolutionActionColumn.setCellFactory(col -> {
            TableCell<Dispute, String> cell = new TableCell<Dispute, String>() {
                private final Label label = new Label();

                {
                    // Add styling to make the label look like a hyperlink
                    label.setStyle("-fx-text-fill: blue; -fx-underline: true; -fx-cursor: hand;");
                    label.setOnMouseClicked(e -> {
                        Dispute dispute = getTableView().getItems().get(getIndex());
                        if (dispute != null) {
                            // Change the status of the dispute for the corresponding row
                        	if (dispute.getStatus() == Dispute.PENDING) {
                        		dispute.setStatus(Dispute.RESOLVED);
                        	}                        		
                        	else { 
                        		dispute.setStatus(Dispute.PENDING);
                        	}
                            // Refresh table view to reflect the change
                            getTableView().refresh();
                            // Update db as well
                            try {
								disputeRepository.update(dispute);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                        }
                    });
                }
                
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        label.setText(item);
                        setGraphic(label);
                    }
                }
            };
            return cell;
        });
        
        loadDisputes();
        // Add observable list data to the table.
        disputeTable.setItems(disputeData);
    }

    private void loadDisputes() throws SQLException {
        List<Dispute> disputes = disputeRepository.findAll();
        for (Dispute d: disputes) {
        	disputeData.add(d);
        }
    }
}
