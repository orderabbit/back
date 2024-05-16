package com.example.back.dto.response.board;

import com.example.back.common.ResponseCode;
import com.example.back.common.ResponseMessage;
import com.example.back.dto.object.BoardListItem;
import com.example.back.dto.response.ResponseDto;
import com.example.back.entity.BoardListViewEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetTop3BoardListResponseDto extends ResponseDto {
    private List<BoardListItem> top3List;

    private GetTop3BoardListResponseDto(List<BoardListViewEntity> boardListViewEntities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.top3List = BoardListItem.getList(boardListViewEntities);

    }

    public static ResponseEntity<? super GetTop3BoardListResponseDto> success (List<BoardListViewEntity> boardListViewEntities){
        GetTop3BoardListResponseDto responseBody = new GetTop3BoardListResponseDto(boardListViewEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
