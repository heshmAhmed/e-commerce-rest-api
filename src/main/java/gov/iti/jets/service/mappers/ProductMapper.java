package gov.iti.jets.service.mappers;

import gov.iti.jets.repo.entity.ClerkEntity;
import gov.iti.jets.repo.entity.ProductEntity;
import gov.iti.jets.service.dtos.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    ProductDto productEntityToDto(ProductEntity productEntity);
    ProductEntity productDtoToEntity(ProductDto productDto);

    default ProductDto mapEntityToDto(ProductEntity productEntity) {
        ProductDto productDto = productEntityToDto(productEntity);
        List<String> categories = new ArrayList<>();
        productEntity.getCategories().forEach(category -> categories.add(category.getCategory()));
        productDto.setCategoryList(categories);
        return productDto;
    }
}
