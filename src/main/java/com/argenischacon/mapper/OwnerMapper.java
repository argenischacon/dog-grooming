package com.argenischacon.mapper;

import com.argenischacon.dto.owner.OwnerListDto;
import com.argenischacon.dto.owner.OwnerDetailDto;
import com.argenischacon.dto.owner.OwnerFormDto;
import com.argenischacon.model.Owner;
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

    OwnerListDto toListDto(Owner owner);

    List<OwnerListDto> toListDto(List<Owner> owners);

    OwnerDetailDto toDetailDto(Owner owner);
}
