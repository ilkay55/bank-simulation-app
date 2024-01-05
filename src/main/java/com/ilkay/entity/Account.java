package com.ilkay.entity;

import com.ilkay.enums.AccountStatus;
import com.ilkay.enums.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "accounts")
public class Account {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Column(columnDefinition = "TIMESTAMP")
    private Date creationDate;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
}
