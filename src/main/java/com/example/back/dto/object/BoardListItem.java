package com.example.back.dto.object;

import com.example.back.entity.BoardListViewEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardListItem {

    private int itemNumber;
    private String title;
    private String content;
    private String videoUrl;
    private String boardTitleImage;
    private String writeDatetime;
    private String writerNickname;
    private String writerProfileImage;
    private int favoriteCount;
    private int commentCount;
    private int viewCount;

    public BoardListItem(BoardListViewEntity boardListViewEntity) {
        this.itemNumber = boardListViewEntity.getItemNumber();
        this.title = boardListViewEntity.getTitle();
        this.content = boardListViewEntity.getContent();
        this.videoUrl = boardListViewEntity.getVideoUrl();
        this.boardTitleImage = boardListViewEntity.getTitleImage();
        this.writeDatetime = boardListViewEntity.getWriteDatetime();
        this.writerNickname = boardListViewEntity.getWriterNickname();
        this.writerProfileImage = boardListViewEntity.getWriterProfileImage();
    }

    public static List<BoardListItem> getList(List<BoardListViewEntity> boardListViewEntities) {
        List<BoardListItem> list = new ArrayList<>();
        for (BoardListViewEntity boardListViewEntity : boardListViewEntities) {
            list.add(new BoardListItem(boardListViewEntity));
        }

        return list;
    }
}
