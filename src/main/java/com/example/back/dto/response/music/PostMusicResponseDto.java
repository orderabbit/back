package com.example.back.dto.response.music;

import com.example.back.common.ResponseCode;
import com.example.back.common.ResponseMessage;
import com.example.back.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostMusicResponseDto extends ResponseDto {

    private PostMusicResponseDto(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<PostMusicResponseDto> success(){
        PostMusicResponseDto responseBody = new PostMusicResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> notExistMusic(){
        ResponseDto responseBody = new ResponseDto(ResponseCode.NOT_EXISTED_MUSIC, ResponseMessage.NOT_EXISTED_MUSIC);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
