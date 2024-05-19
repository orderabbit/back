package com.example.back.dto.response.music;

import com.example.back.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetMusicListResponseDto extends ResponseDto {
    private List<String> playlist;

    public GetMusicListResponseDto(List<String> playlist) {
        this.playlist = playlist;
    }

    public List<String> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<String> playlist) {
        this.playlist = playlist;
    }
}