package gov.iti.jets.service;

import gov.iti.jets.repo.OrderRepo;
import gov.iti.jets.repo.entity.OrderEntity;
import gov.iti.jets.repo.entity.OrderStatus;
import gov.iti.jets.service.dtos.OrderDto;
import gov.iti.jets.service.mappers.OrderMapper;
import gov.iti.jets.service.mappers.ProductMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderService {
    private final static OrderService orderService = new OrderService();
    private final OrderRepo orderRepo = OrderRepo.getInstance();
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    private OrderService() {}
    public static OrderService getInstance() {
        return orderService;
    }

    public List<OrderDto> getAllOrders() {
        List<OrderEntity> orderEntities = orderRepo.getAllOrders();
        return orderEntities.stream().map(orderMapper::mapEntityToDto
        ).collect(Collectors.toList());
    }

    public Optional<OrderDto> getOrder(Long id) {
        Optional<OrderDto> optionalOrderDto = Optional.empty();
        Optional<OrderEntity> optionalOrderEntity = orderRepo.findOrderById(id);
        if(optionalOrderEntity.isPresent()){
            OrderEntity orderEntity = optionalOrderEntity.get();
            OrderDto orderDto = orderMapper.mapEntityToDto(orderEntity);
            optionalOrderDto = Optional.of(orderDto);
        }
        return optionalOrderDto;
    }

    public boolean deleteOrder(Long id) {
        return orderRepo.deleteOrder(id);
    }

    public boolean updateOrderStatus(Long id, String orderStatus) {
        System.out.println("orderStatus is : " + orderStatus);
        return orderRepo.updateOrderStatus(id, OrderStatus.valueOf(orderStatus));
    }

    public Optional<OrderDto> submitOrder(Long customerId, OrderDto orderDto) {
        OrderEntity orderEntity = orderMapper.mapDtoToEntity(orderDto);
        Optional<OrderEntity> optionalOrderEntity = orderRepo.createOrder(customerId, orderEntity);
        if(optionalOrderEntity.isEmpty())
            return Optional.empty();
        orderDto.setId(optionalOrderEntity.get().getId());
        return Optional.of(orderDto);
    }

    public boolean cancelOrder(Long customerId, Long orderId) {
        return orderRepo.cancelOrder(customerId, orderId);
    }
}
