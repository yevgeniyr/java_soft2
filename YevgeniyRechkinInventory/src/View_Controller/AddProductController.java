/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Part;
import Model.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


/**
 * FXML Controller class
 *
 * @author yevgeniy
 */
public class AddProductController extends InventoryProductController implements Initializable {

    //@FXML private Button cancelButton;
    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        

    }  
    
    public void addPart(Part part) {
   //     partsList.add(part);
    //    partsTable.setItems(partsList);
    //    partsTable.refresh();
    }
    
    
    @FXML    
    private void handleOk(ActionEvent actionEvent) {
        Product product = getProductFromControlls();
        
        if ( product == null )  {
            return;
        }
        
        for (Part part: productPartsTable.getItems()) {
            product.addAssociatePart(part);
        }
        
        if (!validate(product) ) {
            return;
        }
        
        int productID  = ((MainScreenController) invocator).nextProductID();
        System.out.println("got product id" + productID);
        product.setProductID(productID);
        
        ((MainScreenController) invocator).addProduct(product);
        closeStage(actionEvent);
    }   
    
    }
