package gov.iti.jets.service;

import gov.iti.jets.repo.UserRepo;
import gov.iti.jets.repo.entity.ClerkEntity;
import gov.iti.jets.service.dtos.ClerkDto;
import gov.iti.jets.service.mappers.ClerkMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum ClerkService {
    INSTANCE;
    private final UserRepo userRepo = UserRepo.getInstance();
    private final ClerkMapper clerkMapper = ClerkMapper.INSTANCE;

    public Optional<ClerkDto> getClerk(Long id) {
        Optional<ClerkEntity> clerkEntityOptional = userRepo.findClerkById(id);
        Optional<ClerkDto> clerkDtoOptional = Optional.empty();
        if(clerkEntityOptional.isPresent()){
            ClerkEntity clerkEntity = clerkEntityOptional.get();
            ClerkDto clerkDto = clerkMapper.clerkEntityToDto(clerkEntity);
            clerkDtoOptional = Optional.of(clerkDto);
        }
        return clerkDtoOptional;
    }

    public List<ClerkDto> getAllClerks() {
        List<ClerkEntity> clerkEntities = userRepo.getAllClerks();
        List<ClerkDto> clerkDtos = new ArrayList<>();
        clerkEntities.forEach(clerkEntity -> {
            clerkDtos.add(clerkMapper.clerkEntityToDto(clerkEntity));
        });
        return clerkDtos;
    }

    public Optional<ClerkDto> insertClerk(ClerkDto clerkDto) {
        ClerkEntity clerkEntity = clerkMapper.clerkDtoToEntity(clerkDto);
        clerkEntity = (ClerkEntity) userRepo.insertUser(clerkEntity);
        if(clerkEntity.getId() == null)
            return Optional.empty();
        clerkDto.setId(clerkEntity.getId());
        return Optional.of(clerkDto);
    }

    public boolean deleteClerk(Long clerkId){
        return userRepo.removeClerk(clerkId);
    }
}
