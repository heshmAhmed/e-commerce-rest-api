package gov.iti.jets.api.dtos;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class PostProductRequest {
    private String name;
    private BigDecimal price;
    private String desc;
    private String img;
    private Long stock;
    @XmlElementWrapper(name = "categories")
    @XmlElement(name = "category")
    private List<String> categoryList;
}
