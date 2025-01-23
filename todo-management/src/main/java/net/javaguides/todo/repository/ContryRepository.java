package net.javaguides.todo.repository;

import net.javaguides.todo.entity.Contry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContryRepository extends JpaRepository<Contry, Long> {

    List<Contry> findByMemberId(Long memberId);
}
