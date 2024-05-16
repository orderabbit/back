package com.example.back.repository.resultSet;

public interface GetBoardResultSet {

    Integer getItemNumber();
    String getTitle();
    String getContent();
    String getVideoUrl();
    String getWriteDatetime();
    String getWriterId();
    String getWriterNickname();
    String getWriterProfileImage();
}
