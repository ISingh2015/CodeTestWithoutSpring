package com.inderjit.config.rest;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
 
@Path("/ping")
public class PingService {
 
	@GET
	@Path("/test")
	public Response getPing() {
		String output = "Ping success !!! Server is running ...";
		return Response.status(200).entity(output).build();
 
	}


}