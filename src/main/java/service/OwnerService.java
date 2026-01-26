package service;

import dto.owner.OwnerListDto;
import dto.owner.OwnerDetailDto;
import dto.owner.OwnerFormDto;

import java.util.List;

public interface OwnerService {
    OwnerDetailDto create(OwnerFormDto dto);

    OwnerDetailDto update(Long id, OwnerFormDto dto);

    OwnerDetailDto findById(Long id);

    List<OwnerListDto> list(int page, int size);

    long count();

    void delete(Long id);
}
