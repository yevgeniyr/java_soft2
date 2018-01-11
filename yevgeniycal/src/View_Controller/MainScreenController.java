/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import Model.Part;
import Model.Product;
import Model.Inhouse;
import Model.Outsourced;


import java.util.concurrent.atomic.AtomicInteger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author yevgeniy
 */
public class MainScreenController extends InventoryController implements Initializable {

    @FXML
    private Button exitButton;

    @FXML
    private Button addpartbutton;
    @FXML
    private Button modifypartbutton;
    @FXML
    private Button deletePartButton;

    @FXML
    private Button addProductButton;
    @FXML
    private Button modifyProductButton;
    @FXML
    private Button deleteProductButton;

    @FXML
    TableView<Part> partsTable;
    @FXML
    TableColumn<Part, Integer> partIDColumn;
    @FXML
    TableColumn<Part, String> partNameColumn;
    @FXML
    TableColumn<Part, Integer> inStockColumn;
    @FXML
    TableColumn<Part, Double> priceColumn;

    private ObservableList<Part> partsList;
    private AtomicInteger partIdCounter;

    @FXML
    TableView<Product> productTable;
    @FXML
    TableColumn<Part, Integer> productIDColumn;
    @FXML
    TableColumn<Part, String> productNameColumn;
    @FXML
    TableColumn<Part, Integer> productInStockColumn;
    @FXML
    TableColumn<Part, Double> productPriceColumn;

    @FXML
    TextField searchPartTextField;
    
    @FXML
    TextField searchProductTextField;

    private ObservableList<Product> productList;
    private AtomicInteger productIdCounter;

    public ObservableList<Part> getPartsList() {
        return partsList;
    }
    
    public ObservableList<Product> getProductList() {
        return productList;
    }
    
    public void removePart(Part part) {
        partsList.remove(part);

    }

    public void addPart(Part part) {
        partsList.add(part);
        partsTable.setItems(partsList);
        partsTable.refresh();
    }

    public void removeProduct(Product product) {
        productList.remove(product);

    }

    public void addProduct(Product product) {
        productList.add(product);
        productTable.setItems(productList);
        productTable.refresh();
    }

    //((MainScreenController) invocator).productList.add(product);
    public int nextPartID() {
        return partIdCounter.getAndIncrement();
    }

    public int nextProductID() {
        return productIdCounter.getAndIncrement();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        partIdCounter = new AtomicInteger(1);
        productIdCounter = new AtomicInteger(1);

        partsList = FXCollections.observableArrayList();
        partsTable.setItems(partsList);
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        inStockColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        Inhouse part = new Inhouse();
        
        part.setName("Part1");
        part.setPartID(nextPartID());
        part.setPrice(5.3);
        part.setInStock(5);
        part.setMax(30);
        part.setMin(4);
        part.setMachineID(3);
        
        Outsourced part2 = new Outsourced();
        
        part2.setPartID(nextPartID());
        part2.setName("Part2");
        part2.setPrice(1000);
        part2.setInStock(5);
        part2.setMax(30);
        part2.setMin(4);
        part2.setCompanyName("Tesla");
        
        addPart(part);
        addPart(part2);
        
        Product product1 = new Product();
        product1.setProductID(nextProductID());
        product1.setName("product1");
        product1.setPrice(111);
        product1.setInStock(111);
        product1.setMax(1110);
        product1.setMin(111);
        product1.addAssociatePart(part);
        product1.addAssociatePart(part2);
        
        Product product2 = new Product();
        product2.setProductID(nextProductID());
        product2.setName("product1");
        product2.setPrice(222);
        product2.setInStock(222);
        product2.setMax(2220);
        product2.setMin(222);        
        product2.addAssociatePart(part);
        product2.addAssociatePart(part2);
               
        deletePartButton.setOnAction(e -> {        
              Part selectedItem = partsTable.getSelectionModel().getSelectedItem();
              if (selectedItem != null) {
                  if ( showConfirmDialog("Are you sure you want to delete this part ?") ) {
                      partsTable.getItems().remove(selectedItem);
                  }       
              }
        });

        // Product related thingies
        productList = FXCollections.observableArrayList();
        productTable.setItems(productList);
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInStockColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        addProduct(product1);
        addProduct(product2);

        deleteProductButton.setOnAction(e -> {
            Product selectedItem = productTable.getSelectionModel().getSelectedItem();      
            if (selectedItem != null) {
                if (! selectedItem.getPartsList().isEmpty() ) {
                  showConfirmDialog("This product has parts. Are you sure you want to delete this product ?");
                      productTable.getItems().remove(selectedItem);
                
                }
                else {
                  if ( showConfirmDialog("Are you sure you want to delete this product ?") ) {
                      productTable.getItems().remove(selectedItem);
                  }
                }
            
            }
        });

        addProductButton.setOnAction(e -> {
            showDialog("Add Product", "View_Controller/AddProduct.fxml");
        });

        initPartFilter();
        initProductFilter();
        
        //     modifyProductButton.setOnAction(e -> {
        //   });
    }

    public FXMLLoader getResource(String resource) {
        return new FXMLLoader(getClass().getClassLoader().getResource(resource));
    }

    public void showDialog(String title, String sceneFXML) {
        showDialog(title, sceneFXML, null);
    }

    public void showDialog(String title, String sceneFXML, Object invocatorData) {
        //get reference to the button's stage         
        //Stage =(Stage) addpartbutton.getScene().getWindow();
        //load up OTHER FXML document
        try {
            FXMLLoader loader = getResource(sceneFXML);
            Parent root;

            root = loader.load();

            Stage _dialogStage = new Stage();
            _dialogStage.setTitle(title);
            Scene scene = new Scene(root);
            _dialogStage.setScene(scene);
            InventoryController ctrl = loader.getController();
            ctrl.setDialogStage(_dialogStage);
            ctrl.setInvocator(this);
            ctrl.setInvocatorData(invocatorData);
            ctrl.postInit();
            ctrl.populateControls();
            
            _dialogStage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void handleAddPartButton(ActionEvent event) throws IOException {
        showDialog("Add Part", "View_Controller/AddPart.fxml");
    }

    @FXML
    private void handleModifyPartButton(ActionEvent event) throws IOException {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            showDialog("Modify Part", "View_Controller/ModifyPart.fxml", selectedPart);
        }
    }

    @FXML
    private void handleModifyProductButton(ActionEvent event) throws IOException {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            showDialog("Modify Product", "View_Controller/ModifyProduct.fxml", selectedProduct);
        }
    }

    private void initProductFilter() {

        searchProductTextField.textProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable o) {

                ObservableList<Product> productTableItems = FXCollections.observableArrayList();

                ObservableList<TableColumn<Product, ?>> cols = productTable.getColumns();

                for (int i = 0; i < productList.size(); i++) {

                    for (int j = 0; j < cols.size(); j++) {

                        TableColumn col = cols.get(j);

                        String cellValue = col.getCellData(productList.get(i)).toString();

                        cellValue = cellValue.toLowerCase();

                        if (cellValue.contains(searchProductTextField.textProperty().get().toLowerCase())) {

                            productTableItems.add(productList.get(i));

                            break;

                        }
                    }
                }
                productTable.setItems(productTableItems);
            }
        }
        );
    }
    
    private void initPartFilter() {

        searchPartTextField.textProperty().addListener((Observable o) -> {
            ObservableList<Part> partsTableItems = FXCollections.observableArrayList();

            ObservableList<TableColumn<Part, ?>> cols = partsTable.getColumns();

            for (int i = 0; i < partsList.size(); i++) {

                for (int j = 0; j < cols.size(); j++) {

                    TableColumn col = cols.get(j);

                    String cellValue = col.getCellData(partsList.get(i)).toString();

                    cellValue = cellValue.toLowerCase();

                    if (cellValue.contains(searchPartTextField.textProperty().get().toLowerCase())) {

                        partsTableItems.add(partsList.get(i));

                        break;

                    }

                }

            }

            partsTable.setItems(partsTableItems);
        });

    }

    @FXML
    private void handleAddProductButton(ActionEvent event) throws IOException {
        //Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        showDialog("Modify Part", "View_Controller/AddProduct.fxml");
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

}
