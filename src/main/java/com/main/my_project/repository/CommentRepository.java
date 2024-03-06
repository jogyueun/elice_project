package com.main.my_project.repository;

import com.main.my_project.dto.CommentDto;
import com.main.my_project.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId ORDER BY c.CommentUpdate ASC")
    List<Comment> findAllByPostIdOrderByCommentUpdateAsc(@Param("postId") Long postId);

    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId ORDER BY c.CommentUpdate DESC")
    List<Comment> findAllByPostIdOrderByCommentUpdateDesc(@Param("postId") Long postId);

    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId")
    List<Comment> findAllByPostId(@Param("postId") Long postId);
}
