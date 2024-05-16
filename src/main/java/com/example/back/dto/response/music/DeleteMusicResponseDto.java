package com.example.back.dto.response.music;

import com.example.back.common.ResponseCode;
import com.example.back.common.ResponseMessage;
import com.example.back.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class DeleteMusicResponseDto extends ResponseDto {

    private DeleteMusicResponseDto(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<DeleteMusicResponseDto> success(){
        DeleteMusicResponseDto result = new DeleteMusicResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> notExistedMusic(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_MUSIC, ResponseMessage.NOT_EXISTED_MUSIC);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
}
