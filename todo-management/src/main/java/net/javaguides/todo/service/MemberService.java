package net.javaguides.todo.service;
import net.javaguides.todo.dto.MemberDto;

import java.util.List;

public interface MemberService {

    MemberDto addMember(MemberDto MemberDto);

    MemberDto getMember(Long id);

    List<MemberDto> getAllMembers();

    MemberDto updateMember(MemberDto MemberDto, Long id);

    void deleteMember(Long id);

    List<MemberDto> getMembersByBCID(Long bcid);
}
