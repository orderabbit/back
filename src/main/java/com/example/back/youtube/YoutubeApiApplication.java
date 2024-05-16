//package com.example.back.youtube;
//
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.youtube.YouTube;
//import com.google.api.services.youtube.model.Video;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.List;
//
//@SpringBootApplication
//public class YoutubeApiApplication {
//
//}
//
//@RestController
//class YoutubeApiController {
//
//    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//    private static final String API_KEY = "AIzaSyBRCweLseGcLizadDsECnpLhBRA2cG8PaM";
//
//    @GetMapping("/api/youtube/top10music")
//    public List<Video> getTop10MusicVideos() throws GeneralSecurityException, IOException {
//        YouTube youtube = new YouTube.Builder(
//                GoogleNetHttpTransport.newTrustedTransport(),
//                JSON_FACTORY,
//                null)
//                .setApplicationName("YoutubeAPI")
//                .build();
//
//        YouTube.Videos.List request = youtube.videos().list();
//        request.setKey(API_KEY);
//        request.setChart("mostPopular");
//        request.setMaxResults(10L);
//        List<Video> videos = request.execute().getItems();
//
//        return videos;
//    }
//}
