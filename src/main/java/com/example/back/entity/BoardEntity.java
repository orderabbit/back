package com.example.back.entity;

import com.example.back.dto.request.auth.board.PatchBoardRequestDto;
import com.example.back.dto.request.auth.board.PostBoardRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "board_list")
@Table(name = "board_list")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // 리스트의 번호
    private int itemNumber;
    // 제목
    @Column(length = 200)
    private String title;
    private String content;
    private String videoUrl;
    private String writeDatetime;
    private String writerId;
    private int viewCount;
    private int favoriteCount;
    private int commentCount;


    public BoardEntity(PostBoardRequestDto dto, String userId){
        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writeDatetime = simpleDateFormat.format(now);

        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.videoUrl = dto.getVideoUrl();
        this.writeDatetime = writeDatetime;
        this.commentCount = 0;
        this.favoriteCount = 0;
        this.viewCount = 0;
        this.writerId = userId;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void increaseFavoriteCount(){
        this.favoriteCount++;
    }

    public void increaseCommentCount(){
        this.commentCount++;
    }

    public void decreaseFavoriteCount() {
        this.favoriteCount--;
    }

    public void patchBoard(PatchBoardRequestDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.videoUrl = dto.getVideoUrl();
    }
}
