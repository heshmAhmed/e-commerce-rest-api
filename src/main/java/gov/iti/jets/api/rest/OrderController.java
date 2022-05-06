package gov.iti.jets.api.rest;

import gov.iti.jets.api.dtos.ErrorMessage;
import gov.iti.jets.api.dtos.PatchStatusRequest;
import gov.iti.jets.service.OrderService;
import gov.iti.jets.service.dtos.OrderDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.Optional;


@Path("orders")
public class OrderController {
    private final OrderService orderService = OrderService.getInstance();
    @GET
    public Response getAllOrders(){
        return Response.ok().entity(orderService.getAllOrders()).build();
    }

    @GET
    @Path("{id}")
    public Response getOrder(@PathParam("id") Long id) {
        Optional<OrderDto> orderDtoOptional = orderService.getOrder(id);
        if(orderDtoOptional.isEmpty()) {
            ErrorMessage errorMessage = new ErrorMessage("Resource not found",404, "Probably wrong id");
            Response response = Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
            throw new NotFoundException(response);
        }
        return Response.ok().entity(orderDtoOptional.get()).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteOrder(@PathParam("id") Long id) {
        boolean deleted = orderService.deleteOrder(id);
        if(!deleted) {
            ErrorMessage errorMessage = new ErrorMessage("Resource not found", 404, "Probably wrong id");
            Response response = Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
            throw new NotFoundException(response);
        }
        return Response.status(Response.Status.OK).build();
    }

    @PATCH
    @Path("{id}/status")
    public Response updateOrderStatus(@PathParam("id") Long id, PatchStatusRequest body) {
        boolean updated = orderService.updateOrderStatus(id, body.getOrderStatus());
        if(!updated) {
            ErrorMessage errorMessage = new ErrorMessage("Resource not found", 404, "Probably wrong id");
            Response response = Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
            throw new NotFoundException(response);
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }
}
