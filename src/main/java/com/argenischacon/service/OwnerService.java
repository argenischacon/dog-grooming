package com.argenischacon.service;

import com.argenischacon.dto.owner.OwnerListDto;
import com.argenischacon.dto.owner.OwnerDetailDto;
import com.argenischacon.dto.owner.OwnerFormDto;

import java.util.List;

public interface OwnerService {
    OwnerDetailDto create(OwnerFormDto dto);

    OwnerDetailDto update(Long id, OwnerFormDto dto);

    OwnerDetailDto findById(Long id);

    List<OwnerListDto> list(int page, int size);

    long count();

    void delete(Long id);
}
