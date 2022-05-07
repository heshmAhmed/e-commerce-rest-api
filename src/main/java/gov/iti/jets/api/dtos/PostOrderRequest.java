package gov.iti.jets.api.dtos;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class PostOrderRequest {
    private Date fireDate;
    private String status;
    private BigDecimal price;
    @XmlElementWrapper(name="orderProductsList")
    @XmlElement(name="orderProduct")
    private List<PostOrderProductRequest> orderProducts;
}
