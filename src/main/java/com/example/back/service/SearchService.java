package com.example.back.service;

import com.example.back.dto.response.search.GetPopularListResponseDto;
import com.example.back.dto.response.search.GetRelationListResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface SearchService {

    ResponseEntity<? super GetPopularListResponseDto> getPopularList();
    ResponseEntity<? super GetRelationListResponseDto> getRelationList(String searchWord);
}
