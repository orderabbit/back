package com.example.back.service.implement;

import com.example.back.dto.request.auth.music.PostMusicRequestDto;
import com.example.back.dto.response.ResponseDto;
import com.example.back.dto.response.music.DeleteMusicResponseDto;
import com.example.back.dto.response.music.GetMusicListResponseDto;
import com.example.back.dto.response.music.PostMusicResponseDto;
import com.example.back.entity.MusicEntity;
import com.example.back.repository.MusicRepository;
import com.example.back.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicServiceImplement implements MusicService {

    private final MusicRepository musicRepository;

    @Override
    public ResponseEntity<? super PostMusicResponseDto> postMusic(PostMusicRequestDto dto) {

        try {
            MusicEntity musicEntity = new MusicEntity(dto);
            musicRepository.save(musicEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostMusicResponseDto.success();
    }

    @Override
    public ResponseEntity<GetMusicListResponseDto> getPlaylist() {

        List<MusicEntity> musicEntities = musicRepository.findAll();
        List<String> playlist = musicEntities.stream()
                .map(MusicEntity::getVideoUrl)
                .collect(Collectors.toList());
        GetMusicListResponseDto responseDto = new GetMusicListResponseDto(playlist);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<? super DeleteMusicResponseDto> deleteBoard(Long id) {

        try {
            MusicEntity musicEntity = musicRepository.findById(id).orElse(null);
            if (musicEntity == null) {
                return DeleteMusicResponseDto.notExistedMusic();
            }
            musicRepository.delete(musicEntity);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return DeleteMusicResponseDto.success();
    }
}
