//package com.example.back.youtube;
//
//import com.google.api.services.youtube.YouTube;
//import com.google.api.services.youtube.model.SearchListResponse;
//import com.google.api.services.youtube.model.SearchResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//public class Top10Controller {
//
//    private final YouTube youtube;
//
//    public Top10Controller() throws GeneralSecurityException, IOException {
//        // YouTube API 인스턴스 생성
//        this.youtube = YouTubeAuth.getYouTubeService();
//    }
//
//    @GetMapping("/top10")
//    public List<String> getTop10() throws IOException {
//        List<String> top10 = new ArrayList<>();
//        YouTube.Search.List searchList = youtube.search().list("snippet");
//        searchList.setKey("AIzaSyBRCweLseGcLizadDsECnpLhBRA2cG8PaM");
//        searchList.setType("video");
//        searchList.setQ("Music");
//        searchList.setMaxResults(10L);
//        SearchListResponse response = searchList.execute();
//        List<SearchResult> items = response.getItems();
//        for (SearchResult item : items) {
//            top10.add(item.getSnippet().getTitle());
//        }
//        return top10;
//    }
//}
