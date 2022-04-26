package gov.iti.jets.service.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String desc;
    private String img;
    private Long stock;
    private List<String> categoryList;

}
