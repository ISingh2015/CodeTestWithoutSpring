package com.inderjit.config.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.inderjit.codetest.model.Porder;
import com.inderjit.codetest.model.Product;
import com.inderjit.domain.dao.OrderDao;
import com.inderjit.domain.dao.ProductDao;


@Path("/products")
public class ProductService {
	
	private final static Logger log = Logger.getLogger("ProductService.class");
	
	private ProductDao productDao;
	private OrderDao orderDao;
	
	/**
	 * Use Singleton product, Order DAO
	 * @throws Exception
	 */
	public ProductService() throws Exception {
		this.productDao = ProductDao.getInstance();
		this.orderDao = OrderDao.getInstance();
	}

	/**
	 * Get All Products.
	 * 
	 * @return
	 */
	@GET
	@Path("/allProducts")
	@Produces("application/json")	
	public Response getAllProducts() {
		log.info("Getting All Products");
		return Response.status(200).entity(productDao.getAllProducts()).build();
	}

	/**
	 * Get Products Sold by a Company and by price, where price is not equal to
	 * entered price.
	 * 
	 * @param company
	 * @param price
	 * @return
	 */
	@GET()
	@Path("/byCompany")
	@Produces("application/json")
	public Response getAllByCompanyOrPrice(@QueryParam("comp") String company,
			@QueryParam("price") Double price) {
		if (company==null || company.isEmpty()) return Response.status(204).entity("Company not found").build();
		log.info("GetAllProductsByCompany -> "+company+ " or Price -> "+ price);
		List<Product> products=new ArrayList<>();;
		filterProductsList(company,price);
		return Response.status(200).entity(products).build();
	}
	
	/**
	 * Get Products Stock from Product and add OrderedQty (i.e. stock ordered) for
	 * the product from Orders placed.
	 * 
	 * @param prodCode
	 * @return
	 */
	@GET()
	@Path("/getStock/{prodCode}")
	@Produces("application/json")
	public Response getStock(@PathParam("prodCode") Integer productCD) {
		log.info("GetStock -> "+ productCD);
		List<Porder> orders=new ArrayList<> ();
		double productStockFromOrders=0d,productStockFromProducts=0d;
		if (productCD > 0) {
			boolean found=false;
			for (Product prod : productDao.getAllProducts().getProductList()) {
				if (prod.getId()==productCD) {
					productStockFromProducts+= prod.getStock();
					found=true;
				}
			}
			if (!found) {
				return Response.status(204).entity("Product not found").build();				
			}
			orders = orderDao.findAll(productCD);
			if (!orders.isEmpty()) {
				for (Porder order : orders) {
					if (order.getProductId()==productCD) {
						productStockFromOrders+= order.getQtyOrdered();
					}
				}
				return Response.status(200).entity(productStockFromOrders+productStockFromProducts).build();				
			} else {
				return Response.status(200).entity(new Double(productStockFromProducts)).build();
			}
		}
		return Response.status(204).entity("Product not found").build();		
	}

	/**
	 * Get Products sold By Category and by price, where price is not equal to
	 * entered price.
	 * 
	 * @param category
	 * @param price
	 * @return
	 */
	@GET()
	@Path("/byCategory")
	@Produces("application/json")
	public Response getAllByCategoryOrPrice(@QueryParam("cat") String category,
			@QueryParam("price") Double price) {
		if (category == null || category.isEmpty()) {
			return Response.status(204).entity("Category Not Found").build();
		}
		log.info("GetAllProductsByCategory -> "+category+" or Price -> "+price);
		List<Product> products = new ArrayList<> ();
		if (price != null && price > 0) {
			for (Product prod : productDao.getAllProducts().getProductList()) {
				if (prod.getCategory().equals(category) && prod.getPrice() == price) {
					products.add(prod);
				}
			}
		} else {
			for (Product prod : productDao.getAllProducts().getProductList()) {
				if (prod.getCategory().equals(category)) {
					products.add(prod);
				}
			}
		}
		return Response.status(200).entity(products).build();
	}

	private ArrayList<Product> filterProductsList (String filterComp, Double filterPrice) {
		ArrayList<Product> filteredList=new ArrayList<>();
		if (filterPrice != null) {
			for (Product prod : productDao.getAllProducts().getProductList()) {
				if (prod.getCompany().equals(filterComp) && filterPrice != prod.getPrice()) {
					filteredList.add(prod);
				}
			}
		} else {
			for (Product prod : productDao.getAllProducts().getProductList()) {
				if (prod.getCompany().equals(filterComp)) {
					filteredList.add(prod);
				}
			}
		}
		return filteredList;
	}

}