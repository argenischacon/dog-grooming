package com.argenischacon.service;

import com.argenischacon.dto.dog.DogDetailDto;
import com.argenischacon.dto.dog.DogFormDto;
import com.argenischacon.dto.dog.DogListDto;

import java.util.List;

public interface DogService {
    DogDetailDto create(DogFormDto dto);

    DogDetailDto update(Long id, DogFormDto dto);

    DogDetailDto findById(Long id);

    List<DogListDto> list(int page, int size);

    long count();

    void delete(Long id);
}
