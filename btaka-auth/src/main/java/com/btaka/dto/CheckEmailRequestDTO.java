package com.btaka.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckEmailRequestDTO implements Serializable {

    @NotEmpty(message = "{btaka.check.email.empty}")
    private String email;

}
