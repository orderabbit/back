package com.example.back.controller;

import com.example.back.dto.request.auth.board.PatchBoardRequestDto;
import com.example.back.dto.request.auth.board.PostBoardRequestDto;
import com.example.back.dto.request.auth.board.PostCommentRequestDto;
import com.example.back.dto.response.board.*;
import com.example.back.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("")
    public ResponseEntity<? super PostBoardResponseDto> postBoard(
            @RequestBody @Valid PostBoardRequestDto requestBody,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PostBoardResponseDto> response = boardService.postBoard(requestBody, userId);
        return response;
    }

    @GetMapping("/{itemNumber}/comment-list")
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(
            @PathVariable("itemNumber") Integer itemNumber
    ) {
        ResponseEntity<? super GetCommentListResponseDto> reponse = boardService.getCommentList(itemNumber);
        return reponse;
    }

    @PostMapping("/{itemNumber}/comment")
    public ResponseEntity<? super PostCommentResponseDto> postComment(
            @RequestBody @Valid PostCommentRequestDto requestBody,
            @PathVariable("itemNumber") Integer itemNumber,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PostCommentResponseDto> reseponse = boardService.postComment(requestBody, itemNumber, userId);
        return reseponse;
    }

    @GetMapping("/detail/{itemNumber}")
    public ResponseEntity<? super GetBoardResponseDto> getBoard(
            @PathVariable("itemNumber") Integer itemNumber
    ) {
        System.out.println(itemNumber);
        ResponseEntity<? super GetBoardResponseDto> response = boardService.getBoard(itemNumber);
        return response;
    }

    @GetMapping("/{itemNumber}/favorite-list")
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(
            @PathVariable("itemNumber") Integer itemNumber
    ){
        ResponseEntity<? super GetFavoriteListResponseDto> response = boardService.getFavoriteList(itemNumber);
        return response;
    }

    @PutMapping("/{itemNumber}/favorite")
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(
            @PathVariable("itemNumber") Integer itemNumber,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PutFavoriteResponseDto> response = boardService.putFavorite(itemNumber, userId);
        return response;
    }

    @PatchMapping("/{itemNumber}")
    public ResponseEntity<? super PatchBoardResponseDto> patchBoard(
            @RequestBody @Valid PatchBoardRequestDto requestBody,
            @PathVariable("itemNumber") Integer itemNumber,
            @AuthenticationPrincipal String userId
    ){
        ResponseEntity<? super PatchBoardResponseDto> response = boardService.patchBoard(requestBody, itemNumber, userId);
        return response;
    }

    @GetMapping("/{itemNumber}/increase-view-count")
    public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(
            @PathVariable("itemNumber") Integer itemNumber
    ) {
        ResponseEntity<? super IncreaseViewCountResponseDto> response = boardService.increaseViewCount(itemNumber);
        return response;
    }

    @GetMapping("/latest-list")
    public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList(){
        ResponseEntity<? super GetLatestBoardListResponseDto> responsse = boardService.getLatestBoardList();
        return responsse;
    }

    @GetMapping("/top-3")
    public ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList(){
        ResponseEntity<? super GetTop3BoardListResponseDto> response = boardService.getTop3BoardList();
        return response;
    }

    @GetMapping(value = {"/search-list/{searchWord}", "/search-list/{searchWord}/{preSearchWord}"})
    public ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(
            @PathVariable("searchWord") String searchWord,
            @PathVariable(value = "preSearchWord", required = false) String preSearchWord
    ){
        ResponseEntity<? super GetSearchBoardListResponseDto> response = boardService.getSearchBoardList(searchWord, preSearchWord);
        return response;
    }

    @GetMapping("/user-board-list/{userId}")
    public ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(
            @PathVariable("userId") String userId
    ){
        ResponseEntity<? super GetUserBoardListResponseDto> response = boardService.getUserBoardList(userId);
        return response;
    }

    @DeleteMapping("/delete/{itemNumber}")
    public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(
            @PathVariable("itemNumber") Integer itemNumber,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super DeleteBoardResponseDto> response = boardService.deleteBoard(itemNumber, userId);
        return response;
    }
}
