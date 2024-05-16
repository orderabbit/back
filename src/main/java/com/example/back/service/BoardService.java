package com.example.back.service;

import com.example.back.dto.request.auth.board.PatchBoardRequestDto;
import com.example.back.dto.request.auth.board.PostBoardRequestDto;
import com.example.back.dto.request.auth.board.PostCommentRequestDto;
import com.example.back.dto.response.board.*;
import org.springframework.http.ResponseEntity;

public interface BoardService {
    ResponseEntity<? super GetBoardResponseDto> getBoard(Integer itemNumber);
    ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer itemNumber);
    ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer itemNumber);
    ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList();
    ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord, String preSearchWord);
    ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(String userId);
    ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList();
    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String userId);
    ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer itemNumber, String userId);
    ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer itemNumber, String userId);
    ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer itemNumber, String userId);
    ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer itemNumber);
    ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer itemNumber, String userId);
}
