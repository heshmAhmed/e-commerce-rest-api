package gov.iti.jets.service;

import gov.iti.jets.repo.OrderRepo;
import gov.iti.jets.repo.UserRepo;
import gov.iti.jets.repo.entity.CustomerEntity;
import gov.iti.jets.repo.entity.OrderEntity;
import gov.iti.jets.service.dtos.CustomerDto;
import gov.iti.jets.service.dtos.OrderDto;
import gov.iti.jets.service.mappers.CustomerMapper;
import gov.iti.jets.service.mappers.OrderMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerService {
    private final static CustomerService customerService = new CustomerService();
    private final UserRepo userRepo = UserRepo.getInstance();
    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    private final OrderRepo orderRepo = OrderRepo.getInstance();
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;
    private CustomerService() {}

    public static CustomerService getInstance() {
        return customerService;
    }

    public Optional<CustomerDto> getCustomer(Long id) {
        Optional<CustomerEntity> customerEntityOptional = userRepo.findCustomerById(id);
        Optional<CustomerDto> customerDtoOptional = Optional.empty();
        if(customerEntityOptional.isPresent()) {
            CustomerEntity customerEntity = customerEntityOptional.get();
            CustomerDto customerDto = customerMapper.customerEntityToDto(customerEntity);
            customerDtoOptional = Optional.of(customerDto);
        }
        return customerDtoOptional;
    }

    public Optional<CustomerDto> insertCustomer(CustomerDto customerDto) {
        CustomerEntity customerEntity = customerMapper.customerDtoToEntity(customerDto);
        customerEntity = (CustomerEntity) userRepo.insertUser(customerEntity);
        if(customerEntity.getId() == null)
            return Optional.empty();
        customerDto.setId(customerEntity.getId());
        return Optional.of(customerDto);
    }

    public List<OrderDto> getCustomerOrders(Long id) {
        List<OrderEntity> orderEntities = orderRepo.getCustomerOrders(id);
        return orderEntities.stream().map(orderMapper::mapEntityToDto).collect(Collectors.toList());
    }

    public Optional<OrderDto> getCustomerOrder(Long customerId, Long orderId) {
        Optional<OrderEntity> optionalOrderEntity = orderRepo.getCustomerOrder(customerId, orderId);
        Optional<OrderDto> optionalOrderDto = Optional.empty();
        if(optionalOrderEntity.isPresent()) {
            OrderEntity orderEntity = optionalOrderEntity.get();
            OrderDto orderDto = orderMapper.mapEntityToDto(orderEntity);
            optionalOrderDto = Optional.of(orderDto);
        }
        return optionalOrderDto;
    }
}
