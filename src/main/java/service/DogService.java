package service;

import dto.dog.DogDetailDto;
import dto.dog.DogFormDto;
import dto.dog.DogListDto;
import model.Dog;

import java.util.List;

public interface DogService {
    DogDetailDto create(DogFormDto dto);

    DogDetailDto update(Long id, DogFormDto dto);

    DogDetailDto findById(Long id);

    List<DogListDto> list(int page, int size);

    long count();

    void delete(Long id);
}
