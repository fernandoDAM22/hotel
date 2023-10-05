package com.hotel.hotel.repository;

import com.hotel.hotel.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByRoomId(Long roomId);
    List<Comment> findByUserId(Long userId);

}
