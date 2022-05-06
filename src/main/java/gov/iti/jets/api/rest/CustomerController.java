package gov.iti.jets.api.rest;


import gov.iti.jets.api.dtos.ErrorMessage;
import gov.iti.jets.api.dtos.PostCustomerRequest;
import gov.iti.jets.api.dtos.PostOrderRequest;
import gov.iti.jets.api.util.Mapper;
import gov.iti.jets.service.CustomerService;
import gov.iti.jets.service.OrderService;
import gov.iti.jets.service.dtos.CustomerDto;
import gov.iti.jets.service.dtos.OrderDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.Optional;

@Path("customers")
public class CustomerController {
    private final CustomerService customerService = CustomerService.getInstance();
    private final OrderService orderService = OrderService.getInstance();
    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id") Long id) {
        Optional<CustomerDto> customerDtoOptional = customerService.getCustomer(id);
        if(customerDtoOptional.isEmpty()) {
            ErrorMessage errorMessage = new ErrorMessage("Resource not found",404, "Probably wrong id");
            Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
            throw new NotFoundException(response);
        }
        return Response.ok().entity(customerDtoOptional.get()).build();
    }

    @POST
    public Response insertCustomer(PostCustomerRequest customer) {
        CustomerDto customerDto = new CustomerDto(customer.getName(), customer.getEmail(), customer.getJoinedDate(),customer.getBirthDate(),customer.getAddress(), customer.getPassword());
        Optional<CustomerDto> customerDtoOptional = customerService.insertCustomer(customerDto);
        if(customerDtoOptional.isEmpty()){
            ErrorMessage errorMessage = new ErrorMessage("Resource could not be created",406, "Probably duplicate email");
            Response response = Response.status(Status.NOT_ACCEPTABLE).entity(errorMessage).build();
            throw new NotAcceptableException(response);
        }
        return Response.status(Status.CREATED).entity(customerDtoOptional.get()).build();
    }

    @GET
    @Path("{id}/orders")
    public Response getCustomerOrders(@PathParam("id") Long id) {
        return Response.status(Status.OK).entity(customerService.getCustomerOrders(id)).build();
    }

    @GET
    @Path("{customerId}/orders/{orderId}")
    public Response getCustomerOrder(@PathParam("customerId") Long customerId, @PathParam("orderId") Long orderId) {
        Optional<OrderDto> optionalOrderDto = customerService.getCustomerOrder(customerId, orderId);
        if(optionalOrderDto.isEmpty()) {
            ErrorMessage errorMessage = new ErrorMessage("Resource not found",404, "Probably wrong ids");
            Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
            throw new NotFoundException(response);
        }
        return Response.ok().entity(optionalOrderDto.get()).build();
    }

    @POST
    @Path("{id}/orders")
    public Response submitOrder(@PathParam("id") Long id, PostOrderRequest postOrderRequest){
        Optional<OrderDto> optionalOrderDto = orderService.submitOrder(id, Mapper.INSTANCE.mapPostOrderRequestToOrderDto(postOrderRequest));
        if(optionalOrderDto.isEmpty()) {
            ErrorMessage errorMessage = new ErrorMessage("Resource could not be created",406, "Probably wrong id");
            Response response = Response.status(Status.NOT_ACCEPTABLE).entity(errorMessage).build();
            throw new NotAcceptableException(response);
        }
        return Response.status(Status.CREATED).entity(optionalOrderDto.get()).build();
    }

    @DELETE
    @Path("{customerId}/orders/{orderId}")
    public Response cancelOrder(@PathParam("customerId") Long customerId, @PathParam("orderId") Long orderId){
        boolean cancelled = orderService.cancelOrder(customerId, orderId);
        if(!cancelled) {
            ErrorMessage errorMessage = new ErrorMessage("Resources not found", 404, "Probably wrong ids");
            Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
            throw new NotFoundException(response);
        }
        return Response.ok().build();
    }
}
