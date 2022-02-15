package com.btaka.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckEmailRequestDTO implements Serializable {

    @NonNull
    private String email;

}
