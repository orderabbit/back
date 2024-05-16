package com.example.back.dto.response.auth;

import com.example.back.common.ResponseCode;
import com.example.back.common.ResponseMessage;
import com.example.back.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class NicknameCheckResponseDto extends ResponseDto {

    private NicknameCheckResponseDto(){
        super();
    }

    public static ResponseEntity<NicknameCheckResponseDto> success(){
        NicknameCheckResponseDto responseBody = new NicknameCheckResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> duplicateNickname(){
        ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATED_NICKNAME, ResponseMessage.DUPLICATED_NICKNAME);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
