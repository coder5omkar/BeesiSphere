package net.javaguides.todo.repository;

import net.javaguides.todo.entity.Contry;
import net.javaguides.todo.entity.Member;
import net.javaguides.todo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findBytodoId(Long bcId);
}
