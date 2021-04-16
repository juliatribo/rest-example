package edu.upc.dsa.services;


import edu.upc.dsa.Classes.Product;
import edu.upc.dsa.Classes.User;
import edu.upc.dsa.Classes.Order;
import edu.upc.dsa.ProductManager;
import edu.upc.dsa.ProductManagerimpl;
import edu.upc.dsa.TracksManager;
import edu.upc.dsa.TracksManagerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/restaurant", description = "Endpoint to Order Service")
@Path("/0rders")
public class OrdersService {

    private TracksManager tm;
    private ProductManager pm;

    public OrdersService() {
        this.tm = TracksManagerImpl.getInstance();
        this.pm = ProductManagerimpl.getInstance();
        if (tm.size()==0) {

            Product product1 = new Product("coca", 2);
            Product product2 = new Product("pan", 1);
            Product product3 = new Product("bocadillo de lomo", 4);
            Product product4 = new Product("patatas", 3);

            this.pm.addProduct(product1);
            this.pm.addProduct(product2);
            this.pm.addProduct(product3);
            this.pm.addProduct(product4);

            User user = new User("Juan");
            User user2 = new User("Maria");

            pm.addUser(user);
            pm.addUser(user2);

        }
    }

    @GET
    @ApiOperation(value = "get Products by Prize", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer="List"),
    })
    @Path("/ProductsByPrize")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByPrize() {

        List<Product> products = this.pm.getProductByPrize();

        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.status(201).entity(entity).build();

    }

    @GET
    @ApiOperation(value = "get Products by Sales", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer="List"),
    })
    @Path("/ProductsBySales")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsBySales() {
        List<Product> products = this.pm.getProductBySales();

        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.status(201).entity(entity).build()  ;
    }

    @DELETE
    @ApiOperation(value = "Process order", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "No orders to process")
    })
    @Path("/")
    public Response processOrder() {

        Order o = pm.processOrder();
        if (o == null) return Response.status(404).build();
        else this.pm.processOrder();
        return Response.status(201).build();
    }

    @GET
    @ApiOperation(value = "get a Orders by User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Order.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrack(@PathParam("id") String id) {
        List<Order> list = this.pm.getOrdersByUser(id);
        if (list == null) return Response.status(404).build();
        else  return Response.status(201).entity(list).build();
    }


    @POST
    @ApiOperation(value = "create a new Order", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Order.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newTrack(Order order) {

        if ( order.getUserName()==null)  return Response.status(500).entity(order).build();
        this.pm.newOrder(order);
        return Response.status(201).build();
    }



}