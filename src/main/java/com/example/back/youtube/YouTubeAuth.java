package com.example.back.youtube;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class YouTubeAuth {

    private static final String APPLICATION_NAME = "My YouTube API Application";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Arrays.asList(YouTubeScopes.YOUTUBE_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    public static YouTube getYouTubeService() throws GeneralSecurityException, IOException {
        InputStream in = YouTubeAuth.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new IOException("Credentials file not found: " + CREDENTIALS_FILE_PATH);
        }

        GoogleCredential credential = GoogleCredential.fromStream(in)
                .createScoped(SCOPES);

        return new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
