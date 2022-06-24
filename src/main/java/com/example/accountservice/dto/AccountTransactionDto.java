package com.example.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author m-sabbaghi
 * <a href="https://www.linkedin.com/in/sabbaghi/">...</a>
 * @version 6/7/2022
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountTransactionDto {

    private Long id;

    @JsonProperty("created_time")
    private Date createdTime;

    @NotNull(message = "can not be null")
    private Long amount;

    @JsonProperty("new_balance")
    private Long newBalance;

    @JsonProperty("reference_no")
    private Long referenceNo;
}
