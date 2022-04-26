package gov.iti.jets.service.mappers;

import gov.iti.jets.repo.entity.ClerkEntity;
import gov.iti.jets.repo.entity.CustomerEntity;
import gov.iti.jets.service.dtos.ClerkDto;
import gov.iti.jets.service.dtos.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    CustomerDto customerEntityToDto(CustomerEntity customerEntity);
    CustomerEntity customerDtoToEntity(CustomerDto customerDto);
}
