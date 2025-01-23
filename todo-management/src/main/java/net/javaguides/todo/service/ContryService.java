package net.javaguides.todo.service;

import net.javaguides.todo.dto.ContryDto;

import java.util.List;

public interface ContryService {

    ContryDto addContry(ContryDto ContryDto);

    ContryDto getContry(Long id);

    List<ContryDto> getContryByMemberId(Long id);

    List<ContryDto> getAllContrys();

    ContryDto updateContry(ContryDto ContryDto, Long id);

    void deleteContry(Long id);
}

