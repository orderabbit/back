package com.example.back.controller;

import com.example.back.dto.request.auth.music.PostMusicRequestDto;
import com.example.back.dto.response.music.DeleteMusicResponseDto;
import com.example.back.dto.response.music.GetMusicListResponseDto;
import com.example.back.dto.response.music.PostMusicResponseDto;
import com.example.back.service.MusicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/music")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @PostMapping("")
    public ResponseEntity<? super PostMusicResponseDto> postMusic(
            @RequestBody @Valid PostMusicRequestDto requestBody
    ) {
        ResponseEntity<? super PostMusicResponseDto> response = musicService.postMusic(requestBody);
        return response;
    }

    @GetMapping("/list")
    public ResponseEntity<? super GetMusicListResponseDto> getMusicList(
    ) {
        ResponseEntity<? super GetMusicListResponseDto> response = musicService.getPlaylist();
        return response;
    }

    @DeleteMapping("/delete/{url}")
    @CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.DELETE)
    public ResponseEntity<? super DeleteMusicResponseDto> deleteMusic(
            @PathVariable("url") String url
    ) {
        ResponseEntity<? super DeleteMusicResponseDto> response = musicService.deleteMusicByUrl(url);
        return response;
    }
}
