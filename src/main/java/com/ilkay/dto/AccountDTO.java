package com.ilkay.dto;

import com.ilkay.enums.AccountStatus;
import com.ilkay.enums.AccountType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.*;


import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    private long id;
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