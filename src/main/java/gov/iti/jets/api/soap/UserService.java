package gov.iti.jets.api.soap;


import gov.iti.jets.api.dtos.PostClerkRequest;
import gov.iti.jets.api.dtos.PostCustomerRequest;
import gov.iti.jets.api.dtos.PostOrderRequest;
import gov.iti.jets.api.util.Mapper;
import gov.iti.jets.service.ClerkService;
import gov.iti.jets.service.CustomerService;
import gov.iti.jets.service.OrderService;
import gov.iti.jets.service.dtos.ClerkDto;
import gov.iti.jets.service.dtos.CustomerDto;
import gov.iti.jets.service.dtos.OrderDto;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;

import java.util.List;
import java.util.Optional;

@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class UserService {
    private final CustomerService customerService = CustomerService.getInstance();
    private final OrderService orderService = OrderService.getInstance();
    private final ClerkService clerkService = ClerkService.INSTANCE;

    @WebMethod(action = "get_clerks",operationName = "getClerks")
    public List<ClerkDto> getAllClerks() {
        return clerkService.getAllClerks();
    }

    @WebMethod
    public ClerkDto getClerk(@WebParam(name = "clerkId") Long id){
        Optional<ClerkDto> clerkDtoOptional = clerkService.getClerk(id);
        if(clerkDtoOptional.isEmpty())
            throw new RuntimeException("Resource not found, Probably wrong id");
        return clerkDtoOptional.get();
    }

    @WebMethod
    public ClerkDto createClerk(@WebParam(name = "clerk") PostClerkRequest clerk) {
        ClerkDto clerkDto = new ClerkDto(clerk.getName(), clerk.getEmail(), clerk.getHireDate(), clerk.getPassword());
        Optional<ClerkDto> clerkDtoOptional = clerkService.insertClerk(clerkDto);
        if(clerkDtoOptional.isEmpty())
            throw new RuntimeException("Resource could not be created, Probably duplicate email");

        return clerkDtoOptional.get();
    }

    @WebMethod
    public void deleteClerk(@WebParam(name = "clerkId") Long clerkId){
        boolean deleted = clerkService.deleteClerk(clerkId);
        if(!deleted){
            throw new RuntimeException("Resource not found, Probably wrong id");
        }
    }

    @WebMethod
    public CustomerDto getCustomer(@WebParam(name = "customerId") Long id) {
        Optional<CustomerDto> customerDtoOptional = customerService.getCustomer(id);
        if(customerDtoOptional.isEmpty())
            throw new RuntimeException("Resource not found, probably wrong id");
        return customerDtoOptional.get();
    }

    @WebMethod(operationName = "createCustomer")
    public CustomerDto insertCustomer(@WebParam(name = "customer") PostCustomerRequest customer) {
        CustomerDto customerDto = new CustomerDto(customer.getName(), customer.getEmail(), customer.getJoinedDate(),customer.getBirthDate(),customer.getAddress(), customer.getPassword());
        Optional<CustomerDto> customerDtoOptional = customerService.insertCustomer(customerDto);
        if(customerDtoOptional.isEmpty())
            throw new RuntimeException("Resource could not be created, probably duplicate email");
        return customerDtoOptional.get();
    }

    @WebMethod
    public List<OrderDto> getCustomerOrders(@WebParam(name = "customerId") Long id) {
        return customerService.getCustomerOrders(id);
    }

    @WebMethod
    public OrderDto getCustomerOrder(@WebParam(name = "customerId") Long customerId, @WebParam(name = "orderId") Long orderId){
        Optional<OrderDto> optionalOrderDto = customerService.getCustomerOrder(customerId, orderId);
        if(optionalOrderDto.isEmpty())
            throw new RuntimeException("Resource not found probably wrong ids");
        return optionalOrderDto.get();
    }

    @WebMethod
    public OrderDto submitOrder(@WebParam(name = "customerId") Long id, @WebParam(name = "order") PostOrderRequest postOrderRequest) {
        Optional<OrderDto> optionalOrderDto = orderService.submitOrder(id, Mapper.INSTANCE.mapPostOrderRequestToOrderDto(postOrderRequest));
        if(optionalOrderDto.isEmpty())
            throw new RuntimeException("Resource could not be created probably wrong id");
        return optionalOrderDto.get();
    }

    @WebMethod
    public void cancelOrder(@WebParam(name = "customerId") Long customerId, @WebParam(name = "orderId") Long orderId){
        boolean cancelled = orderService.cancelOrder(customerId, orderId);
        if(!cancelled) {
            throw new RuntimeException("Resources not found, probably wrong ids");
        }
    }

    @WebMethod(operationName = "getOrders")
    public List<OrderDto> getAllOrders(){
        return orderService.getAllOrders();
    }

    @WebMethod
    public OrderDto getOrder(@WebParam(name = "orderId") Long id) {
        Optional<OrderDto> orderDtoOptional = orderService.getOrder(id);
        if(orderDtoOptional.isEmpty())
            throw new RuntimeException("Resource not found, probably wrong id");
        return orderDtoOptional.get();
    }

    @WebMethod
    public void deleteOrder(@WebParam(name = "orderId") Long id){
        boolean deleted = orderService.deleteOrder(id);
        if(!deleted)
            throw new RuntimeException("Resource not found, probably wrong id");
    }

    @WebMethod
    public void updateOrderStatus(@WebParam(name = "orderId") Long id, @WebParam(name = "status") String status) {
        boolean updated = orderService.updateOrderStatus(id, status);
        if(!updated)
            throw new RuntimeException("Resource not found, probably wrong id");
    }
}
