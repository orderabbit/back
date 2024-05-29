package com.example.back.dto.response.auth;

import com.example.back.common.ResponseCode;
import com.example.back.common.ResponseMessage;
import com.example.back.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class QRSignInResponseDto extends ResponseDto {

    private String token;

    private QRSignInResponseDto(String token){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.token = token;
    }

    public static ResponseEntity<QRSignInResponseDto> success(String token){
        QRSignInResponseDto responseBody = new QRSignInResponseDto(token);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> signInFail(){
        ResponseDto responseBody = new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
}
