package com.example.back.repository;

import com.example.back.entity.ImageEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    List<ImageEntity> findByItemNumber(Integer itemNumber);

    @Transactional
    void deleteByItemNumber(Integer itemNumber);

}
