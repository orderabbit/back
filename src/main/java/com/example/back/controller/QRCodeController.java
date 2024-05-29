package com.example.back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/qr")
public class QRCodeController {

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateQRCode() {
        try {
            // QR 코드 생성 로직 구현
            String text = "https://example.com/login"; // QR 코드로 포함될 텍스트 (로그인 페이지 URL 등)
            ByteArrayOutputStream out = QRCode.from(text).withSize(250, 250).stream(); // QR 코드 생성

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

