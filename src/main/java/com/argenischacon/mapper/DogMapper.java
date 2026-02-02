package com.argenischacon.mapper;

import com.argenischacon.dto.dog.DogDetailDto;
import com.argenischacon.dto.dog.DogFormDto;
import com.argenischacon.dto.dog.DogListDto;
import com.argenischacon.model.Dog;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper (
        componentModel = "default",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DogMapper {
    DogMapper INSTANCE = Mappers.getMapper(DogMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    Dog toEntity(DogFormDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    void updateDogFromDto(DogFormDto dto, @MappingTarget Dog entity);

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.name", target = "ownerName")
    DogListDto toListDto(Dog dog);

    List<DogListDto> toListDto(List<Dog> dogs);

    DogDetailDto toDetailDto(Dog dog);
}
