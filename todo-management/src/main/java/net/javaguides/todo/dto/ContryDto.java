package net.javaguides.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.javaguides.todo.entity.Member;

import java.math.BigInteger;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContryDto {

    private Long id;
    private BigInteger amount;
    private Date countryDate;
    private Short numberOfInst;
    private Long memberId;

}
