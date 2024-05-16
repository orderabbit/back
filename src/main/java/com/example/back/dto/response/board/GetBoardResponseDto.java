package com.example.back.dto.response.board;

import com.example.back.common.ResponseCode;
import com.example.back.common.ResponseMessage;
import com.example.back.dto.response.ResponseDto;
import com.example.back.entity.ImageEntity;
import com.example.back.repository.resultSet.GetBoardResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetBoardResponseDto extends ResponseDto {

    private int itemNumber;
    private String title;
    private String content;
    private String videoUrl;
    private List<String> boardImageList;
    private String writeDatetime;
    private String writerId;
    private String writerNickname;
    private String writerProfileImage;

    public GetBoardResponseDto(GetBoardResultSet resultSet, List<ImageEntity> imageEntities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);

        List<String> boardImageList = new ArrayList<>();
        for(ImageEntity imageEntity: imageEntities){
            String boardImage =  imageEntity.getImage();
            boardImageList.add(boardImage);
        }

        this.itemNumber = resultSet.getItemNumber();
        this.title = resultSet.getTitle();
        this.content = resultSet.getContent();
        this.videoUrl = resultSet.getVideoUrl();
        this.boardImageList = boardImageList;
        this.writeDatetime = resultSet.getWriteDatetime();
        this.writerId = resultSet.getWriterId();
        this.writerNickname = resultSet.getWriterNickname();
        this.writerProfileImage = resultSet.getWriterProfileImage();
    }

    public static ResponseEntity<GetBoardResponseDto> success(GetBoardResultSet resultSet, List<ImageEntity> imageEntities){
        GetBoardResponseDto responseBody = new GetBoardResponseDto(resultSet, imageEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> notExistBoard(){
        ResponseDto responseBody = new ResponseDto(ResponseCode.NOT_EXISTED_BOARD, ResponseMessage.NOT_EXISTED_BOARD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
