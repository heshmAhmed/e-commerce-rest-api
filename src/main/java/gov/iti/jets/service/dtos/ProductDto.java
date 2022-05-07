package gov.iti.jets.service.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String desc;
    private String img;
    private Long stock;
    private List<String> categoryList;

    public ProductDto(String name, BigDecimal price, String desc, String img, Long stock, List<String> categoryList) {
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.img = img;
        this.stock = stock;
        this.categoryList = categoryList;
    }
}
