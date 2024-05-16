package com.example.back.entity;

import com.example.back.dto.request.auth.music.PostMusicRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "music_list")
@Table(name = "music_list")
public class MusicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String videoUrl;

    public MusicEntity(PostMusicRequestDto dto){

        this.videoUrl = dto.getVideoUrl();
    }
}
