package net.javaguides.todo.service.impl;

import lombok.AllArgsConstructor;

import net.javaguides.todo.dto.MemberDto;
import net.javaguides.todo.dto.TodoDto;
import net.javaguides.todo.entity.Member;
import net.javaguides.todo.entity.MemberStatus;
import net.javaguides.todo.entity.Todo;
import net.javaguides.todo.exception.ResourceNotFoundException;
import net.javaguides.todo.repository.MemberRepository;
import net.javaguides.todo.repository.TodoRepository;
import net.javaguides.todo.service.MemberService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    private TodoRepository todoRepository;

    private ModelMapper modelMapper;
    // convert MemberDto into Member Jpa entity

    @Override
    public MemberDto addMember(MemberDto memberDto) {
        // Custom mapping for todoId to ensure proper conversion
        modelMapper.addConverter(new Converter<Todo, Long>() {
            public Long convert(MappingContext<Todo, Long> context) {
                return context.getSource() != null ? context.getSource().getId() : null;  // Assuming 'getId()' gets the Todo's ID
            }
        });

        // Convert MemberDto into Member Jpa entity
        Member member = modelMapper.map(memberDto, Member.class);

        // If a todoId is provided, associate the Todo entity
        if (memberDto.getTodo() != null) {
            // Fetch the Todo entity by its todoId
            Todo todo = todoRepository.findById(memberDto.getTodo())
                    .orElseThrow(() -> new RuntimeException("Todo not found"));

            // Set the Todo entity to the Member
            member.setTodo(todo);
        }

        // Save the Member Jpa entity
        Member savedMember = memberRepository.save(member);

        // Convert saved Member Jpa entity object into MemberDto object
        MemberDto savedMemberDto = modelMapper.map(savedMember, MemberDto.class);

        return savedMemberDto;
    }


    @Override
    public MemberDto getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id:" + id));

        return modelMapper.map(member, MemberDto.class);
    }

    @Override
    public List<MemberDto> getAllMembers() {
        // Configure ModelMapper for Member to MemberDto mapping
        modelMapper.typeMap(Member.class, MemberDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getTodo().getId(), MemberDto::setTodo); // Map Todo's ID to MemberDto
        });

        // Fetch all members from the repository
        List<Member> members = memberRepository.findAll();

        // Convert List<Member> to List<MemberDto> using ModelMapper
        return members.stream()
                .map(member -> modelMapper.map(member, MemberDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public MemberDto updateMember(MemberDto memberDto, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));

        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        member.setPhoneNumber(memberDto.getPhoneNumber());
        member.setAddress(memberDto.getAddress());
        member.setAmountReceived(memberDto.getAmountReceived());
        member.setMaturityAmount(memberDto.getMaturityAmount());
        member.setStatus(memberDto.getStatus());
        member.setDateJoined(memberDto.getDateJoined());
        member.setMaturityDate(memberDto.getMaturityDate());
        Todo todo = todoRepository.getReferenceById(memberDto.getTodo());
        member.setTodo(todo);

        Member updatedMember= memberRepository.save(member);

        MemberDto memberDto1 = new MemberDto();
        memberDto1.setId(updatedMember.getId());
        memberDto1.setName(updatedMember.getName());
        memberDto1.setEmail(updatedMember.getEmail());
        memberDto1.setPhoneNumber(updatedMember.getPhoneNumber());
        memberDto1.setAddress(updatedMember.getAddress());
        memberDto1.setAmountReceived(updatedMember.getAmountReceived());
        memberDto1.setMaturityAmount(updatedMember.getMaturityAmount());
        memberDto1.setStatus(updatedMember.getStatus());
        memberDto1.setDateJoined(updatedMember.getDateJoined());
        memberDto1.setMaturityDate(updatedMember.getMaturityDate());
        memberDto1.setTodo(updatedMember.getTodo().getId());

        return memberDto1;
    }

    @Override
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));

        memberRepository.deleteById(id);
    }
}
