package net.javaguides.todo.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.todo.dto.ContryDto;
import net.javaguides.todo.entity.Contry;
import net.javaguides.todo.entity.Member;
import net.javaguides.todo.exception.ResourceNotFoundException;
import net.javaguides.todo.repository.ContryRepository;
import net.javaguides.todo.repository.MemberRepository;
import net.javaguides.todo.service.ContryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContryServiceImpl implements ContryService {

    private ContryRepository contryRepository;

    private MemberRepository memberRepository;

    private ModelMapper modelMapper;

    @Override
    public ContryDto addContry(ContryDto ContryDto) {
        Contry Contry = new Contry();
        Contry.setAmount(ContryDto.getAmount());
        Contry.setCountryDate(ContryDto.getCountryDate());
        Contry.setNumberOfInst(ContryDto.getNumberOfInst());

        Member member = memberRepository.getById(ContryDto.getMemberId());

        Contry.setMember(member);

        Contry savedContry = contryRepository.save(Contry);

        // Convert the saved Contry entity back to ContryDto for response
        ContryDto savedContryDto = new ContryDto();
        savedContryDto.setId(savedContry.getId());
        savedContryDto.setAmount(savedContry.getAmount());
        savedContryDto.setCountryDate(savedContry.getCountryDate());
        savedContryDto.setNumberOfInst(savedContry.getNumberOfInst());
        savedContryDto.setMemberId(savedContry.getMember().getId());

        return savedContryDto;
    }

    @Override
    public ContryDto getContry(Long id) {
        Contry contry = contryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id:" + id));

        return modelMapper.map(contry, ContryDto.class);
    }

    @Override
    public List<ContryDto> getContryByMemberId(Long id) {

        List<Contry> contries = contryRepository.findByMemberId(id);

        return contries.stream().map((contry) -> modelMapper.map(contry, ContryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContryDto> getAllContrys() {
        List<Contry> contries = contryRepository.findAll();

        return contries.stream().map((contry) -> modelMapper.map(contry, ContryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ContryDto updateContry(ContryDto ContryDto, Long id) {
        Contry contry = contryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));
        contry.setAmount(ContryDto.getAmount());
        contry.setNumberOfInst(ContryDto.getNumberOfInst());
        contry.setCountryDate(ContryDto.getCountryDate());

        Contry updatedContry = contryRepository.save(contry);

        ContryDto savedContryDto = new ContryDto();
        savedContryDto.setId(updatedContry.getId());
        savedContryDto.setAmount(updatedContry.getAmount());
        savedContryDto.setCountryDate(updatedContry.getCountryDate());
        savedContryDto.setNumberOfInst(updatedContry.getNumberOfInst());
        savedContryDto.setMemberId(updatedContry.getMember().getId());

        return savedContryDto;
    }

    @Override
    public void deleteContry(Long id) {

        Contry contry = contryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));

        contryRepository.deleteById(id);
    }
}
