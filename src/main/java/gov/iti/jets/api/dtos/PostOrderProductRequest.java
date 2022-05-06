package gov.iti.jets.api.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PostOrderProductRequest {
    private Long productId;
    private BigDecimal total;
    private Long quantity;
}
