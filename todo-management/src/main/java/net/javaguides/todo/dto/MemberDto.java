package net.javaguides.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.javaguides.todo.entity.MemberStatus;
import net.javaguides.todo.entity.Todo;

import java.math.BigDecimal;
import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private BigDecimal amountReceived;
    private BigDecimal maturityAmount;
    private MemberStatus status;
    private Date dateJoined;
    private Date maturityDate;
    private Long todo;
}
