package gov.iti.jets.service.mappers;

import gov.iti.jets.repo.entity.ClerkEntity;
import gov.iti.jets.repo.entity.ProductEntity;
import gov.iti.jets.service.dtos.ClerkDto;
import gov.iti.jets.service.dtos.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    @Mapping(source = "categories", target = "categories", ignore = true)
    ProductDto productEntityToDto(ProductEntity productEntity);
    @Mapping(source = "categories", target = "categories", ignore = true)
    ProductEntity productDtoToEntity(ProductDto productDto);
}
