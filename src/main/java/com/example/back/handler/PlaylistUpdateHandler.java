package com.example.back.handler;

import com.example.back.dto.response.music.GetMusicListResponseDto;
import com.example.back.service.MusicService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PlaylistUpdateHandler implements WebSocketHandler {

    private final MusicService musicService;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Autowired
    public PlaylistUpdateHandler(MusicService musicService) {
        this.musicService = musicService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("연결 성공: " + session.getId());
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String receivedMessage = (String) message.getPayload();

        if ("updatePlaylist".equals(receivedMessage)) {
            ResponseEntity<? super GetMusicListResponseDto> response = musicService.getPlaylist();
            GetMusicListResponseDto playlistDto = (GetMusicListResponseDto) response.getBody();
            List<String> playlist = playlistDto.getPlaylist();

            ObjectMapper objectMapper = new ObjectMapper();
            String playlistJson = objectMapper.writeValueAsString(playlist);

            for (WebSocketSession clientSession : sessions) {
                clientSession.sendMessage(new TextMessage(playlistJson));
                System.out.println(playlistJson);
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("전송 오류 발생: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("연결 종료: " + session.getId());
        sessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
