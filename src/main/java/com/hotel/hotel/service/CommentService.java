package com.hotel.hotel.service;

import com.hotel.hotel.dto.CommentDTO;
import com.hotel.hotel.dto.InsertCommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO insert(InsertCommentDTO dto, Long id);

    List<CommentDTO> getAllComments();

    CommentDTO findById(Long id, Long userId);

    List<CommentDTO> findByRoom(Long id);

    List<CommentDTO> findByUser(Long id);

    void delete(Long id, Long userId);
}
