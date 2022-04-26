package gov.iti.jets.service.dtos;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class OrderDto {
    private Long id;
    private Date fireDate;
    private String status;
    private BigDecimal price;
    private CustomerDto customer;
    private Boolean isSubmitted;
    private Set<OrderProductDto> orderProducts = new HashSet<>();

//    public BigDecimal getPrice() {
//        price = BigDecimal.valueOf(0);
//        orderProducts.forEach(orderProductDto -> price.add(orderProductDto.getTotal()));
//        return price;
//    }
}
