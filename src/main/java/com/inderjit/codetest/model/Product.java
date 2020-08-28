package com.inderjit.codetest.model;

/**
 * Vale object for creating & adding PRODUCT. 
 * @author Inderjit SS
 *
 */
public class Product {
	
	public Product () {}
	
    public Product(int id, String category, String company, String productName, String color, String description,
			double price, double discount, int stock) {
		super();
		this.id = id;
		this.category = category;
		this.company = company;
		this.productName = productName;
		this.color = color;
		this.description = description;
		this.price = price;
		this.discount = discount;
		this.stock = stock;
	}
	
    private int id;
    private String category;
    private String company;
    private String productName;
    private String color;
    private String description;
    private double price;
    private double discount;
    private int stock;
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", category=" + category + ", company=" + company + ", productName=" + productName
				+ ", color=" + color + ", description=" + description + ", price=" + price + ", discount=" + discount
				+ ", stock=" + stock + "]";
	}
    

}
