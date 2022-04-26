package gov.iti.jets.service.mappers;

import gov.iti.jets.repo.entity.OrderEntity;
import gov.iti.jets.repo.entity.OrderProductEntity;
import gov.iti.jets.repo.entity.OrderProductIdEntity;
import gov.iti.jets.service.dtos.OrderDto;
import gov.iti.jets.service.dtos.OrderProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    @Mapping(source = "orderProducts", target = "orderProducts", ignore = true)
    OrderDto orderEntityToDto(OrderEntity order);
    @Mapping(source = "orderProducts", target = "orderProducts", ignore = true)
    OrderEntity orderDtoToEntity(OrderDto orderDto);

    default OrderDto mapEntityToDto(OrderEntity orderEntity) {
        OrderDto orderDto = orderEntityToDto(orderEntity);
        Set<OrderProductDto> orderProductDtoSet = new HashSet<>();
        System.out.println("from mapper: " + orderEntity.getOrderProducts().size());
        orderEntity.getOrderProducts().forEach(orderProductEntity -> {
            OrderProductDto orderProductDto = new OrderProductDto();
            orderProductDto.setQuantity(orderProductEntity.getQuantity());
            orderProductDto.setProduct(ProductMapper.INSTANCE.mapEntityToDto(orderProductEntity.getProduct()));
            orderProductDtoSet.add(orderProductDto);
        });
        orderDto.setOrderProducts(orderProductDtoSet);

        return orderDto;
    }

    default OrderEntity mapDtoToEntity(OrderDto orderDto) {
        System.out.println(orderDto);
        OrderEntity orderEntity = orderDtoToEntity(orderDto);
        Set<OrderProductEntity> orderProductEntities = new HashSet<>();
        orderDto.getOrderProducts().forEach(orderProductDto -> {
            OrderProductEntity orderProductEntity = new OrderProductEntity();
            orderProductEntity.setQuantity(orderProductDto.getQuantity());
            OrderProductIdEntity orderProductIdEntity = new OrderProductIdEntity();
            orderProductIdEntity.setProductId(orderProductDto.getId().getProductId());
            orderProductEntity.setId(orderProductIdEntity);
            orderProductEntity.setProduct(ProductMapper.INSTANCE.productDtoToEntity(orderProductDto.getProduct()));
            orderProductEntities.add(orderProductEntity);
        });
        orderEntity.setOrderProducts(orderProductEntities);

        return orderEntity;
    }

}
