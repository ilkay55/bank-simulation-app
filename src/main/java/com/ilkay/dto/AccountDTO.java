package com.ilkay.dto;

import com.ilkay.enums.AccountStatus;
import com.ilkay.enums.AccountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class AccountDTO {

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