package com.example.back.dto.request.auth.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchProfileImageRequestDto {

    private String profileImage;
}
