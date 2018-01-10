/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author yevgeniy
 */
public class Product {

    private int productID;
    private String name;
    private int inStock;
    private double price;
    private int min;
    private int max;
            
    private ObservableList<Part> partsList;

    public int compareTo(Object other) {

        if (((Product) other).getProductID() == this.getProductID()) {
            return 0;
        } else {
            return 1;
        }
    }

    public Product() {
        partsList = FXCollections.observableArrayList();
    }
    // Overriding equals() to compare two Complex objects
    @Override
    public boolean equals(Object other) {
        return ((Product) other).getProductID() == this.getProductID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public ObservableList<Part> getPartsList() {
        return partsList;
    }
    
    public void addAssociatePart(Part part) {
        partsList.add(part);
    }

    public void removeAssociatedPart(Part part) {
        partsList.remove(part);
    }

    public Part lookupAssociatedPart(int partID) {
        for (Part part: partsList) {
            if ( part.getPartID() == partID) {
                return part;
            }
        }
        return null;
    }

    public double priceOfParts() {
        double total = 0;
        for (Part part: partsList) {
            total += part.getPrice();
        }
        return total;
    }
    
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

}
