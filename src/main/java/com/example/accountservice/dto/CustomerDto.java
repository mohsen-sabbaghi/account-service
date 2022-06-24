package com.example.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

/**
 * @author m-sabbaghi
 * <a href="https://www.linkedin.com/in/sabbaghi/">...</a>
 * @version 6/7/2022
 */

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class CustomerDto {

    private Long id;

    @JsonProperty("created_time")
    private Date createdTime;

    @NotNull(message = "Name can not be null")
    @Size(message = "name must be between 2 and 50 characters", min = 2, max = 50)
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Surname can not be null")
    @Size(message = "Surname must be between 2 and 50 characters", min = 2, max = 50)
    @JsonProperty("surname")
    private String surname;

    private Set<AccountDto> accounts;
}
