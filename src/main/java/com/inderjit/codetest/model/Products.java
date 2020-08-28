package com.inderjit.codetest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Value object for populating Product data 
 * @author Inderjit SS
 *
 */

public class Products 
{
    private List<Product> productsList;
    
    public List<Product> getProductList() {
        if(productsList == null) {
            productsList = new ArrayList<>();
        }
        return productsList;
    }
 
    public void setProductList(List<Product> employeeList) {
        this.productsList = employeeList;
    }
}
