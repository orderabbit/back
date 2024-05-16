package com.example.back.repository;

import com.example.back.entity.FavoriteEntity;
import com.example.back.entity.primaryKey.FavoritePk;
import com.example.back.repository.resultSet.GetFavoriteListResultSet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, FavoritePk> {

    FavoriteEntity findByItemNumberAndUserId(Integer itemNumber, String userId);

    @Transactional
    void deleteByItemNumber(Integer itemNumber);

    @Query(
            value=
                    "SELECT " +
                            "U.user_id AS userId, " +
                            "U.nickname AS nickname, " +
                            "U.profile_image AS profileImage " +
                            "FROM favorite AS F " +
                            "INNER JOIN user AS U ON F.user_id = U.user_id " +
                            "WHERE F.item_number = ?1 ",
            nativeQuery = true
    )
    List<GetFavoriteListResultSet> getFavoriteList(Integer itemNumber);
}
