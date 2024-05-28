package com.example.back.repository;

import com.example.back.entity.CommentEntity;
import com.example.back.repository.resultSet.GetCommentListResultSet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    @Query(
            value=
                    "SELECT " +
                            "U.nickname AS nickname, " +
                            "U.profile_image AS profileImage, " +
                            "C.write_datetime AS writeDatetime, " +
                            "C.content AS content " +
                            "FROM comment AS C " +
                            "INNER JOIN user AS U ON C.user_id = U.user_id " +
                            "WHERE C.item_number = ?1 " +
                            "ORDER BY writeDatetime DESC",
            nativeQuery = true
    )
    List<GetCommentListResultSet> getCommentList(Integer itemNumber);

    @Transactional
    void deleteByItemNumber(Integer itemNumber);

    @Transactional
    void deleteByUserId(String userId);
}
