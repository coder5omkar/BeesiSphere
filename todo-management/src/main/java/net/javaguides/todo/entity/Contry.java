package net.javaguides.todo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contry")
public class Contry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigInteger amount;

    @Temporal(TemporalType.DATE)
    private Date countryDate;

    @Column(nullable = false)
    private Short numberOfInst;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
