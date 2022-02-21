package com.btaka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndexResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "{btaka.auth.email.empty}")
    private String socialLoginUrl;

}
