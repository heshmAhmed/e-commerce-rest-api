package gov.iti.jets.api.util;

import gov.iti.jets.api.dtos.PostOrderRequest;
import gov.iti.jets.service.dtos.OrderDto;
import gov.iti.jets.service.dtos.OrderProductDto;
import gov.iti.jets.service.dtos.OrderProductId;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public enum Mapper {
    INSTANCE;
    public OrderDto mapPostOrderRequestToOrderDto(PostOrderRequest postOrderRequest) {
        OrderDto orderDto = new OrderDto();
        orderDto.setFireDate(postOrderRequest.getFireDate());
        orderDto.setPrice(postOrderRequest.getPrice());
        orderDto.setStatus(postOrderRequest.getStatus());
        orderDto.setOrderProducts(
        postOrderRequest.getOrderProducts().stream().map(postOrderProductRequest -> {
            OrderProductDto orderProductDto = new OrderProductDto();
            OrderProductId orderProductId = new OrderProductId();
            orderProductId.setProductId(postOrderProductRequest.getProductId());
            orderProductDto.setId(orderProductId);
            return orderProductDto;
        }).collect(Collectors.toSet()));
        return orderDto;
    }
}
