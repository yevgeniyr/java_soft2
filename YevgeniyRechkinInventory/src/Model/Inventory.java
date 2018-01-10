/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.util.ArrayList;


/**
 *
 * @author yevgeniy
 */
public class Inventory {
    ArrayList<Product> products;
    ArrayList<Part> allParts;
    public void addProduct(Product product) { 
    
    }
    
    public boolean removeProduct(int ProductID) {
      return true;
    }
    
    public Product lookupProduct(int ProductID ) {
      return null;
    }
    
    public Product updateProduct(int ProductID) {
      return null;
    }
   
    public void addPart(Part part) {
      
    }
    public boolean deletePart(Part part) {
      return true;
    }
    public Part lookupPart(int partID) {
      return null;
    }
    
    public void updatePart(int partID) {
     
    }
    
}
