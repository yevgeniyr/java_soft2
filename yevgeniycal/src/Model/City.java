/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Objects;

/**
 *
 * @author yevgeniy
 */
public class City {
    
    Integer id; 
    String name;
    Integer countryId;
    //Choice(Integer id)                       { this(id, null); }
    //Choice(String  displayString)            { this(null, displayString); }
    public City(Integer _id, String _name) { 
        id = _id; 
        name = _name; 
    }
    @Override public String toString() { return name; }
    @Override public boolean equals(Object o) {
      return  Objects.equals(((City)o).id, this.id);
    }
    @Override 
    public int hashCode() {
      return name.hashCode();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }
    
  }