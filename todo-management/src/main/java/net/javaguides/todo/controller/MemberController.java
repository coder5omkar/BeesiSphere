package net.javaguides.todo.controller;

import lombok.AllArgsConstructor;
import net.javaguides.todo.dto.ContryDto;
import net.javaguides.todo.dto.MemberDto;
import net.javaguides.todo.service.ContryService;
import net.javaguides.todo.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/members")
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;

    private ContryService contryService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public ResponseEntity<MemberDto> addMember(@RequestBody MemberDto memberDto){

        MemberDto savedMember = memberService.addMember(memberDto);

        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ResponseEntity<MemberDto> getMember(@PathVariable("id") Long memberId){
        MemberDto todoDto = memberService.getMember(memberId);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/members/{todoId}")
    public ResponseEntity<List<MemberDto>> getMembersByBCID(@PathVariable("todoId") Long todoId) {
        List<MemberDto> members = memberService.getMembersByBCID(todoId);
        return ResponseEntity.ok(members);
    }

    // Build Get All Todos REST API
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers(){
        List<MemberDto> todos = memberService.getAllMembers();
        //return new ResponseEntity<>(todos, HttpStatus.OK);
        return ResponseEntity.ok(todos);
    }

    // Build Update Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<MemberDto> updateMember(@RequestBody MemberDto memberDto, @PathVariable("id") Long memberId){
        MemberDto updatedMember = memberService.updateMember(memberDto, memberId);
        return ResponseEntity.ok(updatedMember);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteMember(@PathVariable("id") Long memberId){
        memberService.deleteMember(memberId);
        return ResponseEntity.ok("Todo deleted successfully!.");
    }
}
