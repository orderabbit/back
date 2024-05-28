package com.example.back.repository;

import com.example.back.entity.BoardEntity;
import com.example.back.repository.resultSet.GetBoardResultSet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {


    boolean existsByItemNumber(Integer itemNumber);
    BoardEntity findByItemNumber(Integer itemNumber);
    @Transactional
    void deleteByWriterId(String userId);

    @Query(
            value=
                    "SELECT " +
                            "B.item_number AS itemNumber, " +
                            "B.title AS title, " +
                            "B.content AS content, " +
                            "B.video_url AS videoUrl, " +
                            "B.write_datetime AS writeDatetime, " +
                            "B.writer_id AS writerId, " +
                            "U.nickname AS writerNickname, " +
                            "U.profile_image AS writerProfileImage " +
                            "FROM board_list AS B " +
                            "INNER JOIN user AS U " +
                            "ON B.writer_id = U.user_id " +
                            "WHERE item_number = ?1 ",
                    nativeQuery = true
    )
    GetBoardResultSet getBoard(Integer itemNumber);
}
