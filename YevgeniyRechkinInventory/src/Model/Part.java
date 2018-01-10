/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author yevgeniy
 */
public abstract class Part implements Comparable {
  private int partID;
  private String name;
  private double price;
  private int inStock;
  private int min;
  private int max;

  public int compareTo(Object other) {
      System.out.println("comparing other id " + ((Part) other).getPartID() );
      System.out.println("comparing this id " + this.getPartID() );
      
      if ( ((Part) other).getPartID() == this.getPartID() ) {
        return 0;
       }
      else {
          return 1;
      }
  }
  
   // Overriding equals() to compare two Complex objects
    @Override
    public boolean equals(Object other) {
        return ((Part) other).getPartID() == this.getPartID();
    }
  
  public void setName(String name) { this.name = name; } 
  
  public String getName() { return name; }
  public void setPrice(double price) { this.price = price; }
  public double getPrice() { return price; }
  public void setInStock(int inStock) { this.inStock = inStock; }
  public int getInStock() { return inStock; }
  public void setMin(int min) { this.min = min; }
  public int getMin() {return min; }

  public void setMax(int max) { this.max = max; }
  public int getMax() { return this.max; }
  public void setPartID(int partID) { this.partID = partID; }
  public int getPartID () { return partID; }

}
