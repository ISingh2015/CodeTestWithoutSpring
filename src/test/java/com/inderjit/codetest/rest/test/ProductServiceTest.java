package com.inderjit.codetest.rest.test;

import static org.junit.Assert.assertEquals;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.grizzly2.web.GrizzlyWebTestContainerFactory;

public class ProductServiceTest extends JerseyTest{
	
	private final static Logger log = Logger.getLogger("ProductServiceTest.class");
	
	@Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

	public ProductServiceTest () throws Exception{
		super("com.inderjit.config.rest");
	}
	
	@Test
    public void testPingService() {
		WebResource webResource = resource();
		String responseMsg = webResource.path("ping/test").get(String.class);
        log.info("Ping Executed ...");
        assertEquals("Ping success !!! Server is running ...", responseMsg);
    }

//	@Test
//    public void testGetAllProducts() {
//		WebResource webResource = resource();		
//        String response = webResource.path("products/allProducts").get(String.class);
//        log.log(Level.INFO,"Output from Server .... " + response);
//    }

}
