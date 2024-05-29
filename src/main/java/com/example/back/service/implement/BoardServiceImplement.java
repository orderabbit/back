package com.example.back.service.implement;

import com.example.back.dto.request.auth.board.PatchBoardRequestDto;
import com.example.back.dto.request.auth.board.PostBoardRequestDto;
import com.example.back.dto.request.auth.board.PostCommentRequestDto;
import com.example.back.dto.response.ResponseDto;
import com.example.back.dto.response.board.*;
import com.example.back.entity.*;
import com.example.back.repository.*;
import com.example.back.repository.resultSet.GetBoardResultSet;
import com.example.back.repository.resultSet.GetCommentListResultSet;
import com.example.back.repository.resultSet.GetFavoriteListResultSet;
import com.example.back.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;
    private final FavoriteRepository favoriteRepository;
    private final SearchLogRepository searchLogRepository;
    private final BoardListViewRepository boardListViewRepository;

    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer itemNumber) {

        GetBoardResultSet resultSet = null;
        List<ImageEntity> imageEntities = new ArrayList<>();

        try {
            resultSet = boardRepository.getBoard(itemNumber);

            if (resultSet == null) return GetBoardResponseDto.notExistBoard();

            imageEntities = imageRepository.findByItemNumber(itemNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetBoardResponseDto.success(resultSet, imageEntities);
    }

    @Override
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer itemNumber) {

        List<GetFavoriteListResultSet> resultSets = new ArrayList<>();

        try{
            boolean existedBoard = boardRepository.existsByItemNumber(itemNumber);
            if(!existedBoard) return GetFavoriteListResponseDto.notExistBoard();

            resultSets = favoriteRepository.getFavoriteList(itemNumber);

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetFavoriteListResponseDto.success(resultSets);
    }

    @Override
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer itemNumber) {

        List<GetCommentListResultSet> resultSets = new ArrayList<>();

        try {
            boolean existedBoard = boardRepository.existsByItemNumber(itemNumber);
            if (!existedBoard) return GetCommentListResponseDto.notExistBoard();

            resultSets = commentRepository.getCommentList(itemNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetCommentListResponseDto.success(resultSets);
    }

    @Override
    public ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList() {

        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();

        try{

            Date beforeWeek = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sevenDaysAgo = simpleDateFormat.format(beforeWeek);

            boardListViewEntities = boardListViewRepository.findTop3ByWriteDatetimeGreaterThanOrderByFavoriteCountDescCommentCountDescViewCountDescWriteDatetimeDesc(sevenDaysAgo);

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetTop3BoardListResponseDto.success(boardListViewEntities);
    }

    @Override
    public ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord, String preSearchWord) {

        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();

        try{
            boardListViewEntities = boardListViewRepository.findByTitleContainsOrContentContainsOrderByWriteDatetimeDesc(searchWord, searchWord);

            SearchLogEntity searchLogEntity = new SearchLogEntity(searchWord, preSearchWord, false);
            searchLogRepository.save(searchLogEntity);

            boolean relation = preSearchWord != null;
            if(relation) {
                searchLogEntity = new SearchLogEntity(preSearchWord, searchWord, relation);
                searchLogRepository.save(searchLogEntity);
            }

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetSearchBoardListResponseDto.success(boardListViewEntities);
    }

    @Override
    public ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(String userId) {

        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();

        try {
            boolean existedUser = userRepository.existsByUserId(userId);
            if(!existedUser) return GetUserBoardListResponseDto.notExistUser();

            boardListViewEntities = boardListViewRepository.findByWriterIdOrderByWriteDatetimeDesc(userId);

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetUserBoardListResponseDto.success(boardListViewEntities);
    }

    @Override
    public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList() {

        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();

        try{
            boardListViewEntities = boardListViewRepository.findByOrderByWriteDatetimeDesc();

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetLatestBoardListResponseDto.success(boardListViewEntities);
    }

    @Override
    public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String userId) {

        try {
            boolean existedUserId = userRepository.existsByUserId(userId);
            if (!existedUserId) return PostBoardResponseDto.notExistUser();

            BoardEntity boardEntity = new BoardEntity(dto, userId);
            boardRepository.save(boardEntity);

            int itemNumber = boardEntity.getItemNumber();
            List<String> boardImageList = dto.getBoardImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for (String image : boardImageList) {
                ImageEntity imageEntity = new ImageEntity(itemNumber, image, userId);
                imageEntities.add(imageEntity);
            }
            imageRepository.saveAll(imageEntities);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostBoardResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer itemNumber, String userId) {
        try {

            boolean existedUser = userRepository.existsByUserId(userId);
            if (!existedUser) return PutFavoriteResponseDto.notExistUser();

            BoardEntity boardEntity = boardRepository.findByItemNumber(itemNumber);
            if (boardEntity == null) return PutFavoriteResponseDto.notExistBoard();

            FavoriteEntity favoriteEntity = favoriteRepository.findByItemNumberAndUserId(itemNumber, userId);
            if (favoriteEntity == null) {
                favoriteEntity = new FavoriteEntity(userId, itemNumber);
                favoriteRepository.save(favoriteEntity);
                boardEntity.increaseFavoriteCount();
            } else {
                favoriteRepository.delete(favoriteEntity);
                boardEntity.decreaseFavoriteCount();
            }

            boardRepository.save(boardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PutFavoriteResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer itemNumber, String userId) {

        try{
            BoardEntity boardEntity = boardRepository.findByItemNumber(itemNumber);
            if(boardEntity == null) return PatchBoardResponseDto.notExistBoard();

            boolean existedUser = userRepository.existsByUserId(userId);
            if(!existedUser) return PatchBoardResponseDto.notExistUser();

            String writerId = boardEntity.getWriterId();
            boolean isWriter = writerId.equals(userId);
            if(!isWriter) return PatchBoardResponseDto.notPermission();

            boardEntity.patchBoard(dto);
            boardRepository.save(boardEntity);

            imageRepository.deleteByItemNumber(itemNumber);
            List<String> boardImageList = dto.getBoardImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for(String image: boardImageList) {
                ImageEntity imageEntity = new ImageEntity(itemNumber, image, userId);
                imageEntities.add(imageEntity);
            }

            imageRepository.saveAll(imageEntities);

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchBoardResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer itemNumber, String userId) {

        try {
            BoardEntity boardEntity = boardRepository.findByItemNumber(itemNumber);
            if (boardEntity == null) return PostCommentResponseDto.notExistBoard();

            boolean existUser = userRepository.existsByUserId(userId);
            if (!existUser) return PostCommentResponseDto.notExistUser();

            CommentEntity commentEntity = new CommentEntity(dto, itemNumber, userId);
            commentRepository.save(commentEntity);

            boardEntity.increaseCommentCount();
            boardRepository.save(boardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostCommentResponseDto.success();
    }

    @Override
    public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer itemNumber) {

        try{

            BoardEntity boardEntity = boardRepository.findByItemNumber(itemNumber);
            if(boardEntity == null) return IncreaseViewCountResponseDto.notExistBoard();
            boardEntity.increaseViewCount();
            boardRepository.save(boardEntity);

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return IncreaseViewCountResponseDto.success();
    }

    @Override
    public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer itemNumber, String userId) {
        try{

            boolean existedUser = userRepository.existsByUserId(userId);
            if(!existedUser) return DeleteBoardResponseDto.notExistedUser();

            BoardEntity boardEntity = boardRepository.findByItemNumber(itemNumber);
            if(boardEntity == null) return DeleteBoardResponseDto.notExistedBoard();

            String writerId = boardEntity.getWriterId();
            boolean isWriter = writerId.equals(userId);
            if(!isWriter) return DeleteBoardResponseDto.notPermission();

            imageRepository.deleteByItemNumber(itemNumber);
            commentRepository.deleteByItemNumber(itemNumber);
            favoriteRepository.deleteByItemNumber(itemNumber);
            boardRepository.delete(boardEntity);

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return DeleteBoardResponseDto.success();
    }
}
