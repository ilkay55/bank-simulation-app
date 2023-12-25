package com.ilkay.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
@Data
@Builder
public class TransactionDTO {
    @NotNull
    private UUID sender;
    @NotNull
    private UUID receiver;
    @Positive
    @NotNull
    private BigDecimal amount;
    @NotNull
    @Size(min = 2,max = 250)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String message;
    private Date createDate;

}