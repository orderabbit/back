package com.example.back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "board_list_view")
@Table(name = "board_list_view")
public class BoardListViewEntity {

    @Id
    private int itemNumber;
    private String title;
    private String content;
    private String videoUrl;
    private String titleImage;
    private String writerId;
    private String writerNickname;
    private String writerProfileImage;
    private String writeDatetime;
    private int viewCount;
    private int favoriteCount;
    private int commentCount;
}
