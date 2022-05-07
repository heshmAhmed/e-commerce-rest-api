package gov.iti.jets.api.dtos;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PostOrderProductRequest {
    private Long productId;
    private BigDecimal total;
    private Long quantity;
}
