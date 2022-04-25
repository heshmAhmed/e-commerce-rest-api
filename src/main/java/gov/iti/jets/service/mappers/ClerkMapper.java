package gov.iti.jets.service.mappers;

import gov.iti.jets.repo.entity.ClerkEntity;
import gov.iti.jets.service.dtos.ClerkDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClerkMapper {
    ClerkMapper INSTANCE = Mappers.getMapper(ClerkMapper.class);
    ClerkDto clerkEntityToDto(ClerkEntity clerkEntity);
    ClerkEntity clerkDtoToEntity(ClerkDto clerkDto);
}
