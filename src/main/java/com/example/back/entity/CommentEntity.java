package com.example.back.entity;

import com.example.back.dto.request.auth.board.PostCommentRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comment")
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentNumber;
    private String content;
    private String writeDatetime;
    private String userId;
    private int itemNumber;

    public CommentEntity(PostCommentRequestDto dto, Integer itemNumber, String userId) {

        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writeDatetime = simpleDateFormat.format(now);

        this.content = dto.getContent();
        this.writeDatetime = writeDatetime;
        this.userId = userId;
        this.itemNumber = itemNumber;
    }
}
