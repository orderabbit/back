package com.example.back.dto.request.auth.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordRequestDto {

    private Long userId;
    private String currentPassword;
    private String newPassword;
}
