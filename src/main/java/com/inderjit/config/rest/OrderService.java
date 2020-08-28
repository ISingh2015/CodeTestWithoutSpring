package com.inderjit.config.rest;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.inderjit.codetest.model.Porder;
import com.inderjit.codetest.model.Product;
import com.inderjit.domain.dao.OrderDao;
import com.inderjit.domain.dao.ProductDao;


@Path("/orders")
public class OrderService {
	
	private static final Logger log = Logger.getLogger("OrderService.class");
	
	private ProductDao productDao;
	private OrderDao orderDao;

	public OrderService() throws Exception {
		this.productDao = ProductDao.getInstance();
		this.orderDao = OrderDao.getInstance();
	}

	/**
	 * Save dummy order if stock of product ordered is <> dummy orders.
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */

	@POST
	@Path("/addOrder")
	@Consumes("application/json")
	@Produces("application/json")
	public Response addOrder(Porder order) throws Exception {
		if (order.getProductId()==0) Response.status(Status.NOT_FOUND).entity("Product Id not Found").build(); 
		for (Product prod: productDao.getAllProducts().getProductList()) {
			if (order.getProductId() == prod.getId()) {
				List<Porder> ordersToSumQty = orderDao.findAll(prod.getId());
				double stockQtyOrdered = 0;
				for (Porder orderWithNewQty: ordersToSumQty) {
					stockQtyOrdered += orderWithNewQty.getQtyOrdered();
				}
				if (prod.getStock() != stockQtyOrdered) {
					log.info("Saving Order -> " +order.getDescription());
					orderDao.save(order);
				}
				break;
			}
		}
		return Response.status(Status.CREATED).build();
	}

	/**
	 * Delete dummy order or delete all if not present
	 * 
	 * @param orderNo
	 * @return
	 */
	
	@DELETE
	@Path("/delete/{orderNo}")
	public Response delOrder(@PathParam("id") Integer orderNo) {
		boolean ordDeleted = true;
		if (orderNo != null) {
			Porder porder = orderDao.findById(orderNo);
			if (porder!= null) {
				orderDao.delete(porder,false);
			} else {
				ordDeleted=false;
			}
		} else {
			
		}
		return Response.status(Status.NO_CONTENT).entity("Order No " + orderNo + (ordDeleted ? "Deleted ": "Not Deleted ")).build();
	}
}
