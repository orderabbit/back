package com.example.back.dto.request.auth.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PatchBoardRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private String videoUrl;
    @NotNull
    private List<String> boardImageList;
}
