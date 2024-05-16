package com.example.back.repository;

import com.example.back.entity.MusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, Long> {

    @Query(
            value =
                    "SELECT m.video_url FROM MusicEntity m ",
            nativeQuery = true
    )
    List<String> findAllUrls();
}
