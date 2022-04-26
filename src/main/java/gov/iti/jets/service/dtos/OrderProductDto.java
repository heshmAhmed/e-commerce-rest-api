package gov.iti.jets.service.dtos;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderProductDto {
    private OrderProductId id;
    private ProductDto product;
    private Long quantity;
    private BigDecimal total;
//    public BigDecimal getTotal() {
//        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
//    }
}
