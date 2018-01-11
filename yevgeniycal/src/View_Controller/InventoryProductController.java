package View_Controller;


import Model.Part;
import Model.Product;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 *
 * @author yevgeniy
 */
public class InventoryProductController extends InventoryController {
    @FXML
    TextField name;
    @FXML
    TextField inStock;
    @FXML
    TextField price;

    @FXML
    TextField max;
    @FXML
    TextField min;

    @FXML TextField searchBothTextField;
    
    @FXML
    Label productID;
    
    @FXML Button cancelButton;
    
    @FXML
    TableColumn<Part, Integer> searchPartIDColumn;
    @FXML
    TableColumn<Part, String> searchPartNameColumn;
    @FXML
    TableColumn<Part, Integer> searchPartInStockColumn;
    @FXML
    TableColumn<Part, Double> searchPartPriceColumn;
    
        
    @FXML 
    TableView<Part> partsTable;

    
    @FXML 
    TableView<Part> productPartsTable;
    
    @FXML
    TableColumn<Part, Integer> productPartIDColumn;
    @FXML
    TableColumn<Part, String> productPartNameColumn;
    @FXML
    TableColumn<Part, Integer> productPartInStockColumn;
    @FXML
    TableColumn<Part, Double> productPartPriceColumn;
    
    @FXML Button addPartProductButton;
    @FXML Button deletePartProductButton;
    
    Product getProductFromControlls() {
        Product product = new Product();      
        
        if ( !checkEmpty(name.getText(), "name")) {
            System.out.print("line1");
            return null;
        }
        
        if ( !checkInteger(inStock.getText(), "Inv")) {
            System.out.print("line2");
            return null;
        }

        if ( !checkDouble(price.getText(), "Price")) {
              System.out.print("line3");
            return null;
        }        
        if ( !checkInteger(min.getText(), "Min")) {
              System.out.print("line4");
            return null;
        }
        
        if ( !checkInteger(max.getText(), "Max")) {
              System.out.print("line5");
            return null;
        }
        
        product.setName(name.getText());     
        product.setInStock(Integer.parseInt(inStock.getText()));
        product.setPrice(Double.parseDouble(price.getText()));
        product.setMin(Integer.parseInt(min.getText()));
        product.setMax(Integer.parseInt(max.getText()));
        
        return product;
    }

    @Override
    public void postInit() {          
       MainScreenController ctrl = (MainScreenController) invocator;

       partsTable.setItems(ctrl.getPartsList());
       
       searchPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
       searchPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
       searchPartInStockColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
       searchPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
       partsTable.refresh();
       
       productPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
       productPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
       productPartInStockColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
       productPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
       productPartsTable.refresh();
       
       
       initPartFilter();
    }
    
    private void initPartFilter() {

        searchBothTextField.textProperty().addListener((Observable o) -> {
            ObservableList<Part> partsTableItems = FXCollections.observableArrayList();
            ObservableList<Part> partsList = ((MainScreenController) invocator).getPartsList();
                 
            ObservableList<TableColumn<Part, ?>> cols = partsTable.getColumns();

            
            for (int i = 0; i < partsList.size(); i++) {

                for (int j = 0; j < cols.size(); j++) {

                    TableColumn col = cols.get(j);

                    String cellValue = col.getCellData(partsList.get(i)).toString();

                    cellValue = cellValue.toLowerCase();

                    if (cellValue.contains(searchBothTextField.textProperty().get().toLowerCase())) {

                        partsTableItems.add(partsList.get(i));

                        break;

                    }

                }

            }

            partsTable.setItems(partsTableItems);
        });
}
    
    
        @FXML    
    private void addPartToProductTable(ActionEvent actionEvent) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem(); 
        if (selectedPart != null ) {
            productPartsTable.getItems().add(selectedPart);
            productPartsTable.refresh();
        }
    }
    public void initialize(URL url, ResourceBundle rb) {
        //locationGroup = new ToggleGroup();
        cancelButton.setOnAction(e -> {
            if ( showConfirmDialog("Are you sure you want to cancel") ) {
                closeStage(e);
            }
        });   
        
        deletePartProductButton.setOnAction(e -> {
            Part selectedItem = productPartsTable.getSelectionModel().getSelectedItem();
            productPartsTable.getItems().remove(selectedItem);
        });
    }
}
