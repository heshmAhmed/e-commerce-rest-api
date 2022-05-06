package gov.iti.jets.api.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PostOrderRequest {
    private Date fireDate;
    private String status;
    private BigDecimal price;
    private List<PostOrderProductRequest> orderProducts;
}
