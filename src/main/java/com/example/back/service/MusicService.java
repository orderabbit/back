package com.example.back.service;

import com.example.back.dto.request.auth.music.PostMusicRequestDto;
import com.example.back.dto.response.music.DeleteMusicResponseDto;
import com.example.back.dto.response.music.GetMusicListResponseDto;
import com.example.back.dto.response.music.PostMusicResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MusicService {
    ResponseEntity<? super PostMusicResponseDto> postMusic(PostMusicRequestDto dto);
    ResponseEntity<GetMusicListResponseDto> getPlaylist();
    ResponseEntity<? super DeleteMusicResponseDto> deleteBoard(Long id);
}
