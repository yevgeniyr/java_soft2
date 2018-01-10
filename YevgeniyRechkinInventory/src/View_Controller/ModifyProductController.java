/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Part;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;

import Model.Product;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author yevgeniy
 */
public class ModifyProductController extends InventoryProductController implements Initializable {
    // populates controlls on the screen from the part object

    public void setControllsFromProduct(Product product) {
        name.setText(product.getName());
        inStock.setText(Integer.toString(product.getInStock()));;
        productID.setText(Integer.toString(product.getProductID()));
        price.setText(Double.toString(product.getPrice()));
        min.setText(Integer.toString(product.getMin()));
        max.setText(Integer.toString(product.getMax()));

        productPartsTable.setItems(product.getPartsList());

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }

    @Override
    public void populateControls() {
        setControllsFromProduct((Product) invocatorData);
    }

    @FXML
    private void handleOk(ActionEvent actionEvent) {
        Product product = getProductFromControlls();
        product.setProductID(Integer.parseInt(productID.getText()));

        ((MainScreenController) invocator).removeProduct(product);

        for (Part part : productPartsTable.getItems()) {
            product.addAssociatePart(part);
        }

        if (!validate(product) ) {
            return;
        }
        
        ((MainScreenController) invocator).addProduct(product);

        closeStage(actionEvent);
    }

}
