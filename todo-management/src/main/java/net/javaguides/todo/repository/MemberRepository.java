package net.javaguides.todo.repository;

import net.javaguides.todo.entity.Member;
import net.javaguides.todo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
