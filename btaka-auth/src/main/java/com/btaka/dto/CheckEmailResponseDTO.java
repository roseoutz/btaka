package com.btaka.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckEmailResponseDTO implements Serializable {

    @NonNull
    private boolean isExist;

    private boolean result;

    private int statusCode;
}
