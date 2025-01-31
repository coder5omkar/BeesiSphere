package net.javaguides.todo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for each member

    @Column(nullable = false)
    private String name; // Member's name

    @Column(nullable = false, unique = true)
    private String email; // Member's email

    @Column(nullable = false)
    private String phoneNumber; // Member's phone number

    @Column
    private String address; // Member's address

    @Column
    private BigDecimal amountReceived; // Amount received from the business collection process

    @Column
    private BigDecimal maturityAmount; // Expected maturity amount at the end of the collection process

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status; // Status of the member (e.g., Active, Inactive)

    @Temporal(TemporalType.DATE)
    private Date dateJoined; // Date when the member joined the business collection process

    @Temporal(TemporalType.DATE)
    private Date maturityDate; // Expected date of maturity for the collection

    @ManyToOne
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Contry> countrys;
}

