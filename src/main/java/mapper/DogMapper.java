package mapper;

import dto.dog.DogDetailDto;
import dto.dog.DogFormDto;
import dto.dog.DogListDto;
import model.Dog;
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

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.name", target = "ownerName")
    DogDetailDto toDetailDto(Dog dog);
}
