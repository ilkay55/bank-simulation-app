package com.ilkay.model;

import com.ilkay.enums.AccountType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class Account {

    private UUID id;
    @NotNull
    @Positive
    private BigDecimal balance;
    @NotNull
    private AccountType accountType;
    private Date creationDate;
    @NotNull
    private Long userId;
    private AccountStatus accountStatus;

}