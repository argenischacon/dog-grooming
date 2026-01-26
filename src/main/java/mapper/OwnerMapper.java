package mapper;

import dto.owner.OnwerListDto;
import dto.owner.OwnerDetailDto;
import dto.owner.OwnerFormDto;
import model.Owner;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = "default",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OwnerMapper {
    OwnerMapper INSTANCE = Mappers.getMapper(OwnerMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dogs", ignore = true)
    Owner toEntity(OwnerFormDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dogs", ignore = true)
    void updateFromDto(OwnerFormDto dto, @MappingTarget Owner entity);

    OnwerListDto toListDto(Owner owner);

    List<OnwerListDto> toListDto(List<Owner> owners);

    OwnerDetailDto toDetailDto(Owner owner);
}
